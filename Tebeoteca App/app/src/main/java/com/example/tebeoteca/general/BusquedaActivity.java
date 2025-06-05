package com.example.tebeoteca.general;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.cliente.ClienteAdapter;
import com.example.tebeoteca.cliente.PerfilClienteActivity;
import com.example.tebeoteca.cliente.comic.ComicActivity;
import com.example.tebeoteca.cliente.comic.ComicAdapter;
import com.example.tebeoteca.lector.LectorAdapter;
import com.example.tebeoteca.lector.PerfilLectorActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusquedaActivity extends BaseActivity {

    private EditText etBuscar;
    private ImageButton ibBuscar;
    //private List<Object> resultados = new ArrayList<>();
    private ApiService apiService;
    LinearLayout resultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_buscar);
        setupMenus(R.id.nav_buscar);

        etBuscar = findViewById(R.id.et_buscar);

        ibBuscar = findViewById(R.id.ib_buscar);
        ibBuscar.setOnClickListener(v -> {
            String consulta = etBuscar.getText().toString().trim();
            if (!consulta.isEmpty()) {
                buscarTodo(consulta);
            }
        });

        resultados = findViewById(R.id.rv_resultados_container);
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

    private void buscarTodo(String titulo) {
        //apiService = RetrofitClient.getInstance().getApiService();
        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        apiService.buscarTodo(titulo).enqueue(new Callback<BusquedaRequest>() {
            @Override
            public void onResponse(Call<BusquedaRequest> call, Response<BusquedaRequest> response) {
                if (response.isSuccessful()) {
                    BusquedaRequest resultado = response.body();
                    mostrarResultados(resultado);
                    Log.e("API RESPONSE", "Success: " + resultado.getComics());
                }
            }

            @Override
            public void onFailure(Call<BusquedaRequest> call, Throwable t) {
                Log.e("API RESPONSE", "Error: " + t.getMessage());
            }
        });
    }

    public void mostrarResultados(BusquedaRequest resultado) {
        if (!resultado.getComics().isEmpty()) {
            ComicAdapter adapter = new ComicAdapter(resultado.getComics(), comic -> {
                Intent intent = new Intent(this, ComicActivity.class);
                intent.putExtra("comic", comic);
                startActivity(intent);
            });
            mostrarSeccion("Comics", resultado.getComics(), adapter);
        }
        if(!resultado.getLectores().isEmpty()) {
            LectorAdapter adapter = new LectorAdapter(resultado.getLectores(), lector -> {
                Intent intent = new Intent(this, PerfilLectorActivity.class);
                intent.putExtra("lector", lector);
                //añadir sharepreferences para que no salga por ejemplo el boton de editarperfil del lector o se le reemplaze por "seguir"
                startActivity(intent);
            });
            mostrarSeccion("Lectores", resultado.getComics(), adapter);

        }
        if (!resultado.getClientes().isEmpty()) {
            ClienteAdapter adapter = new ClienteAdapter(resultado.getClientes(), cliente -> {
                Intent intent = new Intent(this, PerfilClienteActivity.class);
                intent.putExtra("cliente", cliente);
                //añadir sharepreferences para que no salga por ejemplo el boton de editarperfil del lector o se le reemplaze por "seguir"
                startActivity(intent);
            });
            mostrarSeccion("Clientes", resultado.getClientes(), adapter);
        }


        /*if (!resultado.getLectores().isEmpty()) {
            LectorAdapter adapter = new LectorAdapter(resultado.getLectores(), lector -> {
                // Aquí puedes abrir una nueva activity o mostrar info del lector
                Toast.makeText(this, "Seleccionaste a " + lector.getNombreUsuario(), Toast.LENGTH_SHORT).show();
            });
            mostrarSeccion("Lectores", resultado.getLectores(), adapter);
        }*/


        /*Cuando tenga lista estas activities descomentar
        if (!resultado.getEventos().isEmpty()) {
            mostrarSeccion("Eventos", resultado.getEventos());
        }

        if (!resultado.getWiki().isEmpty()) {
            mostrarSeccion("Wiki", resultado.getWiki());
        }

        if (!resultado.getRutas().isEmpty()) {
            mostrarSeccion("Rutas de lectura", resultado.getRutas());
        }*/
    }

    private void mostrarSeccion(String titulo, List<?> lista, RecyclerView.Adapter<?> adapter) {
        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setTextSize(14);
        tvTitulo.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        tvTitulo.setTypeface(null, Typeface.BOLD);

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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

}
