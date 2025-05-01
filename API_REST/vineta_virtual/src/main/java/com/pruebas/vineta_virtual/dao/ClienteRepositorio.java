package com.pruebas.vineta_virtual.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.vineta_virtual.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

}
