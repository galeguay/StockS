package com.example.stocks.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.stocks.model.Data.Compra;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.model.Data.Venta;
import com.example.stocks.sql.Contrato.*;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.recyclerAdapter;
import static com.example.stocks.ui.MainActivity.listaProductos;

/**
 * Este objeto, se instancia cuando sea necesario llamar a alguno
 * de sus metodos, todos ellos referidos a la escritura y lectura
 * de datos en la base de datos.
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

    public ArrayList<String[]> listaUltimosMovimientos(int idProducto){
/**
 * Devuelve un ArrayList con los ultimos movimientos del producto pasado.
 * El orden de las columnas es CANTIDAD, TIPO DE MOVIMIENTO, FECHA, ID MOVIMIENTO
 * @param int id o código del producto
 */
        SQLiteDatabase db = adminBDD.getReadableDatabase();
        ArrayList listaUltimosMovimientos = new ArrayList<String[]>();

        Cursor cursor = db.rawQuery( "SELECT C.cantidad, M.Fecha, C.idMovimiento FROM movimientos M, compras C WHERE C.idMovimiento = M.idMovimiento AND M.idproducto = " + idProducto, null);
        if (cursor.moveToFirst()){
            Fecha fecha= new Fecha(cursor.getString(1));
            String[] row = {cursor.getString(0), "Compra", fecha.getStringDMA(), cursor.getString(2)};
            listaUltimosMovimientos.add(row);
            while (cursor.moveToNext()) {
                row = new String[]{cursor.getString(0), "Compra", fecha.getStringDMA(), cursor.getString(2)};
                listaUltimosMovimientos.add(row);
            }
        }
        cursor.close();
        cursor = db.rawQuery( "SELECT V.cantidad, M.Fecha, V.idMovimiento FROM movimientos M, ventas V WHERE V.idMovimiento = M.idMovimiento AND M.idproducto = " + idProducto, null);
        if (cursor.moveToFirst()){
            Fecha fecha= new Fecha(cursor.getString(1));
            String[] row = {cursor.getString(0), "Venta", fecha.getStringDMA(), cursor.getString(2)};
            listaUltimosMovimientos.add(row);
            while (cursor.moveToNext()) {
                row = new String[]{cursor.getString(0), "Venta", fecha.getStringDMA(), cursor.getString(2)};
                listaUltimosMovimientos.add(row);
            }
        }
        cursor.close();
        cursor = db.rawQuery( "SELECT P.cantidad, M.Fecha, P.idMovimiento FROM movimientos M, prestamos P WHERE P.idMovimiento = M.idMovimiento AND P.tipoPrestamo='pedido'AND M.idproducto = " + idProducto, null);
        if (cursor.moveToFirst()){
            Fecha fecha= new Fecha(cursor.getString(1));
            String[] row = {cursor.getString(0), "Prestamo pedido", fecha.getStringDMA(), cursor.getString(2)};
            listaUltimosMovimientos.add(row);
            while (cursor.moveToNext()) {
                row = new String[]{cursor.getString(0), "Prestamo pedido", fecha.getStringDMA(), cursor.getString(2)};
                listaUltimosMovimientos.add(row);
            }
        }
        cursor.close();
        cursor = db.rawQuery( "SELECT P.cantidad, M.Fecha, P.idMovimiento FROM movimientos M, prestamos P WHERE P.idMovimiento = M.idMovimiento AND P.tipoPrestamo='dado' AND M.idproducto = " + idProducto,null );
        if (cursor.moveToFirst()){
            Fecha fecha= new Fecha(cursor.getString(1));
            String[] row = {cursor.getString(0), "Prestamo dado", fecha.getStringDMA(), cursor.getString(2)};
            listaUltimosMovimientos.add(row);
            while (cursor.moveToNext()) {
                row = new String[]{cursor.getString(0), "Prestamo dado", fecha.getStringDMA(), cursor.getString(2)};
                listaUltimosMovimientos.add(row);
            }
        }

        return listaUltimosMovimientos;
    }

    public int obtenerIdUltimoMovimiento(Context context){
        SQLiteDatabase db = adminBDD.getReadableDatabase();
        int aux = 0;
        try{
            String[] campos= {Movimientos.ID_MOVIMIENTO};
            Cursor cursor = db.rawQuery(Tablas.MOVIMIENTOS, campos);
            if (cursor.moveToLast()){
                //(cursor != null && !cursor.isClosed()){
                //cursor.moveToLast();
                aux = cursor.getInt(0);
            }
        }catch (Exception e) {
            Toast.makeText(context, "Error al cargar LastMId", Toast.LENGTH_LONG).show();
        }

        db.close();
        return aux;
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

    public void insertCompra(Context context, ArrayList<Compra> listaCompras){

        SQLiteDatabase db= adminBDD.getWritableDatabase();

        ContentValues dataCompra = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        for (int i = 0; i < listaCompras.size(); i++) {

            //pidiendo el ID movimiento siguiente al ultimo registrado

            //insertando nuevo movimiento en tabla movimientos
            dataMovimiento.put(Movimientos.ID_PRODUCTO, listaCompras.get(i).getCodigoProducto());
            dataMovimiento.put(Movimientos.FECHA, listaCompras.get(i).getFecha());
            long idMovimiento= db.insert(Tablas.MOVIMIENTOS, null, dataMovimiento);

            //insertando nueva compra en tabla compras
            dataCompra.put(Compras.ID_MOVIMIENTO, idMovimiento);
            dataCompra.put(Compras.CANTIDAD, listaCompras.get(i).getCantidad());
            dataCompra.put(Compras.MONTO, listaCompras.get(i).getMontoUnitario());
            db.insert(Tablas.COMPRAS,null,dataCompra);

            //actualizando cambios en cantidad en el producto dentro de tabla productos
            String comando = ("UPDATE "+ Tablas.PRODUCTOS +" SET "+ Productos.CANTIDAD +" = "+ Productos.CANTIDAD +" + "+listaCompras.get(i).getCantidad()+" WHERE "+ Productos.ID_PRODUCTO +" = "+listaCompras.get(i).getCodigoProducto());
            db.execSQL(comando);

            //actualizando cambios en listaProductos(MainActivity)
            Producto p = new Producto(listaCompras.get(i).getCodigoProducto());
            int pos = listaProductos.indexOf(p);
            listaProductos.get(pos).sumarACantidad(listaCompras.get(i).getCantidad());
            recyclerAdapter.notifyItemChanged(pos);

        }
        db.close();
    }

    public int insertVenta(Venta venta){
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
        dataMovimiento.put(Movimientos.ID_PRODUCTO, venta.getCodigoProducto());
        dataMovimiento.put(Movimientos.FECHA, venta.getFecha());
        long idMovimiento= db.insert(Tablas.MOVIMIENTOS, null, dataMovimiento);

        //insertando nueva venta en tabla ventas
        dataVenta.put(Ventas.ID_MOVIMIENTO, idMovimiento);
        dataVenta.put(Ventas.ID_CLIENTE, venta).getCliente());
        dataVenta.put(Ventas.CANTIDAD, venta.getCantidad());
        dataVenta.put(Ventas.MONTO, venta.getMontoUnitario());
        db.insert(Tablas.VENTAS,null,dataVenta);

        //actualizando cambios en cantidad en el producto dentro de tabla productos
        String comando = ("UPDATE "+ Tablas.PRODUCTOS +" SET "+ Productos.CANTIDAD +" = "+ Productos.CANTIDAD +" - "+venta.getCantidad()+" WHERE "+ Productos.ID_PRODUCTO +" = "+ venta.getCodigoProducto());
        db.execSQL(comando);

        db.close();
    }

    public long insertLinea(Context context, String nombreLinea, int codigoColor){

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
