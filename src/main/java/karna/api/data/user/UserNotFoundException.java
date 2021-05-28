package karna.api.data.user;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2885995745329901300L;

	UserNotFoundException(Long id) {
		super("Could not find user by id " + id);
	}

	UserNotFoundException(String name) {
		super("Could not find user by name " + name);
	}
}