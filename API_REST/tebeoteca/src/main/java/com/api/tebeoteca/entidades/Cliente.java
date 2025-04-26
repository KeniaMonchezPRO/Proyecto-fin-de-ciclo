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
	
	public Cliente() {}

	public Cliente(int id, String nombre, String email, String contrasena, String descripcion, String logo,
			String banner, int numSeguidores, String fotoPerfil, String fechaCreacion, String fechaModificacion,
			List<Usuario> usuarios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
		this.descripcion = descripcion;
		this.logo = logo;
		this.banner = banner;
		this.numSeguidores = numSeguidores;
		this.fotoPerfil = fotoPerfil;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.usuarios = usuarios;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getContrasena() {
		return contrasena;
	}



	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getLogo() {
		return logo;
	}



	public void setLogo(String logo) {
		this.logo = logo;
	}



	public String getBanner() {
		return banner;
	}



	public void setBanner(String banner) {
		this.banner = banner;
	}



	public int getNumSeguidores() {
		return numSeguidores;
	}



	public void setNumSeguidores(int numSeguidores) {
		this.numSeguidores = numSeguidores;
	}



	public String getFotoPerfil() {
		return fotoPerfil;
	}



	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}



	public String getFechaCreacion() {
		return fechaCreacion;
	}



	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}



	public String getFechaModificacion() {
		return fechaModificacion;
	}



	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}



	public List<Usuario> getUsuarios() {
		return usuarios;
	}



	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}



	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", email=" + email + ", contrasena=" + contrasena
				+ ", descripcion=" + descripcion + ", logo=" + logo + ", banner=" + banner + ", numSeguidores="
				+ numSeguidores + ", fotoPerfil=" + fotoPerfil + ", fechaCreacion=" + fechaCreacion
				+ ", fechaModificacion=" + fechaModificacion + ", usuarios=" + usuarios + "]";
	}
	
	

}
