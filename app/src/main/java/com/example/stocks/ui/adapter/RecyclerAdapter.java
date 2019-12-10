package com.example.stocks.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocks.R;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.model.Data.Producto;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaLineas;
import static com.example.stocks.ui.MainActivity.listaProductos;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderItems> {

    //private ArrayList<Producto> listaProductos;
    private Context context;

    public RecyclerAdapter(Context context){//ArrayList<Producto> listaProductos){
        //this.listaProductos = listaProductos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_item, null, false);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItems holder, int position) {
        Producto p = listaProductos.get(position);
        holder.tCodigo.setText(String.valueOf(p.getCodigo()));
        holder.tNombre.setText(String.valueOf(p.getNombre()));
        holder.tCantidad.setText(String.valueOf(p.getCantidad()));
        String nombreLinea = p.getLinea();
        int pos = listaLineas.indexOf(new Linea(nombreLinea));
        int color = listaLineas.get(pos).getColor();
        holder.cardView.setCardBackgroundColor(color);
        if (!listaProductos.isEmpty()) {


        }
        /*holder.cardView.setCardBackgroundColor(listaProdcusto;
        if (!listaProductos.isEmpty()){
            String nLinea = listaProductos.get(position).getLinea();
            Linea linea = new Linea(nLinea);
            int pos = 0;
            int i =0;
            for (Linea l: listaLineas){

                if (l.getNombre() == nLinea){
                    pos = i;
                }
                i++;

            }
            //int pos = listaLineas.indexOf(linea);
            Toast.makeText(context, pos, Toast.LENGTH_LONG).show();

        }*/

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView tCodigo,tNombre, tCantidad;
        private CardView cardView;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);

            tCodigo = (TextView) itemView.findViewById(R.id.MA_text_CodigoProducto);
            tNombre = (TextView) itemView.findViewById(R.id.MA_text_NombreProducto);
            tCantidad = (TextView) itemView.findViewById(R.id.tIRCantidad);
            cardView = (CardView) itemView.findViewById(R.id.MA_cardView);

        }
    }

    public void updateList(ArrayList<Producto> listaFiltrada){
        listaProductos = new ArrayList<>();
        listaProductos.addAll(listaFiltrada);
        notifyDataSetChanged();

    }
}
