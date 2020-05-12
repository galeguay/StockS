package com.example.stocks.model.Data;

import java.io.Serializable;

public class Compra extends Movimiento {

    private float precioUnitario;

    public Compra(int idMovimiento, int codProducto, String fecha, int cantidad, float montoUnitario) {
        super(idMovimiento, codProducto, fecha, cantidad);
        this.precioUnitario = montoUnitario;
    }

    public Compra(int codProducto, String fecha, int cantidad, float montoUnitario) {
        super(codProducto, fecha, cantidad);
        this.precioUnitario = montoUnitario;
    }
/*
    public Compra(int codigoProducto, String fecha, int cantidad, double montoUnitario) {
        this.codigoProducto = codigoProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precioUnitario = montoUnitario;
    }*/

    public float getPrecioUnitario() {
        return precioUnitario;
    }

}