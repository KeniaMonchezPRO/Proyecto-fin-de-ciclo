package com.example.vineta_virtual.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vineta_virtual.R;


import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {
    private List<Cliente> listaClientes;
    private ClienteAdapter.OnClienteClickListener listener;
    public interface OnClienteClickListener {
        void onClienteClick(Cliente cliente);
    }
    public ClienteAdapter(List<Cliente> listaClientes, ClienteAdapter.OnClienteClickListener listener) {
        this.listaClientes = listaClientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteAdapter.ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lector, parent, false);
        return new ClienteAdapter.ClienteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.ClienteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);
        holder.bind(cliente);
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    class ClienteViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFotoPerfil;
        TextView tvNombre, tvUsuario;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFotoPerfil = itemView.findViewById(R.id.iv_fotoPerfil);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvUsuario = itemView.findViewById(R.id.tv_nombreUsuario);
        }

        public void bind(Cliente cliente) {
            tvNombre.setText(cliente.getNombreCliente());
            tvUsuario.setText("@" + cliente.getNombreUsuario());
            ivFotoPerfil.setImageResource(R.drawable.dc);

            itemView.setOnClickListener(v -> listener.onClienteClick(cliente));
        }
    }
}
