package com.api.tebeoteca.dto;

public class LoginPeticion {
	
	private String email;
	private String contrasena;
	
	public LoginPeticion() {}
	
	public LoginPeticion(String email, String contrasena) {
		super();
		this.email = email;
		this.contrasena = contrasena;
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
	public void setPassword(String contrasena) {
		this.contrasena = contrasena;
	}

	
}
