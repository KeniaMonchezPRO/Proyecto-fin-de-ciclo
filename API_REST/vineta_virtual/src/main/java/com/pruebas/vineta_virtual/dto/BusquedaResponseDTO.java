package com.pruebas.vineta_virtual.dto;

import java.util.List;

import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.entidades.Lector;

public class BusquedaResponseDTO {
	private List<Comic> comics;
	private List<Lector> lectores;
	private List<Cliente> clientes;
    //private List<Evento> eventos;
    //private List<Wiki> wiki;
    //private List<Ruta> rutas;

	public List<Comic> getComics() {
		return comics;
	}

	public void setComics(List<Comic> comics) {
		this.comics = comics;
	}

	public List<Lector> getLectores() {
		return lectores;
	}

	public void setLectores(List<Lector> lectores) {
		this.lectores = lectores;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	
	
	
	
}
