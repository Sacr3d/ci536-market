package karna.api.data.item;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {

	@Override
	public EntityModel<Item> toModel(Item item) {

		return EntityModel.of(item, //
				linkTo(methodOn(ItemController.class).oneId(item.getId())) //
						.withRel("useritem"));
	}

}
