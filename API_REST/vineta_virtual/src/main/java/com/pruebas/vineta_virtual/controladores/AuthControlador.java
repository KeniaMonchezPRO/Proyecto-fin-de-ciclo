package com.pruebas.vineta_virtual.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.dao.ClienteRepositorio;
import com.pruebas.vineta_virtual.dao.LectorRepositorio;
import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Lector;

@RestController
@RequestMapping("/api/v1")
public class AuthControlador {
	
	@Autowired
	private LectorRepositorio lectorRepositorio;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@PostMapping("/registro/lector")
	public ResponseEntity<?> registrarLector(@RequestBody Lector nuevoLector) {
		System.out.println("ENTRA????");
		try {
			//nuevoLector.setTipoUsuario(TipoUsuario.lector);
			//nuevoLector.setFechaRegistro(LocalDateTime.now());
			//nuevoLector.setFechaModificacion(LocalDateTime.now());
			lectorRepositorio.save(nuevoLector); 
            return ResponseEntity.ok("Lector registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("ENTRADA:" + nuevoLector + "Error al registrar el lector: " + e.getMessage());
        }
    }
	
	@PostMapping("/registro/cliente")
	public ResponseEntity<?> registrarCliente(@RequestBody Cliente nuevoCliente) {
		try {
			clienteRepositorio.save(nuevoCliente); 
            return ResponseEntity.ok("Cliente registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("ENTRADA:" + nuevoCliente + "Error al registrar el cliente: " + e.getMessage());
        }
    }
	
	@GetMapping("/lectores")
	public List<Lector> lectores() {
		return lectorRepositorio.findAll();
	}
	
	@GetMapping("/clientes")
	public List<Cliente> clientes() {
		return clienteRepositorio.findAll();
	}

}
