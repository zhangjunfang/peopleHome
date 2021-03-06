package com.ocean.session.exception;

public class SerializeException extends RuntimeException {

	private static final long serialVersionUID = 8628042609312722546L;

	public SerializeException() {}

    public SerializeException(String s) {
        super(s);
    }

    public SerializeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SerializeException(Throwable throwable) {
        super(throwable);
    }
}
