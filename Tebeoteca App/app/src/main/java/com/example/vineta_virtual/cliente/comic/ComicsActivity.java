package com.example.vineta_virtual.cliente.comic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vineta_virtual.BaseActivity;
import com.example.vineta_virtual.R;
import com.example.vineta_virtual.api.ApiService;
import com.example.vineta_virtual.cliente.Cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicsActivity extends BaseActivity {
    String perfil;
    int idUsuario;
    Cliente cliente;
    private ApiService apiService;
    private TextView tvAnadirComic, tvComicCounter;
    Map<String, LinearLayout> contenedoresPorCategoria;
    String[] categorias;
    LinearLayout contMisteiro, contAccion, contAventura, contComedia, contCrimen, contDrama, contFantasia, contRealista, contSciFi, contSuper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ComicsAct","onCreate()");
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comics);

        Log.d("ComicsAct","onCreate()");

        cargarComicsDesdeApi();

        tvAnadirComic = findViewById(R.id.tv_anadir_comic);
        tvComicCounter = findViewById(R.id.tv_comicCounter);


        SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        perfil = perfilPrefs.getString("perfil", "cliente");
        idUsuario = perfilPrefs.getInt("idUsuario",1);

        Log.d("ComicsAct", "ha entrado: {perfil: " + perfil + ", idUsuario:" + idUsuario + "}");

        cliente = (Cliente) getIntent().getSerializableExtra("cliente"); //cuando entra desde del login
        Log.d("ComicsAct", "datos cliente extra: " + cliente);

        //para enviar a ConfiguracionActivity:
        SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
        SharedPreferences.Editor editorAct = activityAndTabContext.edit();
        editorAct.putString("activity", "ComicsActivity");
        editorAct.putString("tab", "comics");
        editorAct.apply();

        Log.d("ComicsAct", "PERFIL: " + perfil);
        /*if(perfil.equals("lector")) {
            Log.d("DEBUG COMICS ACTIVITY", "entró lector");
            setupMenus(R.id.nav_menu, "lector");
        } else {
            Log.d("DEBUG COMICS ACTIVITY", "entró cliente");
            setupMenus(R.id.nav_comics, "cliente");
        }*/

        contMisteiro = findViewById(R.id.misterio_container);
        contAccion = findViewById(R.id.accion_container);
        contAventura = findViewById(R.id.aventura_container);
        contComedia = findViewById(R.id.comedia_container);
        contCrimen = findViewById(R.id.crimen_container);
        contDrama = findViewById(R.id.drama_container);
        contFantasia = findViewById(R.id.fantasia_container);
        contRealista = findViewById(R.id.realista_container);
        contSciFi = findViewById(R.id.scifi_container);
        contSuper = findViewById(R.id.superheroes_container);
        //limpiarContenedores();
        //cargarComicsDesdeApi();

        int idResArrayItems = R.array.spinner_categorias_array;
        categorias = getResources().getStringArray(idResArrayItems);
        contenedoresPorCategoria = new HashMap<>();
        contenedoresPorCategoria.put(categorias[0], contAccion);
        contenedoresPorCategoria.put(categorias[1], contAventura);
        contenedoresPorCategoria.put(categorias[2], contComedia);
        contenedoresPorCategoria.put(categorias[3], contCrimen);
        contenedoresPorCategoria.put(categorias[4], contDrama);
        contenedoresPorCategoria.put(categorias[5], contFantasia);
        contenedoresPorCategoria.put(categorias[6], contMisteiro);
        contenedoresPorCategoria.put(categorias[7], contRealista);
        contenedoresPorCategoria.put(categorias[8], contSciFi);
        contenedoresPorCategoria.put(categorias[9], contSuper);

        if(cliente != null && !perfil.equals("lector")) {
            if(cliente.getId() != idUsuario) {
                Log.d("ComicsAct", "cliente entró a ver los comics de otro cliente");
                setupMenus(R.id.nav_buscar,"cliente");
                tvAnadirComic.setVisibility(View.GONE);
                limpiarContenedores();
                obtenerComicsCliente(cliente.getId(), contenedoresPorCategoria);
            } else {
                Log.d("ComicsAct", "cliente entró a ver sus comics1");
                setupMenus(R.id.nav_comics, perfil);
                limpiarContenedores();
                obtenerComicsCliente(idUsuario, contenedoresPorCategoria);
            }
        } else if(cliente == null && !perfil.equals("lector")) {
            Log.d("ComicsAct", "cliente entró a ver sus comics2");
            if(!perfil.equals("lector")) {
                setupMenus(R.id.nav_comics, perfil);
                limpiarContenedores();
                obtenerComicsCliente(idUsuario, contenedoresPorCategoria);
            }
        }

        List<Comic> listaFavoritos = (List<Comic>) getIntent().getSerializableExtra("listaFavoritos");
        if(perfil.equals("lector") && listaFavoritos != null) {
            //int idLector = getIntent().getIntExtra("idLector",4);
            setupMenus(R.id.nav_menu,perfil);
            tvAnadirComic.setVisibility(View.GONE);
            tvComicCounter.setText(String.valueOf(listaFavoritos.size()));
            Log.d("ComicsAct", "mostrarComicsFavs()");
            mostrarComics(listaFavoritos, contenedoresPorCategoria);
        }

        if(cliente != null && perfil.equals("lector")) {
            Log.d("ComicsAct", "lector entró a ver los comics de cliente");
            setupMenus(R.id.nav_buscar,"lector");
            tvAnadirComic.setVisibility(View.GONE);
            limpiarContenedores();
            obtenerComicsCliente(cliente.getId(), contenedoresPorCategoria);
        }

        tvAnadirComic.setOnClickListener(view -> { startAnadirComicActivity(); });

    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Log.d("ComicsAct","onResume()");
        setupMenus(R.id.nav_menu, perfil);
