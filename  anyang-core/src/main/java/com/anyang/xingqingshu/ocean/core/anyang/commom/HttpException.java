package com.anyang.xingqingshu.ocean.core.anyang.commom;


public class HttpException extends RuntimeException {
 
	private static final long serialVersionUID = -3889839998682237256L;

	public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    protected HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
