package com.example.tebeoteca.lector;

import java.io.Serializable;

public class Lector implements Serializable {
    private String id;
    private String nombreUsuario;
    private String email;
    private String fotoPerfil;
    private String tipoUsuario;
    private String nombreLector;
    private String apellidosLector;
    private String fechaNac;

    public String getId() {
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
}
