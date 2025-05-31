package com.pruebas.vineta_virtual.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.vineta_virtual.dao.ClienteRepositorio;
import com.pruebas.vineta_virtual.dto.AnadirComicRequestDTO;
import com.pruebas.vineta_virtual.dto.AnadirComicResponseDTO;
import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.entidades.Usuario;
import com.pruebas.vineta_virtual.servicios.IClienteServicio;
import com.pruebas.vineta_virtual.servicios.IComicServicio;

@RestController
@RequestMapping("/api/comics")
public class ComicControlador {
	
	private IComicServicio comicServicio;
	
	private IClienteServicio clienteServicio;
	
	public ComicControlador(IComicServicio comicServicio, IClienteServicio clienteServicio) {
		this.comicServicio = comicServicio;
		this.clienteServicio = clienteServicio;
	}
	
	@PostMapping("/anadir")
    public ResponseEntity<?> crearProducto(@RequestBody AnadirComicRequestDTO dto) {
		Comic nuevoComic = new Comic();
		nuevoComic.setPortada(dto.getPortada());
        nuevoComic.setTitulo(dto.getTitulo());
        nuevoComic.setDescripcion(dto.getDescripcion());
        
        Optional<Cliente> clienteOpt = clienteServicio.buscarPorId(dto.getClienteId());
        
        if (!clienteOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Cliente no encontrado con ID: " + dto.getClienteId());
        }
        Cliente cliente = clienteOpt.get();
        nuevoComic.setCliente(cliente);
        nuevoComic.setAudiencia(dto.getAudiencia());
        nuevoComic.setSelloEditorial(dto.getSelloEditorial());
        nuevoComic.setFechaLanzamiento(dto.getFechaLanzamiento());
        nuevoComic.setEstado(dto.getEstado());
        nuevoComic.setAutores(dto.getAutores());
        nuevoComic.setPaisOrigen(dto.getPaisOrigen());
        nuevoComic.setIdiomaOriginal(dto.getIdiomaOriginal());
        nuevoComic.setCategorias(dto.getCategorias());
        
        Comic guardado = comicServicio.crearComic(nuevoComic);
        
        AnadirComicResponseDTO response = new AnadirComicResponseDTO();
        
        try {
			 response.setId(guardado.getId());
			 response.setClienteId(guardado.getCliente().getId());
			 response.setPortada(guardado.getPortada());
			 response.setTitulo(guardado.getTitulo());
			 response.setDescripcion(guardado.getDescripcion());			 
			 return ResponseEntity.ok(guardado);
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ENTRADA:" + guardado + "Error al registrar el comic: " + e.getMessage());
		 }
    }
	
	@GetMapping("/todos")
    public List<Comic> obtenerTodos() {
        return comicServicio.obtenerTodosLosComics();
    }
	
    @GetMapping("/cliente/{clienteId}")
    public List<Comic> obtenerComicsPorCliente(@PathVariable int clienteId) {
        return comicServicio.obtenerComicsPorCliente(clienteId);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Comic> obtenerComicsPorCategoria(@PathVariable String categoria) {
        return comicServicio.obtenerComicsPorCategoria(categoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comic> obtenerComicPorId(@PathVariable int id) {
    	Comic comic = comicServicio.obtenerComicPorId(id);
        if (comic != null)
            return ResponseEntity.ok(comic);
        return ResponseEntity.notFound().build();
    }

}
