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
//@AllArgsConstructor
//@NoArgsConstructor
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
	private String email;
	private String contrasena;
	
	@Column(name = "foto_perfil")
	private String fotoPerfil;
	
	@Column(name = "fecha_creacion")
	private String fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	private String fechaModificacion;
	
	@ManyToMany(mappedBy = "usuarios")
	private Set<Cliente> clientes;

	public Usuario() {}
	
	public Usuario(int id, String nombreUsuario, String nombre, String apellidos, String email, String contrasena,
			String fotoPerfil, String fechaCreacion, String fechaModificacion, Set<Cliente> clientes) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.fotoPerfil = fotoPerfil;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.clientes = clientes;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombreUsuario() {
		return nombreUsuario;
	}



	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getApellidos() {
		return apellidos;
	}



	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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



	/*public Set<Cliente> getClientes() {
		return clientes;
	}



	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}*/



	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", nombre=" + nombre + ", apellidos="
				+ apellidos + ", email=" + email + ", contrasena=" + contrasena + ", fotoPerfil=" + fotoPerfil
				+ ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + "]";
	}
	
	

}
