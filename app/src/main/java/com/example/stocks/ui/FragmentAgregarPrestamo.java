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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.stocks.R;
import com.example.stocks.model.Data.Producto;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaProductos;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAgregarPrestamo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAgregarPrestamo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAgregarPrestamo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View vista;
    private AutoCompleteTextView autoCNombreProducto, autoCCodigoProducto;
    private ArrayList<String> arrayCodigosProductos, arrayNombresProductos;
    private RadioGroup radioGroup;
    private Spinner spinnerSocia;
    private String socia;

    private OnFragmentInteractionListener mListener;

    public FragmentAgregarPrestamo() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentAgregarPrestamo newInstance() {
        FragmentAgregarPrestamo fragment = new FragmentAgregarPrestamo();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_agregar_prestamo, container, false);
        radioGroup = vista.findViewById(R.id.FAP_radioG_tipoPrestamo);
        autoCNombreProducto = vista.findViewById(R.id.FAP_autoC_nombreProducto);
        autoCCodigoProducto = vista.findViewById(R.id.FAP_autoC_codigoProducto);
        getArraysProductos();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarAutoCompleteViewsData();


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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getArraysProductos(){

        arrayCodigosProductos = new ArrayList<>();
        arrayNombresProductos = new ArrayList<>();

        for (Producto producto : listaProductos ){
            arrayCodigosProductos.add(String.valueOf(producto.getCodigo()));
            arrayNombresProductos.add(producto.getNombre());
        }
    }

    private void cargarAutoCompleteViewsData(){

        //CARGANDO DATOS AUTOCOMPLETES CODIGOS Y NOMBRE
        //configurando autoCompleteCodigo
        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayCodigosProductos);
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

    private void cargarSpinnerData(){

        ArrayList<String> arregloSocias = new ArrayList<>();
        arregloSocias.add("María Luisa");
        arregloSocias.add("Gaby");

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, arregloSocias);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerSocia.setAdapter(adapter);
        spinnerSocia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socia = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void registrarPrestamo() {

        String tipoPrestamo = "nulo";
        RadioButton rB_prestamoPedido = vista.findViewById(R.id.FAP_radioB_pPedido);
        RadioButton rB_prestamoDado = vista.findViewById(R.id.FAP_radioB_pDado);
        EditText eT_socia = vista.findViewById(R.id.FAP_eT_socia);
        EditText eT_cantidad = vista.findViewById(R.id.FAP_et_cantidad);
        if (radioGroup.getCheckedRadioButtonId() == (rB_prestamoPedido.getId())) {
            tipoPrestamo = "prestamo pedido";
        } else if (radioGroup.getCheckedRadioButtonId() == (rB_prestamoDado.getId())) {
            tipoPrestamo = "prestamo dado";
        }
        OperacionesBDD operacionesBDD;
        operacionesBDD = OperacionesBDD.instanceOf(getActivity().getApplicationContext());
        operacionesBDD.insertPrestamo(autoCCodigoProducto.getText().toString(), tipoPrestamo, eT_socia.getText().toString(), eT_cantidad.getText().toString());
        ActivityAgregarMovimiento.fa.finish();

    }

}
