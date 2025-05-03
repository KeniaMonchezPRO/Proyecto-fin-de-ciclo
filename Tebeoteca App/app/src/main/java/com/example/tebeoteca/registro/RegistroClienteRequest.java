package com.example.tebeoteca.registro;

public class RegistroClienteRequest {

    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombreCliente;
    private String fechaCreacionEmpresa;
    private String tipoCliente;

    public RegistroClienteRequest(String nombreUsuario, String email, String contrasena, String nombreCliente, String fechaCreacionEmpresa, String tipoCliente) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreCliente = nombreCliente;
        this.fechaCreacionEmpresa = fechaCreacionEmpresa;
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

    public String getFechaCreacionEmpresa() {
        return fechaCreacionEmpresa;
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

    public void setFechaCreacionEmpresa(String fechaCreacionEmpresa) {
        this.fechaCreacionEmpresa = fechaCreacionEmpresa;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
