package com.example.demo.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.error.AccesorioNotFoundException;
import com.example.demo.error.AlquilerNotFoundException;
import com.example.demo.error.ApiError;
import com.example.demo.error.CocheNotFoundException;
import com.example.demo.error.LineaAlquilerNotFoundException;
import com.example.demo.error.UserDuplicateException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Accesorio;
import com.example.demo.model.Alquiler;
import com.example.demo.model.AlquilerDTO;
import com.example.demo.model.Coche;
import com.example.demo.model.CocheDTO;
import com.example.demo.model.FileMessage;
import com.example.demo.model.LineaAlquiler;
import com.example.demo.model.User;
import com.example.demo.service.AccesorioServiceDB;
import com.example.demo.service.AlquilerServiceDB;
import com.example.demo.service.CochesServiceDB;
import com.example.demo.service.FileServiceImp;
import com.example.demo.service.LineaAlquilerServiceDB;
import com.example.demo.service.UserServiceDB;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 *Gestiona las peticiones de alquileres,accesorios,coches,linea alquiler, buscador y calculadora de precio alquiler
 * @author 34645
 *
 */
@RestController
public class UserController {

    @Autowired private UserServiceDB userRepo;
    @Autowired private CochesServiceDB cocheRepo;
    @Autowired private AccesorioServiceDB accesorioRepo;
    @Autowired private AlquilerServiceDB alquilerRepo;
    @Autowired private LineaAlquilerServiceDB lineaRepo;
    @Autowired private FileServiceImp fileService;

    /**
     * 
     * @return id del usuario que se encuentra logueado
     */
    @GetMapping("/user")
    public ResponseEntity<User> getUserDetails(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        return ResponseEntity.ok(userRepo.findByEmail(email).get());
    }
    
    
    /**
     * 
     * @return lista de coches disponibles haciendo uso de una consulta o excepcion en caso de estar vacía la lista
     */
    @GetMapping("/coches")
    public ResponseEntity<List<Coche>> findAllCoches(){
    	List<Coche>resul = this.cocheRepo.findAllCochesDisponibles();
    	return ResponseEntity.ok(resul);
    	
    }
    
    @PostMapping("/coches")
    public ResponseEntity<Coche>addCoche(@RequestBody CocheDTO cocheDTO){
    	Coche coche = new Coche(cocheDTO.getMarca(), cocheDTO.getModelo(), 
    			cocheDTO.getMotor(), cocheDTO.getYear(), 
    			cocheDTO.getPrecioFijo(), cocheDTO.getImagen(), true);
    	
    	this.cocheRepo.add(coche);
    	return ResponseEntity.status(HttpStatus.CREATED).body(coche);
    }
    
    /**
     * 
     * @param id del coche buscado
     * @return coche buscado o excepción en caso de no encontrarse
     */
    @GetMapping("/coches/{id}")
    public ResponseEntity<Coche> findByIdCoche(@PathVariable Long id){
    	Coche result = this.cocheRepo.findById(id);
    	if(result == null) {
    		throw new CocheNotFoundException(id);
    	}else {
    		return ResponseEntity.ok(result);
    	}
    }
    
