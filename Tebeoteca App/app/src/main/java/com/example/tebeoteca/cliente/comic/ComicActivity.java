package com.example.tebeoteca.cliente.comic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

public class ComicActivity extends BaseActivity {
    private ImageView ivPortada;
    private TextView tvTitulo, tvDescripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comic);
        setupMenus(R.id.nav_comics);

        ivPortada = findViewById(R.id.iv_detalle_portada);
        tvTitulo = findViewById(R.id.tv_detalle_titulo);
        tvDescripcion = findViewById(R.id.tv_detalle_descripcion);

        SharedPreferences sharedPreferences = getSharedPreferences("comicPrefs", MODE_PRIVATE);
        String activityContext = sharedPreferences.getString("activity","none");
        if(activityContext.equals("AnadirComicActivity")) {
            String titulo = sharedPreferences.getString("titulo", "Añadir Título");
            String descripcion = sharedPreferences.getString("descripcion", "Añadir Descripción");
            tvTitulo.setText(titulo);
            tvDescripcion.setText(descripcion);
        } else {
            Comic comic = (Comic) getIntent().getSerializableExtra("comic");
            if (comic != null) {
                ivPortada.setImageResource(comic.getIdImagen());
                tvTitulo.setText(comic.getTitulo());
                tvDescripcion.setText(comic.getAutores());
            }
        }








    }
}
