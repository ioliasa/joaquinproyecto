package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	public Optional<User> findByEmail(String email);
	/**
	 * Consulta que devuelve el usuario que tiene asociado un alquiler apartir de su id
	 * @param id del alquiler
	 * @return id del usuario
	 */
	@Query(value="select al.user_id from user_alquileres al where al.alquileres_id =:id", nativeQuery = true)
	public Long findUserByAlquiler(Long id);
	
  
}
