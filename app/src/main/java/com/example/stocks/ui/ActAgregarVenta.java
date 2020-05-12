package com.example.stocks.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.model.Data.Venta;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.ui.MainActivity.recyclerAdapter;

public class ActAgregarVenta extends AppCompatActivity {

    private Tabla tablaResumen;
    private EditText editCantidad, editMonto, editCliente;
    private AutoCompleteTextView autoCNombreProducto, autoCCodigoProducto;
    private ArrayList<String> arrayCodigosProductos, arrayNombresProductos;
    private ArrayList<Venta> listaVentas;
    private Button buttonRegistrarVenta;
    private OperacionesBDD operacionesBDD;
    private TextView textCantidadEnStock;
    private int enStock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_venta);

        //ocultando actionBar
        getSupportActionBar().hide();

        //inicializando admin de base de datos
        operacionesBDD= OperacionesBDD.instanceOf(getApplicationContext());

        //inicializando views
        textCantidadEnStock = findViewById(R.id.AV_text_cantidadEnStock);
        autoCCodigoProducto = findViewById(R.id.AV_autoC_codigoProducto);
        autoCNombreProducto = findViewById(R.id.AV_autoC_nombreProducto);
        editCantidad = findViewById(R.id.AV_edit_cantidad);
        editMonto = findViewById(R.id.AV_edit_monto);
        editCliente = findViewById(R.id.AV_edit_cliente);
        TableLayout tableLayoutResumen = findViewById(R.id.AV_tableL_resumen);
        buttonRegistrarVenta = findViewById(R.id.AV_button_registrar);
        buttonRegistrarVenta.setEnabled(false);

        //inicializando tabla
        String[] header = new String[]{" # ", " Producto ", " $ por unidad ", " Cliente "};
        float[] weightCol= new float[]{1.5f,4f,2f,2.5f};
        tablaResumen = new Tabla(this, tableLayoutResumen, weightCol, header);

        //INICIALIZANDO VARIABLES
        textCantidadEnStock.setText("0");
        listaVentas = new ArrayList<>();
        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();
        enStock= 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //CARGANDO DATOS AUTOCOMPLETES CODIGOS Y NOMBRE
        //cargando arrays
        getArraysProductos();

        //configurando autoCompleteCodigo
        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayCodigosProductos);
        autoCCodigoProducto.setAdapter(adapterCodigos);

        //al seleccionar un elemento en el autoCompleteCodigo, se complete automaticamente el codigo correspondiente en el autoCompleteNombre
        autoCCodigoProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayCodigosProductos.indexOf(autoCCodigoProducto.getText().toString());
                autoCNombreProducto.setText(arrayNombresProductos.get(n));
                enStock= listaProductos.get(n).getCantidad();
                for (Venta v: listaVentas){
                    if (v.getIdProducto() == Integer.parseInt(autoCCodigoProducto.getText().toString())){
                        enStock= enStock - v.getCantidad();
                    }

                }
                textCantidadEnStock.setText(String.valueOf(enStock));
            }
        });

        //configurando autoCompleteNombre
        ArrayAdapter<String> adapterNombres = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayNombresProductos);
        autoCNombreProducto.setAdapter(adapterNombres);

        //al seleccionar un elemento en el autoCompleteNombre, se complete automaticamente el nombre correspondiente en el autoCompleteCodigo
        autoCNombreProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayNombresProductos.indexOf(autoCNombreProducto.getText().toString());
                autoCCodigoProducto.setText(arrayCodigosProductos.get(n));
                enStock= listaProductos.get(n).getCantidad();
                //chequea si hay ventas cargadas del mismo producto en el resumen para descontar del numero previsible de stock
                for (Venta v: listaVentas){
                    if (v.getIdProducto() == Integer.parseInt(autoCCodigoProducto.getText().toString())){
                        enStock= enStock - v.getCantidad();
                    }

                }
                textCantidadEnStock.setText(String.valueOf(enStock));

            }
        });

    }

    //PROCEDIMIENTO Q CARGA LOS ARREGLOS PASADOS CON LOS NOMBRES Y CODIGOS DE LOS PRODUCTOS EN LA BDD
    public void getArraysProductos() {

        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();

        for (Producto producto : listaProductos) {
            arrayCodigosProductos.add(String.valueOf(producto.getCodigo()));
            arrayNombresProductos.add(producto.getNombre());
        }

    }

    //PROCEDIMIENTO BOTON "AGREGAR A RESUMEN"
    public void agregarVentaAResumen(View view) {

        Fecha fecha = new Fecha();

        //cargando datos de venta en buffer
        if (autoCCodigoProducto.getText().toString().isEmpty()) {

            Toast.makeText(this, "El campo \"CODIGO DE PRODUCTO\" esta incompleto", Toast.LENGTH_LONG).show();

        } else if (autoCNombreProducto.getText().toString().isEmpty()) {

            Toast.makeText(this, "El campo \"NOMBRE DEL PRODUCTO\" esta incompleto", Toast.LENGTH_LONG).show();

        } else {

            int pos = listaProductos.indexOf(new Producto(Integer.parseInt(autoCCodigoProducto.getText().toString())));
            if (pos == -1) {

                Toast.makeText(getApplicationContext(), "No hay ningún producto registrado con ese código", Toast.LENGTH_LONG).show();

            } else if (!listaProductos.get(pos).getNombre().equals(autoCNombreProducto.getText().toString())) {

                Toast.makeText(getApplicationContext(), "El código y el nombre no coinciden con el producto registrado en la base de datos", Toast.LENGTH_LONG).show();

            } else if (editCantidad.getText().toString().isEmpty() || (Integer.parseInt(editCantidad.getText().toString()) <= 0)) {

                Toast.makeText(this, "El campo \"CANTIDAD\" esta incompleto o es menor que 1", Toast.LENGTH_LONG).show();

            } else if (editCliente.getText().toString().isEmpty()) {

                Toast.makeText(this, "El campo \"CLIENTE\" esta incompleto", Toast.LENGTH_LONG).show();

            } else if (enStock < Integer.parseInt((editCantidad.getText().toString()))) {

                Toast.makeText(this, "No hay suficiente stock para la venta", Toast.LENGTH_LONG).show();

            } else {

                //agreagando venta a ArrayList listaVentas
                Venta nuevaVenta = new Venta(Integer.parseInt(autoCCodigoProducto.getText().toString()), fecha.getStringDMAH(), Integer.parseInt(editCantidad.getText().toString()), Float.valueOf(editMonto.getText().toString()), editCliente.getText().toString());
                listaVentas.add(nuevaVenta);

                //agregando venta a tabla resumen (en pantalla)
                String[] row = {editCantidad.getText().toString(), autoCNombreProducto.getText().toString(), editMonto.getText().toString(), editCliente.getText().toString()};
                tablaResumen.addFila(row);

                //limpiando views
                autoCCodigoProducto.setText("");
                autoCNombreProducto.setText("");
                editCantidad.setText("");
                editMonto.setText("");
                editCliente.setText("");
                enStock = 0;
                textCantidadEnStock.setText("0");

                //habilitando boton registrar venta
                buttonRegistrarVenta.setEnabled(true);
            }
        }
    }

    //PROCEDIMIENTO BOTON "REGISTRAR VENTA"
    public void registrarVenta(View view) {

        for (int i = 0; i < listaVentas.size(); i++) {

            operacionesBDD.insertVenta(listaVentas.get(i));
            Producto p = new Producto(listaVentas.get(i).getIdProducto());
            int pos = listaProductos.indexOf(p);
            listaProductos.get(pos).restarACantidad((listaVentas.get(i).getCantidad()));
            recyclerAdapter.notifyItemChanged(pos);

        }

        Toast.makeText(getApplicationContext(), "La venta se registró correctamente", Toast.LENGTH_LONG).show();
        ActAgregarMovimiento.fa.finish();
        this.finish();

    }

    //PROCEDIMIENTO BOTON "LIMPIAR RESUMEN"
    public void limpiarResumen(View view) {
        listaVentas.clear();
        tablaResumen.removeAllViews();
        buttonRegistrarVenta.setEnabled(false);
        Toast.makeText(this, "Se limpió la tabla resumen", Toast.LENGTH_SHORT).show();
    }

}