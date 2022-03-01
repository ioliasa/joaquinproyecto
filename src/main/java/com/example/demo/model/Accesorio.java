package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Accesorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private double precio;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean checked;
	
	
	public Accesorio() {
	}

	public Accesorio(Long id) {
		super();
		this.id = id;
		this.checked= false;
	}

	public Accesorio(String nombre, double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.checked= false;
	}
	
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Accesorio [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", checked=" + checked + "]";
	}

	
	
	
	
	

}
