package com.pruebas.vineta_virtual.entidades;

import java.time.LocalDateTime;

import com.pruebas.vineta_virtual.entidades.enums.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public abstract class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nombre_usuario")
	private String nombreUsuario;
	
	private String email;
	
	private String contrasena;
	
	@Column(name = "foto_perfil")
	private String fotoPerfil;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_usuario")
	private TipoUsuario tipoUsuario;
	
	@Column(name = "fecha_registro", updatable = false)
	private LocalDateTime fechaRegistro;
	
	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;

	/*public Usuario () {
		
	}
	
	public Usuario(int id, String nombreUsuario, String email, String contrasena, String fotoPerfil,
			TipoUsuario tipoUsuario, LocalDateTime fechaRegistro, LocalDateTime fechaModificacion) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contrasena = contrasena;
		this.fotoPerfil = fotoPerfil;
		this.tipoUsuario = tipoUsuario;
		this.fechaRegistro = fechaRegistro;
		this.fechaModificacion = fechaModificacion;
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

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", email=" + email + ", contrasena="
				+ contrasena + ", fotoPerfil=" + fotoPerfil + ", tipoUsuario=" + tipoUsuario + ", fechaRegistro="
				+ fechaRegistro + ", fechaModificacion=" + fechaModificacion + "]";
	}*/

}
