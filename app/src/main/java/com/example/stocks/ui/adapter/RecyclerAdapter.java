package com.example.stocks.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocks.R;
import com.example.stocks.model.Data.Linea;
import com.example.stocks.model.Data.Producto;

import java.util.ArrayList;

import static com.example.stocks.ui.MainActivity.listaLineas;
import static com.example.stocks.ui.MainActivity.listaProductos;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderItems> implements View.OnClickListener {

    private ArrayList<Producto> listaProductosMostrados;
    private View.OnClickListener listener;

    public RecyclerAdapter(){
        this.listaProductosMostrados= listaProductos;
    }

    @NonNull
    @Override
    public ViewHolderItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItems holder, int position) {
        Producto p = listaProductosMostrados.get(position);
        holder.tCodigo.setText(String.valueOf(p.getCodigo()));
        holder.tNombre.setText(String.valueOf(p.getNombre()));
        holder.tCantidad.setText(String.valueOf(p.getCantidad()));
        String nombreLinea = p.getLinea();
        int pos = listaLineas.indexOf(new Linea(nombreLinea));
        int color = listaLineas.get(pos).getColor();
        holder.cardView.setCardBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return listaProductosMostrados.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView tCodigo,tNombre, tCantidad;
        private CardView cardView;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);

            tCodigo = itemView.findViewById(R.id.MA_text_CodigoProducto);
            tNombre = itemView.findViewById(R.id.MA_text_NombreProducto);
            tCantidad = itemView.findViewById(R.id.tIRCantidad);
            cardView = itemView.findViewById(R.id.MA_cardView);

        }
    }

    public void updateList(ArrayList<Producto> listaFiltrada){
        listaProductosMostrados= new ArrayList<>();
        listaProductosMostrados.addAll(listaFiltrada);
        notifyDataSetChanged();

    }
}
