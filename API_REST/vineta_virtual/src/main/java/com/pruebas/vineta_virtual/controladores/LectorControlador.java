package com.pruebas.vineta_virtual.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.servicios.ICompraServicio;
import com.pruebas.vineta_virtual.servicios.ILectorServicio;

@RestController
@RequestMapping("/api/lector")
public class LectorControlador {
	
	private ILectorServicio lectorServicio;
	
	private ICompraServicio compraServicio;
	
	public LectorControlador(ILectorServicio lectorServicio, ICompraServicio compraServicio) {
		this.lectorServicio = lectorServicio;
		this.compraServicio= compraServicio;
	}
	
	@GetMapping("/{idLector}/favoritos")
	public ResponseEntity<?> obtenerFavoritos(@PathVariable int idLector) {
	    try {
	        List<Comic> favoritos = lectorServicio.obtenerFavoritos(idLector);
	        return ResponseEntity.ok(favoritos);
	    } catch (RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	}
	
	@PostMapping("/{idLector}/favoritos/{idComic}")
	public ResponseEntity<?> agregarFavorito(@PathVariable int idLector, @PathVariable int idComic) {
		boolean response = lectorServicio.agregarFavorito(idLector, idComic);
		try {
			if(response) {
				return ResponseEntity.ok("Favorito agregado correctamente"); 
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El favorito ya ha sido agregado");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el favorito");
		}
	} 

	@DeleteMapping("/{idLector}/favoritos/{idComic}")
	public ResponseEntity<?> eliminarFavorito(@PathVariable int idLector, @PathVariable int idComic) {
		//System.out.println("idLector: " + idLector + " idComic: " + idComic);
		boolean response = lectorServicio.eliminarFavorito(idLector, idComic);
	    /*try{
	    	if(response) {
	    		return ResponseEntity.ok().body("Favorito eliminado");
	    	} else {
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el favorito"); 
	    	}
	    } catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el favorito");
		}*/
		return ResponseEntity.ok().body("Favorito eliminado");
	}
	
	@PostMapping("/{idLector}/comprar/{idComic}")
    public ResponseEntity<Void> comprarComic(@PathVariable int idLector, @PathVariable int idComic) {
		compraServicio.generarCompra(idLector, idComic);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idLector}/compras")
    public ResponseEntity<List<Comic>> getComprados(@PathVariable int idLector) {
        return ResponseEntity.ok(compraServicio.obtenerCompras(idLector));
    }
	
	

}
