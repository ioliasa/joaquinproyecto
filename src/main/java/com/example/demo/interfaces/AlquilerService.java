package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.model.Alquiler;


public interface AlquilerService {
	public Long add(Alquiler e);
	public List<Alquiler>findAll();
	public Alquiler findById(Long id);
	public Alquiler edit(Alquiler e);
	public void borrar(Alquiler e);

}
