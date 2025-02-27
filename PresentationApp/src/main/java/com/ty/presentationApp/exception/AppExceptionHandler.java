package com.ty.presentationApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> catchUserNotFoundException(UserNotFoundException message){
		return new ResponseEntity<String>(message.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotAnAdminException.class)
	public ResponseEntity<String> catchUserNotAnAdminException(UserNotAnAdminException message){
		return new ResponseEntity<String>(message.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PresentationNotFoundException.class)
	public ResponseEntity<String> catchPresentationNotFoundException(PresentationNotFoundException message){
		return new ResponseEntity<String>(message.getMessage(),HttpStatus.NOT_FOUND);
	}
}