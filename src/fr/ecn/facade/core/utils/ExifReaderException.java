package fr.ecn.facade.core.utils;

public class ExifReaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExifReaderException() {
		super();
	}

	public ExifReaderException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ExifReaderException(String detailMessage) {
		super(detailMessage);
	}

	public ExifReaderException(Throwable throwable) {
		super(throwable);
	}

}
