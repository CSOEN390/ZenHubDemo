package ca.concordia.encs.conquerdia.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation exception
 */
public class ValidationException extends Exception {

	/**
	 * Errors of the validation
	 */
	private final List<String> validationErrors = new ArrayList<>();

	/**
	 * The constructor for ValidateException Class
	 * 
	 * @param message the string that shows the Validation error.
	 */
	public ValidationException(String message) {
		super(message);
		validationErrors.add("Validation Error! " + message);
	}

	/**
	 * Adds a new validation Error to the String list of Validation Errors
	 * 
	 * @param error the string that shows the Validation error.
	 */
	public void addValidationError(String error) {
		validationErrors.add("Validation Error! " + error);
	}

	/**
	 * Gets the validation Errors
	 * 
	 * @return returns the current String List of Validation Errors
	 */
	public List<String> getValidationErrors() {
		return validationErrors;
	}
}
