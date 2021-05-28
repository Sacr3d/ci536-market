package karna.api.data.item;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ItemNotForSaleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4880075334862866645L;

	public ItemNotForSaleException(Long id) {
		super("Item by id " + id + " is no longer for sale");
	}

}
