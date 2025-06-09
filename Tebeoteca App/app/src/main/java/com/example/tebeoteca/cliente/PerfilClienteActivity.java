package com.example.tebeoteca.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

import java.io.Serializable;
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
    int idUsuario, seguidoresCounter = 0;
    Button btnEditarPerfil;
    ImageButton btnAtras;
    boolean esLector;
    boolean isFollowing = false;
    TextView tvNombreEmpresa, tvNombreUsuario, tvDescripcion, tvNif, tvFechaCreacionEmpresa, tvNumSeguidores, tvNumComics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_perfil_cliente);

        btnEditarPerfil = findViewById(R.id.btn_editarPerfil);
        btnAtras = findViewById(R.id.btn_atras);

        tvNombreEmpresa = findViewById(R.id.tv_displayNombreEmpresa);
        tvNombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        tvDescripcion = findViewById(R.id.tv_displayDescripcion);
        tvNif = findViewById(R.id.tv_nifEmpresa);
        tvFechaCreacionEmpresa = findViewById(R.id.tv_fechaCreacionEmpresa);
        tvNumSeguidores = findViewById(R.id.tv_numSeguidores);
        tvNumComics = findViewById(R.id.tv_numComics);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        esLector = getIntent().getBooleanExtra("esLector", false);
        Log.d("DEBUG PerfClienteAct", "esLectorExtra? " + esLector);

        if (esLector) {
            setupMenus(R.id.nav_buscar, "lector");
            btnAtras.setVisibility(View.VISIBLE);

            //para enviar a configuracion activity
            SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
            SharedPreferences.Editor editorAct = activityAndTabContext.edit();
            editorAct.putString("activity","PerfilClienteActivity");
            editorAct.putString("tab","buscar");
            editorAct.putBoolean("esLector",true);
            editorAct.apply();

            Cliente c = (Cliente) getIntent().getSerializableExtra("cliente");
            tvNombreEmpresa.setText(c.getNombreCliente());
            tvNombreUsuario.setText("@"+c.getNombreUsuario());
            tvDescripcion.setText(c.getDescripcion());
            tvNif.setText(c.getNif());
            tvFechaCreacionEmpresa.setText(c.getFechaCreacionEmpresa());

            btnEditarPerfil.setText("Seguir");
            btnEditarPerfil.setOnClickListener(v -> {
                if(isFollowing) {
                    Toast.makeText(PerfilClienteActivity.this, "Dejaste de seguir a " + tvNombreUsuario.getText(), Toast.LENGTH_SHORT).show();
                    btnEditarPerfil.setText("Seguir");
                    seguidoresCounter--;
                    tvNumSeguidores.setText(String.valueOf(seguidoresCounter));
                    isFollowing = false;
                } else {
                    Toast.makeText(PerfilClienteActivity.this, "Ahora sigues a " + tvNombreUsuario.getText(), Toast.LENGTH_SHORT).show();
                    btnEditarPerfil.setText("Dejar de seguir");
                    seguidoresCounter++;
                    tvNumSeguidores.setText(String.valueOf(seguidoresCounter));
                    isFollowing = true;
                }
            });
            obtenerComicsDelCliente(c.getId());
            obtenerOtrasSecciones();
        } else {
            Log.d("DEBUG PerfClienteAct", "entonces será cliente?");
            setupMenus(R.id.nav_inicio, "cliente");
            btnAtras.setVisibility(View.GONE);
            //para enviar el tipo de perfil a las demas activities
            SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = perfilPrefs.edit();
            editor.putString("perfil","cliente");
            editor.apply();

            //para enviar a configuracion activity
            SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
            SharedPreferences.Editor editorAct = activityAndTabContext.edit();
            editorAct.putString("activity","PerfilClienteActivity");
            editorAct.putString("tab","inicio");
            editorAct.putBoolean("esLector",false);
            editorAct.apply();

            SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
            String nombreEmpresa = sharedPreferences.getString("nombreEmpresa", "Cliente");
            String nombreUsuario = "@"+(sharedPreferences.getString("nombreUsuario", "cliente"));
            String descripcion = sharedPreferences.getString("descripcion", "Sin descripción");
            String nif = sharedPreferences.getString("nif", "Y1238900N");
            String fechaCreacionEmpresa = sharedPreferences.getString("fechaCreacionEmpresa", "1997-10-31");
            idUsuario = sharedPreferences.getInt("idUsuario",1);

            tvNombreEmpresa.setText(nombreEmpresa);
            tvNombreUsuario.setText(nombreUsuario);
            tvDescripcion.setText(descripcion);
            tvNif.setText(nif);
            tvFechaCreacionEmpresa.setText(fechaCreacionEmpresa);

            //Comics:
            obtenerComicsDelCliente(idUsuario);
            /*Comic nuevo = new Comic("Flashpoint 1", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
            Comic nuevo1 = new Comic("Flashpoint 2", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
            Comic nuevo2 = new Comic("Flashpoint 3", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
            ComicRepository.agregarComic(nuevo);
            ComicRepository.agregarComic(nuevo1);
            ComicRepository.agregarComic(nuevo2);
            agregarSeccionComics(ComicRepository.getComics());*/
            obtenerOtrasSecciones();
        }

        fotoPerfil = findViewById(R.id.iv_fotoPerfil);
        fotoPerfil.setImageResource(R.drawable.dc);
        banner = findViewById(R.id.iv_banner);
        banner.setImageResource(R.drawable.flash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*//Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);


        ImageButton btnAtras = findViewById(R.id.btn_atras);*/
        /*if (btnAtras != null && !esLector) {
            btnAtras.setVisibility(View.GONE);
            setupMenus(R.id.nav_inicio, "cliente");
        } else {
            setupMenus(R.id.nav_buscar, "lector");
        }
        //para enviar a configuracion activity
        SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
        SharedPreferences.Editor editorAct = activityAndTabContext.edit();
        editorAct.putString("activity","PerfilClienteActivity");
        editorAct.putString("tab","inicio");
        editorAct.apply();*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //obtenerComicsDelCliente(idUsuario);
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

    private void obtenerOtrasSecciones() {
        //Eventos
        List<Evento> listaEventos = new ArrayList<>();
        agregarSeccionEventos(listaEventos);

        //Rutas
        List<Ruta> listaRutas = new ArrayList<>();
        agregarSeccionRutas(listaRutas);

        //Wiki
        List<Wiki> listaWiki = new ArrayList<>();
        agregarSeccionWiki(listaWiki);
    }
    private void obtenerComicsDelCliente(int idCliente) {
        Call<List<Comic>> call = apiService.obtenerComicsPorCliente(idCliente);

        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comic> listaComics = response.body();
                    tvNumComics.setText(String.valueOf(listaComics.size()));
                    agregarSeccionComics(listaComics);
                } else {
                    Toast.makeText(PerfilClienteActivity.this, "Error al cargar los cómics", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Toast.makeText(PerfilClienteActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                List<Comic> listaVacia = new ArrayList<>();
                agregarSeccionComics(listaVacia);
            }
        });
    }

    private void agregarSeccionNovedades(List<Novedad> listaNovedades) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_novedades, null);

        FrameLayout flAnadirNovedad = seccionView.findViewById(R.id.fl_anadirNovedad);
        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);
        TextView tvTitulo = seccionView.findViewById(R.id.seccion_novedades);
        tvTitulo.setText("Novedades");

        if (listaNovedades == null || listaNovedades.isEmpty()) {
            flAnadirNovedad.setVisibility(View.VISIBLE);
            tvVerTodo.setVisibility(View.GONE);
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
        Log.d("DEBUG PerfClienteAct", "ENTRO EN AGREGAR SECCION COMICS");
        LinearLayout contenedor = findViewById(R.id.comics_container);

        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_comics, null);

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);

        FrameLayout flAnadirComic = seccionView.findViewById(R.id.fl_anadirComic);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_comics);
        tvTitulo.setText("Comics");

        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);

        if (listaComics == null || listaComics.isEmpty()) {
            Log.d("DEBUG PerfClienteAct", "set visibility");
            flAnadirComic.setVisibility(View.VISIBLE);
            tvVerTodo.setVisibility(View.GONE);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recycler.setAdapter(null);
        }
            Log.d("DEBUG PerfClienteAct", "else");
            tvVerTodo.setOnClickListener(view -> startComicsActivity());
            if (listaComics.size() > 6) {
                listaComics.subList(0, 6);
            }

            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            ComicAdapter adapter = new ComicAdapter(listaComics, comic -> {
                SharedPreferences prefs = getSharedPreferences("comicPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("activity", "PerfilClienteActivity");
                //editor.putString("perfil","cliente");
                editor.apply();
                Intent intent = new Intent(PerfilClienteActivity.this, ComicActivity.class);
                intent.putExtra("comic", comic);
                startActivity(intent);
            });
            recycler.setAdapter(adapter);

            recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                           @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    outRect.right = 30;
                }
            });


        contenedor.removeAllViews();
        contenedor.addView(seccionView);
    }

    private void agregarSeccionEventos(List<Evento> listaEventos) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_eventos, null);

        FrameLayout flAnadirEvento = seccionView.findViewById(R.id.fl_anadirEvento);
        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);
        TextView tvTitulo = seccionView.findViewById(R.id.seccion_eventos);
        tvTitulo.setText("Eventos");

        if (listaEventos == null || listaEventos.isEmpty()) {
            if(!esLector) {
                flAnadirEvento.setVisibility(View.VISIBLE);
            }
            tvVerTodo.setVisibility(View.GONE);
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
        contenedor.removeAllViews();
        contenedor.addView(seccionView);
    }

    private void agregarSeccionRutas(List<Ruta> listaRutas) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_rutas, null);

        FrameLayout flAnadirRuta = seccionView.findViewById(R.id.fl_anadirRuta);
        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);
        TextView tvTitulo = seccionView.findViewById(R.id.seccion_rutas);
        tvTitulo.setText("Rutas");

        if (listaRutas == null || listaRutas.isEmpty()) {
            if(!esLector) {
                flAnadirRuta.setVisibility(View.VISIBLE);
            }
            tvVerTodo.setVisibility(View.GONE);
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
        contenedor.removeAllViews();
        contenedor.addView(seccionView);
    }

    private void agregarSeccionWiki(List<Wiki> listaWiki) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_wiki, null);

        FrameLayout flAnadirWiki = seccionView.findViewById(R.id.fl_anadirWiki);
        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);
        TextView tvTitulo = seccionView.findViewById(R.id.seccion_wiki);
        tvTitulo.setText("Wiki");

        if (listaWiki == null || listaWiki.isEmpty()) {
            if(!esLector) {
                flAnadirWiki.setVisibility(View.VISIBLE);
            }
            tvVerTodo.setVisibility(View.GONE);
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
        contenedor.removeAllViews();
        contenedor.addView(seccionView);
    }
}
