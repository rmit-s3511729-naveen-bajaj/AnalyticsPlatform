package com.bda.analyticsplatform.utils;

public class BDAException extends Exception {

	private static final long serialVersionUID = 8013430402001785821L;

	private String errorMessage;

	public BDAException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

}
