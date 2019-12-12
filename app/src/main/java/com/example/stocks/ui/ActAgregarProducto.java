package com.example.stocks.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.stocks.R;
import com.example.stocks.model.AdminDb;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.InsertDataOnDb;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaLineas;
import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.ui.MainActivity.adapterRecycler;
import static com.example.stocks.model.Contract.DB_NOMBRE;

public class ActAgregarProducto extends AppCompatActivity {

    private EditText editCodigo, editNombre, editCantidad;
    private Spinner spinnerLinea;
    private ArrayList<String> arrayLineas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_producto);

        //iniciando admins bdd
        AdminDb adminDB = new AdminDb(getApplicationContext(), DB_NOMBRE, null, 1);
        SQLiteDatabase db = adminDB.getWritableDatabase();

        //inicializando views
        editCodigo = (EditText) findViewById(R.id.AP_edit_Codigo);
        editNombre = (EditText) findViewById(R.id.AP_edit_Nombre);
        spinnerLinea = (Spinner) findViewById(R.id.AP_spinner_Linea);

        cargarDataSpinner();

        Button agregarProducto = (Button) findViewById(R.id.AP_button_Agrergar);

        //PROVISORIO PARA PRUEBAS
        editCantidad = (EditText) findViewById(R.id.AP_edit_Cantidad);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDataSpinner();

    }

    public void cargarDataSpinner(){
        //carga arrayLineas
        arrayLineas = new ArrayList<>();
        if (!listaLineas.isEmpty()) {
            arrayLineas.add("Ninguna");
            for (Linea l : listaLineas) {

                arrayLineas.add(l.getNombre());

            }
        }

        //carga datos en spinner
        if (!arrayLineas.isEmpty()) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayLineas);
            spinnerLinea.setAdapter(arrayAdapter);
        }

    }


    //PROCEDIMIENTO BOTON "AGREGAR LINEA"
    public void agregarLinea(View view){

        Intent intentAgregarLinea= new Intent(this, ActAgregarLinea.class);
        startActivity(intentAgregarLinea);

    }

    //PROCEDIMIENTO BOTON "REGISTRAR PRODUCTO"
    public void agregarProducto(View view) {

        //     PONER IF SI ESTA ALGUN CAMPO VACIO !!!
        if (!spinnerLinea.getSelectedItem().toString().equals("Ninguna")) {
            //agregando producto al arraylist principal
            Producto p = new Producto(Integer.parseInt(editCodigo.getText().toString()), editNombre.getText().toString(), Integer.parseInt(editCantidad.getText().toString()), spinnerLinea.getSelectedItem().toString());

            if (listaProductos.indexOf(p) == -1) {

                listaProductos.add(p);
                adapterRecycler.notifyItemInserted(listaProductos.size() - 1);

                //insertando producto en bdd
                InsertDataOnDb insertDataOnDb = new InsertDataOnDb();
                insertDataOnDb.insertProducto(this.getApplicationContext(), editCodigo.getText().toString(), editNombre.getText().toString(), editCantidad.getText().toString(), spinnerLinea.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(), "EL PRODUCTO SE AGREGÓ CORRECTAMENTE", Toast.LENGTH_LONG).show();
                finish();

            } else {

                Toast.makeText(this.getApplicationContext(), "El producto ya está registrado o tiene el mismo código que otro", Toast.LENGTH_LONG).show();

            }
        }

    }
}
