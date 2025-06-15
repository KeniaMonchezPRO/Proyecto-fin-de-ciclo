package com.example.vineta_virtual.registro;

import com.example.vineta_virtual.cliente.Cliente;

import java.io.Serializable;

public class RegistroClienteResponseDTO implements Serializable {

    private int id;
    private String nombreUsuario;
    private String email;
    private String nombreEmpresa;
    private String tipo;
    private Cliente cliente;
    private String contrasena;

    public RegistroClienteResponseDTO(int id, String nombreUsuario, String email, String nombreEmpresa, String tipo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.nombreEmpresa = nombreEmpresa;
        this.tipo = tipo;
    }

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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
