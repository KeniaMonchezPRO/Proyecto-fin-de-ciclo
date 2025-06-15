package com.example.vineta_virtual.cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.vineta_virtual.R;
import com.example.vineta_virtual.cliente.comic.NuevoComicActivity;
import com.example.vineta_virtual.cliente.evento.NuevoEventoActivity;
import com.example.vineta_virtual.cliente.ruta.NuevaRutaActivity;
import com.example.vineta_virtual.cliente.wiki.NuevaEntradaWikiActivity;

public class DialogNuevaSeccionActivity extends Activity {

    FrameLayout lyAnadirNuevaRuta, lyAnadirNuevoEvento, lyAnadirNuevoComic, lyAnadirNuevaEntradaWiki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_anadir_seccion);
    }

    public void finishDialog(View v) {
        DialogNuevaSeccionActivity.this.finish();
    }

    public void startNuevoComicActivity(View view) {
        Intent intent = new Intent(this, NuevoComicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finishDialog(view);
    }

    public void startNuevaRutaActivity(View view) {
        Intent intent = new Intent(this, NuevaRutaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finishDialog(view);
    }

    public void startNuevoEventoActivity(View view) {
        Intent intent = new Intent(this, NuevoEventoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finishDialog(view);
    }

    public void startNuevaEntradaWikiActivity(View view) {
        Intent intent = new Intent(this, NuevaEntradaWikiActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finishDialog(view);
    }
}
