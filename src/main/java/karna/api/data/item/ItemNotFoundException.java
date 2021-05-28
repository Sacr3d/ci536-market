package karna.api.data.item;

public class ItemNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2885995745329901300L;

	ItemNotFoundException(Long id) {
		super("Could not find item by id " + id);
	}

	ItemNotFoundException(String name) {
		super("Could not find item by name " + name);
	}
}