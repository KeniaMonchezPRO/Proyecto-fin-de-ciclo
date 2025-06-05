package com.pruebas.vineta_virtual.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.vineta_virtual.dao.ClienteRepositorio;
import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.servicios.IClienteServicio;

@Service
public class ClienteServicioImpl implements IClienteServicio {
	
	@Autowired
    private ClienteRepositorio clienteRepositorio;
	
	@Override
	public List<Cliente> obtenerTodosLosClientes() {
		return clienteRepositorio.findAll();
	}

	@Override
	public Optional<Cliente> buscarPorId(int id) {
		/*return clienteRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));*/
		return clienteRepositorio.findById(id);
	}

	@Override
	public Cliente guardar(Cliente cliente) {
		return clienteRepositorio.save(cliente);
	}

	@Override
	public void eliminar(int id) {
		clienteRepositorio.deleteById(id);
	}

	@Override
	public List<Cliente> buscarPorNombreUsuario(String nombreUsuario) {
		return clienteRepositorio.findByNombreUsuarioContainingIgnoreCase(nombreUsuario);
	}

}
