package com.example.stocks;

import android.app.Application;

import com.example.stocks.sql.OperacionesBDD;

public class MyAplication extends Application {

    private int LastMId;


    @Override
    public void onCreate() {
        super.onCreate();
        LastMId = 0;
        setLastMId();

    }

    //CARGA DEL ULTIMO ID DE MOVIMIENTOS
    public void setLastMId(){
        OperacionesBDD oDB= OperacionesBDD.instancia(getApplicationContext());
        LastMId= oDB.obtenerIdUltimoMovimiento(getApplicationContext());
/*
        AdminDb baseDeDatos = new AdminDb(getApplicationContext());
        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        try{

            String[] columnas = {C_ID_MOVIMIENTO};

            Cursor cursor = db.query(TABLA_MOVIMIENTOS, columnas,null, null, null, null, null);

            if (cursor.moveToLast()){//(cursor != null && !cursor.isClosed()){
                //cursor.moveToLast();
                LastMId = cursor.getInt(0);
                Toast.makeText(getApplicationContext(), LastMId, Toast.LENGTH_LONG).show();

            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al cargar LastMId", Toast.LENGTH_LONG).show();
        }

        db.close();*/
    }

    //modifica EL ID DEL ULTIMO MOVIMIENTO AGREGADO
    public void setLastMId(int lmi){
        this.LastMId = lmi;
    }

    //DEVUELVE EL ID DEL ULTIMO MOVIMIENTO AGREGADO
    public int getLastMId(){

        return LastMId;
    }

    //DEVUELVE EL PROXIMO ID DE LA TABLA DE MOVIMIENTO DE LA BD (AUMENTA EN 1 EL LMI)
    public int getNextMId(){

        LastMId = LastMId +1;

        return LastMId;
    }

}
