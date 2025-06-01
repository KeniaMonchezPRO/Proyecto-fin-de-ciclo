package com.example.tebeoteca.cliente.comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.R;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {
    private List<Comic> lista;
    //private final Context context;
    private OnItemClickListener listener;
    public ComicAdapter(List<Comic> lista, OnItemClickListener listener) {
        this.lista = lista;
        //this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Comic comic);
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
        holder.bind(comic, listener);
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
        return lista.size();
    }

    static class ComicViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvAutores, tvSelloEditorial;
        ImageView portada;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutores = itemView.findViewById(R.id.tvAutores);
            portada = itemView.findViewById(R.id.iv_portada);
            tvSelloEditorial = itemView.findViewById(R.id.tvSelloEditorial);
        }

        public void bind(Comic comic, OnItemClickListener listener) {
            tvTitulo.setText(comic.getTitulo());

            int idImagen = itemView.getContext().getResources().getIdentifier(
                    comic.getPortada(), "drawable", itemView.getContext().getPackageName()
            );
            if (idImagen != 0) {
                portada.setImageResource(idImagen);
            } else {
                portada.setImageResource(R.drawable.sin_foto);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(comic));
        }
    }

}
