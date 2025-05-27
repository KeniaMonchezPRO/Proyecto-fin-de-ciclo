package com.example.tebeoteca.cliente;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

import java.util.ArrayList;
import java.util.List;

public class PerfilClienteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_perfil_cliente);

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        String nombreEmpresa = sharedPreferences.getString("nombreEmpresa", "Cliente");
        String nombreUsuario = "@"+(sharedPreferences.getString("nombreUsuario", "cliente"));

        TextView tv_nombreEmpresa = findViewById(R.id.tv_displayNombreEmpresa);
        TextView tv_nombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        tv_nombreEmpresa.setText(nombreEmpresa);
        tv_nombreUsuario.setText(nombreUsuario);

        List<Novedad> listaNovedades = new ArrayList<>();
        //listaNovedades.add(new Novedad("Nuevo cómic disponible", "¡Ya puedes leer el nuevo tomo de..."));
        //listaNovedades.add(new Novedad("Evento especial", "Participa en nuestro concurso de ilustración..."));
        agregarSeccionNovedades(listaNovedades);

        List<Producto> listaProductos = new ArrayList<>();
        //listaProductos.add(new Producto("Comic 1", "¡Ya puedes leer el nuevo tomo de..."));
        //listaProductos.add(new Producto("Comic 2", "Participa en nuestro concurso de ilustración..."));
        //listaProductos.add(new Producto("Comic 3", "Participa en nuestro concurso de ilustración..."));
        agregarSeccionProductos(listaProductos);

        List<Evento> listaEventos = new ArrayList<>();
        agregarSeccionEventos(listaEventos);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void agregarSeccionNovedades(List<Novedad> listaNovedades) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_novedades, null);

        FrameLayout flAnadirNovedad = seccionView.findViewById(R.id.fl_anadirNovedad);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_novedades);
        tvTitulo.setText("Novedades");

        if (listaNovedades == null || listaNovedades.isEmpty()) {
            flAnadirNovedad.setVisibility(View.VISIBLE);
        }

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new NovedadAdapter(listaNovedades));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });

        LinearLayout contenedor = findViewById(R.id.novedades_container);
        contenedor.addView(seccionView);
    }

    private void agregarSeccionProductos(List<Producto> listaProductos) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_productos, null);

        FrameLayout flAnadirNovedad = seccionView.findViewById(R.id.fl_anadirProducto);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_productos);
        tvTitulo.setText("Productos");

        if (listaProductos == null || listaProductos.isEmpty()) {
            flAnadirNovedad.setVisibility(View.VISIBLE);
        }

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new ProductoAdapter(listaProductos));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });

        LinearLayout contenedor = findViewById(R.id.productos_container);
        contenedor.addView(seccionView);
    }

    private void agregarSeccionEventos(List<Evento> listaEventos) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_eventos, null);

        FrameLayout flAnadirEvento = seccionView.findViewById(R.id.fl_anadirEvento);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_eventos);
        tvTitulo.setText("Eventos");

        if (listaEventos == null || listaEventos.isEmpty()) {
            flAnadirEvento.setVisibility(View.VISIBLE);
        }

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new EventoAdapter(listaEventos));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });

        LinearLayout contenedor = findViewById(R.id.eventos_container);
        contenedor.addView(seccionView);
    }
}
