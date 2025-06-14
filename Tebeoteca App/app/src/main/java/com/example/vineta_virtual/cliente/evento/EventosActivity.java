package com.example.vineta_virtual.cliente.evento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.vineta_virtual.BaseActivity;
import com.example.vineta_virtual.R;

public class EventosActivity extends BaseActivity {
    TextView tvAnadirEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_eventos);
        setupMenus(R.id.nav_menu, "cliente");

        tvAnadirEvento = findViewById(R.id.tv_anadir_evento);
        tvAnadirEvento.setOnClickListener(view -> { startNuevoEventoActivity(); });
    }

    private void startNuevoEventoActivity() {
        Intent intent = new Intent(this, NuevoEventoActivity.class);
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
        Log.d("DEBUG","onResume()");
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
