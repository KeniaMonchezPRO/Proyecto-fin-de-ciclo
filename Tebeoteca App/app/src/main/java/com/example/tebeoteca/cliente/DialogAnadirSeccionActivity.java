package com.example.tebeoteca.cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tebeoteca.ConfiguracionActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.cliente.comic.AnadirComicActivity;

public class DialogAnadirSeccionActivity extends Activity {

    FrameLayout lyAnadirNuevaRuta, lyAnadirNuevoEvento, lyAnadirNuevoComic, lyAnadirNuevaEntradaWiki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_anadir_seccion);
    }

    public void finishDialog(View v) {
        DialogAnadirSeccionActivity.this.finish();
    }

    public void startAnadirNuevoComicActivity(View view) {
        Intent intent = new Intent(this, AnadirComicActivity.class);
        startActivity(intent);
        finishDialog(view);
    }

    public void startAnadirNuevaRutaActivity(View view) {
        Intent intent = new Intent(this, AnadirComicActivity.class);
        startActivity(intent);
        finishDialog(view);
    }

    public void startAnadirNuevoEventoActivity(View view) {
        Intent intent = new Intent(this, AnadirComicActivity.class);
        startActivity(intent);
        finishDialog(view);
    }

    public void startAnadirNuevaEntradaWikiActivity(View view) {
        Intent intent = new Intent(this, AnadirComicActivity.class);
        startActivity(intent);
        finishDialog(view);
    }
}
