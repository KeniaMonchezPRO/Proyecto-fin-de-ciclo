package com.api.tebeoteca.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.tebeoteca.entidades.Cliente;
import com.api.tebeoteca.entidades.Usuario;
import com.api.tebeoteca.servicios.IClienteServicio;
import com.api.tebeoteca.servicios.IUsuarioServicio;
import com.api.tebeoteca.utils.Log;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteControlador {
	
	private IClienteServicio clienteServicio;
	//private Log log;

	public ClienteControlador(IClienteServicio clienteServicio/*, Log log*/) {
		this.clienteServicio = clienteServicio;
		//this.log = log;
	}
	
	@GetMapping("/todos")
	public List<Cliente> obtenerTodosLosClientes() {
		//Log.info("INICIO OBTENER TODOS LOS CLIENTES");
		return clienteServicio.obtenerTodosLosClientes();
	}
	
    @GetMapping("/cliente")
    public Optional<Cliente> obtenerClientePorId(@RequestParam(name = "id", defaultValue = "null") int id) {
        return clienteServicio.obtenerClientePorId(id);
    }
}
