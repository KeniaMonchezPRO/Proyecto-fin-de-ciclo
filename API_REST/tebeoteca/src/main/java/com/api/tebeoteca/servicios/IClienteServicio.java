package com.api.tebeoteca.servicios;

import java.util.List;
import java.util.Optional;

import com.api.tebeoteca.entidades.Cliente;
import com.api.tebeoteca.entidades.Usuario;

public interface IClienteServicio {
	
void crearCliente(Cliente c);
	
	void eliminarCliente(int id);
	
	void modificarCliente(Cliente c);
	
	List<Cliente> obtenerTodosLosClientes();
	
	Optional<Cliente> obtenerClientePorId(int id);
	
	List<Usuario> obtenerTodosLosSeguidores();

}
