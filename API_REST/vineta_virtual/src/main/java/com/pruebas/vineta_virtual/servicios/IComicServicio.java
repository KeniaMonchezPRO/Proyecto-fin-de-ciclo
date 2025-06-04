package com.pruebas.vineta_virtual.servicios;

import java.util.List;

import com.pruebas.vineta_virtual.entidades.Comic;

public interface IComicServicio {
	Comic crearComic(Comic comic);
	List<Comic> obtenerTodosLosComics();
	List<Comic> obtenerTodosLosComicsPorTitulo(String titulo);
    List<Comic> obtenerComicsPorCliente(int clienteId);
    List<Comic> obtenerComicsPorCategoria(String categoria);
    Comic obtenerComicPorId(int id);
}
