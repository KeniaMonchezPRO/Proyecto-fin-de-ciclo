package com.api.tebeoteca.entidades;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nombre_usuario")
	private String nombreUsuario;
	
	private String nombre;
	private String apellidos;
	private String contrasena;
	
	@Column(name = "foto_perfil")
	private String fotoPerfil;
	
	@Column(name = "fecha_creacion")
	private String fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	private String fechaModificacion;
	
	@ManyToMany(mappedBy = "usuarios")
	private Set<Cliente> clientes;

}
