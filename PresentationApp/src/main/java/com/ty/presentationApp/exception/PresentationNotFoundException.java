package com.ty.presentationApp.exception;

public class PresentationNotFoundException extends RuntimeException {
	private String message;

	public PresentationNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
