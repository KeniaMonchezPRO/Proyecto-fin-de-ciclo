package com.pruebas.vineta_virtual.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.pruebas.vineta_virtual.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	//@Query("SELECT u FROM Usuario u WHERE TYPE(u) = Lector")
	//List<Usuario> findAllLectores();
	
	//List<Lector> findByTipoUsuario(TipoUsuario tipoUsuario);
	
	//Optional<Usuario> obtenerPorNombreUsuario(String nombreUsuario);
	
	Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);

}
