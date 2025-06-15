package com.example.vineta_virtual.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.example.vineta_virtual.BaseActivity;
import com.example.vineta_virtual.R;
import com.example.vineta_virtual.api.ApiService;
import com.example.vineta_virtual.cliente.comic.Comic;
import com.example.vineta_virtual.cliente.comic.ComicActivity;
import com.example.vineta_virtual.cliente.comic.NuevoComicActivity;
import com.example.vineta_virtual.cliente.evento.Evento;
import com.example.vineta_virtual.cliente.evento.EventoAdapter;
import com.example.vineta_virtual.cliente.evento.NuevoEventoActivity;
import com.example.vineta_virtual.cliente.novedad.Novedad;
import com.example.vineta_virtual.cliente.novedad.NovedadAdapter;
import com.example.vineta_virtual.cliente.comic.ComicAdapter;
import com.example.vineta_virtual.cliente.comic.ComicsActivity;
import com.example.vineta_virtual.cliente.ruta.NuevaRutaActivity;
import com.example.vineta_virtual.cliente.ruta.Ruta;
import com.example.vineta_virtual.cliente.ruta.RutaAdapter;
import com.example.vineta_virtual.cliente.wiki.NuevaEntradaWikiActivity;
import com.example.vineta_virtual.cliente.wiki.Wiki;
import com.example.vineta_virtual.cliente.wiki.WikiAdapter;

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
    ImageButton btnAtras, btnConfig, btnLogout, btnAnadir;
    boolean esLector, isFollowing = false;
    TextView tvNombreEmpresa, tvNombreUsuario, tvDescripcion, tvNif, tvFechaCreacionEmpresa, tvNumSeguidores, tvNumComics, tvEmail;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_perfil_cliente);
        Log.d("PerfClientAct", "ONCREATE()");

        btnEditarPerfil = findViewById(R.id.btn_editarPerfil);
        btnAtras = findViewById(R.id.btn_atras);
        btnConfig = findViewById(R.id.btn_configuracion);
        btnLogout = findViewById(R.id.btn_logout);
        btnAnadir = findViewById(R.id.btn_anadir);

        tvNombreEmpresa = findViewById(R.id.tv_displayNombreEmpresa);
        tvNombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        tvDescripcion = findViewById(R.id.tv_displayDescripcion);
        tvNif = findViewById(R.id.tv_nifEmpresa);
        tvFechaCreacionEmpresa = findViewById(R.id.tv_fechaCreacionEmpresa);
        tvNumSeguidores = findViewById(R.id.tv_numSeguidores);
        tvNumComics = findViewById(R.id.tv_numComics);
        tvEmail = findViewById(R.id.tv_email);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        esLector = getIntent().getBooleanExtra("esLector", false);
        Log.d("PerfClienteAct", "esLectorExtra? " + esLector);

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE); //cuando entra desde otras activities
        idUsuario = sharedPreferences.getInt("idUsuario",1);
        Log.d("PerfClienteAct", "ID USUARIO: " + idUsuario);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente"); //cuando entra desde del login
        Log.d("PerfClienteAct", "datos cliente extra: " + cliente);

        if(cliente == null) {
            cliente = new Cliente();
            cliente.setNombreCliente(sharedPreferences.getString("nombreEmpresa", "Añadir nombre o razón social"));
            cliente.setNombreUsuario("@"+(sharedPreferences.getString("nombreUsuario", "Añadir nombre de usuario")));
            cliente.setDescripcion(sharedPreferences.getString("descripcion", "Añadir descripción"));
            cliente.setNif(sharedPreferences.getString("nif", "Añadir NIF"));
            cliente.setFechaCreacionEmpresa(sharedPreferences.getString("fechaCreacionEmpresa", "Añadir fecha de fundación"));
            cliente.setId(sharedPreferences.getInt("idUsuario",1));
            cliente.setEmail(sharedPreferences.getString("email","Añadir email"));
            cliente.setContrasena(sharedPreferences.getString("contrasena","Añadir contraseña"));
            Log.d("PerfClienteAct", "datos cliente setteados: " + cliente);
        }

        if (esLector) {
            setupMenus(R.id.nav_buscar, "lector");
            Log.d("PerfClienteAct", "entró como lector");
            //btnAtras.setVisibility(View.VISIBLE);

            //para enviar a configuracion activity
            SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
            SharedPreferences.Editor editorAct = activityAndTabContext.edit();
            editorAct.putString("activity","PerfilClienteActivity");
            editorAct.putString("tab","buscar");
            editorAct.putBoolean("esLector",true);
            editorAct.apply();

            SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = perfilPrefs.edit();
            editor.putString("perfil","lector");
            editor.putInt("idUsuario", idUsuario);
            editor.apply();


            tvNombreEmpresa.setText(cliente.getNombreCliente());
            tvNombreUsuario.setText("@"+cliente.getNombreUsuario());
            tvDescripcion.setText(cliente.getDescripcion());
            tvNif.setText(cliente.getNif());
            tvEmail.setText("Contacto: " + cliente.getEmail());
            tvFechaCreacionEmpresa.setText("Fundación: " + cliente.getFechaCreacionEmpresa().substring(0,4));

            btnEditarPerfil.setText("Seguir");
            btnEditarPerfil.setOnClickListener(v -> {seguirCliente();});
            obtenerComicsDelCliente(cliente.getId());
            obtenerOtrasSecciones();
        } else {
            Log.d("PerfClienteAct", "entró como cliente");
            if(cliente.getId() != idUsuario) {
                Log.d("PerfClienteAct", "cliente entró a ver el perfil de otro cliente");
                setupMenus(R.id.nav_buscar, "cliente");
                btnAtras.setVisibility(View.VISIBLE);
                btnConfig.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE);
                btnAnadir.setVisibility(View.GONE);
                btnEditarPerfil.setText("Seguir");
                btnEditarPerfil.setOnClickListener(v -> {seguirCliente();});
            } else {
                Log.d("PerfClienteAct", "entró a ver su propio perfil");
                setupMenus(R.id.nav_inicio, "cliente");
                btnAtras.setVisibility(View.GONE);
                btnConfig.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.VISIBLE);
                btnAnadir.setVisibility(View.VISIBLE);
            }

            //para enviar el tipo de perfil a las demas activities
            SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = perfilPrefs.edit();
            editor.putString("perfil","cliente");
            editor.putInt("idUsuario", sharedPreferences.getInt("idUsuario",1));
            editor.apply();

            //para enviar a configuracion activity
            SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
            SharedPreferences.Editor editorAct = activityAndTabContext.edit();
            editorAct.putString("activity","PerfilClienteActivity");
            editorAct.putString("tab","inicio");
            editorAct.putBoolean("esLector",false);
            editorAct.apply();

            tvNombreEmpresa.setText(cliente.getNombreCliente());
            tvNombreUsuario.setText(cliente.getNombreUsuario());

            if(cliente.getDescripcion() == null || cliente.getDescripcion().isEmpty() || cliente.getDescripcion().equals("null")) {
                tvDescripcion.setText("Añadir descripción");
            } else {
                tvDescripcion.setText(cliente.getDescripcion());
            }

            if(cliente.getNif() == null || cliente.getNif().isEmpty() || cliente.getNif().equals("null")) {
                tvNif.setText("Añadir NIF");
            } else {
                tvNif.setText(cliente.getNif());
            }

            if(cliente.getEmail() == null || cliente.getEmail().isEmpty() || cliente.getEmail().equals("null")) {
                tvEmail.setText("Añadir email");
            } else {
                tvEmail.setText("Contacto: " + cliente.getEmail());
            }

            if(cliente.getFechaCreacionEmpresa() == null || cliente.getFechaCreacionEmpresa().isEmpty() || cliente.getFechaCreacionEmpresa().equals("null")) {
                tvFechaCreacionEmpresa.setText("Añadir fecha fundación");
            } else {
                tvFechaCreacionEmpresa.setText("Fundación: " + cliente.getFechaCreacionEmpresa().substring(0,4));
            }


            //Comics:
            obtenerComicsDelCliente(cliente.getId());
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

    private void seguirCliente() {
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
        /*if(esLector) {
            Log.d("PerfClientAct", "ES LECTOR ON RESUME");
        } else {
            Log.d("PerfClientAct", "ES CLIENTE ON RESUME");
            setupMenus(R.id.nav_inicio,"cliente");
        }*/
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("PerfClientAct", "ONRESTART()");
        //obtenerComicsDelCliente(idUsuario);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PerfClientAct", "ONPAUSE()");
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("PerfClientAct", "ONSTOP()");
    }

    private void startComicsActivity() {
        Intent intent = new Intent(this, ComicsActivity.class);
        intent.putExtra("cliente", cliente);
        startActivity(intent);
    }

    private void startNuevoComicActivity() {
        Intent intent = new Intent(this, NuevoComicActivity.class);
        startActivity(intent);
    }

    private void startNuevoEventoActivity() {
        Intent intent = new Intent(this, NuevoEventoActivity.class);
        startActivity(intent);
    }

    private void startNuevaRutaActivity() {
        Intent intent = new Intent(this, NuevaRutaActivity.class);
        startActivity(intent);
    }

    private void startNuevaEntradaWikiActivity() {
        Intent intent = new Intent(this, NuevaEntradaWikiActivity.class);
        startActivity(intent);
    }

    private void obtenerOtrasSecciones() {
        Log.d("PerfClienteAct", "datos cliente extra: " + cliente);
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
        Log.d("PerfClienteAct", "obtenerComicsDelCliente()");
        Call<List<Comic>> call = apiService.obtenerComicsPorCliente(idCliente);

        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("PerfClienteAct", "response isSuccessful");
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
            //flAnadirNovedad.setVisibility(View.VISIBLE);
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
        Log.d("PerfClienteAct", "ENTRO EN AGREGAR SECCION COMICS");
        LinearLayout contenedor = findViewById(R.id.comics_container);

        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_comics, null);

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);

        FrameLayout flAnadirComic = seccionView.findViewById(R.id.fl_anadirComic);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_comics);
        tvTitulo.setText("Comics");

        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);

        if (listaComics == null || listaComics.isEmpty()) {
            Log.d("PerfClienteAct", "listaComics == null; verTodo = GONE");
            //flAnadirComic.setVisibility(View.VISIBLE);
            tvVerTodo.setVisibility(View.GONE);
            //flAnadirComic.setOnClickListener(view -> startNuevoComicActivity());
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recycler.setAdapter(null);
        }

        Log.d("PerfClienteAct", "listaComics == null; verTodo = VIEW");
        tvVerTodo.setOnClickListener(view -> startComicsActivity());

        if (listaComics.size() > 6) {
            Log.d("PerfClienteAct", "listaComics > 6");
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
            Log.d("PerfClienteAct", "startComicActivity");
            startActivity(intent);
            //this.finish();
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
            /*if(!esLector) {
                flAnadirEvento.setVisibility(View.VISIBLE);
                flAnadirEvento.setOnClickListener(view -> startNuevoEventoActivity());
            }*/
            tvVerTodo.setVisibility(View.GONE);
        }

        //tvVerTodo.setOnClickListener(view -> startEventosActivity());

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
            /*if(!esLector) {
                flAnadirRuta.setVisibility(View.VISIBLE);
                flAnadirRuta.setOnClickListener(view -> startNuevaRutaActivity());
            }*/
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
            /*if(!esLector) {
                flAnadirWiki.setVisibility(View.VISIBLE);
                flAnadirWiki.setOnClickListener(view -> startNuevaEntradaWikiActivity());
            }*/
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
