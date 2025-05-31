package com.pruebas.vineta_virtual.servicios;

import java.util.List;
import java.util.Optional;

import com.pruebas.vineta_virtual.entidades.Cliente;

public interface IClienteServicio {
	
	List<Cliente> obtenerTodosLosClientes();
	
    Optional<Cliente> buscarPorId(int id);
    
    Cliente guardar(Cliente cliente);
    
    void eliminar(int id);

}
