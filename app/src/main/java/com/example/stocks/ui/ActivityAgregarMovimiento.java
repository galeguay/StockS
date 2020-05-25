package com.example.stocks.ui;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stocks.R;

public class ActivityAgregarMovimiento extends AppCompatActivity implements FragmentAgregarCompra.OnFragmentInteractionListener, FragmentAgregarVenta.OnFragmentInteractionListener, FragmentAgregarPrestamo.OnFragmentInteractionListener {

    public Button bCompra;
    public Button bVenta;
    public Button bPrestamo;
    public Button bDevolucion;
    public static Activity fa;
    private FragmentAgregarPrestamo fragmentAgregarPrestamo;
    private FragmentAgregarVenta fragmentAgregarVenta;
    private FragmentAgregarCompra fragmentAgregarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_movimiento);

        //ocultando actionBar
        getSupportActionBar().hide();

        fa = this;

        //inicializando views
        bCompra = (Button) findViewById(R.id.bCompra);
        bVenta = (Button) findViewById(R.id.bVenta);
        bPrestamo = (Button) findViewById(R.id.bPrestamo);
        bDevolucion = (Button) findViewById(R.id.bDevolucion);

        //inicializando fragments
        fragmentAgregarPrestamo = FragmentAgregarPrestamo.newInstance();
        fragmentAgregarCompra = FragmentAgregarCompra.newInstance();
        fragmentAgregarVenta = FragmentAgregarVenta.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.AAM_fl_contenedorFragments, fragmentAgregarPrestamo).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.AAM_fl_contenedorFragments, fragmentAgregarVenta).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.AAM_fl_contenedorFragments, fragmentAgregarCompra).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.AAM_fl_contenedorFragments, FragmentAgregarCompra.newInstance()).addToBackStack(null).commit();

    }


    public void agregarCompra(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.AAM_fl_contenedorFragments, FragmentAgregarCompra.newInstance()).addToBackStack(null).commit();

    }

    public void agregarVenta(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.AAM_fl_contenedorFragments, FragmentAgregarVenta.newInstance()).addToBackStack(null).commit();

    }

    public void agregarPrestamo(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.AAM_fl_contenedorFragments, FragmentAgregarPrestamo.newInstance()).addToBackStack(null).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}