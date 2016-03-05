package com.anyang.xingqingshu.ocean.core.anyang.commom;

public class XmlException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5843161343706357004L;

	public XmlException() {
        super();
    }

    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlException(Throwable cause) {
        super(cause);
    }

    protected XmlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}