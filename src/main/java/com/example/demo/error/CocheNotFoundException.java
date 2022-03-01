package com.example.demo.error;

public class CocheNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6734027569391630482L;
	
	public CocheNotFoundException(Long id) {
		super("No se puede encontrar el coche con la ID: " + id);
	}

}
