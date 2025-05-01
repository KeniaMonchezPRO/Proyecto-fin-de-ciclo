package com.pruebas.vineta_virtual.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.vineta_virtual.dao.LectorRepositorio;
import com.pruebas.vineta_virtual.entidades.Lector;
import com.pruebas.vineta_virtual.servicios.ILectorServicio;

@Service
public class LectorServicioImpl implements ILectorServicio {
	
	@Autowired
    private LectorRepositorio lectorRepositorio;
	
	@Override
	public List<Lector> obtenerTodosLosLectores() {
		return lectorRepositorio.findAll();
	}

	@Override
	public Lector buscarPorId(int id) {
		return lectorRepositorio.findById(id)
	                .orElseThrow(() -> new RuntimeException("Lector no encontrado"));
	}

	@Override
	public Lector guardar(Lector lector) {
		return lectorRepositorio.save(lector);
	}

	@Override
	public void eliminarLector(int id) {
		lectorRepositorio.deleteById(id);
	}
	
	

}
