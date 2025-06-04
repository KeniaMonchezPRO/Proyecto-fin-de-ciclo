package com.example.tebeoteca.general;

import com.example.tebeoteca.cliente.comic.Comic;

import java.util.List;

public class BusquedaRequest {

    private List<Comic> comics;
    //private List<Evento> eventos;
    //private List<Wiki> wiki;
    //private List<RutaLectura> rutas;


    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }
}
