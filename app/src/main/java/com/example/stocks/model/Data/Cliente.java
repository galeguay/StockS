package com.example.stocks.model.Data;

public class Cliente {

    private int codCliente;
    private String nombre;
    private String apellido;
    private String cumpleanios;
    private String telefono;
    private String direccion;

    public Cliente(int codCliente, String nombre, String apellido, String cumpleanios, String telefono, String direccion) {

        this.codCliente = codCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cumpleanios = cumpleanios;
        this.telefono = telefono;
        this.direccion = direccion;

    }
    //constructor sin codCliente (cuando se lo da de alta en la db)
    public Cliente(String nombre, String apellido, String cumpleanios, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cumpleanios = cumpleanios;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCumpleanios() {
        return cumpleanios;
    }

    public void setCumpleanios(String cumpleanios) {
        this.cumpleanios = cumpleanios;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}