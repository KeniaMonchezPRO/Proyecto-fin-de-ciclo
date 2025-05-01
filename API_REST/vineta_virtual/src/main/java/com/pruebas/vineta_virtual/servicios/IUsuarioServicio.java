package com.pruebas.vineta_virtual.servicios;

import java.util.List;
import java.util.Optional;

import com.pruebas.vineta_virtual.entidades.Usuario;

public interface IUsuarioServicio {
	
	Optional<Usuario> obtenerUsuarioPorId(int id);
	
	List<Usuario> obtenerTodosLosUsuarios();
	
	void eliminarUsuarioPorId(int id);

}
