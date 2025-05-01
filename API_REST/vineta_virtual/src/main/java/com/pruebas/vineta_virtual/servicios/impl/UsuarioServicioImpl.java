package com.pruebas.vineta_virtual.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.vineta_virtual.dao.UsuarioRepositorio;
import com.pruebas.vineta_virtual.entidades.Usuario;
import com.pruebas.vineta_virtual.servicios.IUsuarioServicio;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Override
	public Optional<Usuario> obtenerUsuarioPorId(int id) {
		return usuarioRepositorio.findById(id);
	}

	@Override
	public List<Usuario> obtenerTodosLosUsuarios() {
		return usuarioRepositorio.findAll();
	}

	@Override
	public void eliminarUsuarioPorId(int id) {
		usuarioRepositorio.deleteById(id);
		
	}
	

}
