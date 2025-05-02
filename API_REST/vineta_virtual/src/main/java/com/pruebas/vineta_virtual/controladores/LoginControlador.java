package com.pruebas.vineta_virtual.controladores;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.entidades.Usuario;
import com.pruebas.vineta_virtual.servicios.ILoginServicio;

@RestController
@RequestMapping("/api/login")
public class LoginControlador {
	
	private ILoginServicio loginServicio;

    public LoginControlador(ILoginServicio loginServicio) {
        this.loginServicio = loginServicio;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Map<String, String> datos) {
        
    	String usuario = datos.get("usuario");
        String contrasena = datos.get("contrasena");
        
        System.out.println("INPUT RECIBIDO: " + datos);
        System.out.println("CONTRASEÑA RECIBIDA: " + contrasena);
        
        Optional<Usuario> usuarioOpt = loginServicio.buscarPorNombreUsuarioOEmail(usuario);

        if (usuarioOpt.isPresent()) {
        	System.out.println("USUARIO ENCONTRADO EN BD: " + usuarioOpt.get().getNombreUsuario());
            System.out.println("CONTRASEÑA EN BD: " + usuarioOpt.get().getContrasena());
        } 
        
        if(usuarioOpt.get().getContrasena().equals(contrasena)) {
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

}
