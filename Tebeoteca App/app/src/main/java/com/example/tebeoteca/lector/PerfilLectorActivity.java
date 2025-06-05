package com.example.tebeoteca.lector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilLectorActivity extends BaseActivity {

    private TextView nombreUsuario, tvNombreUsuarioLector;
    private ApiService apiService;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupMenus(R.id.nav_inicio);
        setCustomContent(R.layout.activity_inicio_lector);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        String nombreUsuario = "@"+(sharedPreferences.getString("nombreUsuario", "Usuario"));
        String nombreLector = (sharedPreferences.getString("nombreLector", "Nombre")) + " " + (sharedPreferences.getString("apellidos", "Apellidos"));
        String fechaNacimiento = sharedPreferences.getString("fechaNacimiento", "1997-10-31");
        idUsuario = sharedPreferences.getInt("idUsuario",1);
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

}
