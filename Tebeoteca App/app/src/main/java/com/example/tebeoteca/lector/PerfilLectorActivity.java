package com.example.tebeoteca.lector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

public class PerfilLectorActivity extends BaseActivity {

    private TextView nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_perfil_lector);

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        String nombreUsuario = sharedPreferences.getString("nombreUsuario", "Usuario desconocido");

        TextView miTextView = findViewById(R.id.tv_NombreUsuarioLector);
        miTextView.setText("¡Bienvenido, " + nombreUsuario + "!");
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
