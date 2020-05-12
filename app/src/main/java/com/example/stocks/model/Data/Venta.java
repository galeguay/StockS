package com.example.stocks.model.Data;

public class Venta extends Movimiento{

    private double precioUnitario;
    private String cliente;

    public Venta(int idMovimiento, int codProducto, long fecha, int cantidad, double montoUnitario, String cliente) {
        super(idMovimiento, codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
        this.cliente = cliente;
    }

    public Venta(int codProducto, long fecha, int cantidad, double montoUnitario, String cliente) {
        super(codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
        this.cliente = cliente;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public String getCliente() {
        return cliente;
    }

}