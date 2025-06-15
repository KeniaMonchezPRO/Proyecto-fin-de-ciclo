package com.example.vineta_virtual.cliente.ruta;

public class Ruta {
    private String titulo;
    private String descripcion;

    public Ruta(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
}
