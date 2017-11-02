package com.github.mikewtao.webf.exception;

public class FindControllerException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FindControllerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public FindControllerException(String message) {
		super(message);
	}
	
	
  
}
