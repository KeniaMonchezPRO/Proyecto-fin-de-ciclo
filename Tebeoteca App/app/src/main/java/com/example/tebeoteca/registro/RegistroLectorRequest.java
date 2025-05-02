package com.example.tebeoteca.registro;

public class RegistroLectorRequest {
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombreLector;
    private String apellidosLector;
    private String fechaNac;

    public RegistroLectorRequest(String nombreUsuario, String email, String contrasena, String nombreLector, String apellidosLector, String fechaNac) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreLector = nombreLector;
        this.apellidosLector = apellidosLector;
        this.fechaNac = fechaNac;
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

    public String getNombreLector() {
        return nombreLector;
    }

    public String getApellidosLector() {
        return apellidosLector;
    }

    public String getFechaNac() {
        return fechaNac;
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

    public void setNombreLector(String nombreLector) {
        this.nombreLector = nombreLector;
    }

    public void setApellidosLector(String apellidosLector) {
        this.apellidosLector = apellidosLector;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }
}
