package com.api.tebeoteca.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.tebeoteca.dao.UsuarioRepositorio;
import com.api.tebeoteca.dto.LoginPeticion;
import com.api.tebeoteca.entidades.Usuario;
import com.api.tebeoteca.utils.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControlador {
		
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginPeticion loginPeticion) {
		
		Optional<Usuario> usuario = usuarioRepositorio.findByEmail(loginPeticion.getEmail());
		if(usuario.isEmpty()) {
			return ResponseEntity.status(401).body("Usuario no encontrado");
		}
		Usuario u = usuario.get();
		if(u.getContrasena().equals(loginPeticion.getEmail())) {
			String tokenUsuario = jwtUtil.generarToken(u.getEmail());
			return ResponseEntity.ok().body("Token de acceso: " + tokenUsuario);
		}
		throw new RuntimeException("Credenciales invalidas");
	}
	
	/*@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginPeticion loginPeticion) {
		Optional<Usuario> usuario = usuarioRepositorio.findByEmail(loginPeticion.getEmail());
		if(usuario.isEmpty()) {
			return ResponseEntity.status(401).body("Usuario no encontrado");
		}
		Usuario getUsuario = usuario.get();
		if(!getUsuario.getContrasena().equals(loginPeticion.getContrasena()) ) {
			return ResponseEntity.status(401).body("Usuario o contrasena incorrecta");
		}
		
		String token = jwtUtil.generarToken(getUsuario.getEmail());
		System.out.print("TOKEN: " + token);
		return ResponseEntity.ok("Login correcto");
	}*/

}
