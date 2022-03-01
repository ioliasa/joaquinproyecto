package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.model.LineaAlquiler;


public interface LineaAlquilerService {
	public LineaAlquiler add(LineaAlquiler e);
	public List<LineaAlquiler>findAll();
	public LineaAlquiler findById(Long id);
	public LineaAlquiler edit(LineaAlquiler e);
	public void borrar(LineaAlquiler e);

}
