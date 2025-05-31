package com.pruebas.vineta_virtual.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pruebas.vineta_virtual.entidades.Comic;

@Repository
public interface ComicRepositorio extends JpaRepository<Comic, Integer>{
	@Query("SELECT c FROM Comic c WHERE c.cliente.id = :idCliente")
	List<Comic> findByClienteId(@Param("idCliente") int idCliente);
    
	@Query("SELECT c FROM Comic c WHERE c.categorias LIKE %:categoria%")
	List<Comic> findByCategoria(String categoria);
}
