package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.model.Coche;



public interface CochesService {
	public Long add(Coche e);
	public List<Coche>findAll();
	public Coche findById(Long id);
	public Coche edit(Coche e);
	public void borrar(Coche e);
	public List<Coche> findAllCochesDisponibles();
	public List<Coche> findCochesByMarca(String termino);

}
