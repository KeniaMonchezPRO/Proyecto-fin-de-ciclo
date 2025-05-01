package com.pruebas.vineta_virtual.servicios;

import java.util.List;
import java.util.Optional;

import com.pruebas.vineta_virtual.entidades.Lector;

public interface ILectorServicio {
	
	List<Lector> obtenerTodosLosLectores();
	
    Lector buscarPorId(int id);
    
    Lector guardar(Lector lector);
    
    void eliminarLector(int id);

}
