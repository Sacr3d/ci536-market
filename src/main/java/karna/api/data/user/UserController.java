package karna.api.data.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
class UserController {

	@Autowired
	UserModelAssembler assembler;

	@Autowired
	UserRepository repository;

	// Aggregate root

	@GetMapping("/id")
	public ResponseEntity<CollectionModel<EntityModel<User>>> all() {

		List<EntityModel<User>> users = repository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel()));

	}

	// Single item

	@SuppressWarnings("rawtypes")
	@GetMapping("/me")
	public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {

		User user = repository.findByOwner(userDetails.getUsername()) //
				.orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));

		return ResponseEntity.ok(assembler.toModel(user));
	}

	@GetMapping("/id={id}")
	public ResponseEntity<EntityModel<User>> oneId(@PathVariable Long id) {

		User user = repository.findById(id) //
				.orElseThrow(() -> new UserNotFoundException(id));

		return ResponseEntity.ok(assembler.toModel(user));
	}

	@GetMapping("/owner={owner}")
	public ResponseEntity<EntityModel<User>> oneName(@PathVariable String owner) {

		User user = repository.findByOwner(owner) //
				.orElseThrow(() -> new UserNotFoundException(owner));

		return ResponseEntity.ok(assembler.toModel(user));
	}

}