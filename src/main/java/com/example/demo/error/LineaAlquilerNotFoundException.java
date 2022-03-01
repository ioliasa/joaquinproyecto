package com.example.demo.error;

public class LineaAlquilerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895351437127501135L;
	
	public LineaAlquilerNotFoundException(Long id) {
		super("No se puede encontrar la linea de alquiler con ID: "+id);
	}

}
