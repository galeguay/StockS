package com.example.stocks.model.Data;

public class Compra extends Movimiento{

    private double precioUnitario;

    public Compra(int idMovimiento, int codProducto, long fecha, int cantidad, double montoUnitario) {
        super(idMovimiento, codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
    }

    public Compra(int codProducto, long fecha, int cantidad, double montoUnitario) {
        super(codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
    }
/*
    public Compra(int codigoProducto, String fecha, int cantidad, double montoUnitario) {
        this.codigoProducto = codigoProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precioUnitario = montoUnitario;
    }*/

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}