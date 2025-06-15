package com.pruebas.vineta_virtual.servicios;

import java.util.List;
import java.util.Optional;

import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.entidades.Lector;

public interface ILectorServicio {
	
	List<Lector> obtenerTodosLosLectores();
	
    Lector buscarPorId(int id);
    
    Lector guardar(Lector lector);
    
    void eliminarLector(int id);
    
    List<Lector> buscarPorNombreUsuario(String nombreUsuario);
    
    boolean agregarFavorito(int idLector, int idComic);
    
    boolean eliminarFavorito(int idLector, int idComic);
    
    List<Comic> obtenerFavoritos(int idLector);
    
    Lector editarLector(int idLector, Lector lector);
    

}
