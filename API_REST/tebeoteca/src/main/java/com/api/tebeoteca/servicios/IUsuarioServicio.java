package com.api.tebeoteca.servicios;

import java.util.List;
import java.util.Optional;

import com.api.tebeoteca.entidades.Cliente;
import com.api.tebeoteca.entidades.Usuario;

public interface IUsuarioServicio {
	
	void crearUsuario(Usuario u);
	
	void eliminarUsuario(int id);
	
	void modificarUsuario(Usuario u);
	
	List<Usuario> obtenerTodosLosUsuarios();
	
	Optional<Usuario> obtenerUsuarioPorId(int id);
	
	List<Cliente> obtenerTodosLosSeguidos();

}
