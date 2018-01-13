package org.mjjaen.microservices.currencyconversionservice.common.exceptions;

public class LimitsExceededException extends RuntimeException {
	private static final long serialVersionUID = 7711307346434879349L;

	public LimitsExceededException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public LimitsExceededException(String arg0) {
		super(arg0);
	}
}
