package com.ty.presentationApp.exception;

public class UserNotFoundException extends RuntimeException{
	private String message;

	public UserNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
