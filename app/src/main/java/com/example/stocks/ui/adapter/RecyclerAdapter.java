package com.example.stocks.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocks.R;
import com.example.stocks.model.Data.Producto;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderItems> {

    private ArrayList<Producto> listaProductos;

    public RecyclerAdapter(ArrayList<Producto> listaProductos){
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ViewHolderItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_item, null, false);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItems holder, int position) {
        holder.tCodigo.setText(String.valueOf(listaProductos.get(position).getCodigo()));
        holder.tNombre.setText(String.valueOf(listaProductos.get(position).getNombre()));
        holder.tCantidad.setText(String.valueOf(listaProductos.get(position).getCantidad()));



    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        private TextView tCodigo,tNombre, tCantidad;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);

            tCodigo = (TextView) itemView.findViewById(R.id.tIRCodigoProducto);
            tNombre = (TextView) itemView.findViewById(R.id.tIRNombreProducto);
            tCantidad = (TextView) itemView.findViewById(R.id.tIRCantidad);

        }
    }

    public void updateList(ArrayList<Producto> listaFiltrada){
        this.listaProductos = new ArrayList<>();
        this.listaProductos.addAll(listaFiltrada);
        notifyDataSetChanged();

    }
}
