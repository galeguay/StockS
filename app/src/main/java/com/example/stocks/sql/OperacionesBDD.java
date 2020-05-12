package com.example.stocks.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stocks.model.Data.Compra;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Movimiento;
import com.example.stocks.model.Data.Prestamo;
import com.example.stocks.model.Data.Venta;
import com.example.stocks.sql.Contrato.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Este objeto, se instancia cuando sea necesario llamar a alguno
 * de sus metodos referidos a la escritura y lectura
 * de datos en la BDD
 *
 */

public class OperacionesBDD {

    private static AdminBDD adminBDD;

    private static OperacionesBDD instancia = new OperacionesBDD();

    private OperacionesBDD() {
    }

    public static OperacionesBDD instanceOf(Context context) {
        if (adminBDD == null) {
            adminBDD = new AdminBDD(context);
        }
        return instancia;
    }

    public Cursor cursorTablaProductos() {
        SQLiteDatabase db = adminBDD.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.PRODUCTOS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaMovimientos() {
        SQLiteDatabase db = adminBDD.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.MOVIMIENTOS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaClientes() {
        SQLiteDatabase db = adminBDD.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.CLIENTES);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaCompras() {
        SQLiteDatabase db = adminBDD.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.COMPRAS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaVentas() {
        SQLiteDatabase db = adminBDD.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.VENTAS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaPrestamos() {
        SQLiteDatabase db = adminBDD.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.PRESTAMOS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorLineas() {
        SQLiteDatabase db = adminBDD.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.LINEAS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorDetallesMovimiento(int idMovimiento, String tipoMovimiento) {

        SQLiteDatabase db = adminBDD.getReadableDatabase();
        String nombreTabla = "Nulo";
        switch (tipoMovimiento) {
            case "Compra":
                nombreTabla = Tablas.COMPRAS;
                break;
            case "Venta":
                nombreTabla = Tablas.VENTAS;
                break;
            case "Prestamop pedido":
                nombreTabla = Tablas.PRESTAMOS;
                break;
            case "Prestamo dado":
                nombreTabla = Tablas.PRESTAMOS;
                break;
            default:
        }
        Cursor cursor = null;
        if (!nombreTabla.equals("Nulo")) {
            cursor = db.rawQuery("SELECT M.fecha , T.* FROM movimientos M, " + nombreTabla + " T WHERE T.idMovimiento = " + idMovimiento + " AND M.idMovimiento = " + idMovimiento, null);
        }
        return cursor;
    }

    public ArrayList<Movimiento> listaUltimosMovimientos(int idProducto){
/*
 * Devuelve un ArrayList con los ultimos movimientos del producto pasado.
 * El orden de las columnas es CANTIDAD, TIPO DE MOVIMIENTO, FECHA, ID MOVIMIENTO
 * @param int id o código del producto
 */
        SQLiteDatabase db = adminBDD.getReadableDatabase();
        ArrayList listaUltimosMovimientos = new ArrayList<Movimiento>();

        Cursor cursor = db.rawQuery( "SELECT C.idMovimiento, M.Fecha, C.cantidad, C.monto, FROM movimientos M, compras C WHERE C.idMovimiento = M.idMovimiento AND M.idproducto = " + idProducto, null);
        if (cursor.moveToFirst()){
            Fecha fecha= new Fecha(cursor.getString(1));
            //Compra compra = new Compra(cursor.getInt(0), idProducto, cursor.getLong(1), cursor.getInt(2), cursor.getDouble(3));
            //String[] row = {cursor.getString(0), "Compra", fecha.getStringDMA(), cursor.getString(2)};
            listaUltimosMovimientos.add(new Compra(cursor.getInt(0), idProducto, cursor.getLong(1), cursor.getInt(2), cursor.getDouble(3)));
            while (cursor.moveToNext()) {
                fecha= new Fecha(cursor.getString(1));
                listaUltimosMovimientos.add(new Compra(cursor.getInt(0), idProducto, cursor.getLong(1), cursor.getInt(2), cursor.getDouble(3)));
            }
        }
        cursor.close();
        cursor = db.rawQuery( "SELECT V.idMovimiento, M.Fecha, V.cantidad, V.monto, V.cliente FROM movimientos M, ventas V WHERE V.idMovimiento = M.idMovimiento AND M.idproducto = " + idProducto, null);
        if (cursor.moveToFirst()){
            //Fecha fecha= new Fecha(cursor.getString(1));
            //String[] row = {cursor.getString(0), "Venta", fecha.getStringDMA(), cursor.getString(2)};
            //listaUltimosMovimientos.add(row);
            Fecha fecha= new Fecha(cursor.getString(1));
            listaUltimosMovimientos.add(new Venta(cursor.getInt(0), idProducto, cursor.getLong(1), cursor.getInt(2), cursor.getDouble(3), cursor.getString(4)));
            while (cursor.moveToNext()) {
                fecha= new Fecha(cursor.getString(1));
                listaUltimosMovimientos.add(new Venta(cursor.getInt(0), idProducto, cursor.getLong(1), cursor.getInt(2), cursor.getDouble(3), cursor.getString(4)));
            }
        }
        cursor.close();
        cursor = db.rawQuery( "SELECT P.idMovimiento, M.Fecha, P.cantidad, P.tipo, P.socia FROM movimientos M, prestamos P WHERE P.idMovimiento = M.idMovimiento AND P.tipoPrestamo='pedido'AND M.idproducto = " + idProducto, null);
        if (cursor.moveToFirst()){
            /*Fecha fecha= new Fecha(cursor.getString(1));
            String[] row = {cursor.getString(0), "Prestamo pedido", fecha.getStringDMA(), cursor.getString(2)};
            listaUltimosMovimientos.add(row);*/
            Fecha fecha= new Fecha(cursor.getString(1));
            listaUltimosMovimientos.add(new Prestamo(cursor.getInt(0), idProducto, cursor.getInt(1), cursor.getString(2), cursor.getString(3)));
            while (cursor.moveToNext()) {
                fecha= new Fecha(cursor.getString(1));
                listaUltimosMovimientos.add(new Prestamo(cursor.getInt(0), idProducto, cursor.getInt(1), cursor.getString(2), cursor.getString(3)));
            }
        }
        cursor.close();
        cursor = db.rawQuery( "SELECT P.cantidad, M.Fecha, P.idMovimiento FROM movimientos M, prestamos P WHERE P.idMovimiento = M.idMovimiento AND P.tipoPrestamo='dado' AND M.idproducto = " + idProducto,null );
        if (cursor.moveToFirst()){
            /*Fecha fecha= new Fecha(cursor.getString(1));
            String[] row = {cursor.getString(0), "Prestamo dado", fecha.getStringDMA(), cursor.getString(2)};
            listaUltimosMovimientos.add(row);*/
            Fecha fecha= new Fecha(cursor.getString(1));
            listaUltimosMovimientos.add(new Prestamo(cursor.getInt(0), idProducto, cursor.getInt(1), cursor.getString(2), cursor.getString(3)));
            while (cursor.moveToNext()) {
                fecha= new Fecha(cursor.getString(1));
                listaUltimosMovimientos.add(new Prestamo(cursor.getInt(0), idProducto, cursor.getInt(1), cursor.getString(2), cursor.getString(3)));
            }
        }
/*
        Collections.sort(listaUltimosMovimientos, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return new Fecha(o1[2]).compareTo(new Fecha(o2[2]));
            }

        });*/
        Collections.sort(listaUltimosMovimientos);
        adminBDD.close();
        return listaUltimosMovimientos;
    }

    public void dbClose(){
        adminBDD.close();
    }

    public void insertProducto(Context context, String codigo, String nombre, String cantidad, String linea){

        SQLiteDatabase db = adminBDD.getWritableDatabase();

        //creando y cargando contenedor de valores
        ContentValues dataProducto = new ContentValues();
        dataProducto.put(Productos.ID_PRODUCTO, codigo);
        dataProducto.put(Productos.NOMBRE, nombre);
        dataProducto.put(Productos.CANTIDAD, cantidad);
        dataProducto.put(Productos.NOMBRE_LINEA, linea);

        //pasando contenedor de valores a la db
        db.insert(Tablas.PRODUCTOS, null, dataProducto);
        db.close();

    }

    public void insertPrestamo(Context context, String idProducto, String tipoPrestamo, String socia, String cantidad){

        SQLiteDatabase db= adminBDD.getWritableDatabase();
        Fecha fecha= new Fecha();

        //insertando nuevo movimiento en tabla movimientos
        ContentValues dataMovimiento = new ContentValues();
        dataMovimiento.put(Movimientos.ID_PRODUCTO, idProducto);
        dataMovimiento.put(Movimientos.FECHA, fecha.getStringDMAH());
        long idMovimiento= db.insert(Tablas.MOVIMIENTOS,null, dataMovimiento);

        //insertando nueva prestamo en tabla prestamos
        ContentValues dataPrestamo = new ContentValues();
        dataPrestamo.put(Prestamos.ID_MOVIMIENTO, idMovimiento);
        dataPrestamo.put(Prestamos.TIPO_PRESTAMO, tipoPrestamo);
        dataPrestamo.put(Prestamos.SOCIA, socia);
        dataPrestamo.put(Prestamos.CANTIDAD, Integer.parseInt(cantidad));
        db.insert(Tablas.PRESTAMOS,null, dataPrestamo);

        //actualizando cambios en cantidad en el producto dentro de tabla productos
        if (tipoPrestamo.contentEquals("pedido")) {

            String comando = ("UPDATE "+ Tablas.PRODUCTOS +" SET "+ Productos.CANTIDAD +" = "+ Productos.CANTIDAD+ " + "+ cantidad +" WHERE "+ Productos.ID_PRODUCTO +" = "+ idProducto);
            db.execSQL(comando);

        }else if (tipoPrestamo.contentEquals("dado")){

            String comando = ("UPDATE "+ Tablas.PRODUCTOS +" SET "+ Productos.CANTIDAD +" = "+ Productos.CANTIDAD +" - "+ cantidad +" WHERE "+ Productos.ID_PRODUCTO +" = "+ idProducto);
            db.execSQL(comando);

        }
        db.close();
    }

    public void insertCompra(Compra compra){

        SQLiteDatabase db= adminBDD.getWritableDatabase();

        ContentValues dataCompra = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        //insertando nuevo movimiento en tabla movimientos
        dataMovimiento.put(Movimientos.ID_PRODUCTO, compra.getIdProducto());
        dataMovimiento.put(Movimientos.FECHA, compra.getFecha());
        long idMovimiento= db.insert(Tablas.MOVIMIENTOS, null, dataMovimiento);

        //insertando nueva compra en tabla compras
        dataCompra.put(Compras.ID_MOVIMIENTO, idMovimiento);
        dataCompra.put(Compras.CANTIDAD, compra.getCantidad());
        dataCompra.put(Compras.MONTO, compra.getPrecioUnitario());
        long succesInsert = db.insert(Tablas.COMPRAS, null, dataCompra);


        //actualizando cambios en cantidad en el producto dentro de tabla productos
        String comando = ("UPDATE " + Tablas.PRODUCTOS + " SET " + Productos.CANTIDAD + " = " + Productos.CANTIDAD + " + " + compra.getCantidad() + " WHERE " + Productos.ID_PRODUCTO + " = " + compra.getIdProducto());
        db.execSQL(comando);

        db.close();
    }

    public void insertVenta(Venta venta){
/**
 * Metodo que inserta una venta nueva en la base de datos.
 * @param Venta venta
 *
 */

        SQLiteDatabase db= adminBDD.getWritableDatabase();

        ContentValues dataProducto = new ContentValues();
        ContentValues dataVenta = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        //insertando nuevo movimiento en tabla movimientos
        dataMovimiento.put(Movimientos.ID_PRODUCTO, venta.getIdProducto());
        dataMovimiento.put(Movimientos.FECHA, venta.getFecha());
        long idMovimiento= db.insert(Tablas.MOVIMIENTOS, null, dataMovimiento);

        //insertando nueva venta en tabla ventas
        dataVenta.put(Ventas.ID_MOVIMIENTO, idMovimiento);
        dataVenta.put(Ventas.ID_CLIENTE, venta.getCliente());
        dataVenta.put(Ventas.CANTIDAD, venta.getCantidad());
        dataVenta.put(Ventas.MONTO, venta.getPrecioUnitario());
        db.insert(Tablas.VENTAS,null,dataVenta);

        //actualizando cambios en cantidad en el producto dentro de tabla productos
        String comando = ("UPDATE "+ Tablas.PRODUCTOS +" SET "+ Productos.CANTIDAD +" = "+ Productos.CANTIDAD +" - "+venta.getCantidad()+" WHERE "+ Productos.ID_PRODUCTO +" = "+ venta.getIdProducto());
        db.execSQL(comando);

        db.close();
    }

    public long insertLinea(String nombreLinea, int codigoColor){

        SQLiteDatabase db = adminBDD.getWritableDatabase();

        //agregarndo nueva linea a tabla lineas
        ContentValues dataLinea = new ContentValues();
        dataLinea.put(Lineas.NOMBRE, nombreLinea);
        dataLinea.put(Lineas.COLOR, codigoColor);
        long idLinea= db.insert(Tablas.LINEAS, null, dataLinea);

        db.close();
        return idLinea;
    }
}
