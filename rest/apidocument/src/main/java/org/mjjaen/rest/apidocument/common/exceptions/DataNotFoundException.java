package org.mjjaen.rest.apidocument.common.exceptions;

public class DataNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7711307346434879349L;

	public DataNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DataNotFoundException(String arg0) {
		super(arg0);
	}
}
