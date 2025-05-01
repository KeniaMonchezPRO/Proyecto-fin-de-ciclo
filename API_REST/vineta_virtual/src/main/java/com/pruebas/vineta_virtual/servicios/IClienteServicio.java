package com.pruebas.vineta_virtual.servicios;

import java.util.List;

import com.pruebas.vineta_virtual.entidades.Cliente;

public interface IClienteServicio {
	
	List<Cliente> obtenerTodosLosClientes();
	
    Cliente buscarPorId(int id);
    
    Cliente guardar(Cliente cliente);
    
    void eliminar(int id);

}
