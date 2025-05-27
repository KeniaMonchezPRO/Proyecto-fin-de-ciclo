package com.example.tebeoteca.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.R;

import java.util.List;

public class NovedadAdapter extends RecyclerView.Adapter<NovedadAdapter.NovedadViewHolder> {

    private List<Novedad> lista;

    public NovedadAdapter(List<Novedad> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public NovedadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_novedad, parent, false);
        return new NovedadViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull NovedadViewHolder holder, int position) {
        Novedad novedad = lista.get(position);
        holder.tvTitulo.setText(novedad.getTitulo());
        holder.tvDescripcion.setText(novedad.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class NovedadViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion;

        public NovedadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}

