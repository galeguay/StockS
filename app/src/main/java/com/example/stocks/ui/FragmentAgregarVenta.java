package com.example.stocks.ui;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.model.Data.Venta;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;
import static com.example.stocks.ui.MainActivity.recyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAgregarVenta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAgregarVenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAgregarVenta extends Fragment {
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
    private EditText editCantidad, editMonto, editCliente;
    private AutoCompleteTextView autoCNombreProducto, autoCCodigoProducto;
    private ArrayList<String> arrayCodigosProductos, arrayNombresProductos;
    private ArrayList<Venta> listaVentas;
    private Button buttonRegistrarVenta;
    private OperacionesBDD operacionesBDD;
    private TextView textCantidadEnStock;
    private int enStock;

    public FragmentAgregarVenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAgregarVenta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAgregarVenta newInstance() {
        FragmentAgregarVenta fragment = new FragmentAgregarVenta();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_act_agregar_venta);

        //inicializando admin de base de datos
        operacionesBDD= OperacionesBDD.instanceOf(getActivity().getApplicationContext());

        //INICIALIZANDO VARIABLES
        listaVentas = new ArrayList<>();
        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();
        enStock= 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_agregar_venta, container, false);

        //inicializando views
        textCantidadEnStock = vista.findViewById(R.id.FAV_text_cantidadEnStock);
        autoCCodigoProducto = vista.findViewById(R.id.FAV_autoC_codigoProducto);
        autoCNombreProducto = vista.findViewById(R.id.FAV_autoC_nombreProducto);
        editCantidad = vista.findViewById(R.id.FAV_edit_cantidad);
        editMonto = vista.findViewById(R.id.FAV_edit_monto);
        editCliente = vista.findViewById(R.id.FAV_edit_cliente);
        TableLayout tableLayoutResumen = vista.findViewById(R.id.FAV_tableL_resumen);
        buttonRegistrarVenta = vista.findViewById(R.id.FAV_button_registrar);
        buttonRegistrarVenta.setEnabled(false);

        //inicializando tabla
        String[] header = new String[]{" # ", " Producto ", " $ por unidad ", " Cliente "};
        float[] weightCol= new float[]{1.5f,4f,2f,2.5f};
        tablaResumen = new Tabla(getActivity().getApplicationContext(), tableLayoutResumen, weightCol, header);

        textCantidadEnStock.setText("0");

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

    @Override
    public void onResume() {
        super.onResume();
        //CARGANDO DATOS AUTOCOMPLETES CODIGOS Y NOMBRE
        //cargando arrays
        getArraysProductos();

        //configurando autoCompleteCodigo
        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayCodigosProductos);
        autoCCodigoProducto.setAdapter(adapterCodigos);

        //al seleccionar un elemento en el autoCompleteCodigo, se complete automaticamente el codigo correspondiente en el autoCompleteNombre
        autoCCodigoProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n = arrayCodigosProductos.indexOf(autoCCodigoProducto.getText().toString());
                autoCNombreProducto.setText(arrayNombresProductos.get(n));
                enStock= listaProductos.get(n).getCantidad();
                for (Venta v: listaVentas){
                    if (v.getIdProducto() == Integer.parseInt(autoCCodigoProducto.getText().toString())){
                        enStock= enStock - v.getCantidad();
                    }

                }
                textCantidadEnStock.setText(String.valueOf(enStock));
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
                enStock= listaProductos.get(n).getCantidad();
                //chequea si hay ventas cargadas del mismo producto en el resumen para descontar del numero previsible de stock
                for (Venta v: listaVentas){
                    if (v.getIdProducto() == Integer.parseInt(autoCCodigoProducto.getText().toString())){
                        enStock= enStock - v.getCantidad();
                    }

                }
                textCantidadEnStock.setText(String.valueOf(enStock));

            }
        });

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

    //PROCEDIMIENTO Q CARGA LOS ARREGLOS PASADOS CON LOS NOMBRES Y CODIGOS DE LOS PRODUCTOS EN LA BDD
    public void getArraysProductos() {

        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();

        for (Producto producto : listaProductos) {
            arrayCodigosProductos.add(String.valueOf(producto.getCodigo()));
            arrayNombresProductos.add(producto.getNombre());
        }

    }

    //PROCEDIMIENTO BOTON "AGREGAR A RESUMEN"
    public void agregarVentaAResumen(View view) {

        Fecha fecha = new Fecha();

        //cargando datos de venta en buffer
        if (autoCCodigoProducto.getText().toString().isEmpty()) {

            Toast.makeText(getActivity().getApplicationContext(), "El campo \"CODIGO DE PRODUCTO\" esta incompleto", Toast.LENGTH_LONG).show();

        } else if (autoCNombreProducto.getText().toString().isEmpty()) {

            Toast.makeText(getActivity().getApplicationContext(), "El campo \"NOMBRE DEL PRODUCTO\" esta incompleto", Toast.LENGTH_LONG).show();

        } else {

            int pos = listaProductos.indexOf(new Producto(Integer.parseInt(autoCCodigoProducto.getText().toString())));
            if (pos == -1) {

                Toast.makeText(getActivity().getApplicationContext(), "No hay ningún producto registrado con ese código", Toast.LENGTH_LONG).show();

            } else if (!listaProductos.get(pos).getNombre().equals(autoCNombreProducto.getText().toString())) {

                Toast.makeText(getActivity().getApplicationContext(), "El código y el nombre no coinciden con el producto registrado en la base de datos", Toast.LENGTH_LONG).show();

            } else if (editCantidad.getText().toString().isEmpty() || (Integer.parseInt(editCantidad.getText().toString()) <= 0)) {

                Toast.makeText(getActivity().getApplicationContext(), "El campo \"CANTIDAD\" esta incompleto o es menor que 1", Toast.LENGTH_LONG).show();

            } else if (editCliente.getText().toString().isEmpty()) {

                Toast.makeText(getActivity().getApplicationContext(), "El campo \"CLIENTE\" esta incompleto", Toast.LENGTH_LONG).show();

            } else if (enStock < Integer.parseInt((editCantidad.getText().toString()))) {

                Toast.makeText(getActivity().getApplicationContext(), "No hay suficiente stock para la venta", Toast.LENGTH_LONG).show();

            } else {

                //agreagando venta a ArrayList listaVentas
                Venta nuevaVenta = new Venta(Integer.parseInt(autoCCodigoProducto.getText().toString()), fecha.getStringDMAH(), Integer.parseInt(editCantidad.getText().toString()), Float.valueOf(editMonto.getText().toString()), Integer.parseInt(editCliente.getText().toString()));
                listaVentas.add(nuevaVenta);

                //agregando venta a tabla resumen (en pantalla)
                String[] row = {editCantidad.getText().toString(), autoCNombreProducto.getText().toString(), editMonto.getText().toString(), editCliente.getText().toString()};
                tablaResumen.addFila(row);

                //limpiando views
                autoCCodigoProducto.setText("");
                autoCNombreProducto.setText("");
                editCantidad.setText("");
                editMonto.setText("");
                editCliente.setText("");
                enStock = 0;
                textCantidadEnStock.setText("0");

                //habilitando boton registrar venta
                buttonRegistrarVenta.setEnabled(true);
            }
        }
    }

    //PROCEDIMIENTO BOTON "REGISTRAR VENTA"
    public void registrarVenta(View view) {

        for (int i = 0; i < listaVentas.size(); i++) {

            operacionesBDD.insertVenta(listaVentas.get(i));
            Producto p = new Producto(listaVentas.get(i).getIdProducto());
            int pos = listaProductos.indexOf(p);
            listaProductos.get(pos).restarACantidad((listaVentas.get(i).getCantidad()));
            recyclerAdapter.notifyItemChanged(pos);

        }

        Toast.makeText(getActivity().getApplicationContext(), "La venta se registró correctamente", Toast.LENGTH_LONG).show();
        ActivityAgregarMovimiento.fa.finish();

    }

    //PROCEDIMIENTO BOTON "LIMPIAR RESUMEN"
    public void limpiarResumen(View view) {
        listaVentas.clear();
        tablaResumen.removeAllViews();
        buttonRegistrarVenta.setEnabled(false);
        Toast.makeText(getActivity().getApplicationContext(), "Se limpió la tabla resumen", Toast.LENGTH_SHORT).show();
    }

}

