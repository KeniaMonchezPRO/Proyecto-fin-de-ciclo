package com.example.tebeoteca.cliente;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private String nombreUsuario;
    private String email;
    private String fotoPerfil;
    private String tipoUsuario;
    private String nombreCliente;
    private String fechaCreacionEmpresa;
    private String descripcion;
    private String banner;
    private String tipoCliente;
    private String nif;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFechaCreacionEmpresa() {
        return fechaCreacionEmpresa;
    }

    public void setFechaCreacionEmpresa(String fechaCreacionEmpresa) {
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

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", fechaCreacionEmpresa='" + fechaCreacionEmpresa + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", banner='" + banner + '\'' +
                ", tipoCliente='" + tipoCliente + '\'' +
                ", nif='" + nif + '\'' +
                '}';
    }
}
