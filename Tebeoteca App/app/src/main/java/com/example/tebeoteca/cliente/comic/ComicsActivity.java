package com.example.tebeoteca.cliente.comic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comics);
        setupMenus(R.id.nav_comics);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        obtenerComicsPorCategoria("Sci-Fi");

        /*Comic nuevo1 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
        Comic nuevo2 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Superheroes", R.drawable.flash);
        Comic nuevo3 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Superheroes", R.drawable.flash);
        Comic nuevo4 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Superheroes", R.drawable.flash);
        ComicRepository.agregarComic(nuevo1);
        ComicRepository.agregarComic(nuevo2);
        ComicRepository.agregarComic(nuevo3);
        ComicRepository.agregarComic(nuevo4);

        // Simulación de datos
        Map<String, LinearLayout> contenedoresPorCategoria = new HashMap<>();
        contenedoresPorCategoria.put("Misterio", findViewById(R.id.misterio_container));
        contenedoresPorCategoria.put("Superheroes", findViewById(R.id.superheroes_container));
        for (Comic comic : ComicRepository.getComics()) {
            LinearLayout contenedor = contenedor esPorCategoria.get(comic.getCategoria());
            if (contenedor != null) {
                View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, contenedor, false);

                TextView tvNombre = vistaComic.findViewById(R.id.tvTitulo);
                ImageView ivImagen = vistaComic.findViewById(R.id.iv_portada);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0,20,40,0);
                vistaComic.setLayoutParams(params);

                tvNombre.setText(comic.getTitulo());
                ivImagen.setImageResource(comic.getIdImagen());

                contenedor.addView(vistaComic);
                vistaComic.setOnClickListener(v -> {
                    Intent intent = new Intent(this, ComicActivity.class);
                    intent.putExtra("comic", comic);
                    startActivity(intent);
                });
            }
        }*/
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
    public void volverAtras(View view) {
        this.finish();
    }

    private void obtenerComicsPorCategoria(String categoria) {
        Call<List<Comic>> call = apiService.obtenerComicsPorCategoria(categoria);

        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                Log.d("COMICS", "RESPONSE: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    List<Comic> comics = response.body();
                    mostrarComics(comics);
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
    private void mostrarComics(List<Comic> comics) {
        Map<String, LinearLayout> contenedoresPorCategoria = new HashMap<>();
        contenedoresPorCategoria.put("Misterio", findViewById(R.id.misterio_container));
        contenedoresPorCategoria.put("Superheroes", findViewById(R.id.superheroes_container));
        contenedoresPorCategoria.put("Sci-Fi", findViewById(R.id.scifi_container));
        for (Comic comic : comics) {
            LinearLayout contenedor = contenedoresPorCategoria.get(comic.getCategoria());
            if (contenedor != null) {
                Log.d("COMICS", "Contenedor NOT NULL");
                View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, contenedor, false);

                TextView tvNombre = vistaComic.findViewById(R.id.tvTitulo);
                ImageView ivImagen = vistaComic.findViewById(R.id.iv_portada);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0,20,40,0);
                vistaComic.setLayoutParams(params);

                tvNombre.setText(comic.getTitulo());
                ivImagen.setImageResource(comic.getIdImagen());

                contenedor.addView(vistaComic);
                vistaComic.setOnClickListener(v -> {
                    Intent intent = new Intent(this, ComicActivity.class);
                    intent.putExtra("comic", comic);
                    startActivity(intent);
                });
            }
        }

    }
}
