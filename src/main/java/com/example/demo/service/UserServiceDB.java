package com.example.demo.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.UserService;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceDB implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	/**
	 * Añade un usuario
	 */
	@Override
	public User add(User e) {
		return userRepo.save(e);
	}

	/**
	 * Devuelve la lista de usuarios
	 */
	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	/**
	 * Devuelve un usuario a partir de su id o null en caso de no econtrarse
	 */
	@Override
	public User findById(Long id) {
		return userRepo.findById(id).orElse(null);
	}

	/**
	 * Edita un usuario
	 */
	@Override
	public User edit(User e) {
		User usuario = findById(e.getId());
		usuario.setAlquileres(e.getAlquileres());
		return userRepo.save(usuario);
	}

	/**
	 * Borra un usuario
	 */
	@Override
	public void borrar(User e) {
		userRepo.delete(e);
		
	}

	/**
	 * Devuelve un  optional de tipo usuario a partir de su correo
	 */
	@Override
	public Optional<User> findByEmail(String correo) {
		return userRepo.findByEmail(correo);
		
	}
	
	/**
	 * Devuelve la id de un usuario apartir de la id de un alquiler (consulta)
	 * @param id del alquiler
	 * @return id del usuario que tiene asociado dicho alquiler
	 */
	public Long findUserByAlquiler(Long id){
		return userRepo.findUserByAlquiler(id);
	}

	
		
	

}
