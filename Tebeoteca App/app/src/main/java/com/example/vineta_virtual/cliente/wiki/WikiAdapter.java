package com.example.vineta_virtual.cliente.wiki;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vineta_virtual.R;

import java.util.List;

public class WikiAdapter extends RecyclerView.Adapter<WikiAdapter.WikiViewHolder> {

    private List<Wiki> lista;

    public WikiAdapter(List<Wiki> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public WikiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wiki, parent, false);
        return new WikiViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull WikiViewHolder holder, int position) {
        Wiki wiki = lista.get(position);
        holder.tvTitulo.setText(wiki.getTitulo());
        holder.tvDescripcion.setText(wiki.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class WikiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion;

        public WikiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}

