package com.example.vineta_virtual.lector;

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
import com.example.vineta_virtual.cliente.comic.ComicAdapter;
import com.example.vineta_virtual.cliente.ruta.Ruta;
import com.example.vineta_virtual.cliente.ruta.RutaAdapter;
import com.example.vineta_virtual.general.BusquedaActivity;
import com.example.vineta_virtual.general.EditarPerfilActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilLectorActivity extends BaseActivity {

    private TextView tvNombreUsuario, tvNumFavs, tvFechaNac, tvNombreApellidosLector, tvNumSeguidores, tvNumCompras;
    private ImageView fotoPerfil, banner;
    private ApiService apiService;
    int idUsuario, seguidoresCounter = 0;
    boolean esCliente, isFollowing = false;
    Lector lector;
    String perfil;
    ImageButton btnAtras, btnConfig, btnLogout;
    Button btnEditarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCustomContent(R.layout.activity_perfil_lector);

        tvNombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        tvFechaNac = findViewById(R.id.tv_fechaNacimiento);
        tvNombreApellidosLector = findViewById(R.id.tv_displayNombreLector);
        tvNumSeguidores = findViewById(R.id.tv_numSeguidores);
        tvNumFavs = findViewById(R.id.tv_numComics);
        tvNumCompras = findViewById(R.id.tv_numCompras);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        btnAtras = findViewById(R.id.btn_atras);
        btnConfig = findViewById(R.id.btn_configuracion);
        btnLogout = findViewById(R.id.btn_logout);
        btnEditarPerfil = findViewById(R.id.btn_editarPerfil);

        SharedPreferences activityContext = getSharedPreferences("activityAndTabContext",MODE_PRIVATE);
        String activity = activityContext.getString("activity","");
        Log.d("PerfilLectorAct", "activity context: " + activity);

        esCliente = getIntent().getBooleanExtra("esCliente", false);
        Log.d("PerfilLectorAct", "es cliente? " + esCliente);

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE); //cuando entra desde otras activities
        idUsuario = sharedPreferences.getInt("idUsuario",1);
        Log.d("PerfilLectorAct", "ID USUARIO: " + idUsuario);

        lector = (Lector) getIntent().getSerializableExtra("lector"); //cuando entra desde del login
        //Log.d("PerfilLectorAct", "datos lector extra: " + lector + " apellidos: " + lector.getApellidosLector());

        //entra aqui cuando se registra
        if(lector == null) {
            lector = new Lector();
            lector.setNombreLector(sharedPreferences.getString("nombreLector", "Nombre"));
            lector.setApellidosLector(sharedPreferences.getString("apellidos", "Apellidos"));
            lector.setNombreUsuario(sharedPreferences.getString("nombreUsuario","Añadir nombre de usuario"));
            lector.setFechaNac(sharedPreferences.getString("fechaNacimiento","Añadir fecha de nacimiento"));
            lector.setId(sharedPreferences.getInt("idUsuario", 4));
            lector.setEmail(sharedPreferences.getString("email","Añadir email"));
            lector.setContrasena(sharedPreferences.getString("contrasena","Añadir contraseña"));
            Log.d("PerfLectorAct", "datos lector setteados: " + lector);
        }

        if (esCliente) {
            setupMenus(R.id.nav_buscar, "cliente");
            Log.d("PerfilLectorActivity", "entró como cliente");
            perfil = "CLIENTE";
            //para enviar a ConfiguracionActivity:
            SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
            SharedPreferences.Editor editorAct = activityAndTabContext.edit();
            editorAct.putString("activity", "PerfilLectorActivity");
            editorAct.putString("tab", "buscar");
            editorAct.putBoolean("esLector", false);
            editorAct.apply();

            SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = perfilPrefs.edit();
            editor.putString("perfil","cliente");
            editor.putInt("idUsuario", idUsuario);
            editor.apply();

            tvNombreUsuario.setText("@"+lector.getNombreUsuario());
            tvNombreApellidosLector.setText("Añadir nombre y apellidos");
            tvFechaNac.setText(lector.getFechaNac());

            btnEditarPerfil.setText("Seguir");
            btnEditarPerfil.setOnClickListener(v -> {seguirCliente();});
        } else {
            Log.d("PerfilLectorAct", "Entró como lector");
            perfil = "LECTOR";
            if((lector.getId() != idUsuario) && (!activity.equals("EditarPerfilActivity"))) {
                Log.d("PerfilLectorAct", "lector entró a ver el perfil de otro lector");
                setupMenus(R.id.nav_buscar, "lector");
                btnAtras.setVisibility(View.VISIBLE);
                btnConfig.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE);
                btnEditarPerfil.setText("Seguir");
                btnEditarPerfil.setOnClickListener(v -> {seguirCliente();});
            } else {
                Log.d("PerfilLectorAct", "lector entró a ver su propio perfil");
                setupMenus(R.id.nav_inicio, "lector");
                btnAtras.setVisibility(View.GONE);
                btnConfig.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.VISIBLE);
            }

            List<Ruta> listaRutas = new ArrayList<>();
            agregarSeccionRutas(listaRutas);

            //para enviar el tipo de perfil a las demas activities
            SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = perfilPrefs.edit();
            editor.putString("perfil","lector");
            editor.putInt("idUsuario", sharedPreferences.getInt("idUsuario",4));
            editor.apply();

            //para enviar a ConfiguracionActivity:
            SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
            SharedPreferences.Editor editorAct = activityAndTabContext.edit();
            editorAct.putString("activity", "PerfilLectorActivity");
            editorAct.putString("tab", "inicio");
            editorAct.putBoolean("esLector",true);
            editorAct.apply();
            //Log.d("PerfLectorAct", lector.getNombreLector());
            if(lector.getNombreLector() == null || lector.getApellidosLector() == null) {
                tvNombreApellidosLector.setText("Añadir nombre y apellidos");
            } else {
                tvNombreApellidosLector.setText(lector.getNombreLector() + " " + lector.getApellidosLector());
            }

            if(lector.getNombreUsuario() == null || lector.getNombreUsuario().isEmpty() || lector.getNombreUsuario().equals("null")) {
                tvNombreUsuario.setText("@usuario");
            } else {
                tvNombreUsuario.setText("@"+lector.getNombreUsuario());
            }

            if(lector.getFechaNac() == null || lector.getFechaNac().isEmpty() || lector.getFechaNac().equals("null")) {
                tvFechaNac.setText("Añadir fecha de nacimiento");
            } else {
                tvFechaNac.setText(lector.getFechaNac());
            }

            obtenerComprasLector();
            obtenerFavoritosCliente();

            btnEditarPerfil.setOnClickListener(v -> {startEditarPerfilActivity();});
        }

        /*//settear el perfil de acuerdo a la info recibida de LoginActivity
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        String nombreUsuario = "@"+(sharedPreferences.getString("nombreUsuario", "Usuario"));
        String nombreLector = (sharedPreferences.getString("nombreLector", "Nombre")) + " " + (sharedPreferences.getString("apellidos", "Apellidos"));
        String fechaNacimiento = sharedPreferences.getString("fechaNacimiento", "Añadir ");
        idUsuario = sharedPreferences.getInt("idUsuario",2);*/

        /*TextView tv_nombreLector = findViewById(R.id.tv_displayNombreLector);
        TextView tv_nombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        TextView tv_fechaNacimiento = findViewById(R.id.tv_fechaNacimiento);

        tv_nombreLector.setText(nombreLector);
        tv_nombreUsuario.setText(nombreUsuario);
        tv_fechaNacimiento.setText(fechaNacimiento);*/

        fotoPerfil = findViewById(R.id.iv_fotoPerfil);
        fotoPerfil.setImageResource(R.drawable.sin_foto);
        banner = findViewById(R.id.iv_banner);
        banner.setImageResource(R.drawable.green_lantern);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*//Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);*/
        if (btnAtras != null && !esCliente && lector.getId() == idUsuario) {
            setupMenus(R.id.nav_inicio, "lector");
            btnAtras.setVisibility(View.GONE);
        }
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

    private void startEditarPerfilActivity() {
        Log.d("PerfilLectorAct","Iniciando startEditarPerfilActivity()");
        Intent intent = new Intent(PerfilLectorActivity.this, EditarPerfilActivity.class);
        intent.putExtra("tipoUsuario", perfil);
        intent.putExtra("usuario",lector);
        Log.d("PerfilLectorAct","Lector enviado: " + " correo: " + lector.getEmail() + ", contraseña: " + lector.getContrasena());
        startActivity(intent);
    }

    private void seguirCliente() {
        if (isFollowing) {
            Toast.makeText(PerfilLectorActivity.this, "Dejaste de seguir a " + tvNombreUsuario.getText(), Toast.LENGTH_SHORT).show();
            btnEditarPerfil.setText("Seguir");
            seguidoresCounter--;
            tvNumSeguidores.setText(String.valueOf(seguidoresCounter));
            isFollowing = false;
        } else {
            Toast.makeText(PerfilLectorActivity.this, "Ahora sigues a " + tvNombreUsuario.getText(), Toast.LENGTH_SHORT).show();
            btnEditarPerfil.setText("Dejar de seguir");
            seguidoresCounter++;
            tvNumSeguidores.setText(String.valueOf(seguidoresCounter));
            isFollowing = true;
        }
    }

    private void startBusquedaActivity() {
        Intent intent = new Intent(this, BusquedaActivity.class);
        startActivity(intent);
    }

    private void startColeccionActivity() {
        Intent intent = new Intent(this, ColeccionActivity.class);
        //intent.putExtra("lector", lector);
        startActivity(intent);
    }

    private void startSeccionesLectorActivity() {
        Intent intent = new Intent(this, SeccionesLectorActivity.class);
        //intent.putExtra("lector", lector);
        startActivity(intent);
    }
    private void obtenerComprasLector() {
        Log.d("PerfilLectorAct", "obtenerComprasLector()");
        Call<List<Comic>> call = apiService.obtenerCompras(idUsuario);

        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("PerfilLectorAct", "response isSuccessful");
                    List<Comic> listaCompras = response.body();
                    tvNumCompras.setText(String.valueOf(listaCompras.size()));
                    agregarSeccionMiColeccion(listaCompras);
                } else {
                    Toast.makeText(PerfilLectorActivity.this, "Error al cargar las compras", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Toast.makeText(PerfilLectorActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                List<Comic> listaVacia = new ArrayList<>();
                agregarSeccionMiColeccion(listaVacia);
            }
        });
    }
    private void obtenerFavoritosCliente() {
        Log.d("PerfilLectorAct", "obtenerFavoritosCliente()");
        Call<List<Comic>> call = apiService.obtenerFavoritos(idUsuario);

        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("PerfilLectorAct", "response isSuccessful");
                    List<Comic> listaFavs = response.body();
                    tvNumFavs.setText(String.valueOf(listaFavs.size()));
                    agregarSeccionFavoritos(listaFavs);
                } else {
                    Toast.makeText(PerfilLectorActivity.this, "Error al cargar los favoritos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Toast.makeText(PerfilLectorActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                List<Comic> listaVacia = new ArrayList<>();
                agregarSeccionFavoritos(listaVacia);
            }
        });
    }

    private void agregarSeccionMiColeccion(List<Comic> compras) {
        Log.d("PerfLectorAct", "ENTRÓ EN AGREGAR SECCION COMICS");
        LinearLayout contenedor = findViewById(R.id.miColeccion_container);

        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_comics, null);

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);

        FrameLayout flAnadirComic = seccionView.findViewById(R.id.fl_anadirComic);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_comics);
        tvTitulo.setText("Mi colección");

        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);

        if (compras == null || compras.isEmpty()) {
            Log.d("PerfLectorAct", "listaComics vacia, ver todo invible");
            flAnadirComic.setVisibility(View.VISIBLE);
            tvVerTodo.setVisibility(View.GONE);
            flAnadirComic.setOnClickListener(view -> startBusquedaActivity());
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recycler.setAdapter(null);
        }

        Log.d("PerfLectorAct", "listaComics no esta vacia, verTodo invible, anadirCompra visible");
        tvVerTodo.setOnClickListener(view -> startColeccionActivity());

        if (compras.size() > 6) {
            Log.d("PerfLectorAct", "listaComics > 6");
            compras.subList(0, 6);
        }

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ComicAdapter adapter = new ComicAdapter(compras, comic -> {
            SharedPreferences prefs = getSharedPreferences("comicPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("activity", "PerfilLectorActivity");
            editor.putString("perfil","lector");
            editor.apply();
            Intent intent = new Intent(PerfilLectorActivity.this, ComicActivity.class);
            intent.putExtra("comic", comic);
            Log.d("PerfLectorAct", "startComicActivity");
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
    private void agregarSeccionFavoritos(List<Comic> favoritos) {
        Log.d("PerfLectorAct", "ENTRÓ EN AGREGAR SECCION FAVORITOS");
        LinearLayout contenedor = findViewById(R.id.misFavoritos_container);

        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_comics, null);

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);

        FrameLayout flAnadirComic = seccionView.findViewById(R.id.fl_anadirComic);

        TextView tvTitulo = seccionView.findViewById(R.id.seccion_comics);
        tvTitulo.setText("Mis favoritos");

        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);

        if (favoritos == null || favoritos.isEmpty()) {
            Log.d("PerfLectorAct", "listaFavs vacia, ver todo invible");
            flAnadirComic.setVisibility(View.VISIBLE);
            tvVerTodo.setVisibility(View.GONE);
            flAnadirComic.setOnClickListener(view -> startBusquedaActivity());
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recycler.setAdapter(null);
        }

        Log.d("PerfLectorAct", "listaFavs no esta vacia... verTodo invible, anadirFav visible");
        tvVerTodo.setOnClickListener(view -> startSeccionesLectorActivity());

        if (favoritos.size() > 6) {
            Log.d("PerfLectorAct", "listaComics > 6");
            favoritos.subList(0, 6);
        }

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ComicAdapter adapter = new ComicAdapter(favoritos, comic -> {
            SharedPreferences prefs = getSharedPreferences("comicPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("activity", "PerfilLectorActivity");
            editor.putString("perfil","lector");
            editor.apply();
            Intent intent = new Intent(PerfilLectorActivity.this, ComicActivity.class);
            intent.putExtra("comic", comic);
            Log.d("PerfLectorAct", "startComicActivity");
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

    private void agregarSeccionRutas(List<Ruta> rutas) {
        View seccionView = LayoutInflater.from(this).inflate(R.layout.seccion_rutas, null);

        FrameLayout flAnadirRuta = seccionView.findViewById(R.id.fl_anadirRuta);
        TextView tvVerTodo = seccionView.findViewById(R.id.tv_verTodo);
        TextView tvTitulo = seccionView.findViewById(R.id.seccion_rutas);
        tvTitulo.setText("Mis Rutas");

        if (rutas == null || rutas.isEmpty()) {
            Log.d("PerfLectorAct", "listarutas vacia... verTodo invible, anadirRuta visible");
            tvVerTodo.setVisibility(View.GONE);
            flAnadirRuta.setVisibility(View.VISIBLE);
            flAnadirRuta.setOnClickListener(view -> startBusquedaActivity());
        }

        RecyclerView recycler = seccionView.findViewById(R.id.seccion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new RutaAdapter(rutas));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });

        LinearLayout contenedor = findViewById(R.id.misRutas_container);
        contenedor.removeAllViews();
        contenedor.addView(seccionView);

    }


}
