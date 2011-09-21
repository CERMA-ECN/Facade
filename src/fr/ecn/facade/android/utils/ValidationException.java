package fr.ecn.facade.android.utils;

/**
 * An exception thrown when the validation of a input field failed
 * 
 * @author jerome
 *
 */
public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException() {
	}

	public ValidationException(String detailMessage) {
		super(detailMessage);
	}

	public ValidationException(Throwable throwable) {
		super(throwable);
	}

	public ValidationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
