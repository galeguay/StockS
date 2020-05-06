package com.example.stocks.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocks.R;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.sql.OperacionesBDD;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDetalleMovimiento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDetalleMovimiento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetalleMovimiento extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int idMovimiento;
    private String tipoMovimiento;

    public FragmentDetalleMovimiento() {
        // Required empty public constructor
    }

    public static FragmentDetalleMovimiento newInstance(Bundle argumentos) {
        FragmentDetalleMovimiento fragment = new FragmentDetalleMovimiento();
        if (argumentos!= null) {
            fragment.setArguments(argumentos);

        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Bundle bundle = this.getArguments();
            idMovimiento = bundle.getInt("idMovimiento");
            tipoMovimiento = bundle.getString("tipoMovimiento");
            Toast.makeText(getContext(), tipoMovimiento, Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_detalle_movimiento, container, false);

        // asociando views
        TextView tvIdMovimiento = vista.findViewById(R.id.FDM_tv_idMovimiento);
        TableLayout tableLayout = vista.findViewById(R.id.FDM_tl_contenido);

        // inicializando views básicos
        tvIdMovimiento.setText("ID MOVIMIENTO: "+ idMovimiento);

        // creando objetos
        float[] weight = {1f,1f};
        Tabla tabla = new Tabla(getActivity(), tableLayout, weight);

        cargarDatos(tabla);

        return vista;
    }

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
        void onFragmentInteraction(Uri uri);
    }

    private void cargarDatos(Tabla tabla) {
        Cursor cursor = OperacionesBDD.instanceOf(getActivity()).cursorDetallesMovimiento(idMovimiento, tipoMovimiento);
        if (cursor.moveToFirst()){

            Fecha fecha = new Fecha(cursor.getString(0));
            String[] fila = {"Fecha", fecha.getStringDMA()};
            tabla.addFila(fila);

            fila = new String[]{"Cantidad", cursor.getString(2)};
            tabla.addFila(fila);

            switch (tipoMovimiento) {
                case "Compra":

                    fila = new String[]{"Precio unitario", "$"+ cursor.getString(3)};
                    tabla.addFila(fila);

                    float total = Float.parseFloat(cursor.getString(3)) * Float.parseFloat(cursor.getString(2));
                    fila = new String[]{"Monto TOTAL", "$"+ total};
                    tabla.addFila(fila);
                    break;

                case "Venta":

                    fila = new String[]{"Precio unitario", "$"+ cursor.getString(3)};
                    tabla.addFila(fila);

                    fila = new String[]{"Cliente", cursor.getString(4)};
                    tabla.addFila(fila);
                    break;

                case "Prestamo pedido" :

                    fila = new String[]{"Tipo de prestamo", cursor.getString(3)};
                    tabla.addFila(fila);

                    fila = new String[]{"Socia", cursor.getString(4)};
                    tabla.addFila(fila);
                    break;

                case "Prestamo dado":

                    fila = new String[]{"Tipo de prestamo", cursor.getString(3)};
                    tabla.addFila(fila);

                    fila = new String[]{"Socia", cursor.getString(4)};
                    tabla.addFila(fila);
                    break;
                default:
            }
        } else{
            Toast.makeText(getActivity(), "Error de lectura en la base de datos",Toast.LENGTH_LONG).show();}
    }


}
