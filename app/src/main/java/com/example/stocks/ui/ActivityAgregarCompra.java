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
import android.widget.EditText;
import android.widget.Toast;
import com.example.stocks.R;
import com.example.stocks.model.Data.*;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.ui.MainActivity.recyclerAdapter;

public class ActivityAgregarCompra extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_compra);

        //ocultando actionBar
        getSupportActionBar().hide();






    }


}