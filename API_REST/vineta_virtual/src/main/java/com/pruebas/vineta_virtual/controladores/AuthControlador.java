package com.pruebas.vineta_virtual.controladores;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.dao.LectorRepositorio;
import com.pruebas.vineta_virtual.dto.RegistroLectorDTO;
import com.pruebas.vineta_virtual.entidades.Lector;
import com.pruebas.vineta_virtual.entidades.TipoUsuario;
import com.pruebas.vineta_virtual.servicios.impl.RegistroServicio;

@RestController
@RequestMapping("/hola")
public class AuthControlador {
	
	@Autowired
	private LectorRepositorio lectorRepositorio;
	
	@PostMapping("/registro/lector")
	public ResponseEntity<?> registrarLector(@RequestBody Lector nuevoLector) {
		System.out.println("ENTRA????");
		try {
			nuevoLector.setTipoUsuario(TipoUsuario.LECTOR);
			nuevoLector.setFechaRegistro(LocalDateTime.now());
			nuevoLector.setFechaModificacion(LocalDateTime.now());
			lectorRepositorio.save(nuevoLector); 
            return ResponseEntity.ok("Lector registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("ENTRADA:" + nuevoLector + "Error al registrar el lector: " + e.getMessage());
        }
    }

}
