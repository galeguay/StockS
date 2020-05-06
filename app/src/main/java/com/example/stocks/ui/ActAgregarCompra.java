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
import com.example.stocks.sql.AdminBDD;
import com.example.stocks.model.Data.*;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;

public class ActAgregarCompra extends AppCompatActivity {

    private AdminBDD adminBDD;
    private TableLayout tableLayoutAAC1;
    private Tabla tablaResumen;
    private EditText editCantidad, editMonto;
    private AutoCompleteTextView autoCNombreProducto, autoCCodigoProducto;
    private ArrayList<String> arrayCodigosProductos, arrayNombresProductos;

    private ArrayList<Compra> listaCompras;
    private String[] header;
    private Button buttonRegistrarCompra;
    private OperacionesBDD operacionesBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_compra);

        //ocultando actionBar
        getSupportActionBar().hide();

        //inicializando admin de base de datos
        operacionesBDD= OperacionesBDD.instanceOf(getApplicationContext());

        //CARGANDO VIEWS
        TextView tAgregarCompra = (TextView) findViewById(R.id.AC_text_agregarCompra);
        tableLayoutAAC1 = (TableLayout) findViewById(R.id.AC_tableL_resumen);
        autoCNombreProducto = (AutoCompleteTextView) findViewById(R.id.AC_autoC_nombreProducto);
        autoCCodigoProducto = (AutoCompleteTextView) findViewById(R.id.AC_autoC_codigoProducto);
        editCantidad = (EditText) findViewById(R.id.AC_edit_cantidad);
        editMonto = (EditText) findViewById(R.id.AC_edit_monto);
        Button buttonAgregar = (Button) findViewById(R.id.AC_button_agregar);
        Button buttonLimpiarResumen = (Button) findViewById(R.id.AC_button_limpiarResumen);
        buttonRegistrarCompra = (Button) findViewById(R.id.AC_button_registrar);
        buttonRegistrarCompra.setEnabled(false);

        //inicializando tabla
        header = new String[]{"#", "Producto", "$ por unidad"};
        float[] weightCol= new float[]{1.5f,6f,2.5f};
        tablaResumen = new Tabla(this, tableLayoutAAC1, weightCol, header);

        //INICIALIZANDO VARIABLES
        listaCompras = new ArrayList<Compra>();
        arrayCodigosProductos = new ArrayList<String>();
        arrayNombresProductos = new ArrayList<String>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getArraysProductos();

        //CARGANDO DATOS AUTOCOMPLETES CODIGOS Y NOMBRE
        //configurando autoCompleteCodigo
        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayCodigosProductos);
        autoCCodigoProducto.setAdapter(adapterCodigos);

        //al seleccionar un elemento en el autoCompleteCodigo, se complete automaticamente el codigo correspondiente en el autoCompleteNombre
        autoCCodigoProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayCodigosProductos.indexOf(autoCCodigoProducto.getText().toString());
                autoCNombreProducto.setText(arrayNombresProductos.get(n));
            }
        });

        //configurando autoCompleteNombre
        ArrayAdapter<String> adapterNombres = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayNombresProductos);
        autoCNombreProducto.setAdapter(adapterNombres);

        //al seleccionar un elemento en el autoCompleteNombre, se complete automaticamente el nombre correspondiente en el autoCompleteCodigo
        autoCNombreProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayNombresProductos.indexOf(autoCNombreProducto.getText().toString());
                autoCCodigoProducto.setText(arrayCodigosProductos.get(n));
            }
        });

    }

    //PROCEDIMIENTO inicializa y carga los arreglos con nombres y codigos de los productos en la BDD
    public void getArraysProductos(){

        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();

        for (Producto producto : listaProductos ){
            arrayCodigosProductos.add(String.valueOf(producto.getCodigo()));
            arrayNombresProductos.add(producto.getNombre());
        }
    }

    //PROCEDIMIENTO BOTON "AGREGAR A RESUMEN"
    public void agregarCompraAResumen(View view){

        Fecha fecha = new Fecha();

        if (autoCCodigoProducto.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CODIGO DE PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (!listaProductos.contains(new Producto(Integer.parseInt(autoCCodigoProducto.getText().toString())))){

            Toast.makeText(getApplicationContext(), "No hay ningún producto registrado con ese código", Toast.LENGTH_LONG).show();

        } else if (autoCNombreProducto.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"NOMBRE DEL PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (editCantidad.getText().toString().isEmpty() || (Integer.parseInt(editCantidad.getText().toString()) <= 0)) {

            Toast.makeText(this, "El campo \"CANTIDAD\" esta incompleto o es menor que 1", Toast.LENGTH_LONG).show();

        } else {

            if (editMonto.getText().toString().isEmpty()){
                editMonto.setText("-1");
            }

            //agregando compra a bdd
            Compra nuevaCompra = new Compra(Integer.parseInt(autoCCodigoProducto.getText().toString()), fecha.getStringDMAH(), Integer.parseInt(editCantidad.getText().toString()), Double.valueOf(editMonto.getText().toString()));
            listaCompras.add(nuevaCompra);
            int[] maxEms= {0,3,0};

            //agregando compra a tabla resumen (en pantalla)
            if (editMonto.getText().toString() == "-1"){

                String[] row = {editCantidad.getText().toString(), autoCNombreProducto.getText().toString(), "-"};
                tablaResumen.addFilaConMaxEms(row, maxEms);

            }else{

                String[] row = {editCantidad.getText().toString(), autoCNombreProducto.getText().toString(), editMonto.getText().toString()};
                tablaResumen.addFilaConMaxEms(row, maxEms);

            }

            //limpiando views
            autoCCodigoProducto.setText("");
            autoCNombreProducto.setText("");
            editCantidad.setText("");
            editMonto.setText("");

            //habilitando boton registrar venta
            buttonRegistrarCompra.setEnabled(true);
        }
    }

    //PROCEDIMIENTO BOTON REGISTRAR COMPRA
    public void registrarCompra(View view){

        operacionesBDD.insertCompra(this.getApplicationContext(), listaCompras);
        Toast.makeText(this,"La compra se registró correctamente",Toast.LENGTH_LONG).show();
        ActAgregarMovimiento.fa.finish();
        this.finish();

    }

    //PROCEDIMIENTO BOTON LIMPIAR CAMPOS
    public void limpiarCampos(View view){

        autoCCodigoProducto.setText("");
        autoCNombreProducto.setText("");
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
}