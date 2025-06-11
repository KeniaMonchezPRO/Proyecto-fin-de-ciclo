package com.pruebas.vineta_virtual.dto;

import com.pruebas.vineta_virtual.entidades.Cliente;
import com.pruebas.vineta_virtual.entidades.Lector;

public class LoginResponseDTO {
	private int id;
	private String nombreUsuario;
	private String tipoUsuario;
	private String email;
	private String nombreCliente;
	private String nombreLector;
	private String descripcion;
	private String nif;
	private String banner;
	private String fechaCreacionEmpresa;
	private String fotoPerfil;
	private String apellidosLector;
	private String fechaNacimiento;
	private Lector lector;
	private Cliente cliente;
	
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
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getNombreLector() {
		return nombreLector;
	}
	public void setNombreLector(String nombreLector) {
		this.nombreLector = nombreLector;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getFechaCreacionEmpresa() {
		return fechaCreacionEmpresa;
	}
	public void setFechaCreacionEmpresa(String fechaCreacionEmpresa) {
		this.fechaCreacionEmpresa = fechaCreacionEmpresa;
	}
	public String getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	public String getApellidosLector() {
		return apellidosLector;
	}
	public void setApellidosLector(String apellidosLector) {
		this.apellidosLector = apellidosLector;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Lector getLector() {
		return lector;
	}
	public void setLector(Lector lector) {
		this.lector = lector;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	
	
	

}
