package com.api.tebeoteca.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.tebeoteca.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByEmail(String email);
}
