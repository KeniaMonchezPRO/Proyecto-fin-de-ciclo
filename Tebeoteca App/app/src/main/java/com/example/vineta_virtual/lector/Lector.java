package com.example.vineta_virtual.lector;

import java.io.Serializable;

public class Lector implements Serializable {
    private int id;
    private String nombreUsuario;
    private String email;
    private String fotoPerfil;
    private String tipoUsuario;
    private String nombreLector;
    private String apellidosLector;
    private String fechaNac;
    private String contrasena;

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getNombreLector() {
        return nombreLector;
    }

    public String getApellidosLector() {
        return apellidosLector;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setNombreLector(String nombreLector) {
        this.nombreLector = nombreLector;
    }

    public void setApellidosLector(String apellidosLector) {
        this.apellidosLector = apellidosLector;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
