package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Coche;

public interface CocheRepo extends JpaRepository<Coche, Long> {
	/**
	 * Consulta que comprueba el status de todos los coches y lo devuelve en una lista
	 * @return lista de coches disponibles
	 */
	@Query("select c from Coche c where c.status = true")
	List<Coche>findAllCochesDisponibles();
	/**
	 * Consulta que comprueba la marca de todos los coches y lo guarda en una lista en caso de coincidir
	 * @param termino marca de coche buscada
	 * @return lista de coches de dicha marca
	 */
	@Query("select c from Coche c where c.marca =:termino")
	List<Coche>findCocheByMarca(String termino);

}
