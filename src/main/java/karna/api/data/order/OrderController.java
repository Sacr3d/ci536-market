package karna.api.data.order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import karna.api.data.item.Item;
import karna.api.data.item.ItemBuyOwnerMatchException;
import karna.api.data.item.ItemNotFoundException;
import karna.api.data.item.ItemRepository;
import karna.api.data.item.ItemStatus;

@RestController
@RequestMapping("/orders")
class OrderController {

	@Autowired
	OrderModelAssembler assembler;

	@Autowired
	ItemRepository<Item> itemRepository;

	@Autowired
	OrderRepository<Order> orderRepository;

	// Aggregate root

	@GetMapping("/id")
	public ResponseEntity<CollectionModel<EntityModel<Order>>> all() {

		List<EntityModel<Order>> orders = orderRepository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel()));
	}

	@GetMapping("/me/buying")
	public ResponseEntity<CollectionModel<EntityModel<Order>>> currentUserBuying(
			@AuthenticationPrincipal UserDetails userDetails) {

		List<EntityModel<Order>> orders = orderRepository.findAllByOrderBuyer(userDetails.getUsername()).stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(orders,
				linkTo(methodOn(OrderController.class).currentUserBuying(userDetails)).withSelfRel()));
	}

	@GetMapping("/me/selling")
	public ResponseEntity<CollectionModel<EntityModel<Order>>> currentUserSelling(
			@AuthenticationPrincipal UserDetails userDetails) {

		List<EntityModel<Order>> orders = orderRepository.findAllByOrderSeller(userDetails.getUsername()).stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(orders,
				linkTo(methodOn(OrderController.class).currentUserSelling(userDetails)).withSelfRel()));
	}

	// Single item

	@GetMapping("/id={id}")
	public ResponseEntity<EntityModel<Order>> oneId(@PathVariable Long id) {

		Order order = orderRepository.findById(id) //
				.orElseThrow(() -> new OrderNotFoundException(id));

		return ResponseEntity.ok(assembler.toModel(order));
	}

	// Single item

	@GetMapping("/orderStatusOf={id}")
	public ResponseEntity<EntityModel<Order>> oneOrderStatusByItemId(@PathVariable Long id) {

		Order order = orderRepository.findByItemId(id) //
				.orElseThrow(() -> new OrderNotFoundException(id));

		Order orderWithOnlyOrderStatus = new Order();
		orderWithOnlyOrderStatus.setOrderStatus(order.getOrderStatus());

		return ResponseEntity.ok(assembler.toModel(orderWithOnlyOrderStatus));
	}

	@PostMapping("/id={id}/{status}")
	public ResponseEntity<EntityModel<Order>> updateItem(@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long id, @PathVariable String status) {

		Order order = orderRepository.findById(id).orElseThrow(()//
		-> new OrderNotFoundException(id));

		Order newOrder = order;

		if (order.getOrderBuyer().contentEquals(userDetails.getUsername())) {

			switch (status) {
			case "pay":
				order.setOrderStatus(OrderStatus.PAID);
				break;
			case "recive":
				order.setOrderStatus(OrderStatus.COMPLETED);
				updateItemStatus(order.getItemId());
				break;
			case "refundReq":
				order.setOrderStatus(OrderStatus.REFUND_REQUESTED);
				break;
			default:
				throw new OrderParameterException(status);
			}

		} else if (!order.getOrderBuyer().contentEquals(userDetails.getUsername())) {

			switch (status) {
			case "ship":
				order.setOrderStatus(OrderStatus.SHIPPED);
				break;
			case "refund":
				order.setOrderStatus(OrderStatus.REFUNDED);
				break;
			default:
				throw new OrderParameterException(status);
			}

		} else {

			throw new ItemBuyOwnerMatchException(userDetails.getUsername());

		}

		order = orderRepository.save(newOrder);

		return ResponseEntity.ok(assembler.toModel(order));
	}

	private void updateItemStatus(Long itemId) {

		Item item = itemRepository.getOne(itemId);

		Item newItem = item;

		newItem.setItemStatus(ItemStatus.SOLD);
		newItem.setItemCreatedDate(new Date());

		itemRepository.save(newItem);

	}

}