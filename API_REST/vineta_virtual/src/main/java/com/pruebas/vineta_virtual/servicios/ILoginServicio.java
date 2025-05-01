package com.pruebas.vineta_virtual.servicios;

import java.util.Optional;

import com.pruebas.vineta_virtual.entidades.Usuario;

public interface ILoginServicio {
	
	Optional<Usuario> buscarPorNombreUsuarioOEmail(String input);

}
