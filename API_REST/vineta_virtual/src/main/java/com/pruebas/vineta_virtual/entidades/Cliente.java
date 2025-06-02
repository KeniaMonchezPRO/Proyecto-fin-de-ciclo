package com.pruebas.vineta_virtual.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.pruebas.vineta_virtual.entidades.enums.TipoCliente;
import com.pruebas.vineta_virtual.entidades.enums.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Cliente extends Usuario {
	
	@Column(name = "nombre_cliente")
	private String nombreCliente;
	
	@Column(name = "fecha_creacion_empresa")
	private LocalDate fechaCreacionEmpresa;
	
	private String descripcion;
	
	private String banner;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_cliente")
	private TipoCliente tipoCliente;
	
	@PrePersist
	protected void prePersist() {
		this.setTipoUsuario(TipoUsuario.cliente);
		this.setFechaRegistro(LocalDateTime.now());
		this.setFechaModificacion(LocalDateTime.now());
	}
	
	private String nif;

	/*public Cliente() {
		
	}
	
	public Cliente(String nombreCliente, LocalDate fechaCreacionEmpresa, String descripcion, String banner,
			TipoCliente tipoCliente) {
		super();
		this.nombreCliente = nombreCliente;
		this.fechaCreacionEmpresa = fechaCreacionEmpresa;
		this.descripcion = descripcion;
		this.banner = banner;
		this.tipoCliente = tipoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public LocalDate getFechaCreacionEmpresa() {
		return fechaCreacionEmpresa;
	}

	public void setFechaCreacionEmpresa(LocalDate fechaCreacionEmpresa) {
		this.fechaCreacionEmpresa = fechaCreacionEmpresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	@Override
	public String toString() {
		return "Cliente [nombreCliente=" + nombreCliente + ", fechaCreacionEmpresa=" + fechaCreacionEmpresa
				+ ", descripcion=" + descripcion + ", banner=" + banner + ", tipoCliente=" + tipoCliente + "]";
	}*/
}
