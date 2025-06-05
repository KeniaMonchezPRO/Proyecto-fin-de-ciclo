package com.example.tebeoteca.cliente.wiki;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.cliente.ruta.NuevaRutaActivity;

public class EntradasWikiActivity extends BaseActivity {

    TextView tvAnadirEntradaWiki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_entradas_wiki);
        setupMenus(R.id.nav_inicio);

        tvAnadirEntradaWiki = findViewById(R.id.tv_anadir_entrada_wiki);
        tvAnadirEntradaWiki.setOnClickListener(view -> { startNuevaEntradaWikiActivity(); });
    }

    private void startNuevaEntradaWikiActivity() {
        Intent intent = new Intent(this, NuevaEntradaWikiActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onResume() {
        //setupMenus(R.id.nav_inicio);
        super.onResume();
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
    public void volverAtras(View view) {
        this.finish();
    }
}
