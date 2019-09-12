package com.example.stocks.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stocks.MyAplication;
import com.example.stocks.R;
import com.example.stocks.model.AdminDb;
import com.example.stocks.model.Data.*;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Tabla;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.model.Contract.*;


public class ActAgregarCompra extends AppCompatActivity {

    private AdminDb adminDb;
    private TableLayout tableLayoutAAC1;
    private Tabla tablaResumen;
    public EditText editCantidad, editMonto, editPersona, editComentario;
    private AutoCompleteTextView autoCNombreP;
    private AutoCompleteTextView autoCCodigoP;
    private ArrayList<String> arrayCodigosP;
    private ArrayList<String> arrayNombresP;
    public ArrayList<String[]> buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_compra);

        getSupportActionBar().hide();
        //creando admin database
        adminDb= new AdminDb(this, DB_NOMBRE, null, 1);

        //inicializando views
        TextView tAgregarCompra = (TextView) findViewById(R.id.tAgregarCompra);
        tableLayoutAAC1 = (TableLayout) findViewById(R.id.tableLayoutAAC1);
        autoCNombreP = (AutoCompleteTextView) findViewById(R.id.ACautoCompleteNombreProducto);
        autoCCodigoP = (AutoCompleteTextView) findViewById(R.id.autoCCodigoProducto);
        editCantidad = (EditText) findViewById(R.id.editCantidad);
        editMonto = (EditText) findViewById(R.id.editMonto);
        Button buttonAgregar = (Button) findViewById(R.id.bAgregar);
        Button buttonFinalizar = (Button) findViewById(R.id.bFinalizar);

        //inicializando variables
        buffer = new ArrayList<>();

        //inicializando cabecera de tabla
        String[] header= {"Cantidad", "Monto x uni", "Producto"};
        tablaResumen = new Tabla(this, tableLayoutAAC1,"RESUMEN DE COMPRA", header);

        //cargando arrays nombres y codigos para autoCompletes
        arrayCodigosP = new ArrayList<String>();
        arrayNombresP = new ArrayList<String>();
        getArraysProductos(arrayCodigosP, arrayNombresP);

        //configurando autoCompleteNombre
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, arrayNombresP);
        autoCNombreP.setAdapter(adapter1);
        //al seleccionar un elemento en el autoCompleteNombre, se complete automaticamente el nombre correspondiente en el autoCompleteCodigo
        autoCNombreP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayNombresP.indexOf(autoCNombreP.getText().toString());
                autoCCodigoP.setText(arrayCodigosP.get(n));
            }
        });

        //configurando autoCompleteCodigo
        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, arrayCodigosP);
        autoCCodigoP.setAdapter(adapter2);
        //al seleccionar un elemento en el autoCompleteCodigo, se complete automaticamente el nombre correspondiente en el autoCompleteNombre
        autoCCodigoP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayCodigosP.indexOf(autoCCodigoP.getText().toString());
                autoCNombreP.setText(arrayNombresP.get(n));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getArraysProductos(arrayCodigosP, arrayNombresP);


    }

    //PROCEDIMIENTO AL PRESIONAR BOTON "AGREGAR A RESUMEN"
    public void agregarMovimientoATabla(View view){

        Fecha fecha = new Fecha();
/*      Compra compra = new Compra(-1,
                Integer.parseInt(autoCCodigoP.getText().toString()),
                fecha.getLong(),
                Integer.parseInt(editCantidad.getText().toString()),
                Double.parseDouble(editMonto.getText().toString())
        );*/

        //agregando compra al buffer preEscrituraDb
        //formato String[] {fecha, codProducto, cantidad, monto}

        if (autoCCodigoP.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CODIGO DE PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (autoCNombreP.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"NOMBRE DEL PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (editCantidad.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CANTIDAD\" esta incompleto",Toast.LENGTH_LONG).show();

        } else {

            String[] newData = {String.valueOf(fecha.getLong()), autoCCodigoP.getText().toString(), editCantidad.getText().toString(), editMonto.getText().toString()};
            Toast.makeText(getApplicationContext(), "newsdsd", Toast.LENGTH_LONG).show();
            buffer.add(newData);

            //agregando compra a tabla resumen (en pantalla)
            if (editMonto.getText().toString().isEmpty()){

                String[] row = {editCantidad.getText().toString(), "-", autoCNombreP.getText().toString()};
                tablaResumen.addRow(row);

            }else{

                    String[] row = {editCantidad.getText().toString(), editMonto.getText().toString(), autoCNombreP.getText().toString()};
                    tablaResumen.addRow(row);
            }


            //limpiando views
            autoCCodigoP.setText("");
            autoCNombreP.setText("");
            editCantidad.setText("");
            editMonto.setText("");
        }
    }

    //PROCEDIMIENTO BOTON REGISTRAR COMPRA
    public void altaMovimientos(View view){
        SQLiteDatabase db= adminDb.getWritableDatabase();

        //creando y cargando contenedor de valores
        ContentValues dataProducto = new ContentValues();
        ContentValues dataCompra = new ContentValues();
        ContentValues dataMovimiento = new ContentValues();

        for (int i = 0; i < buffer.size(); i++) {

            //dataCompra.put(C_ID_PRODUCTO, Integer.parseInt(buffer.get(i)[1]));
            int nmid=((MyAplication) this.getApplicationContext()).getNMId();
            dataCompra.put(C_ID_MOVIMIENTO, nmid);
            dataCompra.put(C_CANTIDAD, Integer.parseInt(buffer.get(i)[2]));
            dataCompra.put(C_MONTO, Double.parseDouble(buffer.get(i)[3]));
            db.insert(TABLA_COMPRAS,null,dataCompra);

            dataMovimiento.put(C_ID_PRODUCTO, Integer.parseInt(buffer.get(i)[1]));
            dataMovimiento.put(C_FECHA, Long.parseLong(buffer.get(i)[0]));
            db.insert(TABLA_MOVIMIENTOS, null, dataMovimiento);

            String comando = ("UPDATE "+TABLA_PRODUCTOS+" SET "+C_CANTIDAD+" = "+C_CANTIDAD+" + "+buffer.get(i)[2]+" WHERE "+ C_ID_PRODUCTO +" = "+ buffer.get(i)[1]);
            db.execSQL(comando);

            //dataProducto.put(C_CANTIDAD, Integer.parseInt(listaProductos.get));

        }

        //String comando = ("UPDATE "+TABLA_PRODUCTOS+" SET "+C_CANTIDAD+" = "+C_CANTIDAD+" + "+ );
        //db.execSQL(
        //db.update(TABLA_PRODUCTOS,dataProducto,);
        db.close();
        ActAgregarMovimiento.fa.finish();
        this.finish();
    }

    //PROCEDIMIENTO BOTON DE AGREGAR PRODUCTO (+)
    public void agregarProducto(View view){
        Intent intentAgregarProducto= new Intent(this, ActAgregarProducto.class);
        startActivity(intentAgregarProducto);
    }

    //PROCEDIMIENTO Q CARGA LOS ARREGLOS PASADOS CON LOS NOMBRES Y CODIGOS DE LOS PRODUCTOS EN LA BDD
    public void getArraysProductos(ArrayList<String> arrayCodigosProductos, ArrayList<String> arrayNombresProductos){

        arrayCodigosProductos.clear();
        arrayNombresProductos.clear();
        for (Producto producto : listaProductos ){
            arrayCodigosProductos.add(String.valueOf(producto.getCodigo()));
            arrayNombresProductos.add(producto.getNombre());
        }
    }
}