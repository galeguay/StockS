package com.example.stocks.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.stocks.MyAplication;
import com.example.stocks.model.Data.Compra;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Venta;

import java.util.ArrayList;

import static com.example.stocks.model.Contract.C_CANTIDAD;
import static com.example.stocks.model.Contract.C_NOMBRE_CLIENTE;
import static com.example.stocks.model.Contract.C_COLOR;
import static com.example.stocks.model.Contract.C_FECHA;
import static com.example.stocks.model.Contract.C_ID_MOVIMIENTO;
import static com.example.stocks.model.Contract.C_ID_PRODUCTO;
import static com.example.stocks.model.Contract.C_NOMBRE_LINEA;
import static com.example.stocks.model.Contract.C_MONTO;
import static com.example.stocks.model.Contract.C_NOMBRE;
import static com.example.stocks.model.Contract.C_SOCIA;
import static com.example.stocks.model.Contract.C_TIPO_PRESTAMO;
import static com.example.stocks.model.Contract.DB_NOMBRE;
import static com.example.stocks.model.Contract.TABLA_COMPRAS;
import static com.example.stocks.model.Contract.TABLA_LINEAS;
import static com.example.stocks.model.Contract.TABLA_MOVIMIENTOS;
import static com.example.stocks.model.Contract.TABLA_PRESTAMOS;
import static com.example.stocks.model.Contract.TABLA_PRODUCTOS;
import static com.example.stocks.ui.MainActivity.adapterRecycler;
import static com.example.stocks.ui.MainActivity.listaProductos;

//USADO PARA CONTENTER LOS PROCEDIMIENTO AL INSERTAR DATOS EN LA BDD
public class InsertDataOnDb {

    private AdminDb adminDb;

    public InsertDataOnDb(){
    }

    public void insertProducto(Context context, String codigo, String nombre, String cantidad, String linea){

        adminDb = new AdminDb(context, DB_NOMBRE, null, 1);
        SQLiteDatabase db = adminDb.getWritableDatabase();

        //creando y cargando contenedor de valores
        ContentValues dataProducto = new ContentValues();
        dataProducto.put(C_ID_PRODUCTO, codigo);
        dataProducto.put(C_NOMBRE, nombre);
        dataProducto.put(C_CANTIDAD, cantidad);
        dataProducto.put(C_NOMBRE_LINEA, linea);

        //pasando contenedor de valores a la db
        db.insert(TABLA_PRODUCTOS, null, dataProducto);
        db.close();

    }

    public void insertPrestamo(Context context, String idProducto, String tipoPrestamo, String socia, String cantidad){
        adminDb= new AdminDb(context, DB_NOMBRE, null, 1);

        SQLiteDatabase db= adminDb.getWritableDatabase();
        Fecha fecha = new Fecha();
        int nextMId=((MyAplication)context).getNextMId();

        ContentValues dataPrestamo = new ContentValues();
        dataPrestamo.put(C_ID_MOVIMIENTO, nextMId);
        dataPrestamo.put(C_TIPO_PRESTAMO, tipoPrestamo);
        dataPrestamo.put(C_SOCIA, socia);
        dataPrestamo.put(C_CANTIDAD, Integer.parseInt(cantidad));
        db.insert(TABLA_PRESTAMOS,null, dataPrestamo);

        ContentValues dataMovimiento = new ContentValues();
        dataMovimiento.put(C_ID_MOVIMIENTO, nextMId);
        dataMovimiento.put(C_ID_PRODUCTO, idProducto);
        dataMovimiento.put(C_FECHA, fecha.getStringDMAH());
        db.insert(TABLA_MOVIMIENTOS,null, dataMovimiento);

        if (tipoPrestamo.contentEquals("pedido")) {

            String comando = ("UPDATE " + TABLA_PRODUCTOS + " SET " + C_CANTIDAD + " = " + C_CANTIDAD + " + " + cantidad + " WHERE " + C_ID_PRODUCTO + " = " + idProducto);
            db.execSQL(comando);

        }else if (tipoPrestamo.contentEquals("dado")){

            String comando = ("UPDATE " + TABLA_PRODUCTOS + " SET " + C_CANTIDAD + " = " + C_CANTIDAD + " - " + cantidad + " WHERE " + C_ID_PRODUCTO + " = " + idProducto);
            db.execSQL(comando);

        }
        db.close();
    }

