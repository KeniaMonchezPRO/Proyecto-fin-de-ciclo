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
    LinearLayout resultadosComics, resultadosLectores, resultadosClientes;
    SharedPreferences perfilPrefs;
    String perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_buscar);
        Log.d("BusquedaAct", "ONCREATE()");
        //obtengo el tipo de perfil que ingresa a esta activity para settear el menu:
        perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        perfil = perfilPrefs.getString("perfil", "cliente");

        Log.d("DEBUG BusquedaAct", "perfil: " + perfil);

        setupMenus(R.id.nav_buscar, perfil);

        //guardo en pregs el tipo de perfil y cuando le den a un comic se le envie el perfil a ComicActivity
        perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = perfilPrefs.edit();
        editor.putString("perfil", perfil);
        editor.apply();

        //para enviar a ConfiguracionActivity:
        SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
        SharedPreferences.Editor editorAct = activityAndTabContext.edit();
        editorAct.putString("activity", "BusquedaActivity");
        editorAct.putString("tab", "buscar");
        if(perfil.equals("lector")) {
            editorAct.putBoolean("esLector",true);
        }
        editorAct.apply();

        etBuscar = findViewById(R.id.et_buscar);

        ibBuscar = findViewById(R.id.ib_buscar);
        ibBuscar.setOnClickListener(v -> {
            String consulta = etBuscar.getText().toString().trim();
            if (!consulta.isEmpty()) {
                buscarTodo(consulta);
            }
        });

        resultadosComics = findViewById(R.id.rv_resultados_comicsContainer);
        resultadosLectores = findViewById(R.id.rv_resultados_lectoresContainer);
        resultadosClientes = findViewById(R.id.rv_resultados_clientesContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("BusquedaAct", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("BusquedaAct", "onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BusquedaAct", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BusquedaAct", "onStop()");
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
                    //Log.e("API RESPONSE", "Success: " + resultado.getComics());
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
            Log.d("DEBUG BusquedaActivity","Mostrar comics");
            ComicAdapter adapter = new ComicAdapter(resultado.getComics(), comic -> {
                SharedPreferences prefs = getSharedPreferences("comicPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("activity", "BusquedaActivity");
                editor.apply();
                Intent intent = new Intent(this, ComicActivity.class);
                intent.putExtra("comic", comic);
                startActivity(intent);
            });
            mostrarSeccion("Comics", resultado.getComics(), adapter,resultadosComics);
        }
        if(!resultado.getLectores().isEmpty()) {
            Log.d("DEBUG BusquedaActivity","Mostrar lectores");
            LectorAdapter adapter = new LectorAdapter(resultado.getLectores(), lector -> {
                Intent intent = new Intent(this, PerfilLectorActivity.class);
                intent.putExtra("lector", lector);
                //intent.putExtra("esCliente", false);
                if(perfil.equals("lector")) {
                    intent.putExtra("esLector", true);
                    intent.putExtra("esCliente", false);
                } else {
                    intent.putExtra("esLector", false);
                    intent.putExtra("esCliente", true);
                }
                startActivity(intent);
            });
            mostrarSeccion("Lectores", resultado.getLectores(), adapter, resultadosLectores);

        }
        if (!resultado.getClientes().isEmpty()) {
            Log.d("DEBUG BusquedaActivity","Mostrar clientes");
            ClienteAdapter adapter = new ClienteAdapter(resultado.getClientes(), cliente -> {
                Intent intent = new Intent(this, PerfilClienteActivity.class);
                intent.putExtra("cliente", cliente);
                if(perfil.equals("lector")) {
                    intent.putExtra("esLector", true);
                } else {
                    intent.putExtra("esLector", false);
                }
                startActivity(intent);
            });
            mostrarSeccion("Clientes", resultado.getClientes(), adapter,resultadosClientes);
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

    private void mostrarSeccion(String titulo, List<?> lista, RecyclerView.Adapter<?> adapter, LinearLayout resultados) {
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
