package com.example.demo.model;


public class CocheDTO {
	private String marca;
	private String modelo;
	private String motor;
	private String year;
	private byte[] imagen;
	private Double precioFijo;
	private boolean status;
	
	public CocheDTO(String marca, String modelo,String motor, String year, Double precioFijo) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.year = year;
		this.motor=motor;
		this.precioFijo = precioFijo;
		this.status = true;
	}
	
	

	public byte[] getImagen() {
		return imagen;
	}



	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}



	public String getMotor() {
		return motor;
	}



	public void setMotor(String motor) {
		this.motor = motor;
	}



	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


	public Double getPrecioFijo() {
		return precioFijo;
	}

	public void setPrecioFijo(Double precioFijo) {
		this.precioFijo = precioFijo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
