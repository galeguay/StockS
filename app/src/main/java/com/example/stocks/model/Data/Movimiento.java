package com.example.stocks.model.Data;

import android.graphics.Movie;

import java.io.Serializable;

public abstract class Movimiento implements Comparable<Movimiento>, Serializable {

    private int idMovimiento;
    private int idProducto;
    private Fecha fecha;
    private int cantidad;

    public Movimiento(int idMovimiento, int idProducto, String fecha, int cantidad) {

        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.fecha = new Fecha(fecha);
        this.cantidad = cantidad;

    }

    public Movimiento(int idProducto, String fecha, int cantidad){

        this.idMovimiento = -1;
        this.idProducto = idProducto;
        this.fecha = new Fecha(fecha);
        this.cantidad = cantidad;

    }

    @Override
    public int compareTo(Movimiento m) {
        long aux = this.getFecha().getLongAMDH() - m.getFecha().getLongAMDH();
        return (int) aux;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }
}