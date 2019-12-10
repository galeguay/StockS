package com.example.stocks.model;

public class Contract {

    public static String DB_FILEPATH = "/data/data/stock/databases/database.db";
    //constantes de app
    public static final String APP_PAQUETE = "com.example.stocks";

    //CONSTANTES DE BASE DE DATOS
    public static final String DB_NOMBRE = "DB_STOCKS";
    //nombres de tablas
    public static final String TABLA_PRODUCTOS = "productos";
    public static final String TABLA_COMPRAS = "compras";
    public static final String TABLA_VENTAS = "ventas";
    public static final String TABLA_PRESTAMOS = "prestamos";
    public static final String TABLA_LINEAS = "lineas";
    public static final String TABLA_MOVIMIENTOS = "movimientos";
    public static final String TABLA_CLIENTES = "clientes";
    //nombres de campos
    public static final String C_ID_PRODUCTO = "idProducto";
    public static final String C_NOMBRE = "nombre";
    public static final String C_CANTIDAD = "cantidad";

    public static final String C_ID_LINEA = "idLinea";
    public static final String C_NOMBRE_LINEA = "linea";
    public static final String C_COLOR = "color";

    public static final String C_ID_MOVIMIENTO = "idMovimiento";
    public static final String C_FECHA = "fecha";
    public static final String C_MONTO = "monto";

    public static final String C_COMENTARIO = "comentario";

    public static final String C_ID_CLIENTE = "idCliente";
    public static final String C_NOMBRE_CLIENTE = "nombreCliente";
    public static final String C_APELLIDO = "apellido";
    public static final String C_FECHA_NACIMIENTO = "fechaNacimiento";
    public static final String C_TELEFONO = "telefono";
    public static final String C_DIRECCION = "direccion";

    public static final String C_SOCIA = "socia"; //campo usado en los prestamos
    public static final String C_TIPO_PRESTAMO = "tipoPrestamo";

    //creacion de tablas
    public static final String CREAR_TABLA_PRODUCTOS= "CREATE TABLE " + TABLA_PRODUCTOS +"("
            + C_ID_PRODUCTO +" INTEGER PRIMARY KEY, "
            + C_NOMBRE +" TEXT, "
            + C_NOMBRE_LINEA +" INTEGER, "
            + C_CANTIDAD +" INTEGER)";

    public static final String CREAR_TABLA_COMPRAS= "CREATE TABLE " + TABLA_COMPRAS +"("
            + C_ID_MOVIMIENTO +" INTEGER PRIMARY KEY, "
            + C_CANTIDAD +" INTEGER, "
            + C_MONTO +" REAL, "
            + " FOREIGN KEY ("+C_ID_MOVIMIENTO+") REFERENCES "+TABLA_MOVIMIENTOS+"("+C_ID_MOVIMIENTO+"))";

    public static final String CREAR_TABLA_VENTAS= "CREATE TABLE " + TABLA_VENTAS +"("
            + C_ID_MOVIMIENTO +" INTEGER PRIMARY KEY, "
            + C_NOMBRE_CLIENTE +" TEXT, "
            + C_CANTIDAD +" INTEGER, "
            + C_MONTO +" REAL, "
            + " FOREIGN KEY ("+C_ID_MOVIMIENTO+") REFERENCES "+TABLA_MOVIMIENTOS+"("+C_ID_MOVIMIENTO+"))";

    public static final String CREAR_TABLA_PRESTAMOS= "CREATE TABLE " + TABLA_PRESTAMOS +"("
            + C_ID_MOVIMIENTO +" INTEGER PRIMARY KEY, "
            + C_TIPO_PRESTAMO +" TEXT, "
            + C_SOCIA +" TEXT, "
            + C_CANTIDAD +" INTEGER, "
            + " FOREIGN KEY ("+C_ID_MOVIMIENTO+") REFERENCES "+TABLA_MOVIMIENTOS+"("+C_ID_MOVIMIENTO+"))";

    public static final String CREAR_TABLA_LINEAS = "CREATE TABLE " + TABLA_LINEAS +"("
            + C_ID_LINEA +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NOMBRE_LINEA +" TEXT, "
            + C_COLOR +" INTEGER)";

    public static final String CREAR_TABLA_MOVIMIENTOS= "create table " + TABLA_MOVIMIENTOS +"("
            + C_ID_MOVIMIENTO +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_ID_PRODUCTO +" INTEGER, "
            + C_FECHA +" TEXT)";

    public static final String CREAR_TABLA_CLIENTES= "CREATE TABLE " + TABLA_CLIENTES +"("
            + C_ID_CLIENTE +" INTEGER PRIMARY KEY, "
            + C_NOMBRE_CLIENTE +" TEXT, "
            + C_APELLIDO +" TEXT, "
            + C_FECHA_NACIMIENTO +" TEXT, "
            + C_TELEFONO +" TEXT, "
            + C_DIRECCION +" TEXT)";


}