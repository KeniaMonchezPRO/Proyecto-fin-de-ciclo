package com.example.vineta_virtual.cliente.comic;

import com.example.vineta_virtual.cliente.Cliente;

import java.io.Serializable;

public class Comic implements Serializable {
    private int id;
    private String titulo;
    private String autores;
    private String categorias;
    private String portada;
    private String audiencia;
    private String selloEditorial;
    private String fechaLanzamiento;
    private String estado;
    private String descripcion;
    private String pais_origen;
    private String idiomaOriginal;
    private String precioCompra;
    private String precioAlquiler;
    private Cliente cliente;


    public Comic() {}

    public Comic(String titulo, String autores, String categorias, String portada) {
        this.titulo = titulo;
        this.autores = autores;
        this.categorias = categorias;
        this.portada = portada;
    }

    public String getTitulo() { return titulo; }
    public String getAutores() { return autores; }
    public String getPortada() { return portada; }

    public String getCategorias() { return categorias; }

    public String getAudiencia() {
        return audiencia;
    }

    public String getSelloEditorial() {
        return selloEditorial;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getpais_origen() {
        return pais_origen;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public int getId() {
        return id;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public String getPrecioAlquiler() {
        return precioAlquiler;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
