package com.example.vineta_virtual.cliente.comic;

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

import com.example.vineta_virtual.BaseActivity;
import com.example.vineta_virtual.R;
import com.example.vineta_virtual.api.ApiService;
import com.example.vineta_virtual.cliente.PerfilClienteActivity;
import com.example.vineta_virtual.lector.DialogMetodoPagoActivity;
import com.example.vineta_virtual.lector.SeccionesLectorActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicActivity extends BaseActivity {
    int verde, azul, rojo, naranja;
    private ImageView ivPortada;
    private TextView tvTitulo, tvDescripcion, tvSelloEditorial, tvAudiencia, tvFechaLanzamiento, tvEstado, tvAutores, tvPaisOrigen, tvIdiomaOriginal, tvCategorias, tvPrecioCompra, tvPrecioAlquiler;
    private Button btnEliminar, btnEditar, btnLeerComic, btnPreviewComic;
    private ImageButton btnFav, btnDel;
    private LinearLayout lyFichaComicContenedor, lyBotonesLector;
    int idUsuario;
    int idComic;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_comic);
        Log.d("ComicActivity","onCreate()");

        SharedPreferences perfilPrefs = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        String perfil = perfilPrefs.getString("perfil", "cliente");
        Log.d("ComicActivity", "Entró como: " + perfil);
        conexionApi();

        //para enviar a ConfiguracionActivity:
        SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
        SharedPreferences.Editor editorAct = activityAndTabContext.edit();
        editorAct.putString("activity", "ComicActivity");
        editorAct.putString("tab", "comics");
        Log.d("ComicActivity", "perfil: " + perfil);
        if(perfil.equals("lector")) {
            editorAct.putBoolean("esLector",true);
        } else {
            editorAct.putBoolean("esLector",false);
        }
        editorAct.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("comicPrefs", MODE_PRIVATE);
        String activityContext = sharedPreferences.getString("activity","ComicsActivity");
        Log.d("ComicActivity","perfil: " + perfil + ", Activity: " + activityContext);

        if((activityContext.equals("BusquedaActivity") || activityContext.equals("PerfilClienteActivity")) && !perfil.equals("lector")) {
            setupMenus(R.id.nav_buscar, "lector");
        } else {
            setupMenus(R.id.nav_comics, perfil);
        }

        SharedPreferences usuarioPrefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        idUsuario = usuarioPrefs.getInt("idUsuario",2);


        lyFichaComicContenedor = findViewById(R.id.ly_fichaComicContainer);
        lyBotonesLector = findViewById(R.id.ly_botonesLector);
        btnEliminar = findViewById(R.id.btn_eliminarComic);
        btnEditar = findViewById(R.id.btn_editarComic);
        btnLeerComic = findViewById(R.id.btn_leerComic);
        btnPreviewComic = findViewById(R.id.btn_previewComic);
        btnFav = findViewById(R.id.btn_favorito);
        btnDel = findViewById(R.id.btn_eliminarFav);

        if(perfil.equals("lector")) {
            lyBotonesLector.setVisibility(View.VISIBLE);
            btnEliminar.setText("Alquilar");
            btnEditar.setText("Comprar");
            btnEditar.setOnClickListener(v -> startDialogMetodoPagoActivity());
            btnEliminar.setOnClickListener(v -> startDialogMetodoPagoActivity());
        } else {
            lyFichaComicContenedor.setPadding(12,20,12,20);
        }

        btnPreviewComic.setOnClickListener(v -> {
            Intent intent = new Intent(this, ComicViewActivity.class);
            intent.putExtra("preview", true);
            startActivity(intent);
        });

        btnLeerComic.setOnClickListener(v -> {
            Intent intent = new Intent(this, ComicViewActivity.class);
            startActivity(intent);
        });

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
        tvPrecioCompra = findViewById(R.id.tv_detalle_precioCompra);
        tvPrecioAlquiler = findViewById(R.id.tv_detalle_precioAlquiler);


        verde = ContextCompat.getColor(this,android.R.color.holo_green_light);
        azul = ContextCompat.getColor(this,android.R.color.holo_blue_light);
        rojo = ContextCompat.getColor(this,android.R.color.holo_red_light);
        naranja = ContextCompat.getColor(this,android.R.color.holo_orange_light);

        Log.d("ComicActivity", "activityContext: " + activityContext);
            if(activityContext.equals("NuevoComicActivity")) {
                Log.d("ComicActivity", "entró desde Añadir Comic");

                int comicId = sharedPreferences.getInt("idComic", 1);
                btnEliminar.setOnClickListener(v -> eliminarComic(comicId));

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

                String titulo = sharedPreferences.getString("titulo", "Título");
                tvTitulo.setText(titulo);

                String descripcion = sharedPreferences.getString("descripcion", "");
                tvDescripcion.setText(descripcion);

                String audiencia = sharedPreferences.getString("audiencia","Audiencia");
                if(audiencia.contains("niños")) {
                    audiencia = "Niños (3-12)";
                } else if (audiencia.contains("jovenes")) {
                    audiencia = "Jóvenes (13-17)";
                } else if (audiencia.contains("adultos")) {
                    audiencia = "Adultos (18+)";
                } else {
                    audiencia = "Audiencia";
                }
                tvAudiencia.setText(audiencia);

                String selloEditorial = sharedPreferences.getString("selloEditorial", "Sello editorial");
                tvSelloEditorial.setText(selloEditorial);

                String fechaLanzamiento = sharedPreferences.getString("fechaLanzamiento", "Fecha de lanzamiento");
                tvFechaLanzamiento.setText(fechaLanzamiento);

                String estado = sharedPreferences.getString("estado","Estado");
                if(estado.equals("Estado")) {
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

                String autores = sharedPreferences.getString("autores", "Autores");
                tvAutores.setText("Autores: " + autores);

                String paisOrigen = sharedPreferences.getString("paisOrigen","");
                Log.d("ComicAct", "paisOrigen: " + paisOrigen);
                tvPaisOrigen.setText(paisOrigen);

                String idiomaOriginal = sharedPreferences.getString("idiomaOriginal", "");
                tvIdiomaOriginal.setText(idiomaOriginal);

                String categorias = sharedPreferences.getString("categorias","");
                tvCategorias.setText(categorias);

                String precioCompra = sharedPreferences.getString("precioCompra","Precio de compra: 0.00€");
                tvPrecioCompra.setText("Precio de compra: " + precioCompra + "€");

                String precioAlquiler = sharedPreferences.getString("precioAlquiler","Precio de alquiler: 0.00€");
                tvPrecioAlquiler.setText("Precio de alquiler: " + precioAlquiler + "€");

            } else if (activityContext.equals("ColeccionActivity") || activityContext.equals("ComicsActivity") || activityContext.equals(("PerfilClienteActivity")) || perfil.equals("lector") || activityContext.equals("BusquedaActivity")) {
                Comic comic = (Comic) getIntent().getSerializableExtra("comic");
                Log.d("ComicActivity", "Entró desde ComicsActivity, PerfilClienteActivity o como lector: " + comic.getTitulo());
                Log.d("ComicActivity", "Cómic : titulo: " + comic.getTitulo() + ", paisOrigen: " + comic.getpais_origen());
                Log.d("ComicActivity", "tipo : " + perfil);
                if (comic != null) {
                    idComic = comic.getId();
                    if(activityContext.equals("ColeccionActivity")) {
                        btnLeerComic.setAlpha(1);
                        btnPreviewComic.setAlpha(0.5F);
                        btnEliminar.setAlpha(0.5F);
                        btnEditar.setAlpha(0.5F);
                    }
                    btnEliminar.setOnClickListener(v -> eliminarComic(idComic));

                    Log.d("ComicActivity", "Cómic recibido else: id: " + idComic + " titulo " + comic.getTitulo());
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
                            audiencia = "Audiencia";
                        }
                    } else {
                        audiencia = "Audiencia";
                    }
                    tvAudiencia.setText(audiencia);

                    String sello = comic.getSelloEditorial();
                    if(sello != null) {
                        tvSelloEditorial.setText(sello);
                    } else {
                        tvSelloEditorial.setText("Sello editorial");
                    }

                    String fechaLanz = comic.getFechaLanzamiento();
                    if(fechaLanz != null) {
                        tvFechaLanzamiento.setText(fechaLanz);
                    } else {
                        tvFechaLanzamiento.setText("Fecha lanzamiento");
                    }

                    String titulo = comic.getTitulo();
                    if(titulo != null) {
                        tvTitulo.setText(titulo);
                    } else {
                        tvTitulo.setText("Título");
                    }

                    String descripcion = comic.getDescripcion();
                    if(descripcion != null) {
                        tvDescripcion.setText(descripcion);
                    } else {
                        tvDescripcion.setText("");
                    }

                    String precioCompra = comic.getPrecioCompra();
                    if(precioCompra != null) {
                        tvPrecioCompra.setText("Precio de compra: "+precioCompra+"€");
                    } else {
                        tvPrecioCompra.setText("Precio de compra: 0.00€");
                    }

                    String precioAlquiler = comic.getPrecioAlquiler();
                    if(precioAlquiler != null) {
                        tvPrecioAlquiler.setText("Precio de alquiler: "+precioAlquiler+"€");
                    } else {
                        tvPrecioAlquiler.setText("Precio de alquiler: 0.00€");
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
                        tvEstado.setText("Estado");
                    }

                    String autores = comic.getAutores();
                    if(autores != null) {
                        tvAutores.setText("Autores: " + autores);
                    } else {
                        tvAutores.setText("Autores");
                    }

                    String paisOrigen = comic.getpais_origen();
                    if(paisOrigen != null) {
                        tvPaisOrigen.setText(paisOrigen);
                    } else {
                        tvPaisOrigen.setText("");
                    }

                    String idiomaOriginal = comic.getIdiomaOriginal();
                    if(idiomaOriginal != null) {
                        tvIdiomaOriginal.setText(idiomaOriginal);
                    } else {
                        tvIdiomaOriginal.setText("");
                    }

                    String categorias = comic.getCategorias();
                    if(categorias != null) {
                        tvCategorias.setText(categorias);
                    } else {
                        tvCategorias.setText("");
                    }

                    btnFav.setOnClickListener(v -> {
                        agregarFavorito(idUsuario, idComic);
                        Intent intent = new Intent(this, SeccionesLectorActivity.class);
                        startActivity(intent);
                        this.finish();
                    });

                    btnDel.setOnClickListener(v -> {
                        Log.d("ComicActivity", "BOTON ELIMINAR FAV: ID USUARIO: " + idUsuario + " comic " + idComic);
                        Intent intent = new Intent(this, SeccionesLectorActivity.class);
                        startActivity(intent);
                        eliminarFavorito(idUsuario, idComic);
                        this.finish();
                    });
                } else {
                    Toast.makeText(this, "No se pudo cargar el comic", Toast.LENGTH_SHORT).show();
                }
            }


    }
    /*@Override
    protected void onResume() {
        super.onResume();
        Log.d("ComicActivity","onResume()");
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ComicActivity","onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ComicActivity","onPause()");
        View overlay = findViewById(R.id.overlay);
        View subMenu = findViewById(R.id.submenu_layout);

        if (overlay != null) overlay.setVisibility(View.GONE);
        if (subMenu != null) subMenu.setVisibility(View.GONE);
        Log.d("ComicActivity","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ComicActivity","onStop()");
    }*/
    public void eliminarComic(int idComic) {
        Call<ResponseBody> call = apiService.eliminarComic(idComic);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Comic eliminado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ComicActivity.this, PerfilClienteActivity.class);
                startActivity(intent);
                finishAffinity();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al eliminar el comic", Toast.LENGTH_SHORT).show();
            }
        });

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
        SharedPreferences comicId = getSharedPreferences("idComic",MODE_PRIVATE);
        SharedPreferences.Editor editor = comicId.edit();
        editor.putInt("comicId",idComic);
        editor.apply();
        Log.d("ComicActivity", "Cómic guardado en sharedprefs: " + idComic);
        Intent intent = new Intent(this, DialogMetodoPagoActivity.class);
        startActivity(intent);
    }
}
