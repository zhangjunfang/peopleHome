package com.ocean.session.exception;

public class SessionException extends RuntimeException{

	private static final long serialVersionUID = -6246693944712560722L;

	public SessionException() {}

    public SessionException(String message) {
        super(message);
    }

    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionException(Throwable cause) {
        super(cause);
    }
}
