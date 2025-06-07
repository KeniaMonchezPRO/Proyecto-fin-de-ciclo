package com.pruebas.vineta_virtual.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pruebas.vineta_virtual.entidades.Compra;
import com.pruebas.vineta_virtual.entidades.Lector;

@Repository
public interface CompraRepositorio extends JpaRepository<Compra, Integer> {
	List<Compra> findByLector_Id(int idLector);
    boolean existsByLector_IdAndComicId(int idLector, int idComic);
}
