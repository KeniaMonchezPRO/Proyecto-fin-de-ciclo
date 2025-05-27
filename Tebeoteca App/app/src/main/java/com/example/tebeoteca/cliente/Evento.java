package com.example.tebeoteca.cliente;

public class Evento {
    private String titulo;
    private String descripcion;

    public Evento(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
}
