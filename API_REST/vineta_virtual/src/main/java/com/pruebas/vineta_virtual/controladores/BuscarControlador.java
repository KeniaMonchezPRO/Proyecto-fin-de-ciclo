package com.pruebas.vineta_virtual.controladores;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.dto.BusquedaResponseDTO;
import com.pruebas.vineta_virtual.servicios.IClienteServicio;
import com.pruebas.vineta_virtual.servicios.IComicServicio;
import com.pruebas.vineta_virtual.servicios.ILectorServicio;
import com.pruebas.vineta_virtual.servicios.IUsuarioServicio;

@RestController
@RequestMapping("/api/buscar")
public class BuscarControlador {
	
	private IComicServicio comicServicio;
	private IClienteServicio clienteServicio;
	private ILectorServicio lectorServicio;
	//private IEventoServicio eventoServicio;
	//private IRutaServicio rutaServicio;
	//private IWikiEntradaServicio wikiEntradaServicio;
	
	public BuscarControlador(IComicServicio comicServicio, IClienteServicio clienteServicio, ILectorServicio lectorServicio) {
		this.comicServicio = comicServicio;
		this.clienteServicio = clienteServicio;
		this.lectorServicio = lectorServicio;
	}
	
	@GetMapping("/todo")
    public ResponseEntity<?> buscarTodo(@RequestParam String titulo) {
		BusquedaResponseDTO resultado = new BusquedaResponseDTO();
		try {
	        resultado.setComics(comicServicio.obtenerTodosLosComicsPorTitulo(titulo));
	        //resultado.setEventos(comicServicio.findByTituloContainingIgnoreCase(titulo));
	        //resultado.setWiki(comicServicio.findByTituloContainingIgnoreCase(titulo));
	        //resultado.setRutas(comicServicio.findByTituloContainingIgnoreCase(titulo));
	        resultado.setLectores(lectorServicio.buscarPorNombreUsuario(titulo));
	        //resultado.setClientes(Collections.emptyList()); // vacíos
	        resultado.setClientes(clienteServicio.buscarPorNombreUsuario(titulo));
	        //resultado.setLectores(Collections.emptyList());
	        return ResponseEntity.ok(resultado);
		} catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ENTRADA:" + resultado + "Error al hacer la búsqueda: " + e.getMessage());
		 }
    }

}
