package com.pruebas.vineta_virtual.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.vineta_virtual.entidades.Lector;

public interface LectorRepositorio extends JpaRepository<Lector, Integer> {

}
