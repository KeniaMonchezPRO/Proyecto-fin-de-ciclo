package com.pruebas.vineta_virtual.dto;

import java.time.LocalDate;

import com.pruebas.vineta_virtual.entidades.enums.EstadoComic;
import com.pruebas.vineta_virtual.entidades.enums.IdiomaOriginal;
import com.pruebas.vineta_virtual.entidades.enums.PaisOrigen;

public class AnadirComicResponseDTO {
	
	private int id;
    private String portada;
    private int clienteId;
    private String titulo;
    private String audiencia;
    private String selloEditorial;
    private LocalDate fechaLanzamiento;
    private EstadoComic estado;
    private String autores;
    private String descripcion;
    private PaisOrigen paisOrigen;
    private IdiomaOriginal idiomaOriginal;
    private String categorias;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPortada() {
		return portada;
	}
	public void setPortada(String portada) {
		this.portada = portada;
	}
	public int getClienteId() {
		return clienteId;
	}
	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAudiencia() {
		return audiencia;
	}
	public void setAudiencia(String audiencia) {
		this.audiencia = audiencia;
	}
	public String getSelloEditorial() {
		return selloEditorial;
	}
	public void setSelloEditorial(String selloEditorial) {
		this.selloEditorial = selloEditorial;
	}
	public LocalDate getFechaLanzamiento() {
		return fechaLanzamiento;
	}
	public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}
	public EstadoComic getEstado() {
		return estado;
	}
	public void setEstado(EstadoComic estado) {
		this.estado = estado;
	}
	public String getAutores() {
		return autores;
	}
	public void setAutores(String autores) {
		this.autores = autores;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public PaisOrigen getPaisOrigen() {
		return paisOrigen;
	}
	public void setPaisOrigen(PaisOrigen paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	public IdiomaOriginal getIdiomaOriginal() {
		return idiomaOriginal;
	}
	public void setIdiomaOriginal(IdiomaOriginal idiomaOriginal) {
		this.idiomaOriginal = idiomaOriginal;
	}
	public String getCategorias() {
		return categorias;
	}
	public void setCategorias(String categorias) {
		this.categorias = categorias;
	}
    
    

}
