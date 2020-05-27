package com.example.stocks.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.Data.Compra;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.ui.MainActivity.recyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAgregarCompra.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAgregarCompra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAgregarCompra extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View vista;
    private Tabla tablaResumen;
    private EditText editCantidad, editMonto;
    private AutoCompleteTextView autoCNombreProducto, autoCCodigoProducto;
    private ArrayList<String> arrayCodigosProductos, arrayNombresProductos;
    private ArrayList<Compra> listaCompras;
    private Button buttonRegistrarCompra, buttonAgrergarProducto, buttonAgregarAResumen, buttonLimpiarResumen;
    private OperacionesBDD operacionesBDD;

    public FragmentAgregarCompra() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentAgregarCompra newInstance() {
        FragmentAgregarCompra fragment = new FragmentAgregarCompra();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //inicializando admin de base de datos
        operacionesBDD = OperacionesBDD.instanceOf(getActivity().getApplicationContext());

        //INICIALIZANDO VARIABLES
        listaCompras = new ArrayList<>();
        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_agregar_compra, container, false);

        //CARGANDO VIEWS
        TableLayout tableLayoutAAC1 = vista.findViewById(R.id.FAC_tableL_resumen);
        autoCNombreProducto = vista.findViewById(R.id.FAC_autoC_nombreProducto);
        autoCCodigoProducto = vista.findViewById(R.id.FAC_autoC_codigoProducto);
        editCantidad = vista.findViewById(R.id.FAC_edit_cantidad);
        editMonto = vista.findViewById(R.id.FAC_edit_monto);
        buttonRegistrarCompra = vista.findViewById(R.id.FAC_button_registrar);
        buttonRegistrarCompra.setEnabled(false);
        buttonAgregarAResumen = vista.findViewById(R.id.FAC_button_aResumen);
        buttonAgrergarProducto = vista.findViewById(R.id.FAC_button_agregarProducto);
        buttonLimpiarResumen = vista.findViewById(R.id.FAC_button_limpiarResumen);


        //asignando funciones a botones en pantalla
        buttonAgrergarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAgregarProducto(v);
            }
        });
        buttonAgregarAResumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAgregarAResumen(v);
            }
        });
        buttonRegistrarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegistrarCompra(v);
            }
        });
        buttonLimpiarResumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLimpiarResumen(v);
            }
        });


        //inicializando tabla
        String[] header = new String[]{"#", "Producto", "$ por unidad"};
        float[] weightCol = new float[]{1.5f, 6f, 2.5f};
        tablaResumen = new Tabla(getActivity().getApplicationContext(), tableLayoutAAC1, weightCol, header);
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {

        super.onResume();
        cargarAutoCompletesViews();

    }

    private void cargarAutoCompletesViews(){

        //OBTENIENDO ARRAYS DE LIASTA DE PRODUCTO
        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();

        for (Producto producto : listaProductos ){
            arrayCodigosProductos.add(String.valueOf(producto.getCodigo()));
            arrayNombresProductos.add(producto.getNombre());
        }

        //CARGANDO DATOS AUTOCOMPLETES CODIGOS Y NOMBRE
        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCodigosProductos);
        autoCCodigoProducto.setAdapter(adapterCodigos);

        //al seleccionar un elemento en el autoCompleteNombre, se carga automaticamente el codigo correspondiente en el autoCompleteCodigo
        autoCCodigoProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayCodigosProductos.indexOf(autoCCodigoProducto.getText().toString());
                autoCNombreProducto.setText(arrayNombresProductos.get(n));
            }
        });

        //configurando autoCompleteNombre
        ArrayAdapter<String> adapterNombres = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayNombresProductos);
        autoCNombreProducto.setAdapter(adapterNombres);

        //al seleccionar un elemento en el autoCompleteNombre, se complete automaticamente el nombre correspondiente en el autoCompleteCodigo
        autoCNombreProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayNombresProductos.indexOf(autoCNombreProducto.getText().toString());
                autoCCodigoProducto.setText(arrayCodigosProductos.get(n));
            }
        });

    }

    public void onClickAgregarAResumen(View view) {

        Fecha fecha = new Fecha();

        //chequeandos if-else de datos ingresados por el usuario
        if (autoCCodigoProducto.getText().toString().isEmpty()) {

            Toast.makeText(getActivity().getApplicationContext(), "El campo \"CODIGO DE PRODUCTO\" esta incompleto", Toast.LENGTH_LONG).show();

        } else if (autoCNombreProducto.getText().toString().isEmpty()) {

            Toast.makeText(getActivity().getApplicationContext(), "El campo \"NOMBRE DEL PRODUCTO\" esta incompleto", Toast.LENGTH_LONG).show();

        } else {

            int pos = listaProductos.indexOf(new Producto(Integer.parseInt(autoCCodigoProducto.getText().toString())));
            if (pos == -1) {

                Toast.makeText(getActivity().getApplicationContext(), "No hay ningún producto registrado con ese código", Toast.LENGTH_LONG).show();

            } else if (!listaProductos.get(pos).getNombre().equals(autoCNombreProducto.getText().toString())){

                Toast.makeText(getActivity().getApplicationContext(), "El código y el nombre no coinciden con el producto registrado en la base de datos", Toast.LENGTH_LONG).show();

            } else if (editCantidad.getText().toString().isEmpty() || (Integer.parseInt(editCantidad.getText().toString()) <= 0)) {

                Toast.makeText(getActivity().getApplicationContext(), "El campo \"CANTIDAD\" esta incompleto o es menor que 1", Toast.LENGTH_LONG).show();

            } else {

                if (editMonto.getText().toString().isEmpty()) {
                    editMonto.setText("-1");
                }

                //agregando compra a bdd
                Compra nuevaCompra = new Compra(Integer.parseInt(autoCCodigoProducto.getText().toString()), fecha.getStringDMAH(), Integer.parseInt(editCantidad.getText().toString()), Float.valueOf(editMonto.getText().toString()));
                listaCompras.add(nuevaCompra);

                //agregando compra a tabla resumen (en pantalla)
                if (editMonto.getText().toString().equals("-1")) {

                    String[] row = {editCantidad.getText().toString(), autoCNombreProducto.getText().toString(), "-"};
                    tablaResumen.addFila(row);

                } else {

                    String[] row = {editCantidad.getText().toString(), autoCNombreProducto.getText().toString(), editMonto.getText().toString()};
                    tablaResumen.addFila(row);

                }

                //limpiando views
                autoCCodigoProducto.setText("");
                autoCNombreProducto.setText("");
                editCantidad.setText("");
                editMonto.setText("");

                //habilitando boton registrar venta
                buttonRegistrarCompra.setEnabled(true);

            }
        }
    }

    //PROCEDIMIENTO BOTON REGISTRAR COMPRA
    public void onClickRegistrarCompra(View view){

        for (int i = 0; i < listaCompras.size(); i++) {

            operacionesBDD.insertCompra(listaCompras.get(i));
            Producto p = new Producto(listaCompras.get(i).getIdProducto());
            int pos = listaProductos.indexOf(p);
            listaProductos.get(pos).sumarACantidad(listaCompras.get(i).getCantidad());
            recyclerAdapter.notifyItemChanged(pos);

        }

        Toast.makeText(getActivity().getApplicationContext(),"La compra se registró correctamente",Toast.LENGTH_LONG).show();
        ActivityAgregarMovimiento.fa.finish();

    }

    //PROCEDIMIENTO BOTON DE AGREGAR PRODUCTO (+)
    public void onClickAgregarProducto(View view){

        Intent intentAgregarProducto= new Intent(getActivity(), ActivityAgregarProducto.class);
        startActivity(intentAgregarProducto);

    }

    //PROCEDIMIENTO BOTON LIMPIAR RESUMEN
    public void onClickLimpiarResumen(View view){

        listaCompras.clear();
        tablaResumen.removeAllViews();
        buttonRegistrarCompra.setEnabled(false);
        Toast.makeText(getActivity().getApplicationContext(),"Se limpió la tabla resumen",Toast.LENGTH_SHORT).show();
    }

}
