package cc.chinagps.gateway.exceptions;

public class WrongFormatException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WrongFormatException() {
		super();
	}

	public WrongFormatException(String message) {
		super(message);
	}

	public WrongFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongFormatException(Throwable cause) {
		super(cause);
	}
}