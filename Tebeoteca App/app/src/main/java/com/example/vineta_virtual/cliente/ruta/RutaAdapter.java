package com.example.vineta_virtual.cliente.ruta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vineta_virtual.R;

import java.util.List;

public class RutaAdapter extends RecyclerView.Adapter<RutaAdapter.RutaViewHolder> {
    private List<Ruta> lista;

    public RutaAdapter(List<Ruta> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public RutaAdapter.RutaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rutas, parent, false);
        return new RutaAdapter.RutaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RutaAdapter.RutaViewHolder holder, int position) {
        Ruta ruta = lista.get(position);
        holder.tvTitulo.setText(ruta.getTitulo());
        holder.tvDescripcion.setText(ruta.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class RutaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion;

        public RutaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}
