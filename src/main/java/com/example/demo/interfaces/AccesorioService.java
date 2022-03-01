package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.model.Accesorio;



public interface AccesorioService {
	public Long add(Accesorio e);
	public List<Accesorio>findAll();
	public Accesorio findById(Long id);
	public Accesorio edit(Accesorio e);
	public void borrar(Accesorio e);

}
