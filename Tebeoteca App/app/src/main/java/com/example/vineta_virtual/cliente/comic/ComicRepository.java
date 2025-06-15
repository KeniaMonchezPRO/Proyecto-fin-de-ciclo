package com.example.vineta_virtual.cliente.comic;

import java.util.ArrayList;
import java.util.List;

//Esto nos sirve como un "almacén global" temporal accesible desde cualquier parte de la app.
public class ComicRepository {
    private static List<Comic> listaComics = new ArrayList<>();

    public static List<Comic> getComics() {
        return listaComics;
    }

    public static void agregarComic(Comic comic) {
        listaComics.add(comic);
    }
}

