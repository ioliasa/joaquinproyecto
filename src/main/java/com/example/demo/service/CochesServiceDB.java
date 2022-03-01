package com.example.demo.service;

import java.util.List;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.CochesService;
import com.example.demo.model.Coche;
import com.example.demo.repository.CocheRepo;
@Service
public class CochesServiceDB implements CochesService {
	
	@Autowired
	private CocheRepo cocheRepo;

	/**
	 * Añade coche y devuelve su id
	 */
	@Override
	public Long add(Coche e) {
		cocheRepo.save(e);
		return e.getId();
	}

	/**
	 * Devuelve la lista de coches
	 */
	@Override
	public List<Coche> findAll() {
		return cocheRepo.findAll();
	}

	/**
	 * Devuelve un coche a partir de su id o null en caso de no econtrarse
	 */
	@Override
	public Coche findById(Long id) {
		return cocheRepo.findById(id).orElse(null);
	}

	/**
	 * Edita un coche
	 */
	@Override
	public Coche edit(Coche e) {
		return cocheRepo.save(e);
	}

	/**
	 * Borra un coche
	 */
	@Override
	public void borrar(Coche e) {
		cocheRepo.delete(e);
		
	}

	
	/**
	 * Devuelve la lista de coches disponibles (consulta)
	 */
	@Override
	public List<Coche> findAllCochesDisponibles() {
		return cocheRepo.findAllCochesDisponibles();
	}
	
	/**
	 * Cambia el estatus de un coche y edita dicho coche con el cambio realizado
	 * @param c
	 */
	public void cambiarStatus(Coche c) {
		c.setStatus(false);
		cocheRepo.save(c);
	}

	/**
	 * Devuelve la lista de coches de una determinada marca (consulta)
	 */
	@Override
	public List<Coche> findCochesByMarca(String termino) {
		return cocheRepo.findCocheByMarca(termino);
	}
		

	

}
