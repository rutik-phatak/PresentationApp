package com.ty.presentationApp.exception;

public class UserNotAnAdminException extends RuntimeException {
	private String message;

	public UserNotAnAdminException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
