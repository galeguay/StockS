package com.example.stocks.sql;

public class Contrato {

    interface CamposProductos{
        String ID_PRODUCTO = "idproducto";
        String NOMBRE= "nombre";
        String CANTIDAD= "cantidad";
        String NOMBRE_LINEA= "nombreLinea";
    }

    interface CamposMovimientos{
        String ID_MOVIMIENTO= "idMovimiento";
        String ID_PRODUCTO= "idProducto";
        String FECHA= "fecha";
    }

    interface CamposClientes{
        String ID_CLIENTE= "idCliente";
        String NOMBRE= "nombre";
        String APELLIDO= "apellido";
        String NACIMIENTO= "nacimiento";
        String TELEFONO= "telefono";
        String DOMICILIO= "domicilio";
    }

    interface CamposCompras{
        String ID_MOVIMIENTO= "idMovimiento";
        String CANTIDAD= "cantidad";
        String MONTO= "monto";
    }

    interface CamposVentas{
        String ID_MOVIMIENTO= "idMovimiento";
        String CANTIDAD= "cantidad";
        String MONTO= "monto";
        String ID_CLIENTE= "idCliente";
    }

    interface CamposPrestamos{
        String ID_MOVIMIENTO= "idMovimiento";
        String TIPO_PRESTAMO= "tipoPrestamo";
        String SOCIA= "socia";
        String CANTIDAD= "cantidad";
    }

    interface CamposLineas{
        String ID_LINEA= "idLinea";
        String NOMBRE= "nombre";
        String COLOR= "color";
    }

    interface nombresTablas{
        String PRODUCTOS= "productos";
        String MOVIMIENTOS= "movimientos";
        String CLIENTES= "clientes";
        String COMPRAS= "compras";
        String VENTAS= "ventas";
        String PRESTAMOS= "prestamos";
        String LINEAS= "lineas";
    }

    public static class Productos implements CamposProductos{}

    public static class Movimientos implements CamposMovimientos{}

    public static class Clientes implements CamposClientes{}

    public static class Compras implements CamposCompras{}

    public static class Ventas implements CamposVentas{}

    public static class Prestamos implements CamposPrestamos{}

    public static class Lineas implements CamposLineas{}

    public static class Tablas implements nombresTablas{}

    private Contrato(){}

}
