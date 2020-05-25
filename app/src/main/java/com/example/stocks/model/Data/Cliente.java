package com.example.stocks.model.Data;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String apellido;
    private String cumpleanios;
    private String telefono;
    private String domicilio;

    public Cliente(int codCliente, String nombre, String apellido, String cumpleanios, String telefono, String direccion) {

        this.idCliente = codCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cumpleanios = cumpleanios;
        this.telefono = telefono;
        this.domicilio = direccion;

    }
    //constructor sin idCliente (cuando se lo da de alta en la db)
    public Cliente(String nombre, String apellido, String cumpleanios, String telefono, String domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cumpleanios = cumpleanios;
        this.telefono = telefono;
        this.domicilio = domicilio;
    }

    public int getIdCliente() {
        return idCliente;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

}