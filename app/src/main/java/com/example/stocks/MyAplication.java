package com.example.stocks;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.stocks.model.AdminDb;

import static com.example.stocks.model.Contract.C_ID_MOVIMIENTO;
import static com.example.stocks.model.Contract.DB_NOMBRE;
import static com.example.stocks.model.Contract.TABLA_MOVIMIENTOS;

public class MyAplication extends Application {

    private int LMId;

    @Override
    public void onCreate() {
        super.onCreate();
        LMId = 0;
        setLMI();
        Toast.makeText(this,String.valueOf(getLMId()), Toast.LENGTH_LONG).show();


    }

    //CARGA DEL ULTIMO ID DE MOVIMIENTOS
    public void setLMI(){

        AdminDb adminDb= new AdminDb(this, DB_NOMBRE, null, 1);
        SQLiteDatabase db = adminDb.getReadableDatabase();

        try{

            String[] columnas = {C_ID_MOVIMIENTO};

            Cursor cursor = db.query(TABLA_MOVIMIENTOS, columnas,null, null, null, null, null);

            if (cursor != null && !cursor.isClosed()){
                cursor.moveToLast();
                LMId = cursor.getInt(0);
            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al cargar LMId", Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    //modifica EL ID DEL ULTIMO MOVIMIENTO AGREGADO
    public void setLMId(int lmi){
        this.LMId = lmi;
    }

    //DEVUELVE EL ID DEL ULTIMO MOVIMIENTO AGREGADO
    public int getLMId(){

        return LMId;
    }

    //DEVUELVE EL PROXIMO ID DE LA TABLA DE MOVIMIENTO DE LA BD (AUMENTA EN 1 EL LMI)
    public int getNMId(){

        LMId = LMId+1;

        return LMId;
    }

}
