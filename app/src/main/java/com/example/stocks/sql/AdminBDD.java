package com.example.stocks.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stocks.model.Data.FileUtils;
import com.example.stocks.sql.Contrato.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**OBJETO PARA LA ADMINISTRACIÓN DE LA ESTRUCTURA DE BASE DE DATOS
 *
 */
// TODO CAMBIAR NOMBRE DE CAMPO DONDE SE USE monto -> precioUnitario
// TODO ACTUALIZAR BDD PARA CAMBIAR ORDEN CAMPOS EN PRESTAMO, MOVIENDO CANTIDAD A LA 2DA POSICION
public class AdminBDD extends SQLiteOpenHelper {

    public static final String NOMBRE_DB = "DB_STOCKS";

    public static final int VERSION_ACTUAL_BDD= 1;

    private static final String DB_FILEPATH = "/data/data/stock/databases/database.db";

    public static final String APP_PAQUETE = "com.example.stocks";

    private final Context context;


    public AdminBDD(Context context) {
        super(context, NOMBRE_DB, null, VERSION_ACTUAL_BDD);
        this.context=  context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tablas.PRODUCTOS +"("
                + Productos.ID_PRODUCTO +" INTEGER PRIMARY KEY, "
                + Productos.NOMBRE +" TEXT, "
                + Productos.CANTIDAD +" INTEGER, "
                + Productos.NOMBRE_LINEA +" TEXT, "
                + " FOREIGN KEY ("+Productos.NOMBRE_LINEA+") REFERENCES "+Tablas.LINEAS+"("+Movimientos.ID_MOVIMIENTO+"))");

        db.execSQL("CREATE TABLE "+ Tablas.COMPRAS +"("
                + Compras.ID_MOVIMIENTO +" INTEGER PRIMARY KEY, "
                + Compras.CANTIDAD +" INTEGER, "
                + Compras.MONTO +" REAL, "
                + " FOREIGN KEY ("+Compras.ID_MOVIMIENTO+") REFERENCES "+Tablas.MOVIMIENTOS+"("+Movimientos.ID_MOVIMIENTO+"))");

        db.execSQL("CREATE TABLE "+ Tablas.VENTAS +"("
                + Ventas.ID_MOVIMIENTO +" INTEGER PRIMARY KEY, "
                + Ventas.CANTIDAD +" INTEGER, "
                + Ventas.MONTO +" REAL, "
                + Ventas.ID_CLIENTE +" INTEGER, "
                + " FOREIGN KEY ("+Ventas.ID_MOVIMIENTO+") REFERENCES "+Tablas.MOVIMIENTOS+"("+Movimientos.ID_MOVIMIENTO+"))");

        db.execSQL("CREATE TABLE "+ Tablas.PRESTAMOS +"("
                + Prestamos.ID_MOVIMIENTO +" INTEGER PRIMARY KEY, "
                + Prestamos.TIPO_PRESTAMO +" TEXT, "
                + Prestamos.SOCIA +" TEXT, "
                + Prestamos.CANTIDAD +" INTEGER, "
                + " FOREIGN KEY ("+Prestamos.ID_MOVIMIENTO+") REFERENCES "+Tablas.MOVIMIENTOS+"("+Movimientos.ID_MOVIMIENTO+"))");

        db.execSQL("CREATE TABLE " + Tablas.LINEAS +"("
                + Lineas.ID_LINEA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Lineas.NOMBRE +" TEXT, "
                + Lineas.COLOR +" INTEGER)");

        db.execSQL("CREATE TABLE " + Tablas.MOVIMIENTOS +"("
                + Movimientos.ID_MOVIMIENTO +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Movimientos.ID_PRODUCTO +" INTEGER, "
                + Movimientos.FECHA +" TEXT)");

        db.execSQL("CREATE TABLE " + Tablas.CLIENTES +"("
                + Clientes.ID_CLIENTE +" INTEGER PRIMARY KEY, "
                + Clientes.NOMBRE +" TEXT, "
                + Clientes.APELLIDO +" TEXT, "
                + Clientes.NACIMIENTO +" TEXT, "
                + Clientes.TELEFONO +" TEXT, "
                + Clientes.DOMICILIO +" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Tablas.PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS "+Tablas.COMPRAS);
        db.execSQL("DROP TABLE IF EXISTS "+Tablas.VENTAS);
        db.execSQL("DROP TABLE IF EXISTS "+Tablas.PRESTAMOS);
        db.execSQL("DROP TABLE IF EXISTS "+Tablas.LINEAS);
        db.execSQL("DROP TABLE IF EXISTS "+Tablas.MOVIMIENTOS);
        db.execSQL("DROP TABLE IF EXISTS "+Tablas.CLIENTES);

        onCreate(db);
    }

    public boolean importDatabase(String dbPath) throws IOException {

        close();
        File newDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);
        if (newDb.exists()) {
            FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
            getWritableDatabase().close();
            return true;
        }
        return false;
    }
}