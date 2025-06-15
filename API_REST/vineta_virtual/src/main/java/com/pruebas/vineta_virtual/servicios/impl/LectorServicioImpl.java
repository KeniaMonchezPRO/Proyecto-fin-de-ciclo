package com.pruebas.vineta_virtual.servicios.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pruebas.vineta_virtual.controladores.AuthControlador;
import com.pruebas.vineta_virtual.dao.ComicRepositorio;
import com.pruebas.vineta_virtual.dao.LectorRepositorio;
import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.entidades.Lector;
import com.pruebas.vineta_virtual.servicios.ILectorServicio;

@Service
public class LectorServicioImpl implements ILectorServicio {

    private final AuthControlador authControlador;
	
	@Autowired
    private LectorRepositorio lectorRepositorio;
	
	@Autowired
	private ComicRepositorio comicRepositorio;

    LectorServicioImpl(AuthControlador authControlador) {
        this.authControlador = authControlador;
    }
	
	@Override
	public List<Lector> obtenerTodosLosLectores() {
		return lectorRepositorio.findAll();
	}

	@Override
	public Lector buscarPorId(int id) {
		return lectorRepositorio.findById(id)
	                .orElseThrow(() -> new RuntimeException("Lector no encontrado"));
	}

	@Override
	public Lector guardar(Lector lector) {
		return lectorRepositorio.save(lector);
	}

	@Override
	public void eliminarLector(int id) {
		lectorRepositorio.deleteById(id);
	}

	@Override
	public List<Lector> buscarPorNombreUsuario(String nombreUsuario) {
		return lectorRepositorio.findByNombreUsuarioContainingIgnoreCase(nombreUsuario);
	}

	@Override
	public boolean agregarFavorito(int idLector, int idComic) {
		Optional<Lector> lectorOpt = lectorRepositorio.findById(idLector);
	    Optional<Comic> comicOpt = comicRepositorio.findById(idComic);
	    if(lectorOpt.isPresent() && comicOpt.isPresent()) {
	    	Lector lector = lectorOpt.get();
	    	Comic comic = comicOpt.get();
	    	if(lector.getFavoritos().contains(comic)) {
	    		return false;
	    	} else {
	    		lector.getFavoritos().add(comic);
				lectorRepositorio.save(lector);
				return true;
	    	}
	    } else {
	    	return false;
	    }
	}

	@Override
	public boolean eliminarFavorito(int idLector, int idComic) {
		/*System.out.println("idLector: " + idLector + " idComic: " + idComic);
		Optional<Lector> lectorOpt = lectorRepositorio.findById(idLector);
	    Optional<Comic> comicOpt = comicRepositorio.findById(idComic);
	    if(lectorOpt.isPresent() && comicOpt.isPresent()) {
	    	Lector lector = lectorOpt.get();
	    	Comic comic = comicOpt.get();
	    	//System.out.println("lector" + comic);
	    	System.out.println("lector " + lector + " comic " + comic);
	    	if(lector.getFavoritos().contains(comic) || lector.getFavoritos().isEmpty()) {
	    		System.out.println("NO SE PUDO");
	    		System.out.println("favs lector: " + lector.getFavoritos());
	    		return false;
	    	} else {
	    		System.out.println("SE PUDO ELIMINAR");
	    		lector.getFavoritos().remove(idComic);
				lectorRepositorio.save(lector);
				return true;
	    	}
	    } else {
	    	System.out.println("lector y comic no existen");
	    	return false;
	    }*/
		Lector lector = lectorRepositorio.findById(idLector)
		        .orElseThrow(() -> new RuntimeException("Lector no encontrado"));
		    Comic comic = comicRepositorio.findById(idComic)
		        .orElseThrow(() -> new RuntimeException("Comic no encontrado"));

		    lector.getFavoritos().remove(comic);
		    lectorRepositorio.save(lector);
		    return true;
	}

	@Override
	public List<Comic> obtenerFavoritos(int idLector) {
		Optional<Lector> lectorOpt = lectorRepositorio.findById(idLector);
	    if (lectorOpt.isEmpty()) {
	        throw new RuntimeException("No se encontró el lector con ID: " + idLector);
	    }
	    return new ArrayList<>(lectorOpt.get().getFavoritos());
	}

	@Override
	public Lector editarLector(int idLector, Lector lector) {
		Optional<Lector> lectorOpt = lectorRepositorio.findById(idLector);

        if (lectorOpt.isPresent()) {
            Lector lectorExistente = lectorOpt.get();

            lectorExistente.setNombreUsuario(lector.getNombreUsuario());
            lectorExistente.setEmail(lector.getEmail());
            lectorExistente.setNombreLector(lector.getNombreLector());
            lectorExistente.setApellidosLector(lector.getApellidosLector());
            lectorExistente.setFechaModificacion(LocalDateTime.now());
            lectorExistente.setFechaNac(lector.getFechaNac());
            lectorExistente.setContrasena(lector.getContrasena());
            lectorExistente.setFotoPerfil(lector.getFotoPerfil());

            return lectorRepositorio.save(lectorExistente);
        } else {
            throw new RuntimeException("Lector no encontrado con Id: " + idLector);
        }
	}
	
	

}
