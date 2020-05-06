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
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.sql.OperacionesBDD;
import com.example.stocks.ui.adapter.RecyclerAdapter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.example.stocks.sql.AdminBDD.APP_PAQUETE;
import static com.example.stocks.sql.AdminBDD.NOMBRE_DB;

/*
HACER
      -ver problema de tamaño de fuente dependieno el celular
      -chequeo de que nombre y codigo correspondan a un mismo producto, en act agregar compra y agregar venta


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

14-12
-se sigue con las mejoras en la administración y uso de db, específicamente el archivo OperacionesBDD
-se integró archivo insertOnDB en OperacionesBDD
-se comentó comando de idMovimiento, en OperacioneBDD, al agregar compra prestamo o venta,(supuestamente no es necesario porque esta definido en la db como autoincrement)
-se debugueo luego de los cambios
-se elimino MyAplication

28-1
-debugueo de activity agregarventa
-se borraron procedimientos sin uso de objeto Tabla: newCellDefault y newCell2
-se hicieron correciones en los xml de activities de agregar compra y agregar venta
-se mejoró la previsualizacion de stock en activity agregar venta

3-3
-se pasò act agregarcompra y agregarventa de relativeLayout a constraintLayout
-debugueo de activity agregarcompra
-debugueo de activity agregarventa

4-3
-se agregó filtro en activity agregar venta y activity agregar compra:
    -cuando se intenta ingresar un producto q no esta registrado
    -cantidad ingresada < 0
-se modificó la configuración de algunas de las activitys para evitar que se reinicie al rotar la pantalla

6-3
-se solucionó problema de tablas de resumen en activitys agregar compra y agregar venta

17-4
-se continua creando la activity info de productos (ultios movieminetos)
-se creo metodo .ultimosMovimientos para clase OperacionesBDD que se usa en ActInfoProducto
+se busca implementar codigo para que al presionar un producto en la lista (recyclerList) muestre ActInfoProducto con los datos correspondientes a ese producto
-se agrego metodo addRow1() en objeto Tabls, que a diferencia de addFila() no usa arreglo de ancho de columnas

21-4
-se quitó boton "limpiar campos" de activities AgregarVenta y AgregarCompra
-se hicieron cambios en el codigo del objeto Tabla referidos al parametro "weight column" para una mejor visualización de los datos en la tabla
    -por dichos cambios, se adaptó el codigo en las activities AgregarVenta, AgregarCompra y InfoProd para su correcto funcionamiento
-se implemento codigo para mostrar últimos movimiento (ejecutando el activity InfoProd)del producto seleccionado en el recyclerView del MainActivity

22-4
-se agregó codigo en el objeto Tabla para poder personalizar el parametro maxEMS por columna

28-4
- se creo el fragment para detalles de movimientos y se empezo a trasladar la partes grafica del activityInfoProd a un nuevo fragmentUltimosMovimientos

3-5
-se traslado la interfaz del ActInfoPod al FragmentUltimosMovimientos con éxito
*se comenzó a implementar codigo para que al seleccionar una fila del la tabla ultimos movimientos muestre el fragmentDetalles

4-5
-se termino de implementar el cambio anterior, solo falta la carga y presentación de datos correspondientes a dicho movimiento en el fragment
*revisar si los metodos de OperacionesBDD siguen la inea del proposito de este objeto



 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static ArrayList<Linea> listaLineas;
    public static ArrayList<Producto> listaProductos;
    public static RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerProductos;
    private int permissionWrite;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL = 1;
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 1;
    private OperacionesBDD operacionesBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIA EL ADMINISTRADOR DE BASE DE DATOS
        operacionesBDD = OperacionesBDD.instanceOf(getApplicationContext());

        //CARGA LAS LISTAS DE DATOS
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
        recyclerAdapter = new RecyclerAdapter(getApplicationContext());//listaProductos);
        recyclerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfoProducto = new Intent(getApplicationContext(), ActInfoProducto.class);
                intentInfoProducto.putExtra("codigoProducto", listaProductos.get(recyclerProductos.getChildAdapterPosition(v)).getCodigo());
                intentInfoProducto.putExtra("nombreProducto", listaProductos.get(recyclerProductos.getChildAdapterPosition(v)).getNombre());
                startActivity(intentInfoProducto);

            }
        });
        recyclerProductos.setAdapter(recyclerAdapter);

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
        recyclerAdapter.notifyDataSetChanged();

    }

    //CARGA "ARRAYLIST<LINEAS> listaLineas" CON LOS PRODUCTOS QUE SE ENCUENTRAN EN LA BASE DE DATOS
    public void cargarListaLineas(){
        try {
            Cursor cursor = operacionesBDD.cursorLineas();//db.query(AdminBDD.Tablas.LINEAS, "*", null, null, null, null, null);
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
                recyclerAdapter.updateList(listaProductos);
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
                recyclerAdapter.updateList(listaFiltrada);
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
                    String sFecha = String.valueOf(fecha.getLongAMDH());
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

    //PORCEDIMIENTO DEL BOTON IMPORTAR DB (desde "/StockS/importDB.db)3
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
                    Toast.makeText(getBaseContext(), "Se creó la carpeta /stocks en la Tarjeta de memoria", Toast.LENGTH_LONG)
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
