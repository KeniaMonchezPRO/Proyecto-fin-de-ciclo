package com.example.tebeoteca.lector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

public class ColeccionActivity extends BaseActivity {
    int idUsuario;
    SharedPreferences perfilPrefs;
    String perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_coleccion_lector);

        perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        perfil = perfilPrefs.getString("perfil", "cliente");
        setupMenus(R.id.nav_comics, perfil);

        SharedPreferences usuarioPrefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        idUsuario = usuarioPrefs.getInt("idUsuario",2);
        Log.d("DEBUG LECTOR", "idUsuario: " + idUsuario);
    }
}
