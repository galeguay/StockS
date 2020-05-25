package com.example.stocks.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.sql.OperacionesBDD;

import static com.example.stocks.ui.MainActivity.listaLineas;

public class ActivityAgregarLinea extends AppCompatActivity {

    private int codigoColor;
    private EditText editNombre, editCodigoColor;
    private TextView textMuestraColor;
    private Button buttonRegistrar;
    private OperacionesBDD operacionesBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_linea);

        //iniciando adminBDD
        operacionesBDD= OperacionesBDD.instanceOf(getApplicationContext());

        //ocultando actionBar
        getSupportActionBar().hide();

        //asociando views
        buttonRegistrar =  findViewById(R.id.AL_button_registrarLinea);
        textMuestraColor = findViewById(R.id.AL_text_muestraColor);
        editNombre = findViewById(R.id.AL_edit_nombreLinea);
        editCodigoColor = findViewById(R.id.AL_edit_colorPersonalizado);

        //inicializando views
        buttonRegistrar.setEnabled(false);

    }

    public void botonColores (View view){

        ColorDrawable viewColor = (ColorDrawable) view.getBackground();
        codigoColor = viewColor.getColor();
        textMuestraColor.setBackgroundColor(codigoColor);
        buttonRegistrar.setEnabled(true);

    }

    public void colorPersonalizado(View view){

        try {
            codigoColor = Color.parseColor(editCodigoColor.getText().toString());
            textMuestraColor.setBackgroundColor(codigoColor);
            buttonRegistrar.setEnabled(true);
        } catch (Exception e) {
            Toast.makeText(this, "EL CODIGO DE COLOR INGRESADO NO ES VÁLIDO", Toast.LENGTH_SHORT).show();
        }


    }

    //PROCEDIMIENTO BOTON "REGISTRAR LINEA"
    public void registrarLinea(View view){


        if (!editNombre.getText().toString().equals("")) {

            //chequeo de nombre duplicado o color en uso
            boolean enUso = false;
            for (Linea l : listaLineas) {
                if (l.getNombre().equals(editNombre.getText().toString())) {
                    enUso = true;
                    Toast.makeText(this, "EL NOMBRE YA ESTÁ EN USO", Toast.LENGTH_LONG).show();
                }
                if (l.getColor() == codigoColor) {
                    enUso = true;
                    Toast.makeText(this, "EL COLOR YA ESTÁ EN USO", Toast.LENGTH_LONG).show();
                }
            }

            if (!enUso) {
                long id = operacionesBDD.insertLinea(editNombre.getText().toString(), codigoColor);
                Toast.makeText(this, "Se registró correctamente la linea", Toast.LENGTH_LONG).show();

                //agregando lina nueva a listaLineas del MAIN
                Linea nuevaLinea = new Linea(id, editNombre.getText().toString(), codigoColor);
                listaLineas.add(nuevaLinea);
                this.finish();
            }
        }else{
            Toast.makeText(this, "INGRESE EL NOMBRE DE LA LINEA", Toast.LENGTH_LONG).show();
        }
    }

}
