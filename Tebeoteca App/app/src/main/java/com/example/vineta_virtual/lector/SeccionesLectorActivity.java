package com.example.vineta_virtual.lector;

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
import com.example.vineta_virtual.cliente.comic.Comic;
import com.example.vineta_virtual.cliente.comic.ComicActivity;
import com.example.vineta_virtual.cliente.comic.ComicsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeccionesLectorActivity extends BaseActivity {
    SharedPreferences perfilPrefs;
    String perfil;
    private ApiService apiService;
    LinearLayout contFavs, contRutas, contEntradas, contEventos;
    TextView verTodoFavs, verTodoRutas, verTodoEntradas, verTodoEventos;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_secciones_lector);

        perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        perfil = perfilPrefs.getString("perfil", "cliente");
        Log.d("SeccionesLectorAct", "perfil: " + perfil);
        setupMenus(R.id.nav_menu, perfil);

        contFavs = findViewById(R.id.favoritos_container);
        contRutas = findViewById(R.id.rutasLectura_container);
        contEntradas = findViewById(R.id.entradasWiki_container);
        contEventos = findViewById(R.id.eventosLector_container);

        verTodoFavs = findViewById(R.id.tv_verTodoFavs);
        verTodoRutas = findViewById(R.id.tv_verTodoRutas);
        verTodoEntradas = findViewById(R.id.tv_verTodoEntradas);
        verTodoEventos = findViewById(R.id.tv_verTodoEventos);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        SharedPreferences usuarioPrefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        idUsuario = usuarioPrefs.getInt("idUsuario",2);
        Log.d("SeccionesLectorAct", "idUsuario: " + idUsuario);
        limpiarContenedores();
        obtenerFavoritosDesdeApi();
    }

    /*@Override
    protected void onResume() {
        setupMenus(R.id.nav_menu, perfil);
        super.onResume();
        limpiarContenedores();
        obtenerFavoritosDesdeApi();
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
    }*/

    private void limpiarContenedores() {
        contFavs.removeAllViews();
        contRutas.removeAllViews();
        contEntradas.removeAllViews();
        contEventos.removeAllViews();
    }

    private void obtenerFavoritosDesdeApi() {
        Call<List<Comic>> call = apiService.obtenerFavoritos(idUsuario);
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Comic> favoritos = response.body();
                    //Log.d("DEBUG LECTOR", "favoritos: " + favoritos);
                    mostrarFavoritos(favoritos);
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Toast.makeText(SeccionesLectorActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                List<Comic> listaVacia = new ArrayList<>();
                mostrarFavoritos(listaVacia);
            }
        });
    }

    private void mostrarFavoritos(List<Comic> comics) {
        for (Comic comic : comics) {
            int idImagen = 0;
            String nombrePortada = comic.getPortada();
            if(nombrePortada != null) {
                idImagen = this.getResources().getIdentifier(
                        nombrePortada, "drawable", this.getPackageName()
                );
            }
            if (contFavs != null) {
                verTodoFavs.setVisibility(View.VISIBLE);
                verTodoFavs.setOnClickListener(view -> startComicsActivity(comics));
                View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, contFavs, false);

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

                contFavs.addView(vistaComic);
                vistaComic.setOnClickListener(v -> {
                    Log.d("DEBUG", "comic enviado: " + comic.getTitulo() + " categoria: " + comic.getCategorias() + " paisOrigen: " + comic.getpais_origen());

                    Intent intent = new Intent(this, ComicActivity.class);
                    intent.putExtra("comic", comic);
                    startActivity(intent);
                });
            }
        }
    }

    private void startComicsActivity(List<Comic> comics) {
        Intent intent = new Intent(this, ComicsActivity.class);
        intent.putExtra("listaFavoritos", (Serializable) comics);
        intent.putExtra("idLector", idUsuario);
        startActivity(intent);
    }
}
