package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Alquiler {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double precio;
	private LocalDateTime fecha;
	private LocalDateTime fechaEntrega;
	private int numDias;
	
	@ManyToOne(fetch= FetchType.EAGER)
	private Coche coche;
	
	@OneToMany(cascade = CascadeType.ALL,fetch= FetchType.EAGER, orphanRemoval = true)
	private List<LineaAlquiler>listaLinea = new ArrayList<LineaAlquiler>();
	
	
	
	
	public Alquiler(Long id) {
		super();
		this.id = id;
	}


	public Alquiler() {
		this.fecha= LocalDateTime.now();
	}
	
	
	public Alquiler(int numDias, Coche coche, List<LineaAlquiler> accesorios) {
		super();
		this.fecha = LocalDateTime.now();
		this.numDias = numDias;
		this.coche = coche;
		this.precio = calcularPrecio();
		this.listaLinea = accesorios;
	}


	
	
	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}


	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	/**
	 * Calcula el precio del alquiler sumando el precio fijo del coche asociado así como todas sus lineas de alquiler
	 * @return precio total del aquiler
	 */
	private double calcularPrecio() {
		double total = this.numDias*this.coche.getPrecioFijo();
		
		for(LineaAlquiler accesorio : this.listaLinea) {
			total += accesorio.getAccesorio().getPrecio();
		}
		
		return total;
	}
	
	public void addLineaAlquiler(LineaAlquiler a) {
		this.listaLinea.add(a);
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	

	public LocalDateTime getFecha() {
		return fecha;
	}


	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}


	public List<LineaAlquiler> getListaLinea() {
		return listaLinea;
	}


	public void setListaLinea(List<LineaAlquiler> listaLinea) {
		this.listaLinea = listaLinea;
	}


	public Coche getCoche() {
		return coche;
	}

	public void setCoche(Coche coche) {
		this.coche = coche;
	}

	

	public int getNumDias() {
		return numDias;
	}


	public void setNumDias(int numDias) {
		this.numDias = numDias;
	}




	public void setAccesorios(List<LineaAlquiler> accesorios) {
		this.listaLinea = accesorios;
	}

	
	
}
