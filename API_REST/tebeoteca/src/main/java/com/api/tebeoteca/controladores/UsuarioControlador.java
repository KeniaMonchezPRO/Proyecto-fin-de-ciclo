package com.api.tebeoteca.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tebeoteca.entidades.Usuario;
import com.api.tebeoteca.servicios.IClienteServicio;
import com.api.tebeoteca.servicios.IUsuarioServicio;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {
	
	private IUsuarioServicio usuarioServicio;
	
	public UsuarioControlador(IUsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}
	
	@GetMapping("/todos")
	public List<Usuario> obtenerTodosLosUsuarios() {
		return usuarioServicio.obtenerTodosLosUsuarios();
	}

}
