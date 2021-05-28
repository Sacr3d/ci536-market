package karna.api.data.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2885995745329901300L;

	OrderNotFoundException(Long id) {
		super("Could not find order by id " + id);
	}

	OrderNotFoundException(String name) {
		super("Could not find order by name " + name);
	}
}