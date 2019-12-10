package com.example.stocks.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stocks.model.Data.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.stocks.model.Contract.*;

public class AdminDb extends SQLiteOpenHelper {

    public AdminDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_PRODUCTOS);
        db.execSQL(CREAR_TABLA_COMPRAS);
        db.execSQL(CREAR_TABLA_VENTAS);
        db.execSQL(CREAR_TABLA_PRESTAMOS);
        db.execSQL(CREAR_TABLA_LINEAS);
        db.execSQL(CREAR_TABLA_MOVIMIENTOS);
        db.execSQL(CREAR_TABLA_CLIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_COMPRAS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_VENTAS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_PRESTAMOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_LINEAS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_MOVIMIENTOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_CLIENTES);

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