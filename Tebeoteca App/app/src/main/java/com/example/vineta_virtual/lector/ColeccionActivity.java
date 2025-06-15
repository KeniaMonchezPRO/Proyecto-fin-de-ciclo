package com.example.vineta_virtual.lector;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vineta_virtual.BaseActivity;
import com.example.vineta_virtual.R;
import com.example.vineta_virtual.api.ApiService;
import com.example.vineta_virtual.cliente.comic.Comic;
import com.example.vineta_virtual.cliente.comic.ComicActivity;
import com.example.vineta_virtual.cliente.comic.ComicAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ColeccionActivity extends BaseActivity {
    int idUsuario;
    SharedPreferences perfilPrefs;
    String perfil;
    ApiService apiService;
    LinearLayout coleccionLector;
    private TextView tvComicCounterColeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_coleccion_lector);

        tvComicCounterColeccion = findViewById(R.id.tv_comicCounterColeccion);

        perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        perfil = perfilPrefs.getString("perfil", "cliente");
        setupMenus(R.id.nav_comics, perfil);

        //para enviar a ConfigActivity:
        SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
        SharedPreferences.Editor editorAct = activityAndTabContext.edit();
        editorAct.putString("activity", "ColeccionActivity");
        editorAct.putString("tab", "comics");
        if(perfil.equals("lector")) {
            editorAct.putBoolean("esLector",true);
        }
        editorAct.apply();

        SharedPreferences usuarioPrefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        idUsuario = usuarioPrefs.getInt("idUsuario",2);
        Log.d("DEBUG LECTOR ColeccionActivity", "idUsuario: " + idUsuario);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        coleccionLector = findViewById(R.id.ly_coleccionLector);
        limpiarContenedores();
        obtenerComprasDesdeApi();

    }

    public void limpiarContenedores() {
        coleccionLector.removeAllViews();
    }

    private void obtenerComprasDesdeApi() {
        Call<List<Comic>> call = apiService.obtenerCompras(idUsuario);
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Comic> compras = response.body();
                    mostrarCompras(compras);
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Toast.makeText(ColeccionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                List<Comic> listaVacia = new ArrayList<>();
                //Log.d("DEBUG LECTOR", "compras onFailure: " + listaVacia);
                mostrarCompras(listaVacia);
            }
        });
    }

    private void mostrarCompras(List<Comic> compras) {
        tvComicCounterColeccion.setText(String.valueOf(compras.size() + " Comics"));
        tvComicCounterColeccion.setVisibility(View.VISIBLE);
        Log.d("ColeccionAct: ","Perfil: " + perfil + ", Mostrar compras");
        if (coleccionLector != null) {
            Log.d("ColeccionAct: ","Perfil: " + perfil + ", coleccionLector no está vacío");
            if (!compras.isEmpty()) {
                Log.d("ColeccionAct", "Perfil: " + perfil + ", lista de compras no está vacía");
                ComicAdapter adapter = new ComicAdapter(compras, comic -> {
                    SharedPreferences prefs = getSharedPreferences("comicPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("activity", "ColeccionActivity");
                    editor.apply();
                    Intent intent = new Intent(this, ComicActivity.class);
                    intent.putExtra("comic", comic);
                    startActivity(intent);
                });
                mostrarSeccion("Comics", compras, adapter, coleccionLector);
            }
        }
    }

    private void mostrarSeccion(String titulo, List<?> lista, RecyclerView.Adapter<?> adapter, LinearLayout resultados) {
        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setTextSize(14);
        tvTitulo.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        tvTitulo.setTypeface(null, Typeface.BOLD);

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 30;
            }
        });

        resultados.removeAllViews();
        resultados.addView(tvTitulo);
        resultados.addView(recyclerView);
    }

    private void mostrarCompras2(List<Comic> compras) {
        LinearLayout filaActual = null;
        int contador = 0;
        for (Comic compra : compras) {
            contador++;
            Log.d("DEBUG LECTOR ColeccionActivity","contador: " + contador);
            int idImagen = 0;
            String nombrePortada = compra.getPortada();
            if(nombrePortada != null) {
                idImagen = this.getResources().getIdentifier(
                        nombrePortada, "drawable", this.getPackageName()
                );
            }
            if (coleccionLector != null) {
                View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, coleccionLector, false);

                TextView tvNombre = vistaComic.findViewById(R.id.tvTitulo);
                ImageView ivImagen = vistaComic.findViewById(R.id.iv_portada);
                TextView tvAutores = vistaComic.findViewById(R.id.tvAutores);
                TextView tvSelloEditorial = vistaComic.findViewById(R.id.tvSelloEditorial);

                if (contador % 2 == 0) {
                    Log.d("DEBUG LECTOR ColeccionActivity","par");
                    filaActual = new LinearLayout(this);
                    filaActual.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams filaParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    filaActual.setLayoutParams(filaParams);
                    coleccionLector.addView(filaActual);
                    Log.d("DEBUG LECTOR ColeccionActivity","añade al ly la fila actual ");
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f
                );
                params.setMargins(0,20,40,0);
                vistaComic.setLayoutParams(params);

                if (idImagen != 0) {
                    ivImagen.setImageResource(idImagen);
                } else {
                    ivImagen.setImageResource(R.drawable.sin_foto); //imagen por defecto
                }
                tvNombre.setText(compra.getTitulo());
                tvAutores.setText(compra.getAutores());
                tvSelloEditorial.setText(compra.getSelloEditorial());

                filaActual.addView(vistaComic);

                vistaComic.setOnClickListener(v -> {
                    Log.d("DEBUG", "comic enviado: " + compra.getTitulo());

                    Intent intent = new Intent(this, ComicActivity.class);
                    intent.putExtra("comic", compra);
                    startActivity(intent);
                });
            }
        }
    }
}
