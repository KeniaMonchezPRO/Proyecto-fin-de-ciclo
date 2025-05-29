package com.example.tebeoteca.cliente.comic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.R;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {
    private List<Comic> lista;

    public ComicAdapter(List<Comic> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ComicAdapter.ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comic, parent, false);
        return new ComicAdapter.ComicViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicAdapter.ComicViewHolder holder, int position) {
        Comic comic = lista.get(position);
        holder.tvTitulo.setText(comic.getTitulo());
        holder.tvAutores.setText(comic.getAutores());
        holder.idImagen.setImageResource(comic.getIdImagen());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ComicViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvAutores;
        ImageView idImagen;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutores = itemView.findViewById(R.id.tvAutores);
            idImagen = itemView.findViewById(R.id.iv_portada);
        }
    }

}
