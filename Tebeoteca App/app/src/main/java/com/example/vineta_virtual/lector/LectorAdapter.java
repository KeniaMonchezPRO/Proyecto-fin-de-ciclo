package com.example.vineta_virtual.lector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vineta_virtual.R;

import java.util.List;

public class LectorAdapter extends RecyclerView.Adapter<LectorAdapter.LectorViewHolder> {
    private List<Lector> listaLectores;
    private LectorAdapter.OnLectorClickListener listener;
    public interface OnLectorClickListener {
        void onLectorClick(Lector lector);
    }
    public LectorAdapter(List<Lector> listaLectores, LectorAdapter.OnLectorClickListener listener) {
        this.listaLectores = listaLectores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LectorAdapter.LectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lector, parent, false);
        return new LectorAdapter.LectorViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull LectorViewHolder holder, int position) {
        Lector lector = listaLectores.get(position);
        holder.bind(lector);
    }

    @Override
    public int getItemCount() {
        return listaLectores.size();
    }

    class LectorViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFotoPerfil;
        TextView tvNombre, tvUsuario;

        public LectorViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFotoPerfil = itemView.findViewById(R.id.iv_fotoPerfil);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvUsuario = itemView.findViewById(R.id.tv_nombreUsuario);
        }

        public void bind(Lector lector) {
            tvNombre.setText(lector.getNombreLector() + " " + lector.getApellidosLector());
            tvUsuario.setText("@" + lector.getNombreUsuario());
            ivFotoPerfil.setImageResource(R.drawable.dc);

            itemView.setOnClickListener(v -> listener.onLectorClick(lector));
        }
    }
}
