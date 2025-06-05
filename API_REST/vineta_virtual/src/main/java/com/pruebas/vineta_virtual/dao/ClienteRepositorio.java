package com.pruebas.vineta_virtual.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.vineta_virtual.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
	List<Cliente> findByNombreUsuarioContainingIgnoreCase(String nombreUsuario);
}
