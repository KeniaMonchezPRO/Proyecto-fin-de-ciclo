package com.example.vineta_virtual.login;

import com.example.vineta_virtual.cliente.Cliente;
import com.example.vineta_virtual.lector.Lector;

import java.io.Serializable;

//Recibe la respuesta de la API
public class LoginResponseDTO implements Serializable {
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNombreLector() {
        return nombreLector;
    }

    public String getEmail() {
        return email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNif() {
        return nif;
    }

    public String getBanner() {
        return banner;
    }

    public String getFechaCreacionEmpresa() {
        return fechaCreacionEmpresa;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Lector getLector() {
        return lector;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }
}
