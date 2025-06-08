package com.example.tebeoteca.cliente.comic;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.R;

import java.util.List;

public class PaginaAdapter extends RecyclerView.Adapter<PaginaAdapter.PaginaViewHolder> {

    private final List<Bitmap> pdfPages;

    public PaginaAdapter(List<Bitmap> pdfPages) {
        this.pdfPages = pdfPages;
    }

    @NonNull
    @Override
    public PaginaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada página (item_pdf_page.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagina_comic, parent, false);
        return new PaginaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaginaViewHolder holder, int position) {
        // Asigna el Bitmap de la página actual a la ImageView
        holder.imageView.setImageBitmap(pdfPages.get(position));
    }

    @Override
    public int getItemCount() {
        return pdfPages.size();
    }

    // ViewHolder para mantener las referencias a las vistas de cada página
    static class PaginaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PaginaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pdf_page_image);
        }
    }
}