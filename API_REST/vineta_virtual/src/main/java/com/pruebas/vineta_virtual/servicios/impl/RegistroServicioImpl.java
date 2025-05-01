package com.pruebas.vineta_virtual.servicios.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.vineta_virtual.dao.ClienteRepositorio;
import com.pruebas.vineta_virtual.dao.LectorRepositorio;
import com.pruebas.vineta_virtual.dao.UsuarioRepositorio;
import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Lector;
import com.pruebas.vineta_virtual.entidades.Usuario;
import com.pruebas.vineta_virtual.servicios.IRegistroServicio;

@Service
public class RegistroServicioImpl implements IRegistroServicio {
	
    private LectorRepositorio lectorRepositorio;
    private ClienteRepositorio clienteRepositorio;
    
    public RegistroServicioImpl(LectorRepositorio lectorRepositorio, ClienteRepositorio clienteRepositorio) {
    	this.lectorRepositorio = lectorRepositorio;
    	this.clienteRepositorio = clienteRepositorio;
    }
    
    @Override
	public Lector registrarLector(Lector l) {
		return lectorRepositorio.save(l);
	}

	@Override
	public Cliente registrarCliente(Cliente c) {
		return clienteRepositorio.save(c);
	}

	/*@Autowired
    private LectorRepositorio lectorRepositorio;
	
	public Lector registrarLector(String nombreLector, String apellidosLector, LocalDate fechaNac, String email, String contrasena) {
        Lector nuevoLector = new Lector();
        nuevoLector.setNombreLector(nombreLector);
        nuevoLector.setApellidosLector(apellidosLector);
        nuevoLector.setFechaNac(fechaNac);
        nuevoLector.setEmail(email);
        nuevoLector.setContrasena(contrasena);

        return lectorRepositorio.save(nuevoLector);
    }*/

}
