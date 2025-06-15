package com.example.vineta_virtual.cliente.novedad;

public class Novedad {
    private String titulo;
    private String descripcion;

    public Novedad(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
}

