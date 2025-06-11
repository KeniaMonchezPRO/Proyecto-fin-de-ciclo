package com.example.tebeoteca.general;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombreUsuario;
    private String tipoUsuario;
    private String nombreCliente;
    private String nombreLector;
    private String email;

    private String descripcion;

    private String nif;
    private String banner;
    private String fechaCreacionEmpresa;
    private String fotoPerfil;
    private String apellidos;
    private String fechaNacimiento;
    private Usuario usuario;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", nombreLector='" + nombreLector + '\'' +
                ", email='" + email + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nif='" + nif + '\'' +
                ", banner='" + banner + '\'' +
                ", fechaCreacionEmpresa='" + fechaCreacionEmpresa + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