    public void insertCompra(Context context, ArrayList<Compra> listaCompras){
        adminDb= new AdminDb(context, DB_NOMBRE, null, 1);
        SQLiteDatabase db= adminDb.getWritableDatabase();

        ContentValues dataCompra = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        for (int i = 0; i < listaCompras.size(); i++) {

            //pidiendo el ID movimiento siguiente al ultimo registrado
            int nextMId=((MyAplication)context).getNextMId();

            //insertando nueva compra en tabla compras
            dataCompra.put(C_ID_MOVIMIENTO, nextMId);
            dataCompra.put(C_CANTIDAD, listaCompras.get(i).getCantidad());
            dataCompra.put(C_MONTO, listaCompras.get(i).getMontoUnitario());
            db.insert(TABLA_COMPRAS,null,dataCompra);

            //insertando nuevo movimiento en tabla movimientos
            dataMovimiento.put(C_ID_MOVIMIENTO, nextMId);
            dataMovimiento.put(C_ID_PRODUCTO, listaCompras.get(i).getCodigoProducto());
            dataMovimiento.put(C_FECHA, listaCompras.get(i).getFecha());
            db.insert(TABLA_MOVIMIENTOS, null, dataMovimiento);

            //actualizando cambios en cantidad en el producto dentro de tabla productos
            String comando = ("UPDATE "+TABLA_PRODUCTOS+" SET "+C_CANTIDAD+" = "+C_CANTIDAD+" + "+listaCompras.get(i).getCantidad()+" WHERE "+ C_ID_PRODUCTO +" = "+listaCompras.get(i).getCodigoProducto());
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

        adminDb= new AdminDb(context, DB_NOMBRE, null, 1);
        SQLiteDatabase db= adminDb.getWritableDatabase();

        ContentValues dataProducto = new ContentValues();
        ContentValues dataVenta = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        for (int i = 0; i < listaVentas.size(); i++) {

            //pidiendo el ID movimiento siguiente al ultimo registrado
            int nextMId=((MyAplication)context).getNextMId();

            dataVenta.put(C_ID_MOVIMIENTO, nextMId);
            dataVenta.put(C_NOMBRE_CLIENTE, listaVentas.get(i).getCliente());
            dataVenta.put(C_CANTIDAD, listaVentas.get(i).getCantidad());
            dataVenta.put(C_MONTO, listaVentas.get(i).getMontoUnitario());
            db.insert(TABLA_COMPRAS,null,dataVenta);

            dataMovimiento.put(C_ID_PRODUCTO, listaVentas.get(i).getCodigoProducto());
            dataMovimiento.put(C_FECHA, listaVentas.get(i).getFecha());
            db.insert(TABLA_MOVIMIENTOS, null, dataMovimiento);

            String comando = ("UPDATE "+TABLA_PRODUCTOS+" SET "+C_CANTIDAD+" = "+C_CANTIDAD+" - "+listaVentas.get(i).getCantidad()+" WHERE "+ C_ID_PRODUCTO +" = "+ listaVentas.get(i).getCodigoProducto());
            db.execSQL(comando);

            //actualizando cambios en listaProductos(MainActivity)
            Producto p = new Producto(listaVentas.get(i).getCodigoProducto());
            int pos = listaProductos.indexOf(p);
            listaProductos.get(pos).sumarACantidad(listaVentas.get(i).getCantidad());
            adapterRecycler.notifyItemChanged(pos);

        }
        db.close();
    }

    public long insertLinea(Context context, String nombreLinea, int codigoColor){

        adminDb = new AdminDb(context, DB_NOMBRE, null, 1);
        SQLiteDatabase db = adminDb.getWritableDatabase();

        ContentValues dataLinea = new ContentValues();
        dataLinea.put(C_NOMBRE_LINEA, nombreLinea);
        dataLinea.put(C_COLOR, codigoColor);
        long resultado = db.insert(TABLA_LINEAS, null, dataLinea);

        db.close();

        return resultado;
    }

}
