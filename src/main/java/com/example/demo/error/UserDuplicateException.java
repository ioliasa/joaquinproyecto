package com.example.demo.error;

public class UserDuplicateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6571817453663089357L;
	
	public UserDuplicateException(String email) {
		super("No se puedo registrar poque : "+email+" ya se encuentra registrado");
	}

}
