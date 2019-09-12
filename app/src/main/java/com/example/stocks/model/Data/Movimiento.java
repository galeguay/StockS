package com.example.stocks.model.Data;

public class Movimiento {

    private int codMovimiento;
    private int codProducto;
    private long fecha;
    private int cantidad;


    void Movimiento(int codMovimiento, int cosProducto, long fecha, int cantidad){

        this.codMovimiento = codMovimiento;
        this.codProducto = codProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;

    }
    //constructor sin pedir codMovimiento (para cuando se lo da de alta en la db)
    void Movimiento(int codProducto, long fecha, int cantidad){

        this.codProducto = codProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;

    }

    public int getCodMovimiento() {
        return codMovimiento;
    }

    public int getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}