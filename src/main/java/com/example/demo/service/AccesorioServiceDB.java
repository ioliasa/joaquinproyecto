package com.example.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.AccesorioService;
import com.example.demo.model.Accesorio;
import com.example.demo.repository.AccesorioRepo;

@Service
public class AccesorioServiceDB implements AccesorioService {
	@Autowired
	private AccesorioRepo accesorioRepo;

	/**
	 * Añade un accesorio y devuelve su id
	 */
	@Override
	public Long add(Accesorio e) {
		accesorioRepo.save(e);
		return e.getId();
	}
	/**
	 * Devuelve la lista de accesorios
	 */
	@Override
	public List<Accesorio> findAll() {
		return accesorioRepo.findAll();
	}

	/**
	 * Devuelve un accesorio concreto a partir de su id o null en caso de no econtrarse
	 */
	@Override
	public Accesorio findById(Long id) {
		return accesorioRepo.findById(id).orElse(null);
	}

	/**
	 * Edita un accesorio
	 */
	@Override
	public Accesorio edit(Accesorio e) {
		return accesorioRepo.save(e);
	}

	/**
	 * Borra un accesorio
	 */
	@Override
	public void borrar(Accesorio e) {
		accesorioRepo.delete(e);
		
	}
	

}
