package com.pruebas.vineta_virtual.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lectores")
public class Lector extends Usuario {
	
	@Column(name = "nombre_lector")
	private String nombreLector;
	
	@Column(name = "apellidos_lector")
	private String apellidosLector;
	
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNac;
	
	@PrePersist
	protected void prePersist() {
		this.setTipoUsuario(TipoUsuario.lector);
		this.setFechaRegistro(LocalDateTime.now());
		this.setFechaModificacion(LocalDateTime.now());
	}
	
	/*public Lector() {
		
	}

	public Lector(String nombreLector, String apellidosLector, LocalDate fechaNac) {
		super();
		this.nombreLector = nombreLector;
		this.apellidosLector = apellidosLector;
		this.fechaNac = fechaNac;
	}

	public String getNombreLector() {
		return nombreLector;
	}

	public void setNombreLector(String nombreLector) {
		this.nombreLector = nombreLector;
	}

	public String getApellidosLector() {
		return apellidosLector;
	}

	public void setApellidosLector(String apellidosLector) {
		this.apellidosLector = apellidosLector;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	@Override
	public String toString() {
		return "Lector [nombreLector=" + nombreLector + ", apellidosLector=" + apellidosLector + ", fechaNac="
				+ fechaNac + "]";
	}*/
}
