package com.api.tebeoteca.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.tebeoteca.dao.UsuarioRepositorio;
import com.api.tebeoteca.entidades.Cliente;
import com.api.tebeoteca.entidades.Usuario;
import com.api.tebeoteca.servicios.IUsuarioServicio;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {
	
	private UsuarioRepositorio usuarioRepositorio;
	
	public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

	@Override
	public void crearUsuario(Usuario u) {
		usuarioRepositorio.save(u);
		
	}

	@Override
	public void eliminarUsuario(int id) {
		usuarioRepositorio.deleteById(id);
		
	}

	@Override
	public void modificarUsuario(Usuario u) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Usuario> obtenerTodosLosUsuarios() {
		return usuarioRepositorio.findAll();
	}

	@Override
	public Optional<Usuario> obtenerUsuarioPorId(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Cliente> obtenerTodosLosSeguidos() {
		// TODO Auto-generated method stub
		return null;
	}

}
