package com.example.tebeoteca.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.cliente.comic.Comic;
import com.example.tebeoteca.cliente.comic.ComicActivity;
import com.example.tebeoteca.cliente.evento.Evento;
import com.example.tebeoteca.cliente.evento.EventoAdapter;
import com.example.tebeoteca.cliente.novedad.Novedad;
import com.example.tebeoteca.cliente.novedad.NovedadAdapter;
import com.example.tebeoteca.cliente.comic.ComicAdapter;
import com.example.tebeoteca.cliente.comic.ComicRepository;
import com.example.tebeoteca.cliente.comic.ComicsActivity;
import com.example.tebeoteca.cliente.ruta.Ruta;
import com.example.tebeoteca.cliente.ruta.RutaAdapter;
import com.example.tebeoteca.cliente.wiki.Wiki;
import com.example.tebeoteca.cliente.wiki.WikiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilClienteActivity extends BaseActivity {
    private ImageView fotoPerfil, banner;
    private ApiService apiService;
    TextView verAllComics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_perfil_cliente);
        setupMenus(R.id.nav_inicio);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        ImageButton btnAtras = findViewById(R.id.btn_atras);
        if (btnAtras != null) {
            btnAtras.setVisibility(View.GONE);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        String nombreEmpresa = sharedPreferences.getString("nombreEmpresa", "Cliente");
        String nombreUsuario = "@"+(sharedPreferences.getString("nombreUsuario", "cliente"));
        int idUsuario = sharedPreferences.getInt("idUsuario",1);

        TextView tv_nombreEmpresa = findViewById(R.id.tv_displayNombreEmpresa);
        TextView tv_nombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        tv_nombreEmpresa.setText(nombreEmpresa);
        tv_nombreUsuario.setText(nombreUsuario);

        //Eventos
        List<Evento> listaEventos = new ArrayList<>();
        agregarSeccionEventos(listaEventos);

        //Rutas
        List<Ruta> listaRutas = new ArrayList<>();
        agregarSeccionRutas(listaRutas);

        //Comics:
        obtenerComicsDelCliente(idUsuario);
        /*Comic nuevo = new Comic("Flashpoint 1", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
        Comic nuevo1 = new Comic("Flashpoint 2", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
        Comic nuevo2 = new Comic("Flashpoint 3", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
        ComicRepository.agregarComic(nuevo);
        ComicRepository.agregarComic(nuevo1);
        ComicRepository.agregarComic(nuevo2);
        agregarSeccionComics(ComicRepository.getComics());*/

        //Wiki
        List<Wiki> listaWiki = new ArrayList<>();
        agregarSeccionWiki(listaWiki);

        fotoPerfil = findViewById(R.id.iv_fotoPerfil);
        fotoPerfil.setImageResource(R.drawable.dc);
        banner = findViewById(R.id.iv_banner);
        banner.setImageResource(R.drawable.flash);

        /*verAllComics = findViewById(R.id.seccion_comics_ver_mas);
        verAllComics.setOnClickListener(view -> startComicsActivity());*/
    }

    @Override
    protected void onResume() {
        setupMenus(R.id.nav_inicio);
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

    private void startComicsActivity() {
        Intent intent = new Intent(this, ComicsActivity.class);
        startActivity(intent);
    }

    private void obtenerComicsDelCliente(int idCliente) {
        Call<List<Comic>> call = apiService.obtenerComicsPorCliente(idCliente);

        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comic> listaComics = response.body();
                    agregarSeccionComics(listaComics);
                } else {
                    Toast.makeText(PerfilClienteActivity.this, "Error al cargar los cómics", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Toast.makeText(PerfilClienteActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
                outRect.right = 50;
            }
        });

        LinearLayout contenedor = findViewById(R.id.novedades_container);
        contenedor.removeAllViews(); //para limpiar antes de agregar, por si se llama agegarSeccionComics varias veces
        contenedor.addView(seccionView);
    }

    private void agregarSeccionComics(List<Comic> listaComics) {
        LinearLayout contenedor = findViewById(R.id.comics_container);

        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_comics, null);

        FrameLayout flAnadirComic = seccionView.findViewById(R.id.fl_anadirComic);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_comics);
        tvTitulo.setText("Comics");

        if (listaComics == null || listaComics.isEmpty()) {
            flAnadirComic.setVisibility(View.VISIBLE);
        }

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new ComicAdapter(listaComics));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });
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

    private void agregarSeccionRutas(List<Ruta> listaRutas) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_rutas, null);

        FrameLayout flAnadirRuta = seccionView.findViewById(R.id.fl_anadirRuta);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_rutas);
        tvTitulo.setText("Rutas");

        if (listaRutas == null || listaRutas.isEmpty()) {
            flAnadirRuta.setVisibility(View.VISIBLE);
        }

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new RutaAdapter(listaRutas));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });

        LinearLayout contenedor = findViewById(R.id.rutas_container);
        contenedor.addView(seccionView);
    }

    private void agregarSeccionWiki(List<Wiki> listaWiki) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_wiki, null);

        FrameLayout flAnadirWiki = seccionView.findViewById(R.id.fl_anadirWiki);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_wiki);
        tvTitulo.setText("Wiki");

        if (listaWiki == null || listaWiki.isEmpty()) {
            flAnadirWiki.setVisibility(View.VISIBLE);
        }

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new WikiAdapter(listaWiki));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });

        LinearLayout contenedor = findViewById(R.id.wiki_container);
        contenedor.addView(seccionView);
    }
}
