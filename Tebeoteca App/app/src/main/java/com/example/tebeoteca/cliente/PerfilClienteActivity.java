package com.example.tebeoteca.cliente;

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

public class PerfilClienteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_perfil_cliente);

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        String nombreEmpresa = sharedPreferences.getString("nombreEmpresa", "Cliente");
        String nombreUsuario = "@"+(sharedPreferences.getString("nombreUsuario", "cliente"));

        TextView tv_nombreEmpresa = findViewById(R.id.tv_displayNombreEmpresa);
        TextView tv_nombreUsuario = findViewById(R.id.tv_displayNombreUsuario);
        tv_nombreEmpresa.setText(nombreEmpresa);
        tv_nombreUsuario.setText(nombreUsuario);
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
