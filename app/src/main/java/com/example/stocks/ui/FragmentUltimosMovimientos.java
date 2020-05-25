package com.example.stocks.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.stocks.R;
import com.example.stocks.model.Data.Compra;
import com.example.stocks.model.Data.Fecha;
import com.example.stocks.model.Data.Movimiento;
import com.example.stocks.model.Data.Prestamo;
import com.example.stocks.model.Data.Tabla;
import com.example.stocks.model.Data.Venta;
import com.example.stocks.sql.OperacionesBDD;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentUltimosMovimientos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentUltimosMovimientos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUltimosMovimientos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<Movimiento> listaUltimosMovimientos;

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;
    int codigoProducto;
    //View vista;

    public FragmentUltimosMovimientos() {
        // Required empty public constructor
    }

    public static FragmentUltimosMovimientos newInstance(Bundle argumentos) {
        FragmentUltimosMovimientos fragment = new FragmentUltimosMovimientos();
        if (argumentos!= null) {
            fragment.setArguments(argumentos);

        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Bundle bundle = this.getArguments();
            codigoProducto = bundle.getInt("codigoProducto");
        }catch (Exception e){
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_ultimos_movimientos, container, false);

        //inicializando views
        TableLayout tableLayout = (TableLayout)vista.findViewById(R.id.FUM_tableLayout_movimientos);

        //cargando datos en tabla
        cargarTabla(tableLayout);

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

    private void cargarTabla(final TableLayout tableLayout) {

        String[] header = new String[]{"#", "Tipo", "Fecha"};
        float[] weightCol= new float[]{1.5f,4.25f,4.25f};
        Tabla tabla = new Tabla(getActivity(), tableLayout, weightCol, header);
        listaUltimosMovimientos = OperacionesBDD.instanceOf(getActivity()).listaUltimosMovimientos(codigoProducto);
        int i = 0;
        if (!listaUltimosMovimientos.isEmpty()) {
            //int i = 0;
            while (i < listaUltimosMovimientos.size()) {
                String tipoMovimiento = "Error";
                if (listaUltimosMovimientos.get(i) instanceof Compra){
                    tipoMovimiento = "Compra";
                } else if (listaUltimosMovimientos.get(i) instanceof Venta) {
                    tipoMovimiento = "Venta";
                } else if (listaUltimosMovimientos.get(i) instanceof Prestamo){
                    //TODO diferencias prastamo pedido de prestamo dado
                    tipoMovimiento = "Prestamo";
                }
                //String[] detallesMovimient o= {listaUltimosMovimientos.get(i).getIdMovimiento(),listaUltimosMovimientos.get(i).getFecha(),listaUltimosMovimientos.get(i).getCantidad(),listaUltimosMovimientos.get(i)};
                tabla.addFila(new String[]{String.valueOf(listaUltimosMovimientos.get(i).getCantidad()), tipoMovimiento, listaUltimosMovimientos.get(i).getFecha().getStringDMA()});
                final Bundle argumentos = new Bundle();
                //argumentos.putString("idMovimiento", String.valueOf(listaUltimosMovimientos.get(i).getIdMovimiento()));
                argumentos.putString("tipoMovimiento", tipoMovimiento);
                argumentos.putSerializable("movimiento", listaUltimosMovimientos.get(i));

                tabla.getRow(i+1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.AIP_fl_contenedorFragments, FragmentDetalleMovimiento.newInstance(argumentos)).addToBackStack(null).commit();
                    }
                });
                i++;
            }
            tableLayout.setColumnCollapsed(3, true);
        }
    }
}
