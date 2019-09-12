package com.example.stocks.model.Data;

public class Compra extends Movimiento {

    private double montoUnitario;

    public Compra(int codMovimiento, int codProducto, long fecha, int cantidad, double montoUnitario){

        if (codMovimiento == -1) {
            super.Movimiento(codProducto, fecha, cantidad);
            this.montoUnitario = montoUnitario;
        }else {
            super.Movimiento(codMovimiento, codMovimiento, fecha, cantidad);
            this.montoUnitario = montoUnitario;
        }

    }

}