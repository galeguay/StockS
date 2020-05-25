package com.example.stocks.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.sql.AdminBDD;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;

public class ActivityAgregarPrestamo extends AppCompatActivity {

    private AdminBDD adminBDD;
    private EditText eCantidad, eSocia;
    private AutoCompleteTextView autoCCodigoProducto, autoCNombreProducto;
    private ArrayList<String> arrayCodigosP, arrayNombresP, TiposPrestamos;
    private RadioButton radioBPrestamoPedido, radioBPrastamoDado;
    private OperacionesBDD operacionesBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_agregar_prestamo);

        //ocultando actionBar
        getSupportActionBar().hide();

        //inicializando admin de base de datos
        operacionesBDD= OperacionesBDD.instanceOf(getApplicationContext());

        //CARGANDO VIEWS
        TextView tTitulo = (TextView)findViewById(R.id.AP_text_titulo);
        eSocia = (EditText) findViewById(R.id.AP_edit_socia);
        eCantidad = (EditText) findViewById(R.id.AP_edit_cantidad);
        autoCCodigoProducto = (AutoCompleteTextView) findViewById(R.id.AP_autoC_codigoProducto);
        autoCNombreProducto = (AutoCompleteTextView) findViewById(R.id.AP_autoC_nombreProducto);
        radioBPrestamoPedido = (RadioButton) findViewById(R.id.AP_radioB_PrestamoPedido);
        radioBPrastamoDado = (RadioButton) findViewById(R.id.AP_radioB_PrestamoDado);
        Button bAgregarPrestamo = (Button) findViewById(R.id.AP_button_agregarPrestamo);

        //INICIALIZANDO VARIABLES
        arrayCodigosP = new ArrayList<>();
        arrayNombresP = new ArrayList<>();

        //CARGANDO DATOS AUTOCOMPLETES CODIGO Y NOMBRE
        getArraysProductos(arrayCodigosP,arrayNombresP);

        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,arrayCodigosP);
        autoCCodigoProducto.setAdapter(adapterCodigos);
        autoCCodigoProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayCodigosP.indexOf(autoCCodigoProducto.getText().toString());
                autoCNombreProducto.setText(arrayNombresP.get(n));
            }
        });

        ArrayAdapter<String> adapterNombres = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,arrayNombresP);
        autoCNombreProducto.setAdapter(adapterNombres);
        autoCNombreProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayNombresP.indexOf(autoCNombreProducto.getText().toString());
                autoCCodigoProducto.setText(arrayCodigosP.get(n));
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.AP_radioB_PrestamoPedido:
                if (checked)
                    Toast.makeText(this,"Prestamo PEDIDO", Toast.LENGTH_SHORT).show();
                    break;
            case R.id.AP_radioB_PrestamoDado:
                if (checked)
                    Toast.makeText(this,"Prestamo DADO", Toast.LENGTH_SHORT).show();
                    break;
        }
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

    //PROCEDIMIENTO BUTTON REGISTRAR PRESTAMO
    public void agregarPrestamo(View view){
        if ((!radioBPrastamoDado.isChecked()) && (!radioBPrestamoPedido.isChecked())){

            Toast.makeText(this,"No seleccionó el tipo de prestamo", Toast.LENGTH_SHORT).show();

        }else {

            if (radioBPrestamoPedido.isChecked()) {

               // operacionesBDD.insertPrestamo(this.getApplicationContext(), autoCCodigoProducto.getText().toString(),"pedido", eSocia.getText().toString(), eCantidad.getText().toString());
                Toast.makeText(this, "Se registró correctamente el prestamo", Toast.LENGTH_SHORT).show();

            } else if (radioBPrastamoDado.isChecked()) {

                //operacionesBDD.insertPrestamo(this.getApplicationContext(), autoCCodigoProducto.getText().toString(),"dado", eSocia.getText().toString(), eCantidad.getText().toString());
                Toast.makeText(this, "Se registró correctamente el prestamo", Toast.LENGTH_SHORT).show();

            }
        }

        ActivityAgregarMovimiento.fa.finish();
        this.finish();

    }

}