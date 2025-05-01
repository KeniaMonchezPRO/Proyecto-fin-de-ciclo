package com.pruebas.vineta_virtual.servicios.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pruebas.vineta_virtual.dao.UsuarioRepositorio;
import com.pruebas.vineta_virtual.entidades.Usuario;
import com.pruebas.vineta_virtual.servicios.ILoginServicio;

@Service
public class LoginServicioImpl implements ILoginServicio {

	private UsuarioRepositorio usuarioRepositorio;
	
	public LoginServicioImpl(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}
	
	@Override
	public Optional<Usuario> buscarPorNombreUsuarioOEmail(String datos) {
		return usuarioRepositorio.findByNombreUsuarioOrEmail(datos, datos);
	}

}
