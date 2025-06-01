package com.example.tebeoteca.cliente.comic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

public class ComicActivity extends BaseActivity {
    int verde, azul, rojo, naranja;
    private ImageView ivPortada;
    private TextView tvTitulo, tvDescripcion, tvSelloEditorial, tvAudiencia, tvFechaLanzamiento, tvEstado, tvAutores, tvPaisOrigen, tvIdiomaOriginal, tvCategorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comic);
        setupMenus(R.id.nav_comics);

        ivPortada = findViewById(R.id.iv_detalle_portada);
        tvTitulo = findViewById(R.id.tv_detalle_titulo);
        tvSelloEditorial = findViewById(R.id.tv_detalle_selloEditorial);
        tvAudiencia = findViewById(R.id.tv_detalle_audiencia);
        tvFechaLanzamiento = findViewById(R.id.tv_detalle_fechaLanzamiento);
        tvEstado = findViewById(R.id.tv_detalle_estado);
        tvAutores = findViewById(R.id.tv_detalle_autores);
        tvPaisOrigen = findViewById(R.id.tv_detalle_paisOrigen);
        tvIdiomaOriginal = findViewById(R.id.tv_detalle_idiomaOriginal);
        tvCategorias = findViewById(R.id.tv_detalle_categorias);
        tvDescripcion = findViewById(R.id.tv_detalle_descripcion);

        verde = ContextCompat.getColor(this,android.R.color.holo_green_light);
        azul = ContextCompat.getColor(this,android.R.color.holo_blue_light);
        rojo = ContextCompat.getColor(this,android.R.color.holo_red_light);
        naranja = ContextCompat.getColor(this,android.R.color.holo_orange_light);

        SharedPreferences sharedPreferences = getSharedPreferences("comicPrefs", MODE_PRIVATE);
        String activityContext = sharedPreferences.getString("activity","none");

        //Log.d("DEBUG", "activityContext: " + activityContext);
        if(activityContext.equals("AnadirComicActivity")) {
            Log.d("DEBUG", "entró en el if");
            String portada = sharedPreferences.getString("portada","null");
            int idImagen = 0;
            if(portada != null) {
                idImagen = this.getResources().getIdentifier(
                        portada, "drawable", this.getPackageName()
                );
            }
            if (idImagen != 0) {
                ivPortada.setImageResource(idImagen);
            } else {
                ivPortada.setImageResource(R.drawable.sin_foto);
            }
            String titulo = sharedPreferences.getString("titulo", "Añadir Título");
            tvTitulo.setText(titulo);
            String descripcion = sharedPreferences.getString("descripcion", "Añadir Descripción");
            tvDescripcion.setText(descripcion);
            String audiencia = sharedPreferences.getString("audiencia","Añadir Audiencia");
            if(audiencia.contains("niños")) {
                audiencia = "Niños (3-12)";
            } else if (audiencia.contains("jovenes")) {
                audiencia = "Jóvenes (13-17)";
            } else if (audiencia.contains("adultos")) {
                audiencia = "Adultos (18+)";
            } else {
                audiencia = "Añadir audiencia";
            }
            tvAudiencia.setText(audiencia);
            String selloEditorial = sharedPreferences.getString("selloEditorial", "Añadir Sello Editorial");
            tvSelloEditorial.setText(selloEditorial);
            String fechaLanzamiento = sharedPreferences.getString("fechaLanzamiento", "Añadir Fecha de Lanzamiento");
            tvFechaLanzamiento.setText(fechaLanzamiento);

            String estado = sharedPreferences.getString("estado","Añadir Estado");
            tvEstado.setText(estado);
            if(estado.equals("activo")) {
                tvEstado.setTextColor(verde);
            } else if(estado.equals("publicado")) {
                tvEstado.setTextColor(azul);
            } else if(estado.equals("terminado")) {
                tvEstado.setTextColor(naranja);
            } else if(estado.equals("descontinuado")) {
                tvEstado.setTextColor(rojo);
            }

            String autores = sharedPreferences.getString("autores", "Añadir Autores");
            tvAutores.setText(autores);
            String paisOrigen = sharedPreferences.getString("paisOrigen","Añadir País de Origen");
            tvPaisOrigen.setText(paisOrigen);
            String idiomaOriginal = sharedPreferences.getString("idiomaOriginal", "Añadir Idioma Original");
            tvIdiomaOriginal.setText(idiomaOriginal);
            String categorias = sharedPreferences.getString("categorias","Añadir Categorías");
            tvCategorias.setText(categorias);

        } else if (activityContext.equals("ComicsActivity") || activityContext.equals(("PerfilClienteActivity"))) {
            Comic comic = (Comic) getIntent().getSerializableExtra("comic");
            //Log.d("DEBUG", "Cómic recibido else: " + comic.getTitulo());
            if (comic != null) {
                int idImagen = 0;
                String nombrePortada = comic.getPortada();
                if(nombrePortada != null) {
                    idImagen = this.getResources().getIdentifier(
                            nombrePortada, "drawable", this.getPackageName()
                    );
                }

                if (idImagen != 0) {
                    ivPortada.setImageResource(idImagen);
                } else {
                    ivPortada.setImageResource(R.drawable.sin_foto);
                }

                int idResArrayItems = R.array.spìnner_audiencia_array;
                String[] audienciaArray = getResources().getStringArray(idResArrayItems);

                String audiencia = comic.getAudiencia();
                if(audiencia != null) {
                    if(audiencia.contains("niños")) {
                        audiencia = audienciaArray[0];
                    } else if (audiencia.contains("jovenes")) {
                        audiencia = audienciaArray[1];
                    } else if (audiencia.contains("adultos")) {
                        audiencia = audienciaArray[2];
                    } else {
                        audiencia = "Añadir audiencia";
                    }
                } else {
                    audiencia = "Añadir audiencia";
                }
                tvAudiencia.setText(audiencia);

                tvSelloEditorial.setText(comic.getSelloEditorial());
                tvFechaLanzamiento.setText(comic.getFechaLanzamiento());
                tvTitulo.setText(comic.getTitulo());
                tvDescripcion.setText(comic.getDescripcion());

                String estado = comic.getEstado();
                if(estado != null) {
                    String estadoUp = estado.toUpperCase();
                    if(estadoUp.equals("activo")) {
                        tvEstado.setTextColor(verde);
                    } else if(estadoUp.equals("publicado")) {
                        tvEstado.setTextColor(azul);
                    } else if(estadoUp.equals("terminado")) {
                        tvEstado.setTextColor(naranja);
                    } else if(estadoUp.equals("descontinuado")) {
                        tvEstado.setTextColor(rojo);
                    }
                    tvEstado.setText(estado);
                } else {
                    tvEstado.setText("Añadir Estado");
                }

                tvAutores.setText(comic.getAutores());
                tvPaisOrigen.setText(comic.getPais_origen());
                tvIdiomaOriginal.setText(comic.getIdiomaOriginal());
                tvCategorias.setText(comic.getCategorias());
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
}
