package com.example.stocks.model.Data;

public class Venta{

    private int codigoMovimiento;
    private int codigoProducto;
    private String fecha;
    private int cantidad;
    private double montoUnitario;
    private String cliente;

    public Venta(int codigoMovimiento, int codigoProducto, String fecha, int cantidad, double montoUnitario, String cliente) {
        this.codigoMovimiento = codigoMovimiento;
        this.codigoProducto = codigoProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.montoUnitario = montoUnitario;
        this.cliente = cliente;
    }

    public Venta(int codigoProducto, String fecha, int cantidad, double montoUnitario, String cliente) {
        this.codigoProducto = codigoProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.montoUnitario = montoUnitario;
        this.cliente = cliente;
    }

    public int getCodigoMovimiento() {
        return codigoMovimiento;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public String getFecha() {
        return fecha;
    }

    public double getMontoUnitario() {
        return montoUnitario;
    }

    public String getCliente() {
        return cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}