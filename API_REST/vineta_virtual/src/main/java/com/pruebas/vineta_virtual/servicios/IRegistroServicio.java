package com.pruebas.vineta_virtual.servicios;

import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Lector;

public interface IRegistroServicio {
	
	Lector registrarLector(Lector l);
	
	Cliente registrarCliente(Cliente c);

}
