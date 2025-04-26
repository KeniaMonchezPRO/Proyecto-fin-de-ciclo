package com.api.tebeoteca.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.tebeoteca.dao.ClienteRepositorio;
import com.api.tebeoteca.entidades.Cliente;
import com.api.tebeoteca.entidades.Usuario;
import com.api.tebeoteca.servicios.IClienteServicio;

@Service
public class ClienteServicioImpl implements IClienteServicio {
	
	private ClienteRepositorio clienteRepsitorio;
	
	public ClienteServicioImpl(ClienteRepositorio clienteRepositorio) {
		this.clienteRepsitorio = clienteRepositorio;
		
	}

	@Override
	public void crearCliente(Cliente c) {
		clienteRepsitorio.save(c);
	}

	@Override
	public void eliminarCliente(int id) {
		clienteRepsitorio.deleteById(id);		
	}

	@Override
	public void modificarCliente(Cliente c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cliente> obtenerTodosLosClientes() {
		return clienteRepsitorio.findAll();
	}

	@Override
	public Optional<Cliente> obtenerClientePorId(int id) {
		return clienteRepsitorio.findById(id);
	}

	@Override
	public List<Usuario> obtenerTodosLosSeguidores() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
