package com.example.demo.controller;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.AccesorioNotFoundException;
import com.example.demo.error.AlquilerNotFoundException;
import com.example.demo.error.ApiError;
import com.example.demo.error.CocheNotFoundException;
import com.example.demo.error.InvalidCredentialException;
import com.example.demo.error.LineaAlquilerNotFoundException;
import com.example.demo.error.UserDuplicateException;
import com.example.demo.error.UserNotFoundException;

import com.example.demo.model.LoginCredentials;
import com.example.demo.model.User;
import com.example.demo.security.JWTUtil;

import com.example.demo.service.UserServiceDB;
import com.fasterxml.jackson.databind.JsonMappingException;
/**
 * Gestiona las peticiones de login y registro
 * @author 34645
 *
 */
@RestController
public class AuthController {

    @Autowired private UserServiceDB userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    
    /**
     * Recibe un usuario y despues de codificar la password si el usuario no se encuentra registrado lo añade al
     * repositorio de usuarios y devuelve el token asociado al usuario
     * @param user usuario que se quiere registrar
     * @return token o exception en caso de encontrarse registrado el email
     */
    @PostMapping("auth/register")
    public Map<String, Object> registerHandler(@RequestBody User user){
    	
    	user.setRol("USER");
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
      
        try {
        	this.userRepo.add(user);
        	 String token = jwtUtil.generateToken(user.getEmail());
        	 
        	 Map<String, Object>userDetails = new HashMap<String, Object>();
        	 userDetails.put("idUser", user.getId());
        	 userDetails.put("rol", user.getRol());
        	 userDetails.put("access_token", token);
             return userDetails;
        }catch(Exception ex) {
        	throw new UserDuplicateException(user.getEmail());
        } 
    }

    /**
     * Recibe un usuario y comprueba que el usuario se encuentra registrado.
     * @param body usuario (email y password)
     * @return token si el usuario se encuentra registrado o exception en caso contrario
     */
    @PostMapping("auth/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getEmail());
            Map<String, Object>userDetails = new HashMap<String, Object>();
            User usuario =  this.userRepo.findByEmail(body.getEmail()).orElse(null);
            if(usuario!=null) {
            	userDetails.put("idUser", usuario.getId());
            	userDetails.put("rol", usuario.getRol());
            	userDetails.put("access_token", token);
            }
            return userDetails;
        }catch (Exception e){
            throw new InvalidCredentialException();
        }
    }
    
    
    
    
    /**
     * 
     * @param ex excepcion de tipo usuario not found
     * @return 404 y el mensaje de usuario no encontrado
     */
    @ExceptionHandler(UserNotFoundException.class)
   	public ResponseEntity<ApiError> handleUserNoEncontrado(UserNotFoundException ex) {
   		ApiError apiError = new ApiError();
   		apiError.setEstado(HttpStatus.NOT_FOUND);
   		apiError.setFecha(LocalDateTime.now());
   		apiError.setMensaje(ex.getMessage());
   		
   		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
   	}
    
    /**
     * 
     * @param ex excepcion de tipo usuario duplicado
     * @return 409 y mensaje de usuario ya registrado
     */
    @ExceptionHandler(UserDuplicateException.class)
	public ResponseEntity<ApiError> handleUserDuplicado(UserDuplicateException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.CONFLICT);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}
    
    /**
     * 
     * @param ex excepcion de tipo coche not found
     * @return 404 y mensaje de coche id no encontrado
     */
    @ExceptionHandler(CocheNotFoundException.class)
   	public ResponseEntity<ApiError> handleCocheNoEncontrado(CocheNotFoundException ex) {
   		ApiError apiError = new ApiError();
   		apiError.setEstado(HttpStatus.NOT_FOUND);
   		apiError.setFecha(LocalDateTime.now());
   		apiError.setMensaje(ex.getMessage());
   		
   		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
   	}
    
  
    /**
     * 
     * @param ex excepcion de tipo alquiler not found
     * @return 404 y mensaje de alquiler id no encontrado
     */
    @ExceptionHandler(AlquilerNotFoundException.class)
   	public ResponseEntity<ApiError> handleAlquilerNoencontrado(AlquilerNotFoundException ex) {
   		ApiError apiError = new ApiError();
   		apiError.setEstado(HttpStatus.NOT_FOUND);
   		apiError.setFecha(LocalDateTime.now());
   		apiError.setMensaje(ex.getMessage());
   		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
   	}
    
    /**
     * 
     * @param ex excepcion del tipo accesorio not found
     * @return 404 y mensaje de accesorio id no encontrado
     */
    @ExceptionHandler(AccesorioNotFoundException.class)
   	public ResponseEntity<ApiError> handleAccesorioNoencontrado(AccesorioNotFoundException ex) {
   		ApiError apiError = new ApiError();
   		apiError.setEstado(HttpStatus.NOT_FOUND);
   		apiError.setFecha(LocalDateTime.now());
   		apiError.setMensaje(ex.getMessage());
   		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
   	}
    
    
    @ExceptionHandler(LineaAlquilerNotFoundException.class)
   	public ResponseEntity<ApiError> handleLineaNoencontrado(LineaAlquilerNotFoundException ex) {
   		ApiError apiError = new ApiError();
   		apiError.setEstado(HttpStatus.NOT_FOUND);
   		apiError.setFecha(LocalDateTime.now());
   		apiError.setMensaje(ex.getMessage());
   		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
   	}
    
    
	/**
	 * 
	 * @param ex excepcion del tipo json mapping
	 * @return 400 y mensaje de error en el json
	 */
	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<ApiError> handleJsonMappingException(JsonMappingException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.BAD_REQUEST);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	
	/**
	 * 
	 * @param ex exception del tipo invalid credentials
	 * @return 401 y mensaje de no autorizado
	 */
	 @ExceptionHandler(InvalidCredentialException.class)
	   	public ResponseEntity<ApiError> handleCocheNoEncontrado(InvalidCredentialException ex) {
	   		ApiError apiError = new ApiError();
	   		apiError.setEstado(HttpStatus.UNAUTHORIZED);
	   		apiError.setFecha(LocalDateTime.now());
	   		apiError.setMensaje(ex.getMessage());
	   		
	   		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	   	}


}
