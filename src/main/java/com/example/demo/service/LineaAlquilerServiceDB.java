package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.LineaAlquilerService;
import com.example.demo.model.Accesorio;
import com.example.demo.model.LineaAlquiler;
import com.example.demo.repository.LineaAlquilerRepo;

@Service
public class LineaAlquilerServiceDB implements LineaAlquilerService {
	
	@Autowired LineaAlquilerRepo lineaAlquilerRepo;

	/**
	 * Añade una linea de alquiler
	 */
	@Override
	public LineaAlquiler add(LineaAlquiler e) {
		return lineaAlquilerRepo.save(e);
	}

	/**
	 * Devuelve la lista de lineas de alquiler
	 */
	@Override
	public List<LineaAlquiler> findAll() {
		return lineaAlquilerRepo.findAll();
	}

	/**
	 * Devuelve una linea de alquiler a partir de su id o null en caso de no encontrarse
	 */
	@Override
	public LineaAlquiler findById(Long id) {
		return lineaAlquilerRepo.findById(id).orElse(null);
	}

	/**
	 * Edita una linea de alquiler
	 */
	@Override
	public LineaAlquiler edit(LineaAlquiler e) {
		return lineaAlquilerRepo.save(e);
	}

	/**
	 * Borra una linea de alquiler
	 */
	@Override
	public void borrar(LineaAlquiler e) {
		lineaAlquilerRepo.delete(e);
		
	}
	
	public LineaAlquiler editarLineaAlquiler(LineaAlquiler result, LineaAlquiler recibida, Accesorio accesorio) {
		result.setAccesorio(accesorio);
		result.setAlquiler(recibida.getAlquiler());
		result.setFecha(new Date());
		return result;
	}
}
