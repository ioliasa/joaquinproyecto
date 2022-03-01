package com.example.demo.error;

public class InvalidCredentialException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4754135622071388903L;
	
	public InvalidCredentialException() {
		super("Los datos introducidos no son correctos");
	}

}
