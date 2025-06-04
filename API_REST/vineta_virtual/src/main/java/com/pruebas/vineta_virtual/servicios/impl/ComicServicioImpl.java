package com.pruebas.vineta_virtual.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.vineta_virtual.dao.ComicRepositorio;
import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.servicios.IComicServicio;

@Service
public class ComicServicioImpl implements IComicServicio {
	
	@Autowired
    private ComicRepositorio comicRepositorio;
	
	@Override
	public Comic crearComic(Comic comic) {
		return comicRepositorio.save(comic);
	}

	@Override
	public List<Comic> obtenerComicsPorCliente(int clienteId) {
		return comicRepositorio.findByClienteId(clienteId);
	}

	@Override
	public List<Comic> obtenerComicsPorCategoria(String categoria) {
		return comicRepositorio.findByCategoria(categoria);
	}

	@Override
	public Comic obtenerComicPorId(int id) {
		return comicRepositorio.findById(id).orElse(null);
	}

	@Override
	public List<Comic> obtenerTodosLosComics() {
		return comicRepositorio.findAll();
	}

	@Override
	public List<Comic> obtenerTodosLosComicsPorTitulo(String titulo) {
		return comicRepositorio.findByTituloContainingIgnoreCase(titulo);
	}

}
