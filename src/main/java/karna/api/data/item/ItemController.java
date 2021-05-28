package karna.api.data.item;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import karna.api.data.order.Order;
import karna.api.data.order.OrderRepository;
import karna.api.data.order.OrderStatus;
import karna.api.data.order.OrderModelAssembler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/items")
class ItemController {

	@Autowired
	ItemModelAssembler itemAssembler;

	@Autowired
	OrderModelAssembler orderAssembler;

	@Autowired
	ItemRepository<Item> itemRepository;

	@Autowired
	OrderRepository<Order> orderRepository;

	// Aggregate root

	@GetMapping("/all")
	public ResponseEntity<CollectionModel<EntityModel<Item>>> all() {

		List<EntityModel<Item>> items = itemRepository.findAll().stream() //
				.map(itemAssembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(items, linkTo(methodOn(ItemController.class).all()).withSelfRel()));
	}

	@GetMapping("/recent={count}")
	public ResponseEntity<CollectionModel<EntityModel<Item>>> recentCount(@PathVariable Integer count) {

		List<EntityModel<Item>> items = itemRepository.findAllRecentN(PageRequest.of(0, count)).stream() //
				.map(itemAssembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(items, linkTo(methodOn(ItemController.class).recentCount(count)).withSelfRel()));
	}

	@GetMapping("/trending={count}")
	public ResponseEntity<CollectionModel<EntityModel<Item>>> trendingCount(@PathVariable Integer count) {

		List<EntityModel<Item>> items = itemRepository.findAllTrendingN(PageRequest.of(0, count)).stream() //
				.map(itemAssembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				CollectionModel.of(items, linkTo(methodOn(ItemController.class).trendingCount(count)).withSelfRel()));

	}

	@GetMapping("/category={cat}")
	public ResponseEntity<CollectionModel<EntityModel<Item>>> allCategory(@PathVariable String cat) {

		List<EntityModel<Item>> items = itemRepository.findAllByItemCategoryAndItemStatus(cat, ItemStatus.LISTED)
				.stream() //
				.map(itemAssembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(items, linkTo(methodOn(ItemController.class).allCategory(cat)).withSelfRel()));

	}

	@GetMapping("/trending")
	public ResponseEntity<CollectionModel<EntityModel<Item>>> findAllTrending() {

		List<EntityModel<Item>> items = itemRepository.findAllTrending().stream() //
				.map(itemAssembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(items, linkTo(methodOn(ItemController.class).findAllTrending()).withSelfRel()));
	}

	@GetMapping("/me")
	public ResponseEntity<CollectionModel<EntityModel<Item>>> currentUserItems(
			@AuthenticationPrincipal UserDetails userDetails) {

		List<EntityModel<Item>> items = itemRepository.findAllByOwner(userDetails.getUsername()).stream() //
				.map(itemAssembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(items,
				linkTo(methodOn(ItemController.class).currentUserItems(userDetails)).withSelfRel()));
	}

	@GetMapping("/me={status}")
	public ResponseEntity<CollectionModel<EntityModel<Item>>> currentUserItemsStatus(
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable String status) {

		ItemStatus itemStatus;
		switch (status) {
		case "LISTED":
			itemStatus = ItemStatus.LISTED;
			break;
		case "DELISTED":
			itemStatus = ItemStatus.DELISTED;
			break;
		default:
			itemStatus = ItemStatus.SOLD;
			break;
		}

		List<EntityModel<Item>> items = itemRepository
				.findAllByOwnerAndItemStatus(userDetails.getUsername(), itemStatus).stream() //
				.map(itemAssembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(items,
				linkTo(methodOn(ItemController.class).currentUserItemsStatus(userDetails, status)).withSelfRel()));
	}

	// Single item

	@GetMapping("/id={id}")
	public ResponseEntity<EntityModel<Item>> oneId(@PathVariable Long id) {

		Item item = itemRepository.findById(id) //
				.orElseThrow(() -> new ItemNotFoundException(id));

		return ResponseEntity.ok(itemAssembler.toModel(item));
	}

	@PostMapping("/id={id}/order")
	public ResponseEntity<EntityModel<Order>> orderItem(@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long id) throws ItemNotForSaleException {

		boolean isItemForSale = false;

		Item item = itemRepository.findById(id).orElseThrow(()//
		-> new ItemNotFoundException(id));

		Order newOrder = null;

		if (item.getOwner().contentEquals(userDetails.getUsername())) {

			throw new ItemBuyOwnerMatchException(userDetails.getUsername());

		} else {
			try {

				newOrder = orderRepository.findByItemId(id).orElseThrow(() -> new ItemNotFoundException(id));

			} catch (ItemNotFoundException e) {

				isItemForSale = true;

			} finally {
				if (isItemForSale) {
					newOrder = new Order();
					newOrder.setOrderBuyer(userDetails.getUsername());
					newOrder.setItemId(item.getId());
					newOrder.setOrderSeller(item.getOwner());
					newOrder.setOrderItem(item.getItemName());
					newOrder.setOrderPrice(item.getItemPrice());
					newOrder.setOrderDate(new Date());
					newOrder.setOrderStatus(OrderStatus.AWATING_PAYMENT);
					orderRepository.save(newOrder);

					item.setItemStatus(ItemStatus.DELISTED);

					itemRepository.save(item);
				}
			}

			if (isItemForSale)
				return ResponseEntity.ok(orderAssembler.toModel(newOrder));

			throw new ItemNotForSaleException(id);

		}
	}

	@PostMapping("add")
	public ResponseEntity<EntityModel<Item>> newItem(@AuthenticationPrincipal UserDetails userDetails,
			@RequestBody ItemDTO newItem) {

		Item item = new Item();

		item.setItemName(newItem.getItemName());
		item.setItemCategory(newItem.getItemCategory());
		item.setItemPrice(newItem.getItemPrice());

		item.setItemStatus(ItemStatus.LISTED);

		item.setOwner(userDetails.getUsername());
		item.setItemCreatedDate(new Date());

		item.setItemTrend(0);

		itemRepository.save(item);

		return ResponseEntity.ok(itemAssembler.toModel(item));
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ItemDTO implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 475136732557779547L;

		private String itemName;
		private List<String> itemCategory;
		private int itemPrice;
	}

}