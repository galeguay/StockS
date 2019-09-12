package com.example.stocks.model.Data;

public class Producto {

    private String nombre;
    private int codigo;
    private int cantidad;
    private String coleccion;

    public Producto(int codigo, String nombre,int cantidad, String coleccion) {

        this.nombre = nombre;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.coleccion = coleccion;
    }

    //GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getColeccion() {
        return coleccion;
    }

    public void setColeccion(String coleccion) {
        this.coleccion = coleccion;
    }


}
