package com.example.stocks.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.AdminDb;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.model.Data.Venta;
import com.example.stocks.model.InsertDataOnDb;

import java.util.ArrayList;

import static com.example.stocks.model.Contract.DB_NOMBRE;
import static com.example.stocks.ui.MainActivity.listaProductos;

public class ActAgregarVenta extends AppCompatActivity {


    private AdminDb adminDb;
    private TableLayout tableLayoutResumen;
    private Tabla tablaResumen;
    private EditText editCantidad, editMonto, editCliente, editComentario;
    private AutoCompleteTextView autoCNombreP;
    private AutoCompleteTextView autoCCodigoP;
    private ArrayList<String> arrayCodigosP;
    private ArrayList<String> arrayNombresP;
    private ArrayList<Venta> listaVentas;
    private String[] header;
    private InsertDataOnDb insertDataOnDB;
    private Button buttonRegistrarVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_venta);

        //ocultando actionBar
        getSupportActionBar().hide();

        //inicializando admin de base de datos
        adminDb = new AdminDb(this, DB_NOMBRE, null, 1);

        //inicializando views
        TextView tituloActivity = (TextView) findViewById(R.id.AV_text_tituloActivity);
        AutoCompleteTextView autoCCodigoProducto = (AutoCompleteTextView) findViewById(R.id.AV_autoC_codigoProducto);
        AutoCompleteTextView autoCNombreProducto = (AutoCompleteTextView) findViewById(R.id.AV_autoC_codigoProducto);
        editCantidad = (EditText) findViewById(R.id.AV_edit_cantidad);
        editMonto = (EditText) findViewById(R.id.AV_edit_monto);
        editCliente = (EditText) findViewById(R.id.AV_edit_cliente);
        tableLayoutResumen = (TableLayout) findViewById(R.id.AV_tableL_resumen);
        Button bAgregarAResumen = (Button) findViewById(R.id.AV_button_aResumen);
        Button bLimpiarCampos = (Button)findViewById(R.id.AV_button_limpiarCampos);
        Button bLimpiarResumen = (Button)findViewById(R.id.AV_button_limpiarResumen);
        buttonRegistrarVenta = (Button) findViewById(R.id.AV_button_registrar);
        buttonRegistrarVenta.setEnabled(false);

        //inicializando tabla
        header=new String[] {"Producto", "Cantidad", "Monto x uni", "Cliente"};
        tablaResumen = new Tabla(this, tableLayoutResumen,"RESUMEN DE COMPRA", header);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getArraysProductos(arrayCodigosP, arrayNombresP);


    }

    //PROCEDIMIENTO BOTON "LIMPIAR CAMPOS"
    public void limpiarCampos(View view){
        autoCCodigoP.setText("");
        autoCNombreP.setText("");
        editCantidad.setText("");
        editMonto.setText("");
        Toast.makeText(this,"Se limpiaron todos los campos",Toast.LENGTH_SHORT).show();
    }

    //PROCEDIMIENTO BOTON "LIMPIAR RESUMEN"
    public void limpiarResumen(View view){
        listaVentas.clear();
        tablaResumen.removeAllViews();
        buttonRegistrarVenta.setEnabled(false);
        Toast.makeText(this,"Se limpió la tabla resumen",Toast.LENGTH_SHORT).show();
    }

    //PROCEDIMIENTO Q CARGA LOS ARREGLOS PASADOS CON LOS NOMBRES Y CODIGOS DE LOS PRODUCTOS EN LA BDD
    public void getArraysProductos(ArrayList<String> arrayCodigosProductos, ArrayList<String> arrayNombresProductos) {

        arrayCodigosProductos.clear();
        arrayNombresProductos.clear();
        for (Producto producto : listaProductos) {
            arrayCodigosProductos.add(String.valueOf(producto.getCodigo()));
            arrayNombresProductos.add(producto.getNombre());
        }


    }

    //PROCEDIMIENTO BOTON "AGREGAR A RESUMEN"
    public void agregarMovimientoATabla(View view){

        Fecha fecha = new Fecha();

        //cargando datos de venta en buffer
        if (autoCCodigoP.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CODIGO DE PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (autoCNombreP.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"NOMBRE DEL PRODUCTO\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (editCantidad.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CANTIDAD\" esta incompleto",Toast.LENGTH_LONG).show();

        } else if (editCliente.getText().toString().isEmpty()){

            Toast.makeText(this,"El campo \"CLIENTE\" esta incompleto",Toast.LENGTH_LONG).show();

        } else {

            if (editMonto.getText().toString().isEmpty()){
                editMonto.setText("-1");
            }

            //agreagando venta a bdd
            Venta nuevaVenta = new Venta(Integer.parseInt(autoCCodigoP.getText().toString()), fecha.getStringDMAH(), Integer.parseInt(editCantidad.getText().toString()), Double.valueOf(editMonto.getText().toString()), editCliente.getText().toString());
            listaVentas.add(nuevaVenta);

            //agregando venta a tabla resumen (en pantalla)
            if (editMonto.getText().toString() == "-1"){

                String[] row = {autoCNombreP.getText().toString(), editCantidad.getText().toString(), "-", editCliente.getText().toString()};
                tablaResumen.addRow(row);

            }else{

                String[] row = {autoCNombreP.getText().toString(), editCantidad.getText().toString(), editMonto.getText().toString(), editCliente.getText().toString()};
                tablaResumen.addRow(row);
            }

            //limpiando views
            autoCCodigoP.setText("");
            autoCNombreP.setText("");
            editCantidad.setText("");
            editMonto.setText("");

            buttonRegistrarVenta.setEnabled(true);

        }

    }

    //PROCEDIMIENTO BOTON "REGISTRAR VENTA"
    public void registrarVenta(){

        insertDataOnDB.insertVenta(this.getApplicationContext(),listaVentas);
        Toast.makeText(this,"La venta se registró correctamente",Toast.LENGTH_LONG);
        ActAgregarMovimiento.fa.finish();
        this.finish();

    }
}
