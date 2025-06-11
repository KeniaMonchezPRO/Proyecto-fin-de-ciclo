package com.example.tebeoteca.cliente.ruta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.cliente.evento.NuevoEventoActivity;

public class RutasActivity extends BaseActivity {

    TextView tvAnadirRuta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_rutas);
        setupMenus(R.id.nav_menu, "cliente");

        tvAnadirRuta = findViewById(R.id.tv_anadir_ruta);
        tvAnadirRuta.setOnClickListener(view -> { startNuevaRutaActivity(); });

    }

    private void startNuevaRutaActivity() {
        Intent intent = new Intent(this, NuevaRutaActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        //setupMenus(R.id.nav_inicio);
        super.onResume();
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
        Log.d("DEBUG", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
        Log.d("ComicsAct","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}