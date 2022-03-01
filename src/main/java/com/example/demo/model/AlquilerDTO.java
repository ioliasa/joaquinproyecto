package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlquilerDTO {
	private Long idUser;
	private Long coche;
	private int numDias;
	private LocalDateTime fecha;
	private List<Accesorio>accesorios = new ArrayList<Accesorio>();
	
	

	public AlquilerDTO(Long idUser, Long coche, int numDias,LocalDateTime fecha, List<Accesorio> accesorios) {
		super();
		this.idUser = idUser;
		this.coche = coche;
		this.numDias = numDias;
		this.fecha=fecha;
		this.accesorios = accesorios;
	}
	
	
	

	public LocalDateTime getFecha() {
		return fecha;
	}




	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}




	public List<Accesorio> getAccesorios() {
		return accesorios;
	}



	public void setAccesorios(List<Accesorio> accesorios) {
		this.accesorios = accesorios;
	}


	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}


	

	public Long getCoche() {
		return coche;
	}



	public void setCoche(Long coche) {
		this.coche = coche;
	}



	public int getNumDias() {
		return numDias;
	}

	public void setNumDias(int numDias) {
		this.numDias = numDias;
	}
}
