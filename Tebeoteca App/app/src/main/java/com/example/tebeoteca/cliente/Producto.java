package com.example.tebeoteca.cliente;

public class Producto {
    private String titulo;
    private String descripcion;

    public Producto(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }

}
