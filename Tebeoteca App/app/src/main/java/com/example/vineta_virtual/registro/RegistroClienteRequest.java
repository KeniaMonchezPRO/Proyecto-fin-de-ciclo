package com.example.vineta_virtual.registro;

public class RegistroClienteRequest {

    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombreCliente;
    private String tipoCliente;

    public RegistroClienteRequest(String nombreUsuario, String email, String contrasena, String nombreCliente, String tipoCliente) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreCliente = nombreCliente;
        this.tipoCliente = tipoCliente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
