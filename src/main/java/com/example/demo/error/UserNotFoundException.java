package com.example.demo.error;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2518408353976859288L;
	
	
	public UserNotFoundException(Long id) {
		super("No existe el usuario con ID: "+id);
	}

}
