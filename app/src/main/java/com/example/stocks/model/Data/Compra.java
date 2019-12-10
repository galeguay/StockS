package com.example.stocks.model.Data;

public class Compra  {

    private int codigoMovimiento;
    private int codigoProducto;
    private String fecha;
    private int cantidad;
    private double montoUnitario;

    public Compra(int codigoMovimiento, int codigoProducto, String fecha, int cantidad, double montoUnitario) {
        this.codigoMovimiento = codigoMovimiento;
        this.codigoProducto = codigoProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.montoUnitario = montoUnitario;
    }

    public Compra(int codigoProducto, String fecha, int cantidad, double montoUnitario) {
        this.codigoProducto = codigoProducto;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.montoUnitario = montoUnitario;
    }

    public int getCodigoMovimiento() {
        return codigoMovimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMontoUnitario() {
        return montoUnitario;
    }

    public void setMontoUnitario(double montoUnitario) {
        this.montoUnitario = montoUnitario;
    }
}