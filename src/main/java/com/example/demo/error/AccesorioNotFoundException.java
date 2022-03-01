package com.example.demo.error;

public class AccesorioNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1056783082749690584L;
	
	public AccesorioNotFoundException(Long id) {
		super("No se puede encontrar el accesorio con ID: "+id);
	}

}
