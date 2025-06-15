package com.example.vineta_virtual.general;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vineta_virtual.R;
import com.example.vineta_virtual.cliente.comic.Comic;

import java.util.List;

public class BusquedaAdapter extends RecyclerView.Adapter<BusquedaAdapter.BusquedaViewHolder> {

    private List<Comic> listaComic;
    /*private List<Evento> listaEvento;
    private List<Wiki> listaEntradaWiki;
    private List<Ruta> listaRuta;*/

    public BusquedaAdapter(List<Comic> listaComic) {
        this.listaComic = listaComic;
    }

    @NonNull
    @Override
    public BusquedaAdapter.BusquedaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comic, parent, false);
        return new BusquedaAdapter.BusquedaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull BusquedaAdapter.BusquedaViewHolder holder, int position) {
        Comic comic = listaComic.get(position);
        holder.tvTitulo.setText(comic.getTitulo());
        holder.tvAutores.setText(comic.getAutores());
        holder.tvSelloEditorial.setText(comic.getSelloEditorial());
        String nombrePortada = comic.getPortada();
        if (nombrePortada == null) {
            holder.portada.setImageResource(R.drawable.sin_foto);
        } /*else {
            int idImagen = context.getResources().getIdentifier(
                    nombrePortada, "drawable", context.getPackageName()
            );
            holder.portada.setImageResource(idImagen);
        }*/
    }

    @Override
    public int getItemCount() {
        return listaComic.size();
    }

    static class BusquedaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvAutores, tvSelloEditorial;
        ImageView portada;

        public BusquedaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutores = itemView.findViewById(R.id.tvAutores);
            portada = itemView.findViewById(R.id.iv_portada);
            tvSelloEditorial = itemView.findViewById(R.id.tvSelloEditorial);
        }
    }
}
