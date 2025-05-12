package com.pruebas.vineta_virtual.dto;

public class LoginResponseDTO {
	private int id;
	private String nombreUsuario;
	private String tipoUsuario;
	private String email;
	private String nombreCliente;
	private String nombreLector;
	
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
	
	
	
	

}
