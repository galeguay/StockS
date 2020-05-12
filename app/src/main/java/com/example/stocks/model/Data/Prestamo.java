package com.example.stocks.model.Data;

public class Prestamo extends Movimiento {

    private String tipo;
    private String socia;

    public Prestamo(int idMovimiento, int codProducto, String fecha, int cantidad, String tipo, String socia) {
        super(idMovimiento, codProducto, fecha, cantidad);
        this.tipo = tipo;
        this.socia = socia;
    }

    public Prestamo(int codProducto, String fecha, int cantidad, String tipo, String socia) {
        super(codProducto, fecha, cantidad);
        this.tipo = tipo;
        this.socia = socia;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSocia() {
        return socia;
    }
}
