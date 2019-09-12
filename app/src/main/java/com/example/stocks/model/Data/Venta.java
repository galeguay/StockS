package com.example.stocks.model.Data;

public class Venta extends Movimiento{

    private double montoUnitario;
    private Cliente cliente;

    void Venta(int codMovimiento, int codProducto, long fecha, int cantidad, double montoUnitario, Cliente cliente){

        if (codMovimiento == -1) {
            super.Movimiento(codProducto, fecha, cantidad);
            this.montoUnitario = montoUnitario;
            this.cliente = cliente;
        }else {
            super.Movimiento(codMovimiento, codMovimiento, fecha, cantidad);
            this.montoUnitario = montoUnitario;
            this.cliente = cliente;
        }

    }



}