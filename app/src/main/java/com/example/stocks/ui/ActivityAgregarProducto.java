package com.example.stocks.ui;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.stocks.R;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaLineas;
import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.ui.MainActivity.recyclerAdapter;

public class ActivityAgregarProducto extends AppCompatActivity {

    private EditText editCodigo, editNombre, editCantidad;
    private Spinner spinnerLinea;
    private ArrayList<String> arrayLineas;
    private boolean alertOk;
    private OperacionesBDD operacionesBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_producto);

        //ocultando actionBar
        getSupportActionBar().hide();

        //iniciando admins bdd
        operacionesBDD = OperacionesBDD.instanceOf(getApplicationContext());

        //inicializando views
        editCodigo = (EditText) findViewById(R.id.AP_edit_Codigo);
        editNombre = (EditText) findViewById(R.id.AP_edit_Nombre);
        spinnerLinea = (Spinner) findViewById(R.id.AP_spinner_Linea);

        cargaListaSpinner();

        Button agregarProducto = findViewById(R.id.AP_button_Agrergar);

        alertOk = false;

        //PROVISORIO PARA PRUEBAS
        editCantidad = (EditText) findViewById(R.id.AP_edit_Cantidad);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargaListaSpinner();

    }

    public void cargaListaSpinner() {
        //carga arrayLineas
        arrayLineas = new ArrayList<>();
        arrayLineas.add("Ninguna");
        for (Linea l : listaLineas) {
            arrayLineas.add(l.getNombre());
        }
        //carga lista en spinner
        if(!arrayLineas.isEmpty()){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayLineas);
            spinnerLinea.setAdapter(arrayAdapter);
        }

}


    //PROCEDIMIENTO BOTON "AGREGAR LINEA"
    public void agregarLinea(View view) {

        Intent intentAgregarLinea = new Intent(this, ActivityAgregarLinea.class);
        startActivity(intentAgregarLinea);

    }

    //PROCEDIMIENTO BOTON "REGISTRAR PRODUCTO"
    public void agregarProducto(View view) {

        //los IF son chequeos de campos vacíos
        if (!editNombre.getText().toString().isEmpty()) {
            if (!editCodigo.getText().toString().isEmpty() && editCodigo.getText().toString().length() == 8) {
                if (!spinnerLinea.getSelectedItem().toString().equals("Ninguna")) {

                    //chequeo de nombre duplicado en una misma linea de prdocutos
                    for (Producto p : listaProductos) {
                        if (p.getNombre().equals(editNombre.getText().toString()) && p.getLinea().equals(spinnerLinea.getSelectedItem().toString())) {
                            Toast.makeText(getApplicationContext(), "EN LA LINEA SELECCIONADA YA ESTÁ REGISTRADO UN PRODUCTO CON EL MISMO NOMBRE", Toast.LENGTH_LONG).show();
                        }
                    }

                    //agregando producto al arraylist listaproductos del main
                    Producto p = new Producto(Integer.parseInt(editCodigo.getText().toString()), editNombre.getText().toString(), Integer.parseInt(editCantidad.getText().toString()), spinnerLinea.getSelectedItem().toString());

                    if (listaProductos.indexOf(p) == -1) {

                        listaProductos.add(p);
                        recyclerAdapter.notifyItemInserted(listaProductos.size() - 1);

                        //insertando producto en bdd
                        operacionesBDD.insertProducto(editCodigo.getText().toString(), editNombre.getText().toString(), editCantidad.getText().toString(), spinnerLinea.getSelectedItem().toString());
                        Toast.makeText(getApplicationContext(), "El producto se agregó correctamente", Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "EL PRODUCTO YA ESTÁ REGISTRADO O TIENE EL MISMO CODIGO QUE OTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "NO SELECCIONÓ NINGUNA LINEA", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "EL CODIGO INGRESADO NO ES VÁLIDO", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "NO INGRESÓ EL NOMBRE DEL PRODUCTO", Toast.LENGTH_LONG).show();
        }

    }
}

