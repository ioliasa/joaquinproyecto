package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Coche {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String marca;
	private String modelo;
	private String motor;
	private String year;
	private Double precioFijo;
	@Lob	
	private byte[] imagen;
	private boolean status;
	
	public Coche() {}
	
	
	public Coche(Long id) {
		super();
		this.id=id;
	}
	
	public Coche(String marca, String modelo,String motor, String year, double precioFijo, byte[] imagen, boolean status) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.motor=motor;
		this.year = year;
		this.precioFijo = precioFijo;
		this.imagen = imagen;
		this.status=status;
	}
	
	
	
	
	public String getMotor() {
		return motor;
	}


	public void setMotor(String motor) {
		this.motor = motor;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Coche [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", motor=" + motor + ", year=" + year
				+ ", precioFijo=" + precioFijo + ", imagen=" + imagen + ", status=" + status + "]";
	}

	


}
