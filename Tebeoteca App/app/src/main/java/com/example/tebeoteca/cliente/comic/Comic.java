package com.example.tebeoteca.cliente.comic;

import java.io.Serializable;

public class Comic implements Serializable {
    private String titulo;
    private String autores;
    private String categoria;
    private int idImagen;

    public Comic(String titulo, String autores, String categoria, int idImagen) {
        this.titulo = titulo;
        this.autores = autores;
        this.categoria = categoria;
        this.idImagen = idImagen;
    }

    public String getTitulo() { return titulo; }
    public String getAutores() { return autores; }
    public int getIdImagen() { return idImagen; }

    public String getCategoria() { return categoria; }
}
