package com.anyang.xingqingshu.ocean.core.anyang.commom;


public class HttpsException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5715932050052547812L;

	public HttpsException() {
        super();
    }

    public HttpsException(String message) {
        super(message);
    }

    public HttpsException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpsException(Throwable cause) {
        super(cause);
    }

    protected HttpsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}