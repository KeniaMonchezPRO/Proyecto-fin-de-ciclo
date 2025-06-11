package com.pruebas.vineta_virtual.controladores;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.dto.RegistroClienteResponseDTO;
import com.pruebas.vineta_virtual.dto.RegistroLectorResponseDTO;
import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Lector;
import com.pruebas.vineta_virtual.servicios.IRegistroServicio;

@RestController
@RequestMapping("/api/v2/registro")
public class RegistroControlador {
	
	private IRegistroServicio registroServicio;
	
	public RegistroControlador(IRegistroServicio registroServicio) {
		this.registroServicio = registroServicio;
	}
	
	@PostMapping("/lector")
	 public ResponseEntity<?> registrarLector(@RequestBody Lector lector) {
		 Lector nuevoLector = registroServicio.registrarLector(lector);
		 RegistroLectorResponseDTO response = new RegistroLectorResponseDTO();
		 try {
			 response.setId(nuevoLector.getId());
			 response.setNombreUsuario(nuevoLector.getNombreUsuario());
			 response.setContrasena(nuevoLector.getContrasena());
			 response.setEmail(nuevoLector.getEmail());
			 response.setFechaNac(nuevoLector.getFechaNac());
			 return ResponseEntity.ok(response);
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ENTRADA:" + nuevoLector + "Error al registrar el lector: " + e.getMessage());
		 }
	            
		 //Lector nuevoLector = registroServicio.registrarLector(lector);
	     //return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLector);
	 }

	 @PostMapping("/cliente")
	 public ResponseEntity<?> registrarCliente(@RequestBody Cliente cliente) {
		 Cliente nuevoCliente = registroServicio.registrarCliente(cliente);
		 RegistroClienteResponseDTO response = new RegistroClienteResponseDTO();
		 try {
			 response.setId(nuevoCliente.getId());
			 response.setNombreUsuario(nuevoCliente.getNombreUsuario());
			 response.setEmail(nuevoCliente.getEmail());
			 response.setTipo(nuevoCliente.getTipoCliente());
			 response.setNombreEmpresa(nuevoCliente.getNombreCliente());
			 response.setCliente(nuevoCliente);
			 return ResponseEntity.ok(response);
		 } catch (Exception e) { 
			 return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
		 }
	 }
	
	 /*@PostMapping("/lector")
	 public ResponseEntity<?> registrarLector(@RequestBody Lector lector) {
		 Lector nuevoLector = registroServicio.registrarLector(lector);
		 try {
			 return ResponseEntity.status(HttpStatus.CREATED).body("Lector creado con éxito: " + nuevoLector);
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ENTRADA:" + nuevoLector + "Error al registrar el lector: " + e.getMessage());
		 }
	            
		 //Lector nuevoLector = registroServicio.registrarLector(lector);
	     //return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLector);
	 }

	 @PostMapping("/cliente")
	 public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
		 Cliente nuevoCliente = registroServicio.registrarCliente(cliente);
	     return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
	 }*/

}
