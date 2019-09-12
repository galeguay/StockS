package com.example.stocks.ui;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stocks.R;

public class ActAgregarMovimiento extends AppCompatActivity {

    public Button bCompra;
    public Button bVenta;
    public Button bPedido;
    public Button bDevolucion;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_movimiento);

        fa=this;

        //inicializando views
        bCompra = (Button) findViewById(R.id.bCompra);
        bVenta = (Button) findViewById(R.id.bVenta);
        bPedido = (Button) findViewById(R.id.bPrestamo);
        bDevolucion = (Button) findViewById(R.id.bDevolucion);

    }


    public void agregarCompra(View view){
        Intent intentAgregarCompra = new Intent(this, ActAgregarCompra.class);
        startActivity(intentAgregarCompra);
    }
/*
    public void agregarVenta(View view){
        Intent intentAgregarVenta = new Intent(this, ActAgregarVenta.class);
        startActivity(intentAgregarVenta);
    }*/

}