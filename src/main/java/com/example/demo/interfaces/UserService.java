package com.example.demo.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.User;


public interface UserService {
	public User add(User e);
	public List<User>findAll();
	public User findById(Long id);
	public User edit(User e);
	public void borrar(User e);
	public Optional<User> findByEmail(String correo);
	
}
