package com.pruebas.vineta_virtual.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.vineta_virtual.entidades.Lector;

public interface LectorRepositorio extends JpaRepository<Lector, Integer> {
	List<Lector> findByNombreUsuarioContainingIgnoreCase(String nombreUsuario);

}
