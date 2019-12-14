package com.example.stocks.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.AdminDb;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.sql.OperacionesBDD;
import com.example.stocks.ui.adapter.RecyclerAdapter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.example.stocks.model.AdminDb.APP_PAQUETE;
import static com.example.stocks.model.AdminDb.NOMBRE_DB;

/*
ULTIMOS CAMBIOS
10-11
-desactive constructor de objeto Linea(idLinea)

11-11
-mejoras en activity agregar linea
-chequeo de duplicado al agregar una linea nueva

12-12
-mejoras en activity agregar producto
-se agregó el chequeo de campos en activity agregar producto

13-12
-comienzo de mejoras en la administración y uso de db

14-14
-se sigue con las mejoras en la administración y uso de db, específicamente el archivo OperacionesBDD
-se integró archivo insertOnDB en OperacionesBDD
-se comentó comando de idMovimiento, en OperacioneBDD, al agregar compra prestamo o venta,(supuestamente no es necesario porque esta definido en la db como autoincrement)
-se debugueo luego de los cambios


 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static ArrayList<Linea> listaLineas;
    public static ArrayList<Producto> listaProductos;
    public static RecyclerAdapter adapterRecycler;
    private RecyclerView recyclerProductos;
    private TableLayout tableLayout1;
    private AdminDb adminDb;
    private Tabla tabla;
    private int permissionWrite;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL = 1;
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 1;
    private OperacionesBDD operacionesBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIA EL ADMINISTRADOR DE BASE DE DATOS
        //adminDb = new AdminDb(this);
        operacionesBDD = OperacionesBDD.instancia(getApplicationContext());
        listaProductos = new ArrayList<>();
        listaLineas = new ArrayList<>();

        cargarListaLineas();
        cargarListaProductos();

        //INICIALIZANDO VIEWS
        recyclerProductos = (RecyclerView)findViewById(R.id.rvMAListaProductos);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        final FloatingActionButton fabAgregarMovimiento = (FloatingActionButton) findViewById(R.id.fabAgregarMovimiento);
        final FloatingActionButton fabAgregarProducto = (FloatingActionButton) findViewById(R.id.fabAgregarProducto);

        //CARGANDO CONTENIDO DE RECYCLERVIEW
        adapterRecycler = new RecyclerAdapter(getApplicationContext());//listaProductos);
        recyclerProductos.setAdapter(adapterRecycler);

        //ASIGNANDO FUNCIONES DE LOS BOTONES DEL MENU FLOTANTE
        fabAgregarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.collapse();
                agregarMovimiento(fabAgregarMovimiento);}
        });
        fabAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {fabMenu.collapse();
                agregarProducto(fabAgregarProducto);}
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterRecycler.notifyDataSetChanged();

    }

    //CARGA "ARRAYLIST<LINEAS> listaLineas" CON LOS PRODUCTOS QUE SE ENCUENTRAN EN LA BASE DE DATOS
    public void cargarListaLineas(){
        try {
            Cursor cursor = operacionesBDD.cursorLineas();//db.query(AdminDb.Tablas.LINEAS, "*", null, null, null, null, null);
            if (cursor.moveToFirst()) {

                listaLineas.add(new Linea(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                while (cursor.moveToNext()) {
                    listaLineas.add(new Linea(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                }

            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al cargar lista de líneas", Toast.LENGTH_LONG).show();
        }
    }

    //CARGA "ARRAYLIST<PRODUCTO> listaProductos" CON LOS PRODUCTOS QUE SE ENCUENTRAN EN LA BASE DE DATOS
    public void cargarListaProductos(){
        try {
            Cursor cursor = operacionesBDD.cursorTablaProductos();//db.query(TABLA_PRODUCTOS, campos, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                listaProductos.add(new Producto(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));

                while (cursor.moveToNext()) {
                    listaProductos.add(new Producto(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
                }
            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al cargar lista de productos", Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(getApplicationContext(), String.valueOf(operacionesBDD.dbClose()), Toast.LENGTH_LONG).show();

    }

    //FUNCION DEL BOTON AGREGAR MOVIMIENTO DEL MENU FLOTANTE
    public void agregarMovimiento(View view){
        Intent intentAgregarMovimiento= new Intent(this, ActAgregarMovimiento.class);
        startActivity(intentAgregarMovimiento);
    }

    //FUNCION DEL BOTON AGREGAR PRODUCTO
    public void agregarProducto(View view){
        Intent intentAgregarProducto= new Intent(this, ActAgregarProducto.class);
        startActivity(intentAgregarProducto);
    }

    //CREANDO MENU DE OPCIONES
    public boolean onCreateOptionsMenu (Menu buscador) {
        getMenuInflater().inflate(R.menu.menu_buscador,buscador);

            MenuItem item = buscador.findItem(R.id.buscador);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                adapterRecycler.updateList(listaProductos);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String busqueda) {

        ArrayList<Producto> listaFiltrada = new ArrayList<>()   ;

        try{
                for(Producto producto: listaProductos){
                    String nombreProducto = producto.getNombre().toLowerCase();
                    String codigoProducto = String.valueOf(producto.getCodigo()).toLowerCase();
                    if (nombreProducto.contains(busqueda.toLowerCase()) || codigoProducto.contains(busqueda.toLowerCase())){
                        listaFiltrada.add(producto);
                    }
                adapterRecycler.updateList(listaFiltrada);
           }

        }catch(Exception e){

            e.printStackTrace();

        }
        return true;
    }

    //PROCEDIMIENTO DEL BOTON EXPORTAR DB
    public void exportDB(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //se piden los permisos de escritura de almacenamiento externo
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL);
        } else{
            try {
                File internalDirectoy = new File(Environment.getExternalStorageDirectory(), "/StockS");

                //si no existe el directorio /StockS en la memoria interna, lo crea
                if (!internalDirectoy.exists()) {
                    if (internalDirectoy.mkdir()) {
                        Toast.makeText(getBaseContext(), "Se creo la carpeta /Stocks", Toast.LENGTH_LONG)
                                .show();
                    }

                }
                File appData = Environment.getDataDirectory(); //pidiendo directorio "data" de la app para hacer copia del archivo .db

                //si tiene los permisos de escritura del directorio de internalDirectoy exporta la base de datos
                if (internalDirectoy.canWrite()) {
                    String directorioDB = "//data//" + APP_PAQUETE + "//databases//" + NOMBRE_DB;
                    Fecha fecha = new Fecha();
                    String sFecha = String.valueOf(fecha.getLong());
                    String nameBackupDB = "StocksDb" + sFecha + ".db";
                    File exportFile = new File(appData, directorioDB);
                    File exportTo = new File(internalDirectoy, nameBackupDB);
                    if (exportFile.exists()) {
                        FileChannel src = new FileInputStream(exportFile).getChannel();
                        FileChannel dst = new FileOutputStream(exportTo).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    } else{
                        Toast.makeText(getBaseContext(), "No se halló la base de datos interna.", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getBaseContext(), "Se exportó correctamente la base de datos.", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getBaseContext(), "Error en la escritura del archivo backup.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Se produjo un error y la base de datos NO se exportó.", Toast.LENGTH_LONG).show();

            }
        }
    }

    //PORCEDIMIENTO DEL BOTON IMPORTAR DB (desde "/StockS/importDB.db)
    public void importDB(View view) {

        //se piden los permisos de lectura de almacenamiento externo
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_READ_EXTERNAL_STORAGE);

        try {
            File internalDirectoy = new File(Environment.getExternalStorageDirectory(), "/StockS");

            //si no existe el directorio /StockS en la memoria interna, lo crea
            if (!internalDirectoy.exists()) {
                if (internalDirectoy.mkdir()) {
                    Toast.makeText(getBaseContext(), "se creo /stocks", Toast.LENGTH_LONG)
                            .show();
                }

            }

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String nombreDB = "//data//" + APP_PAQUETE + "//databases//" + NOMBRE_DB;
                String nombreBackupDB = "/StockS/importDB.db";
                File importFrom = new File(sd, nombreBackupDB);
                File importTo = new File(data, nombreDB);

                FileChannel src = new FileInputStream(importFrom).getChannel();
                FileChannel dst = new FileOutputStream(importTo).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), "Se importó correctamente el backup de la base de datos.", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "No se pudo importar la base de datos", Toast.LENGTH_LONG).show();

        }

    }

}