    /**
     * Busca una marca de coche a través de una consulta y devuelve un boolean
     * @param marcaBuscada marca del coche que se quiere buscar
     * @return true en caso de econtrarse o false
     */
   @GetMapping("/buscador")
    public ResponseEntity<Boolean> findCochesByMarca(@RequestParam String marca) {
    	
    	boolean encontrado = false;
    	List<Coche>result = this.cocheRepo.findCochesByMarca(marca);
    	if(result.size()>0) {
    		encontrado = true;
    	}
    	return ResponseEntity.ok(encontrado);
    }
    
    
    /**
     * Devuelve la lista de accesorios
     * @return lista de accesorios o excepción en caso de estar vacía la lista
     */
    @GetMapping("/accesorios")
    public ResponseEntity<List<Accesorio>> findAllAccesorios(){
    	List<Accesorio>resul = this.accesorioRepo.findAll();
    	
    	if(resul.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}else {
    		return ResponseEntity.ok(resul);
    	}
    }
    
    
    /**
     * Crea un alquiler a partir del dto que recibe que contiene la id del usuario, el id del coche elegido y los accesorios marcados.
     * 
     * @param alquilerDTO objeto que contiene los datos introducidos en el formulario de alquiler(frontend)
     * @return alquiler si valida la id del usuario e id del coche.
     */
    @PostMapping("/alquiler")
    public ResponseEntity<Alquiler>crearAlquiler(@RequestBody AlquilerDTO alquilerDTO){
    	User result = this.userRepo.findById(alquilerDTO.getIdUser());
    	Coche cocheElegido = this.cocheRepo.findById(alquilerDTO.getCoche());
    	if(result == null) {
    		throw new UserNotFoundException(alquilerDTO.getIdUser());
    	}else if(cocheElegido == null){
    		throw new  CocheNotFoundException(alquilerDTO.getCoche());
    	}else {
    		Alquiler alquiler = this.alquilerRepo.crearAlquiler(alquilerDTO);
    		this.alquilerRepo.add(alquiler);
    		alquiler = this.alquilerRepo.crearLineasAlquiler(alquiler, alquilerDTO);
    		alquiler.setPrecio(this.alquilerRepo.calcularPrecio(alquiler));
    		
    		this.alquilerRepo.edit(alquiler);
    		result.addAlquiler(alquiler);  
    		this.userRepo.edit(result);
    		this.cocheRepo.cambiarStatus(cocheElegido);	
    		return ResponseEntity.status(HttpStatus.CREATED).body(alquiler);
    	}
    }
    
    /**
     * Devuelve los alquileres de un usuario a través del id del usuario que recibe del path
     * @param id del usuario
     * @return alquiler buscado o excepción en caso de no econtrarse el usuario
     */
    @GetMapping("/alquiler")
    public ResponseEntity<Set<Alquiler>> findAllAlquilerById(@RequestParam Long id){
    	User usuario = this.userRepo.findById(id);
    	if(usuario == null) {
    		throw new UserNotFoundException(id);
    	}else {
        	Set<Alquiler>resul = usuario.getAlquileres();
        	return ResponseEntity.ok(resul);
    	}
    }
    
    /**
     * Edita un pedido a través de la id del alquiler que recibe del path
     * @param id del pedido a editar
     * @param alquilerDTO datos que se van a incluir,modificar,eliminar del alquiler
     * @return alquiler editado
     */
    @PutMapping("alquiler/{id}")
    public ResponseEntity<Alquiler> editAlquiler(@PathVariable Long id,@RequestBody AlquilerDTO alquilerDTO) {
    	Alquiler alquiler = this.alquilerRepo.findById(id);
    	User usuario = this.userRepo.findById(alquilerDTO.getIdUser());
    	Coche coche = this.cocheRepo.findById(alquilerDTO.getCoche());
    	
    	if(alquiler == null) {
    		throw new AlquilerNotFoundException(id);
    	}
    	else if(usuario==null) {
    		throw new UserNotFoundException(alquilerDTO.getIdUser());
    	}else if(coche==null) {
    		throw new CocheNotFoundException(alquilerDTO.getCoche());
    	}else {
    		this.alquilerRepo.editarAlquiler(alquilerDTO, alquiler, coche);
    		this.alquilerRepo.edit(alquiler);
    	}
		return ResponseEntity.status(HttpStatus.OK).body(alquiler);	
    }
    
    
    /**
     * Edita una linea de alquiler a través de la id del alquiler y la id de la linea que recibe del path
     * @param id de la linea de alquiler
     * @param idLinea
     * @param linea datos que se van a modificar
     * @return linea de alquiler modificada
     */

