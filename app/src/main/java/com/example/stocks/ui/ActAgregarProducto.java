package com.example.stocks.ui;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.stocks.R;
import com.example.stocks.model.AdminDb;
import com.example.stocks.model.Data.Producto;

import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.ui.MainActivity.adapterRecycler;
import static com.example.stocks.model.Contract.C_CANTIDAD;
import static com.example.stocks.model.Contract.C_ID_PRODUCTO;
import static com.example.stocks.model.Contract.C_LINEA;
import static com.example.stocks.model.Contract.C_NOMBRE;
import static com.example.stocks.model.Contract.DB_NOMBRE;
import static com.example.stocks.model.Contract.TABLA_PRODUCTOS;

public class ActAgregarProducto extends AppCompatActivity {

    private EditText eCodigo, eNombre, eCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_producto);
        //obteniendo lista de productos pasada en el intent

        //inicializando views
        eCodigo = (EditText) findViewById(R.id.eAAPCodigo);
        eNombre = (EditText) findViewById(R.id.eAAPNombre);
        Button agregarProducto = (Button) findViewById(R.id.bAAPAgrergar);

        //PROVISORIO PARA PRUEBAS
        eCantidad = (EditText) findViewById(R.id.eAAPCantidad);

    }

    //agregar de un producto a la db
    public void agregarProducto(View view){

        Integer codigo= Integer.parseInt(eCodigo.getText().toString());
        String nombre= eNombre.getText().toString();
        Integer cantidad= Integer.parseInt(eCantidad.getText().toString());

        //       PONER IF SI ESTA ALGUN CAMPO VACIO

        Integer linea = 0;//PROVISORIO

        AdminDb adminDB = new AdminDb(getApplicationContext(), DB_NOMBRE, null, 1);
        SQLiteDatabase db = adminDB.getWritableDatabase();

        //agregando producto al arraylist principal
        listaProductos.add(new Producto(codigo ,nombre, cantidad, String.valueOf(linea)));
        adapterRecycler.notifyItemInserted(listaProductos.size()-1);

        //creando y cargando contenedor de valores
        ContentValues valores = new ContentValues();
        valores.put(C_ID_PRODUCTO, codigo.toString());
        valores.put(C_NOMBRE, nombre);
        valores.put(C_CANTIDAD, cantidad.toString());
        valores.put(C_LINEA, linea.toString());

        //pasando contenedor de valores a la db
        db.insert(TABLA_PRODUCTOS, null, valores);
        db.close();
        Toast.makeText(getApplicationContext(), "EL PRODUCTO SE AGREGÓ CORRECTAMENTE", Toast.LENGTH_LONG).show();
        finish();

    }
}