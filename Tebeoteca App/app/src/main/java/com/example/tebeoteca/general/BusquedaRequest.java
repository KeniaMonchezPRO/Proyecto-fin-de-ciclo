package com.example.tebeoteca.general;

import com.example.tebeoteca.cliente.Cliente;
import com.example.tebeoteca.cliente.comic.Comic;
import com.example.tebeoteca.lector.Lector;

import java.util.List;

public class BusquedaRequest {

    private List<Comic> comics;
    private List<Cliente> clientes;
    private List<Lector> lectores;
    //private List<Evento> eventos;
    //private List<Wiki> wiki;
    //private List<RutaLectura> rutas;


    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setCliente(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Lector> getLectores() {
        return lectores;
    }

    public void setLectores(List<Lector> lectores) {
        this.lectores = lectores;
    }
}
