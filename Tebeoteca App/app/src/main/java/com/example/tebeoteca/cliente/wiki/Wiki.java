package com.example.tebeoteca.cliente.wiki;

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

