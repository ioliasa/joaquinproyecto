package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Column(name="email", unique=true)
	@Email
	private String email;
	private String nombre;
	private String apellidos;
	private String rol;
	
	@OneToMany(fetch= FetchType.EAGER)
	Set<Alquiler>alquileres = new HashSet<Alquiler>();
	
	//Evita que el campo password se incluya en el JSON de respuesta
	@NotEmpty
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	public User() {
	}
	

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
		this.rol="USER";
	}
	
	
	public User(@NotEmpty @Email String email,String password, String nombre, String apellidos, String rol) {
		super();
		this.email = email;
		this.password=password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.rol=rol;
	}


	public Set<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(Set<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}
	
	public void addAlquiler(Alquiler al) {
		this.alquileres.add(al);
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + ", rol="
				+ rol + ", password=" + password + "]";
	}

}
