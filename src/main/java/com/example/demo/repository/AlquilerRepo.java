package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Alquiler;

public interface AlquilerRepo extends JpaRepository<Alquiler, Long> {

}
