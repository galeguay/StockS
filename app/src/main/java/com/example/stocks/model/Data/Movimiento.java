package com.example.stocks.model.Data;

import android.graphics.Movie;

public abstract class Movimiento implements Comparable<Movimiento>{

    private int idMovimiento;
    private int idProducto;
    private long fecha;
    private int cantidad;

    public Movimiento(int idMovimiento, int idProducto, long fecha, int cantidad) {

        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;

    }

    public Movimiento(int idProducto, long fecha, int cantidad){

        this.idMovimiento = -1;
        this.idProducto = idProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;

    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public long getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public int compareTo(Movimiento o) {
        long aux = this.fecha - o.getFecha();
        return (int) aux;
    }
}