//        if(!perfil.equals("lector")) {
//            limpiarContenedores();
//            cargarComicsDesdeApi();
//            setupMenus(R.id.nav_comics, perfil);
//        }
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
        Log.d("ComicsAct","onPause()");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ComicsAct","onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
        Log.d("ComicsAct","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ComicsAct","onPause()");
    }*/

    private void startAnadirComicActivity() {
        Intent intent = new Intent(this, NuevoComicActivity.class);
        startActivity(intent);
    }

    private void cargarComicsDesdeApi() {
        Log.d("ComicsAct","cargarComicsDesdeApi()");
        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        //obtenerComicsPorCategoria(categorias, contenedoresPorCategoria);

    }

    private void obtenerComicsCliente(int idCliente, Map<String, LinearLayout> contenedoresPorCategoria) {
        Call<List<Comic>> call = apiService.obtenerComicsPorCliente(idCliente);
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comic> comicsApi = response.body();
                    Log.d("ComicsAct","Num de Comics: " + comicsApi.size());
                    tvComicCounter.setText(String.valueOf(comicsApi.size()));
                    mostrarComics(comicsApi, contenedoresPorCategoria);
                } else {
                    Toast.makeText(ComicsActivity.this, "No se encontraron cómics", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Toast.makeText(ComicsActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void obtenerComicsPorCategoria(String[] categorias, Map<String, LinearLayout> contenedoresPorCategoria) {
        Log.d("ComicsAct","obtenerComicsPorCategoria()");
        for (String categoria : categorias) {
            Call<List<Comic>> call = apiService.obtenerComicsPorCategoria(categoria);
            call.enqueue(new Callback<List<Comic>>() {
                @Override
                public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Comic> comics = response.body();
                        mostrarComics(comics,contenedoresPorCategoria);
                    } else {
                        Toast.makeText(ComicsActivity.this, "No se encontraron cómics", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Comic>> call, Throwable t) {
                    Toast.makeText(ComicsActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        /*Log.d("ComicsAct","terminó la criba");
        Log.d("ComicsAct","comicsClienteBusqueda: " + comicsClienteBusqueda.size());
        Log.d("ComicsAct","comicsClienteReal: " + comicsClienteReal.size());*/
    }

    private void limpiarContenedores() {
        contAccion.removeAllViews();
        contAventura.removeAllViews();
        contComedia.removeAllViews();
        contCrimen.removeAllViews();
        contDrama.removeAllViews();
        contFantasia.removeAllViews();
        contMisteiro.removeAllViews();
        contRealista.removeAllViews();
        contSciFi.removeAllViews();
        contSuper.removeAllViews();
    }

    private void mostrarComicsDep(List<Comic> comics, Map<String, LinearLayout> contenedoresPorCategoria) {
        for (Comic comic : comics) {
            Log.d("ComicsAct","Comic: " + comic.getId() + ", " + comic.getTitulo() + ", " + comic.getCategorias());
            int idImagen = 0;
            String nombrePortada = comic.getPortada();
            if(nombrePortada != null) {
                idImagen = this.getResources().getIdentifier(
                        nombrePortada, "drawable", this.getPackageName()
                );
            }
            List<String> cats = new ArrayList<String>(Arrays.asList(comic.getCategorias().split(",")));
            for (String categoria : cats) {
                Log.d("ComicsAct","Categoria: " + categoria);
                LinearLayout contenedor = contenedoresPorCategoria.get(categoria);
                if (contenedor != null) {
                    View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, contenedor, false);

                    TextView tvNombre = vistaComic.findViewById(R.id.tvTitulo);
                    ImageView ivImagen = vistaComic.findViewById(R.id.iv_portada);
                    TextView tvAutores = vistaComic.findViewById(R.id.tvAutores);
                    TextView tvSelloEditorial = vistaComic.findViewById(R.id.tvSelloEditorial);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0,20,40,0);
                    vistaComic.setLayoutParams(params);

                    if (idImagen != 0) {
                        ivImagen.setImageResource(idImagen);
                    } else {
                        ivImagen.setImageResource(R.drawable.sin_foto); //imagen por defecto
                    }
                    tvNombre.setText(comic.getTitulo());
                    tvAutores.setText(comic.getAutores());
                    tvSelloEditorial.setText(comic.getSelloEditorial());

                    contenedor.addView(vistaComic);

                    vistaComic.setOnClickListener(v -> {
                        Log.d("ComicsAct", "comic enviado: " + comic.getTitulo() + " categoria: " + comic.getCategorias() + " paisOrigen: " + comic.getpais_origen());

                        Intent intent = new Intent(this, ComicActivity.class);
                        intent.putExtra("comic", comic);
                        startActivity(intent);
                    });
                }
            }

        }

        /*for (Comic comic : comics) {
            int idImagen = 0;
            String nombrePortada = comic.getPortada();
            if(nombrePortada != null) {
                idImagen = this.getResources().getIdentifier(
                        nombrePortada, "drawable", this.getPackageName()
                );
            }

            LinearLayout contenedor = contenedoresPorCategoria.get(categoria);
            if (contenedor != null) {
                View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, contenedor, false);

                TextView tvNombre = vistaComic.findViewById(R.id.tvTitulo);
                ImageView ivImagen = vistaComic.findViewById(R.id.iv_portada);
                TextView tvAutores = vistaComic.findViewById(R.id.tvAutores);
                TextView tvSelloEditorial = vistaComic.findViewById(R.id.tvSelloEditorial);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0,20,40,0);
                vistaComic.setLayoutParams(params);

                if (idImagen != 0) {
                    ivImagen.setImageResource(idImagen);
                } else {
                    ivImagen.setImageResource(R.drawable.sin_foto); //imagen por defecto
                }
                tvNombre.setText(comic.getTitulo());
                tvAutores.setText(comic.getAutores());
                tvSelloEditorial.setText(comic.getSelloEditorial());

                contenedor.addView(vistaComic);

                vistaComic.setOnClickListener(v -> {
                    SharedPreferences prefs = getSharedPreferences("comicPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("activity", "ComicsActivity");
                    editor.apply();

                    Log.d("ComicsAct", "comic enviado: " + comic.getTitulo() + " categoria: " + comic.getCategorias() + " paisOrigen: " + comic.getPais_origen());

                    Intent intent = new Intent(this, ComicActivity.class);
                    intent.putExtra("comic", comic);
                    startActivity(intent);
                });
            }
        }*/
    }

    private void mostrarComics(List<Comic> comics, Map<String, LinearLayout> contenedoresPorCategoria) {
        for (Comic comic : comics) {
            Log.d("ComicsAct","Comic: " + comic.getId() + ", " + comic.getTitulo() + ", " + comic.getCategorias());
            int idImagen = 0;
            String nombrePortada = comic.getPortada();
            if(nombrePortada != null) {
                idImagen = this.getResources().getIdentifier(
                        nombrePortada, "drawable", this.getPackageName()
                );
            }
            List<String> cats = new ArrayList<String>(Arrays.asList(comic.getCategorias().split(",")));
            for (String categoria : cats) {
                Log.d("ComicsAct","Categoria: " + categoria);
                LinearLayout contenedor = contenedoresPorCategoria.get(categoria);
                if (contenedor != null) {
                    View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, contenedor, false);

                    TextView tvNombre = vistaComic.findViewById(R.id.tvTitulo);
                    ImageView ivImagen = vistaComic.findViewById(R.id.iv_portada);
                    TextView tvAutores = vistaComic.findViewById(R.id.tvAutores);
                    TextView tvSelloEditorial = vistaComic.findViewById(R.id.tvSelloEditorial);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0,20,40,0);
                    vistaComic.setLayoutParams(params);

                    if (idImagen != 0) {
                        ivImagen.setImageResource(idImagen);
                    } else {
                        ivImagen.setImageResource(R.drawable.sin_foto); //imagen por defecto
                    }
                    tvNombre.setText(comic.getTitulo());
                    tvAutores.setText(comic.getAutores());
                    tvSelloEditorial.setText(comic.getSelloEditorial());

                    contenedor.addView(vistaComic);

                    vistaComic.setOnClickListener(v -> {
                        Log.d("ComicsAct", "comic enviado: " + comic.getTitulo() + " categoria: " + comic.getCategorias() + " paisOrigen: " + comic.getpais_origen());

                        Intent intent = new Intent(this, ComicActivity.class);
                        intent.putExtra("comic", comic);
                        intent.putExtra("idUsuario", idUsuario);
                        startActivity(intent);
                    });
                }
            }

        }
    }
}
