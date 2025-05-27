package com.example.tebeoteca.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.R;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private List<Producto> lista;

    public ProductoAdapter(List<Producto> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ProductoAdapter.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoAdapter.ProductoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ProductoViewHolder holder, int position) {
        Producto producto = lista.get(position);
        holder.tvTitulo.setText(producto.getTitulo());
        holder.tvDescripcion.setText(producto.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }

}
