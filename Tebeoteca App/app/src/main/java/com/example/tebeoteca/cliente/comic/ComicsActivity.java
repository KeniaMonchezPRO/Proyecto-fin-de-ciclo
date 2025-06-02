package com.example.tebeoteca.cliente.comic;

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

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicsActivity extends BaseActivity {

    private ApiService apiService;
    private TextView tvAnadirComic;
    LinearLayout contMisteiro, contAccion, contAventura, contComedia, contCrimen, contDrama, contFantasia, contRealista, contSciFi, contSuper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comics);
        setupMenus(R.id.nav_comics);

        Log.d("DEBUG","onCreate()");
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

        tvAnadirComic = findViewById(R.id.tv_anadir_comic);
        tvAnadirComic.setOnClickListener(view -> { startAnadirComicActivity(); });
    }

    @Override
    protected void onResume() {
        setupMenus(R.id.nav_comics);
        super.onResume();
        Log.d("DEBUG","onResume()");
        limpiarContenedores();
        cargarComicsDesdeApi();
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
    public void volverAtras(View view) {
        this.finish();
    }

    private void startAnadirComicActivity() {
        Intent intent = new Intent(this, AnadirComicActivity.class);
        startActivity(intent);
    }

    private void cargarComicsDesdeApi() {
        Log.d("DEBUG","cargarComicsDesdeApi()");
        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        int idResArrayItems = R.array.spinner_categorias_array;
        String[] categorias = getResources().getStringArray(idResArrayItems);
        Map<String, LinearLayout> contenedoresPorCategoria = new HashMap<>();
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

        obtenerComicsPorCategoria(categorias, contenedoresPorCategoria);

    }

    private void obtenerComicsPorCategoria(String[] categorias, Map<String, LinearLayout> contenedoresPorCategoria) {

        for (String categoria : categorias) {
            Call<List<Comic>> call = apiService.obtenerComicsPorCategoria(categoria);

            call.enqueue(new Callback<List<Comic>>() {
                @Override
                public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Comic> comics = response.body();
                        Log.d("DEBUG","Lista de comics: " + comics);
                        mostrarComics(comics, categoria, contenedoresPorCategoria);
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
    private void mostrarComics(List<Comic> comics, String categoria, Map<String, LinearLayout> contenedoresPorCategoria) {
        for (Comic comic : comics) {
            Log.d("DEBUG","Comic: " + comic.getPais_origen() + " " + comic.getCategorias());
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

                    Log.d("DEBUG", "comic enviado: " + comic.getTitulo() + " categoria: " + comic.getCategorias() + " paisOrigen: " + comic.getPais_origen());

                    Intent intent = new Intent(this, ComicActivity.class);
                    intent.putExtra("comic", comic);
                    startActivity(intent);
                });
            }
        }
    }
}
