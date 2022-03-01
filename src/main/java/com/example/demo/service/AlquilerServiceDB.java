package com.example.demo.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.AlquilerService;
import com.example.demo.model.Accesorio;
import com.example.demo.model.Alquiler;
import com.example.demo.model.AlquilerDTO;
import com.example.demo.model.Coche;
import com.example.demo.model.LineaAlquiler;
import com.example.demo.repository.AlquilerRepo;

@Service
public class AlquilerServiceDB implements AlquilerService {
	@Autowired AlquilerRepo alquilerRepo;
	@Autowired CochesServiceDB cocheRepo;
	@Autowired LineaAlquilerServiceDB lineaRepo;

	/**
	 * Crea un alquiler y devuelve su id
	 */
	@Override
	public Long add(Alquiler e) {
		alquilerRepo.save(e);
		return e.getId();
	}

	/**
	 * Devuelve la lista de alquileres
	 */
	@Override
	public List<Alquiler> findAll() {
		return alquilerRepo.findAll();
	}

	
	/**
	 * Devuelve un alquiler a través de su id o null en caso de no encontrarse
	 */
	@Override
	public Alquiler findById(Long id) {
		return alquilerRepo.findById(id).orElse(null);
	}

	/**
	 * Edita un alquiler
	 */
	@Override
	public Alquiler edit(Alquiler e) {
		return alquilerRepo.save(e);
	}

	/**
	 * Borra un alquiler
	 */
	@Override
	public void borrar(Alquiler e) {
		alquilerRepo.delete(e);
		
	}
	
	/**
	 * Calcula el precio de un alquiler recorriendo todas sus lineas y los accesorios que tengan asociados
	 * @param alquiler del cual se quiere calcular su precio
	 * @return precio total
	 */
	public double calcularPrecio(Alquiler alquiler) {
		double total = alquiler.getNumDias()*alquiler.getCoche().getPrecioFijo();
		
		for(LineaAlquiler accesorio : alquiler.getListaLinea()) {
			total += accesorio.getAccesorio().getPrecio();
		}
		
		return total;
	}
	
	/**
	 * Crea un alquiler apartir de un dto que contiene los datos del formulario (frontend)
	 * @param alquilerDTO objeto que contiene los datos del formulario
	 * @return alquiler
	 */
	public Alquiler crearAlquiler(AlquilerDTO alquilerDTO) {
		Alquiler al = new Alquiler();
		al.setFecha(alquilerDTO.getFecha());
		
		Coche coche = this.cocheRepo.findById(alquilerDTO.getCoche());
		al.setCoche(coche);
		al.setNumDias(alquilerDTO.getNumDias());
		
		Long aux = (long) alquilerDTO.getNumDias();
		al.setFechaEntrega(alquilerDTO.getFecha().plusDays(alquilerDTO.getNumDias()));
		return al;
	}
	
	/**
	 * Crea las lineas de alquiler a través del dto
	 * @param al alquiler que hemos creado previamente
	 * @param alquilerDTO objeto que contiene los datos del formulario (accesorios marcados,fecha...)
	 * @return alquiler con las lineas de alquiler añadidas
	 */
	public Alquiler crearLineasAlquiler(Alquiler al, AlquilerDTO alquilerDTO) {
		for(Accesorio accesorio: alquilerDTO.getAccesorios()) {
			LineaAlquiler l = new LineaAlquiler(accesorio, al);
			this.lineaRepo.add(l);

			al.addLineaAlquiler(l);
		}
		return al;
	}
	
	
	/**
	 * 
	 * @param al alquiler que hemos creado previamente
	 * @param alquilerDTO objeto que contiene los datos del formulario (accesorios marcados,fecha...)
	 * @return alquiler con las lineas de alquiler añadidas en memoria
	 */
	public Alquiler crearLineasAlquilerCalculadora(Alquiler al, AlquilerDTO alquilerDTO) {
		for(Accesorio accesorio: alquilerDTO.getAccesorios()) {
			LineaAlquiler l = new LineaAlquiler(accesorio, al);
			al.addLineaAlquiler(l);
		}
		return al;
	}
	
	/**
	 * Edita un alquiler
	 * @param alquilerDTO datos que se van a incluir en el alquiler
	 * @param alquiler que se va a modificar
	 * @param coche que se quiere alquilar
	 * @return alquiler editado
	 */
	public Alquiler editarAlquiler(AlquilerDTO alquilerDTO, Alquiler alquiler, Coche coche) {
		alquiler.setNumDias(alquilerDTO.getNumDias());
		alquiler.setCoche(coche);
		alquiler.setFecha(alquilerDTO.getFecha());
		alquiler.setFechaEntrega(alquilerDTO.getFecha().plusDays(alquilerDTO.getNumDias()));
		double precio =alquiler.getNumDias()*alquiler.getCoche().getPrecioFijo();
		
		Iterator<LineaAlquiler>it = alquiler.getListaLinea().iterator();
		while(it.hasNext()) {
			LineaAlquiler linea = it.next();
			this.lineaRepo.borrar(linea);
		}
		alquiler.getListaLinea().clear();
		this.alquilerRepo.save(alquiler);
		for(Accesorio a : alquilerDTO.getAccesorios()) {
			LineaAlquiler linea = new LineaAlquiler(a, alquiler);
			this.lineaRepo.add(linea);
			alquiler.addLineaAlquiler(linea);
			precio+=a.getPrecio();
		}
		alquiler.setPrecio(precio);
		return alquiler;
	}

	/**
	 * Borra una linea de un alquiler
	 * @param al alquiler
	 * @param result linea
	 * @return alquiler con la linea borrada
	 */
	public Alquiler deleteLinea(Alquiler al, LineaAlquiler result) {
		this.lineaRepo.borrar(result);
		al.getListaLinea().remove(result);
		return al;
		
	}
	
	

}
