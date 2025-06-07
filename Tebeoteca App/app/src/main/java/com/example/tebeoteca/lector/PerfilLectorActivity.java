package com.example.tebeoteca.lector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.cliente.PerfilClienteActivity;
import com.example.tebeoteca.cliente.comic.Comic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilLectorActivity extends BaseActivity {

    private TextView nombreUsuario, tvNombreUsuarioLector;
    private ImageView fotoPerfil, banner;
    private ApiService apiService;
    int idUsuario;
    SharedPreferences perfilPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupMenus(R.id.nav_inicio, "lector");
        setCustomContent(R.layout.activity_perfil_lector);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        //para enviar el tipo de perfil a las demas activities
        perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = perfilPrefs.edit();
        editor.putString("perfil","lector");
        editor.apply();

        //settear el perfil de acuerdo a la info recibida de LoginActivity
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        String nombreUsuario = "@"+(sharedPreferences.getString("nombreUsuario", "Usuario"));
        String nombreLector = (sharedPreferences.getString("nombreLector", "Nombre")) + " " + (sharedPreferences.getString("apellidos", "Apellidos"));
        String fechaNacimiento = sharedPreferences.getString("fechaNacimiento", "1997-10-31");
        idUsuario = sharedPreferences.getInt("idUsuario",1);

        TextView tv_nombreLector = findViewById(R.id.tv_displayNombreLector);
        TextView tv_nombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        TextView tv_fechaNacimiento = findViewById(R.id.tv_fechaNacimiento);

        tv_nombreLector.setText(nombreLector);
        tv_nombreUsuario.setText(nombreUsuario);
        tv_fechaNacimiento.setText(fechaNacimiento);

        /*fotoPerfil = findViewById(R.id.iv_fotoPerfil);
        fotoPerfil.setImageResource(R.drawable.dc);
        banner = findViewById(R.id.iv_banner);
        banner.setImageResource(R.drawable.flash);*/
    }



    @Override
    protected void onResume() {
        setupMenus(R.id.nav_inicio, "lector");
        super.onResume();
        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        ImageButton btnAtras = findViewById(R.id.btn_atras);
        if (btnAtras != null) {
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


}
