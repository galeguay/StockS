package com.example.stocks.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.model.InsertDataOnDb;

import static com.example.stocks.ui.MainActivity.adapterRecycler;
import static com.example.stocks.ui.MainActivity.listaLineas;
import static com.example.stocks.ui.MainActivity.listaProductos;

public class ActAgregarLinea extends AppCompatActivity {

    private int codigoColor;
    private TextView editNombre, editColorPersonalizado;
    private RadioButton radioBVerdeAgua, radioBCerezo, radioBTerra, radioBRosa, radioBNegro, radioBVerdeclaro, radioBCeleste, radioBAzul, radioBAmarillo, radioBPersonalizado;
    private RadioGroup radioGroupColores;
    private boolean personalizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_linea);

        editNombre = (EditText) findViewById(R.id.AL_edit_nombreLinea);
        editColorPersonalizado = (EditText) findViewById(R.id.AL_edit_colorPersonalizado);
        editColorPersonalizado.setEnabled(false);
        personalizado = false;
        codigoColor = Color.parseColor("#008866");

        radioGroupColores = (RadioGroup) findViewById(R.id.AL_radioGroup_colores);

        radioGroupColores.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.AL_radioButton_verdeAgua:
                        //if (checkedId == R.id.AL_radioButton_verdeAgua)
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#008866");
                        break;
                    case R.id.AL_radioButton_cerezo:
                        //if (checkedId)
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#9f021e");
                        break;
                    case R.id.AL_radioButton_terra:
                        //if (checkedId)
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#434D4B");
                        break;
                    case R.id.AL_radioButton_rosa:
                        //if (checkedId)
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#fcbcb4");
                        break;
                    case R.id.AL_radioButton_negro:
                        //if (checkedId)
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#000000");
                        break;
                    case R.id.AL_radioButton_verdeClaro:
                        //if (checkedId)
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#00bb2d");
                        break;
                    case R.id.AL_radioButton_celeste:
                        //if (checkedId)

                        //Toast.makeText(this.g, "asdasd", Toast.LENGTH_LONG).show();
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#51d1f6");
                        break;
                    case R.id.AL_radioButton_azul:
                        //if (checkedId)
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#0000ff");
                        break;
                    case R.id.AL_radioButton_amarillo:
                        //if (checkedId)
                        //Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();
                        personalizado = false;
                        editColorPersonalizado.setEnabled(false);
                        codigoColor = Color.parseColor("#ffff00");
                        break;
                    case R.id.AL_radioButton_personalizado:
                        //if (checkedId)
                        personalizado = true;
                        editColorPersonalizado.setEnabled(true);

                        break;

                }
                }

        });

    }

    public void check(View view){

        Toast.makeText(this.getApplicationContext(), String.valueOf(codigoColor), Toast.LENGTH_LONG).show();

    }

    //PROCEDIMIENTO BOTON "REGISTRAR LINEA"
    public void registrarLinea(View view){

        if (personalizado) {
            try {

                codigoColor = Color.parseColor(editColorPersonalizado.getText().toString());
                Toast.makeText(this, String.valueOf(codigoColor),+ Toast.LENGTH_LONG).show();
                Toast.makeText(this, editNombre.getText().toString(), Toast.LENGTH_LONG).show();
                InsertDataOnDb insertDataOnDb = new InsertDataOnDb();
                insertDataOnDb.insertLinea(this.getApplicationContext(), editNombre.getText().toString(), codigoColor);
                Toast.makeText(this,"Se registró correctamente la linea",Toast.LENGTH_LONG).show();
                this.finish();

            }catch (Exception e){

                Toast.makeText(this,"El color personalizado ingresado no es válido", Toast.LENGTH_SHORT).show();

            }

        }else {

            InsertDataOnDb insertDataOnDb = new InsertDataOnDb();
            long id = insertDataOnDb.insertLinea(this.getApplicationContext(), editNombre.getText().toString(), codigoColor);
            Toast.makeText(this, "Se registró correctamente la linea", Toast.LENGTH_LONG).show();

            Linea nuevaLinea = new Linea(id, editNombre.getText().toString(), codigoColor);
            listaLineas.add(nuevaLinea);

            this.finish();
        }
    }

}
