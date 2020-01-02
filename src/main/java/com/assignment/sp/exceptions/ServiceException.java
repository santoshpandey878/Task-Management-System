package com.assignment.sp.exceptions;

/**
 * Class to handle Service layer exceptions
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = -7687673545340323332L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}


}
