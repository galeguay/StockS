package com.example.stocks.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.stocks.R;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.sql.AdminBDD;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;

public class ActInfoProducto extends AppCompatActivity implements FragmentUltimosMovimientos.OnFragmentInteractionListener, FragmentDetalleMovimiento.OnFragmentInteractionListener{

    private AdminBDD baseDeDatos;
    private Bundle extras;
    private int codigoProducto;
    private String nombreProducto;
    private OperacionesBDD operacionesBDD;
    private FragmentUltimosMovimientos fragmentUltimosMovimientos;
    private FragmentDetalleMovimiento fragmentDetalleMovimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_info_producto);

        //ocultando actionBar
        getSupportActionBar().hide();

        //cargando datos pasados por el mainAct (nombre y codgo del producto seleccionado en el recyclerview)
        obtenerExtras();

        //instanciando objeto para operaciones en base de datos
        //operacionesBDD = OperacionesBDD.instanceOf(this.getApplicationContext());

        //inicializando views
        TextView tvNombreProducto = (TextView)findViewById(R.id.AIP_textview_nombreProducto);
        FrameLayout contenedorFragments = (FrameLayout)findViewById(R.id.AIP_fl_contenedorFragments);
        fragmentUltimosMovimientos = FragmentUltimosMovimientos.newInstance(extras);
        //fragmentDetalleMovimiento = FragmentDetalleMovimiento.newInstance();
        //fragmentDetalleMovimiento= new FragmentDetalleMovimiento();

        //pasando bundle con variables de codigoProducto y nombreProducto al fragmentUltimosmovimientos
        //Bundle bundle= extras;
        //fragmentUltimosMovimientos.setArguments(bundle);

        //cargando fragmentUltimosMovimientos en el contenedor
        getSupportFragmentManager().beginTransaction().add(R.id.AIP_fl_contenedorFragments,fragmentUltimosMovimientos).commit();

        //cargando datos en los views correspondientes
        tvNombreProducto.setText(nombreProducto);

        //cargarMovimientos();
    }

    private void obtenerExtras() {
        extras = getIntent().getExtras();
        codigoProducto = extras.getInt("codigoProducto");
        nombreProducto = extras.getString("nombreProducto");
    }

/*
    private void cargarMovimientos(){

        //SQLiteDatabase db = baseDeDatos.getReadableDatabase();
        //try {
            //OperacionesBDD operacionesBDD = OperacionesBDD();
            ArrayList<String[]> lista= operacionesBDD.listaUltimosMovimientos(codigoProducto);
            if (!lista.isEmpty()) {
                int i = 0;
                while (i < lista.size()) {
                    tabla.addFila(lista.get(i));
                    i++;
                }
            }

        }catch(Exception e){
            Toast.makeText(this.getApplicationContext(),"Error al cargar nombre producto", Toast.LENGTH_LONG).show();

        }


        Cursor cursor = db.rawQuery("select M."+C_ID_PRODUCTO+", M."+C_FECHA+",  from "+TABLA_MOVIMIENTOS+" M, "+TABLA_COMPRAS+" C,"+TABLA_VENTAS+" V, "+TABLA_PRESTAMOS+" P, where "+ C_ID_PRODUCTO +" = ",null);
        cursor.moveToFirst();

        while (!cursor.isNull()){
        }

            String[] row = {};
            cursor.moveToNext();





        }

        //SELECT OrderID, C.CustomerID, CompanyName, OrderDate FROM Customers C, Orders O, Employees E WHERE C.CustomerID = O.CustomerID AND O.EmployeeID = E.EmployeeID







        long registros = DatabaseUtils.longForQuery(db,"SELECT COUNT(*) FROM "+ TABLA_MOVIMIENTOS,null);
        String registro = String.valueOf(registros);
        String[] campos= {C_ID_LINEA, C_NOMBRE_LINEA, C_COLOR};

        try {
            Cursor cursor = db.query(TABLA_LINEAS, campos, null, null, null, null, null);
            if (cursor.moveToFirst()) {

                listaLineas.add(new Linea(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                while (cursor.moveToNext()) {
                    listaLineas.add(new Linea(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                }

            }
*/


    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
