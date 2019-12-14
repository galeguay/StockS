package com.example.stocks.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.stocks.MyAplication;
import com.example.stocks.model.AdminDb;
import com.example.stocks.model.Data.Compra;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Venta;
import com.example.stocks.sql.Contrato.*;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.adapterRecycler;
import static com.example.stocks.ui.MainActivity.listaProductos;

public class OperacionesBDD {

    private static AdminDb adminDb;

    private static OperacionesBDD instancia= new OperacionesBDD();

    private OperacionesBDD(){}

    public static OperacionesBDD instancia(Context context){
        if (adminDb == null) {
            adminDb = new AdminDb(context);
        }
        return instancia;
    }

    public Cursor cursorTablaProductos() {
        SQLiteDatabase db = adminDb.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.PRODUCTOS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaMovimientos() {
        SQLiteDatabase db = adminDb.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.MOVIMIENTOS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaClientes() {
        SQLiteDatabase db = adminDb.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.CLIENTES);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaCompras() {
        SQLiteDatabase db = adminDb.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.COMPRAS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaVentas() {
        SQLiteDatabase db = adminDb.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.VENTAS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorTablaPrestamos() {
        SQLiteDatabase db = adminDb.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.PRESTAMOS);

        return db.rawQuery(sql, null);
    }

    public Cursor cursorLineas() {
        SQLiteDatabase db = adminDb.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s",
                Tablas.LINEAS);

        return db.rawQuery(sql, null);
    }
/*
    public Cursor obtenerDetallesPorIdPedido(String idCabeceraPedido) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                Tablas., CabecerasPedido.ID_CABECERA_PEDIDO);

        String[] selectionArgs = {idCabeceraPedido};

        return db.rawQuery(sql, selectionArgs);*/
    public int obtenerIdUltimoMovimiento(Context context){
        SQLiteDatabase db = adminDb.getReadableDatabase();
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

    public boolean dbClose(){
        boolean aux= false;
        if(adminDb != null) {
            aux=false;
            adminDb.close();
        }else {aux= true;}
        return aux;
    }

    public void insertProducto(Context context, String codigo, String nombre, String cantidad, String linea){

        SQLiteDatabase db = adminDb.getWritableDatabase();

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

        SQLiteDatabase db= adminDb.getWritableDatabase();
        Fecha fecha= new Fecha();
        int nextMId=((MyAplication)context).getNextMId();

        //insertando nuevo movimiento en tabla movimientos
        ContentValues dataMovimiento = new ContentValues();
        //dataMovimiento.put(Movimientos.ID_MOVIMIENTO, nextMId);
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

        SQLiteDatabase db= adminDb.getWritableDatabase();

        ContentValues dataCompra = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        for (int i = 0; i < listaCompras.size(); i++) {

            //pidiendo el ID movimiento siguiente al ultimo registrado
            int nextMId=((MyAplication)context).getNextMId();

            //insertando nuevo movimiento en tabla movimientos
            //dataMovimiento.put(Movimientos.ID_MOVIMIENTO, nextMId);
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
            Toast.makeText(context, String.valueOf(pos), Toast.LENGTH_LONG).show();
            listaProductos.get(pos).sumarACantidad(listaCompras.get(i).getCantidad());
            adapterRecycler.notifyItemChanged(pos);

        }
        db.close();
    }

    public void insertVenta(Context context, ArrayList<Venta> listaVentas){

        SQLiteDatabase db= adminDb.getWritableDatabase();

        ContentValues dataProducto = new ContentValues();
        ContentValues dataVenta = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        for (int i = 0; i < listaVentas.size(); i++) {

            //pidiendo el ID movimiento siguiente al ultimo registrado
            int nextMId=((MyAplication)context).getNextMId();

            //insertando nuevo movimiento en tabla movimientos
            dataMovimiento.put(Movimientos.ID_PRODUCTO, listaVentas.get(i).getCodigoProducto());
            dataMovimiento.put(Movimientos.FECHA, listaVentas.get(i).getFecha());
            long idMovimiento= db.insert(Tablas.MOVIMIENTOS, null, dataMovimiento);

            //insertando nueva venta en tabla ventas
            dataVenta.put(Ventas.ID_MOVIMIENTO, idMovimiento);
            dataVenta.put(Ventas.ID_CLIENTE, listaVentas.get(i).getCliente());
            dataVenta.put(Ventas.CANTIDAD, listaVentas.get(i).getCantidad());
            dataVenta.put(Ventas.MONTO, listaVentas.get(i).getMontoUnitario());
            db.insert(Tablas.VENTAS,null,dataVenta);

            //actualizando cambios en cantidad en el producto dentro de tabla productos
            String comando = ("UPDATE "+ Tablas.PRODUCTOS +" SET "+ Productos.CANTIDAD +" = "+ Productos.CANTIDAD +" - "+listaVentas.get(i).getCantidad()+" WHERE "+ Productos.ID_PRODUCTO +" = "+ listaVentas.get(i).getCodigoProducto());
            db.execSQL(comando);

            //actualizando cambios en listaProductos(MainActivity) y adapterRecycler
            Producto p = new Producto(listaVentas.get(i).getCodigoProducto());
            int pos = listaProductos.indexOf(p);
            listaProductos.get(pos).sumarACantidad(listaVentas.get(i).getCantidad());
            adapterRecycler.notifyItemChanged(pos);

        }
        db.close();
    }

    public long insertLinea(Context context, String nombreLinea, int codigoColor){

        SQLiteDatabase db = adminDb.getWritableDatabase();

        //agregarndo nueva linea a tabla lineas
        ContentValues dataLinea = new ContentValues();
        dataLinea.put(Lineas.NOMBRE, nombreLinea);
        dataLinea.put(Lineas.COLOR, codigoColor);
        long idLinea= db.insert(Tablas.LINEAS, null, dataLinea);

        db.close();
        return idLinea;
    }
}
