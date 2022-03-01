package com.example.demo.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class LineaAlquiler {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch= FetchType.EAGER)
	private Accesorio accesorio;
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JsonBackReference
	private Alquiler alquiler;
	
	private Date fecha;
	
	
	public LineaAlquiler() {
		
	}


	public LineaAlquiler(Accesorio accesorio, Alquiler alquiler) {
		super();
		this.accesorio = accesorio;
		this.alquiler = alquiler;
		this.fecha= new Date();
	}
	
	public LineaAlquiler(Accesorio accesorio, Alquiler alquiler, Date fecha) {
		super();
		this.accesorio = accesorio;
		this.alquiler = alquiler;
		this.fecha = fecha;
	}
	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Accesorio getAccesorio() {
		return accesorio;
	}


	


	public void setAccesorio(Accesorio accesorio) {
		this.accesorio = accesorio;
	}


	public Alquiler getAlquiler() {
		return alquiler;
	}


	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
