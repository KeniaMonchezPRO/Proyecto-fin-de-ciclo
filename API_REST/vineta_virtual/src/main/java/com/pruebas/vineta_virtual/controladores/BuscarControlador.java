package com.pruebas.vineta_virtual.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.dto.BusquedaResponseDTO;
import com.pruebas.vineta_virtual.servicios.IComicServicio;

@RestController
@RequestMapping("/api/buscar")
public class BuscarControlador {
	
	private IComicServicio comicServicio;
	//private IEventoServicio eventoServicio;
	//private IRutaServicio rutaServicio;
	//private IWikiEntradaServicio wikiEntradaServicio;
	
	public BuscarControlador(IComicServicio comicServicio) {
		this.comicServicio = comicServicio;
	}
	
	@GetMapping("/todo")
    public ResponseEntity<?> buscarTodo(@RequestParam String titulo) {
		BusquedaResponseDTO resultado = new BusquedaResponseDTO();
		try {
	        resultado.setComics(comicServicio.obtenerTodosLosComicsPorTitulo(titulo));
	        //resultado.setEventos(comicServicio.findByTituloContainingIgnoreCase(titulo));
	        //resultado.setWiki(comicServicio.findByTituloContainingIgnoreCase(titulo));
	        //resultado.setRutas(comicServicio.findByTituloContainingIgnoreCase(titulo));
	        return ResponseEntity.ok(resultado);
		} catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ENTRADA:" + resultado + "Error al hacer la búsqueda: " + e.getMessage());
		 }
    }

}
