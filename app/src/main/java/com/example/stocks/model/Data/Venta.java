package com.example.stocks.model.Data;

public class Venta extends Movimiento{

    private float precioUnitario;
    private String cliente;

    public Venta(int idMovimiento, int codProducto, String fecha, int cantidad, float montoUnitario, String cliente) {
        super(idMovimiento, codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
        this.cliente = cliente;
    }

    public Venta(int codProducto, String fecha, int cantidad, float montoUnitario, String cliente) {
        super(codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
        this.cliente = cliente;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public String getCliente() {
        return cliente;
    }

}