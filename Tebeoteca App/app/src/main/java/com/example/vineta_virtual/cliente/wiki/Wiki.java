package com.example.vineta_virtual.cliente.wiki;

public class Wiki {
    private String titulo;
    private String descripcion;

    public Wiki(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
}