    @PutMapping("alquiler/{id}/lineaAlquiler/{idLinea}")
    public ResponseEntity<LineaAlquiler> editLinea(@PathVariable Long id,@PathVariable Long idLinea,@RequestBody LineaAlquiler linea) {
    	LineaAlquiler result= this.lineaRepo.findById(idLinea);
    	Alquiler al = this.alquilerRepo.findById(id);
    	Accesorio a = this.accesorioRepo.findById(linea.getAccesorio().getId());
    	if(result==null) {
    		throw new LineaAlquilerNotFoundException(id);
    	}else if(al==null) {
    		throw new AlquilerNotFoundException(linea.getAlquiler().getId());
    	}else if(a==null) {
    		throw new AccesorioNotFoundException(linea.getAccesorio().getId());
    	}else {
    		this.lineaRepo.editarLineaAlquiler(result, linea, a);
    		this.lineaRepo.edit(result);
    		al.setPrecio(this.alquilerRepo.calcularPrecio(al));
    		this.alquilerRepo.edit(al);
    	}
    	return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    /**
     * Borra de un alquiler una linea concreta a partir de la id del alquiler y la linea que se obtiene del path
     * @param id del alquiler
     * @param idLinea
     * @return no contenido en caso de borrarse correctamente o excepción en caso de no encontrar el alquiler o la id
     */
    @DeleteMapping("alquiler/{id}/lineaAlquiler/{idLinea}")
    public ResponseEntity<?> deleteLineaAlquiler(@PathVariable Long id, @PathVariable Long idLinea){
    	LineaAlquiler result= this.lineaRepo.findById(idLinea);
    	Alquiler al = this.alquilerRepo.findById(id);
    	
    	if(result==null) {
    		throw new LineaAlquilerNotFoundException(id);
    	}else if(al==null) {
    		throw new AlquilerNotFoundException(al.getId());
    	}else {
    		this.alquilerRepo.deleteLinea(al,result);
    		this.alquilerRepo.edit(al);
    	}
    	return ResponseEntity.noContent().build();
    }
    
    
    
    /**
     * Busca el usuario a través del id del alquiler haciendo uso de una consulta (nativa) y borra dicho alquiler
     * @param id del alquiler
     * @return no contenido en caso de borrarse correctamente o excepción en caso de no econtrarse el alquiler
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/alquiler/{id}")
    public ResponseEntity<?> deleteAlquiler(@PathVariable Long id){
    	Alquiler result = this.alquilerRepo.findById(id);
    	if(result == null) {
    		throw new AlquilerNotFoundException(id);
    	}else {
    		User usuario = this.userRepo.findById(this.userRepo.findUserByAlquiler(id));
    		result.getCoche().setStatus(true);
    		this.cocheRepo.edit(result.getCoche());
    		usuario.getAlquileres().remove(result);
    		this.userRepo.edit(usuario);
    		this.alquilerRepo.borrar(result);
    		
    		return ResponseEntity.noContent().build();
    	}
    }
    
    /**
     * Devuelve el precio que tendría un alquiler a través del dto que recibe. A diferencia del crear alquiler, 
     * aqui no se persiste el alquiler o las líneas. Se realiza en memoria.
     * @param alquilerDTO objeto que contiene los datos introducidos en el formulario de alquiler(frontend)
     * @return simulación del precio del alquiler o exceptión en caso de no econtrarse el usuario
     */
    @PostMapping("/calcular-alquiler")
    public ResponseEntity<Double> calcularPrecio(@RequestBody AlquilerDTO alquilerDTO){
    	User result = this.userRepo.findById(alquilerDTO.getIdUser());
    	Coche cocheElegido = this.cocheRepo.findById(alquilerDTO.getCoche());
    	if(result == null) {
    		throw new UserNotFoundException(alquilerDTO.getIdUser());
    	}else if(cocheElegido == null){
    		throw new  CocheNotFoundException(alquilerDTO.getCoche());
    	}else {
    		Alquiler alquiler = this.alquilerRepo.crearAlquiler(alquilerDTO);
    		
    		alquiler = this.alquilerRepo.crearLineasAlquilerCalculadora(alquiler, alquilerDTO);
    		alquiler.setPrecio(this.alquilerRepo.calcularPrecio(alquiler));
    		return ResponseEntity.ok(alquiler.getPrecio());
    	}
    }
    
    /**
     * Recibe los datos de un coche (marca,modelo...) y el fichero que contiene la imagen del coche.
     * @param file imagen del coche
     * @param marca
     * @param modelo
     * @param motor
     * @param year
     * @param precioFijo
     * @return POST de coches  o excepción en caso de sobrepasar el limite de tamaño de la imagen
     */
    @PostMapping("/upload")
    public ResponseEntity<FileMessage> uploadFiles(@RequestBody MultipartFile file,
    		@RequestParam String marca, @RequestParam String modelo,@RequestParam String motor,
    		@RequestParam String year,@RequestParam Double precioFijo
    		){
    	 String message = "";
    	    try {
    	      byte[] imagen = this.fileService.save(file);
    	      CocheDTO coche = new CocheDTO(marca, modelo, motor, year, precioFijo);
    	      coche.setImagen(imagen);

    	      addCoche(coche);
    	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
    	      return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
    	    } catch (Exception e) {
    	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
    	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
    	    }
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<FileMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage("File too large!"));
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

}
    
