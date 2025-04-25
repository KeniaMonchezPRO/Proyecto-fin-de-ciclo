package com.api.tebeoteca.entidades;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private String email;
	private String contrasena;
	private String descripcion;
	private String logo;
	private String banner;
	
	@Column(name = "num_seguidores")
	private int numSeguidores;
	
	@Column(name = "foto_perfil")
	private String fotoPerfil;
	
	@Column(name = "fecha_creacion")
	private String fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	private String fechaModificacion;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "clientes_usuarios",
		joinColumns = @JoinColumn(name = "id_cliente"),
		inverseJoinColumns = @JoinColumn(name = "id_usuario")
	)
	private List<Usuario> usuarios;

}
