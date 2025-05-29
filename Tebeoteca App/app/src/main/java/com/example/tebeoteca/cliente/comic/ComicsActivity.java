package com.example.tebeoteca.cliente.comic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

import java.util.HashMap;
import java.util.Map;

public class ComicsActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comics);
        setupMenus(R.id.nav_comics);
        Comic nuevo1 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Misterio", R.drawable.flash);
        Comic nuevo2 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Superheroes", R.drawable.flash);
        Comic nuevo3 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Superheroes", R.drawable.flash);
        Comic nuevo4 = new Comic("Flashpointttt", "Neil Gaiman, Stan Lee", "Superheroes", R.drawable.flash);
        ComicRepository.agregarComic(nuevo1);
        ComicRepository.agregarComic(nuevo2);
        ComicRepository.agregarComic(nuevo3);
        ComicRepository.agregarComic(nuevo4);

        // Simulación de datos
        Map<String, LinearLayout> contenedoresPorCategoria = new HashMap<>();
        contenedoresPorCategoria.put("Misterio", findViewById(R.id.misterio_container));
        contenedoresPorCategoria.put("Superheroes", findViewById(R.id.superheroes_container));
        for (Comic comic : ComicRepository.getComics()) {
            LinearLayout contenedor = contenedoresPorCategoria.get(comic.getCategoria());
            if (contenedor != null) {
                View vistaComic = LayoutInflater.from(this).inflate(R.layout.item_comic, contenedor, false);

                TextView tvNombre = vistaComic.findViewById(R.id.tvTitulo);
                ImageView ivImagen = vistaComic.findViewById(R.id.iv_portada);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                //params.setMarginEnd(30);
                params.setMargins(0,20,40,0);
                vistaComic.setLayoutParams(params);

                tvNombre.setText(comic.getTitulo());
                ivImagen.setImageResource(comic.getIdImagen());

                contenedor.addView(vistaComic);
            }
        }
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
    public void volverAtras(View view) {
        this.finish();
    }
}
