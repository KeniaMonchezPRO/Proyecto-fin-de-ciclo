package com.pruebas.vineta_virtual.dto;

import java.util.List;

import com.pruebas.vineta_virtual.entidades.Comic;

public class BusquedaResponseDTO {
	private List<Comic> comics;
    //private List<Evento> eventos;
    //private List<Wiki> wiki;
    //private List<Ruta> rutas;

	public List<Comic> getComics() {
		return comics;
	}

	public void setComics(List<Comic> comics) {
		this.comics = comics;
	}
	
	
	
}
