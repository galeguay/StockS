package com.example.stocks.model.Data;

public class Producto implements Comparable<Producto>{

    private String nombre;
    private int codigo;
    private int cantidad;
    private String linea;
    private int color;

    public Producto(int codigo, String nombre,int cantidad, String linea) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.linea = linea;

    }

    public Producto(int codigo) {

        this.codigo = codigo;

    }

    //COMPARABLE

    @Override
    public int compareTo(Producto producto) {
        return this.codigo - producto.codigo;
    }

    //COMPARA POR CODIGO
    public boolean equals(Object o){
        if (o instanceof Producto){
            Producto otro = (Producto)o;
            if (this.codigo == otro.codigo){
                return true;
            }else return false;

        }else return false;

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

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }


    //METODOS DE LA CLASE
    public void sumarACantidad(int suma){
        this.cantidad = this.cantidad + suma;
    }

    public void restarACantidad(int suma){
        this.cantidad = this.cantidad - suma;
    }
}
