package cc.chinagps.seat.auth;

public class NoSuchUserException extends RuntimeException {
	private static final long serialVersionUID = -381862261253565400L;
	public NoSuchUserException(String msg) {
		super(msg);
	}
	
	public NoSuchUserException(Throwable cause) {
		super(cause);
	}
}
