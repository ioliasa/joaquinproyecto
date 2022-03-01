package com.example.demo.error;

public class AlquilerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1504177819028914576L;
	
	public AlquilerNotFoundException(Long id) {
		super("No se puede encontrar el alquiler con ID: "+id);
	}

}
