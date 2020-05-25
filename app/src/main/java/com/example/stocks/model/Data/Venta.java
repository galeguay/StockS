package com.example.stocks.model.Data;

public class Venta extends Movimiento{

    private float precioUnitario;
    private int idCliente;

    public Venta(int idMovimiento, int codProducto, String fecha, int cantidad, float montoUnitario, int idCliente) {
        super(idMovimiento, codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
        this.idCliente = idCliente;
    }

    public Venta(int codProducto, String fecha, int cantidad, float montoUnitario, int idCiente) {
        super(codProducto,fecha, cantidad);
        this.precioUnitario = montoUnitario;
        this.idCliente = idCliente;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public int getIdCliente() {
        return idCliente;
    }

}