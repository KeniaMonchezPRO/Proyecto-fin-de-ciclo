package com.example.tebeoteca.cliente.comic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.cliente.DialogNuevaSeccionActivity;
import com.example.tebeoteca.lector.DialogMetodoPagoActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicActivity extends BaseActivity {
    int verde, azul, rojo, naranja;
    private ImageView ivPortada;
    private TextView tvTitulo, tvDescripcion, tvSelloEditorial, tvAudiencia, tvFechaLanzamiento, tvEstado, tvAutores, tvPaisOrigen, tvIdiomaOriginal, tvCategorias;
    private Button btnEliminar, btnEditar, btnPreviewComic;
    private ImageButton btnFav, btnDel;
    private LinearLayout lyFichaComicContenedor, lyBotonesLector;
    int idUsuario;
    int idComic;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comic);

        SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        String perfil = perfilPrefs.getString("perfil", "cliente");
        setupMenus(R.id.nav_comics, perfil);

        conexionApi();

        SharedPreferences sharedPreferences = getSharedPreferences("comicPrefs", MODE_PRIVATE);
        String activityContext = sharedPreferences.getString("activity","none");

        SharedPreferences usuarioPrefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        idUsuario = usuarioPrefs.getInt("idUsuario",2);


        lyFichaComicContenedor = findViewById(R.id.ly_fichaComicContainer);
        lyBotonesLector = findViewById(R.id.ly_botonesLector);
        btnEliminar = findViewById(R.id.btn_eliminarComic);
        btnEditar = findViewById(R.id.btn_editarComic);
        btnPreviewComic = findViewById(R.id.btn_previewComic);
        btnFav = findViewById(R.id.btn_favorito);
        btnDel = findViewById(R.id.btn_eliminarFav);

        if(perfil.equals("lector")) {
            lyBotonesLector.setVisibility(View.VISIBLE);
            btnPreviewComic.setVisibility(View.VISIBLE);
            btnEliminar.setText("Comprar");
            btnEditar.setText("Alquilar");
            btnEliminar.setOnClickListener(v -> startDialogMetodoPagoActivity());
            btnEditar.setOnClickListener(v -> startDialogMetodoPagoActivity());
        } else {
            lyFichaComicContenedor.setPadding(12,20,12,20);
        }

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

            //Log.d("DEBUG", "activityContext: " + activityContext);
            if(activityContext.equals("NuevoComicActivity")) {
                //Log.d("DEBUG", "entró en el if");
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

                String titulo = sharedPreferences.getString("titulo", "Añadir título");
                tvTitulo.setText(titulo);

                String descripcion = sharedPreferences.getString("descripcion", "Añadir descripción");
                tvDescripcion.setText(descripcion);

                String audiencia = sharedPreferences.getString("audiencia","Añadir audiencia");
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

                String selloEditorial = sharedPreferences.getString("selloEditorial", "Añadir sello editorial");
                tvSelloEditorial.setText(selloEditorial);

                String fechaLanzamiento = sharedPreferences.getString("fechaLanzamiento", "Añadir fecha de lanzamiento");
                tvFechaLanzamiento.setText(fechaLanzamiento);

                String estado = sharedPreferences.getString("estado","Añadir estado");
                if(estado.equals("Añadir estado")) {
                    tvEstado.setText(estado);
                } else {
                    tvEstado.setText(estado.toUpperCase());
                    if(estado.equals("activo")) {
                        tvEstado.setTextColor(verde);
                    } else if(estado.equals("publicado")) {
                        tvEstado.setTextColor(azul);
                    } else if(estado.equals("terminado")) {
                        tvEstado.setTextColor(naranja);
                    } else if(estado.equals("descontinuado")) {
                        tvEstado.setTextColor(rojo);
                    }
                }

                String autores = sharedPreferences.getString("autores", "Añadir autores");
                tvAutores.setText(autores);

                String paisOrigen = sharedPreferences.getString("paisOrigen","Añadir país de origen");
                tvPaisOrigen.setText(paisOrigen);

                String idiomaOriginal = sharedPreferences.getString("idiomaOriginal", "Añadir idioma original");
                tvIdiomaOriginal.setText(idiomaOriginal);

                String categorias = sharedPreferences.getString("categorias","Añadir categorías");
                tvCategorias.setText(categorias);

            } else if (activityContext.equals("ComicsActivity") || activityContext.equals(("PerfilClienteActivity")) || perfil.equals("lector")) {
                Comic comic = (Comic) getIntent().getSerializableExtra("comic");
                //Log.d("DEBUG", "Cómic recibido else: " + comic.getTitulo());
                if (comic != null) {
                    idComic = comic.getId();
                    Log.d("DEBUG", "Cómic recibido else: id: " + idComic + " titulo " + comic.getTitulo());
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

                    String sello = comic.getSelloEditorial();
                    if(sello != null) {
                        tvSelloEditorial.setText(sello);
                    } else {
                        tvSelloEditorial.setText("Añadir sello editorial");
                    }

                    String fechaLanz = comic.getFechaLanzamiento();
                    if(fechaLanz != null) {
                        tvFechaLanzamiento.setText(fechaLanz);
                    } else {
                        tvFechaLanzamiento.setText("Añadir fecha lanzamiento");
                    }

                    String titulo = comic.getTitulo();
                    if(titulo != null) {
                        tvTitulo.setText(titulo);
                    } else {
                        tvTitulo.setText("Añadir título");
                    }

                    String descripcion = comic.getDescripcion();
                    if(descripcion != null) {
                        tvDescripcion.setText(descripcion);
                    } else {
                        tvDescripcion.setText("Añadir descripción");
                    }

                    String estado = comic.getEstado();
                    if(estado != null) {
                        String estadoUp = estado.toUpperCase();
                        if(estadoUp.equals("ACTIVO")) {
                            tvEstado.setTextColor(verde);
                        } else if(estadoUp.equals("PUBLICADO")) {
                            tvEstado.setTextColor(azul);
                        } else if(estadoUp.equals("TERMINADO")) {
                            tvEstado.setTextColor(naranja);
                        } else if(estadoUp.equals("DESCONTINUADO")) {
                            tvEstado.setTextColor(rojo);
                        }
                        tvEstado.setText(estadoUp);
                    } else {
                        tvEstado.setText("Añadir Estado");
                    }

                    String autores = comic.getAutores();
                    if(autores != null) {
                        tvAutores.setText(autores);
                    } else {
                        tvAutores.setText("Añadir autores");
                    }

                    String paisOrigen = comic.getPais_origen();
                    if(paisOrigen != null) {
                        tvPaisOrigen.setText(paisOrigen);
                    } else {
                        tvPaisOrigen.setText("Añadir país de origen");
                    }

                    String idiomaOriginal = comic.getIdiomaOriginal();
                    if(idiomaOriginal != null) {
                        tvIdiomaOriginal.setText(idiomaOriginal);
                    } else {
                        tvIdiomaOriginal.setText("Añadir idioma original");
                    }

                    String categorias = comic.getCategorias();
                    if(categorias != null) {
                        tvCategorias.setText(categorias);
                    } else {
                        tvCategorias.setText("Añadir categorías");
                    }

                    btnFav.setOnClickListener(v -> {
                        agregarFavorito(idUsuario, idComic);
                        this.finish();
                    });

                    btnDel.setOnClickListener(v -> {
                        Log.d("BOTON ELIMINAR FAV", "ID: " + idUsuario + " comic " + idComic);
                        eliminarFavorito(idUsuario, idComic);
                        this.finish();
                    });
                } else {
                    Toast.makeText(this, "No se pudo cargar el comic", Toast.LENGTH_SHORT).show();
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

    public void agregarFavorito(int idLector, int idComic) {
        Call<ResponseBody> call = apiService.agregarFavorito(idLector,idComic);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Comic agregado a Favoritos!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al agregar el comic a la lista de favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void eliminarFavorito(int idLector, int idComic) {
        Call<ResponseBody> call = apiService.eliminarFavorito(idLector,idComic);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Comic eliminado de Favoritos!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al eliminar el comic a la lista de favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void conexionApi() {
        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void startDialogMetodoPagoActivity() {
        Intent intent = new Intent(this, DialogMetodoPagoActivity.class);
        startActivity(intent);
    }
}
