package com.example.stocks.ui;

import android.content.Intent;

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

import com.example.stocks.R;
import com.example.stocks.model.AdminDb;
import com.example.stocks.model.Data.*;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.model.InsertDataOnDb;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;

public class ActAgregarCompra extends AppCompatActivity {

    private AdminDb adminDb;
    private TableLayout tableLayoutAAC1;
    private Tabla tablaResumen;
    private EditText editCantidad, editMonto, editPersona, editComentario;
    private AutoCompleteTextView autoCNombreP, autoCCodigoP;
    private ArrayList<String> arrayCodigosP, arrayNombresP;
    private ArrayList<Compra> listaCompras;
    private String[] header;
    private InsertDataOnDb insertDataOnDb;
    private Button buttonRegistrarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_compra);

        //ocultando actionBar
        getSupportActionBar().hide();

        //inicializando admin de base de datos
        //adminDb= new AdminDb(this, DB_NOMBRE, null, 1);
        insertDataOnDb = new InsertDataOnDb();

        //CARGANDO VIEWS
        TextView tAgregarCompra = (TextView) findViewById(R.id.AC_text_agregarCompra);
        tableLayoutAAC1 = (TableLayout) findViewById(R.id.AC_tableL_resumen);
        autoCNombreP = (AutoCompleteTextView) findViewById(R.id.AC_autoC_nombreProducto);
        autoCCodigoP = (AutoCompleteTextView) findViewById(R.id.AC_autoC_codigoProducto);
        editCantidad = (EditText) findViewById(R.id.AC_edit_cantidad);
        editMonto = (EditText) findViewById(R.id.AC_edit_monto);
        Button buttonAgregar = (Button) findViewById(R.id.AC_button_agregar);
        Button buttonLimpiarCampos = (Button) findViewById(R.id.AC_button_limpiarCampos);
        Button buttonLimpiarResumen = (Button) findViewById(R.id.AC_button_limpiarResumen);
        buttonRegistrarCompra = (Button) findViewById(R.id.AC_button_registrar);
        buttonRegistrarCompra.setEnabled(false);
        //inicializando tabla
        header = new String[]{"Producto", "Cantidad", "Monto x uni"};
        tablaResumen = new Tabla(this, tableLayoutAAC1, "RESUMEN DE COMPRA", header);

        //INICIALIZANDO VARIABLES
        listaCompras = new ArrayList<Compra>();
        arrayCodigosP = new ArrayList<String>();
        arrayNombresP = new ArrayList<String>();

        //CARGANDO DATOS AUTOCOMPLETES CODIGOS Y NOMBRE
        //cargando arrays
        getArraysProductos(arrayCodigosP, arrayNombresP);
        //configurando autoCompleteCodigo
        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayCodigosP);
        autoCCodigoP.setAdapter(adapterCodigos);
        //al seleccionar un elemento en el autoCompleteCodigo, se complete automaticamente el codigo correspondiente en el autoCompleteNombre
        autoCCodigoP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayCodigosP.indexOf(autoCCodigoP.getText().toString());
                autoCNombreP.setText(arrayNombresP.get(n));
            }
        });
        //configurando autoCompleteNombre
        ArrayAdapter<String> adapterNombres = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayNombresP);
        autoCNombreP.setAdapter(adapterNombres);
        //al seleccionar un elemento en el autoCompleteNombre, se complete automaticamente el nombre correspondiente en el autoCompleteCodigo
        autoCNombreP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayNombresP.indexOf(autoCNombreP.getText().toString());
                autoCCodigoP.setText(arrayCodigosP.get(n));
            }
        });
    }

    //PROCEDIMIENTO AL PRESIONAR BOTON "AGREGAR A RESUMEN"
    public void agregarCompraAResumen(View view){

        Fecha fecha = new Fecha();

        if (autoCCodigoP.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CODIGO DE PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (autoCNombreP.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"NOMBRE DEL PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (editCantidad.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CANTIDAD\" esta incompleto",Toast.LENGTH_LONG).show();

        } else {

            if (editMonto.getText().toString().isEmpty()){
                editMonto.setText("-1");
            }

            //agregando compra a bdd
            Compra nuevaCompra = new Compra(Integer.parseInt(autoCCodigoP.getText().toString()), fecha.getStringDMAH(), Integer.parseInt(editCantidad.getText().toString()), Double.valueOf(editMonto.getText().toString()));
            listaCompras.add(nuevaCompra);

            //agregando compra a tabla resumen (en pantalla)
            if (editMonto.getText().toString() == "-1"){

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

            buttonRegistrarCompra.setEnabled(true);
            }
    }

    //PROCEDIMIENTO BOTON LIMPIAR CAMPOS
    public void limpiarCampos(View view){
        autoCCodigoP.setText("");
        autoCNombreP.setText("");
        editCantidad.setText("");
        editMonto.setText("");
        Toast.makeText(this,"Se limpiaron todos los campos",Toast.LENGTH_SHORT).show();
    }

    //PROCEDIMIENTO BOTON DE AGREGAR PRODUCTO (+)
    public void agregarProducto(View view){
        Intent intentAgregarProducto= new Intent(this, ActAgregarProducto.class);
        startActivity(intentAgregarProducto);
    }

    //PROCEDIMIENTO BOTON LIMPIAR RESUMEN
    public void limpiarResumen(View view){
        listaCompras.clear();
        tablaResumen.removeAllViews();
        buttonRegistrarCompra.setEnabled(false);
        Toast.makeText(this,"Se limpió la tabla resumen",Toast.LENGTH_SHORT).show();
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

    //PROCEDIMIENTO BOTON REGISTRAR COMPRA
    public void registrarCompra(View view){

        insertDataOnDb.insertCompra(this.getApplicationContext(), listaCompras);
        Toast.makeText(this,"La compra se registró correctamente",Toast.LENGTH_LONG).show();
        ActAgregarMovimiento.fa.finish();
        this.finish();

    }
}