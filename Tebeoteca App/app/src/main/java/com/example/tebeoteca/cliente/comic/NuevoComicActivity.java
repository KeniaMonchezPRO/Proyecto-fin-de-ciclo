package com.example.tebeoteca.cliente.comic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

import androidx.core.content.ContextCompat;

import com.example.tebeoteca.api.ApiService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NuevoComicActivity extends BaseActivity {
    private ApiService apiService;
    private Button btnAnadirComic;
    private ImageView ivPortada;
    private int idCliente;
    TextInputEditText fechaLanzamiento, etTitulo, etSello, etDescripcion;
    int idResLayout, idResSpinner, idResArrayItems;
    private TextInputLayout textInputLayoutAutores, textInputLayoutCategorias;
    private AutoCompleteTextView autoCompleteTextViewAutores, autoCompleteTextViewCategorias, etAudiencia, etIdiomaOriginal, etPaisOrigen;
    private ChipGroup chipGroupAutores, chipGroupCategorias;
    // Conjunto para almacenar los autores seleccionados (usamos Set para evitar duplicados)
    private Set<String> selectedAutores = new HashSet<>(), selectedCategorias = new HashSet<>();
    private RadioButton rbEstadoPublicado;
    private RadioGroup rgEstado;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_anadir_comic);
        setupMenus(R.id.nav_comics, "cliente");

        //obtener info de cliente:
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        idCliente = sharedPreferences.getInt("idUsuario",1);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        //por defecto checkear estado a publicado:
        rgEstado = findViewById(R.id.rg_EstadoComic);
        rbEstadoPublicado = findViewById(R.id.r_publicado);
        rgEstado.check(rbEstadoPublicado.getId());

        //spinner audiencia
        idResLayout = R.id.spinner_audiencia_layout;
        idResSpinner = R.id.spinner_audiencia;
        idResArrayItems = R.array.spìnner_audiencia_array;
        cargarSpinner(idResLayout, idResSpinner, idResArrayItems);

        //spinner idiomaOriginal
        idResLayout = R.id.spinner_idiomaOriginal_layout;
        idResSpinner = R.id.spinner_idiomaOriginal;
        idResArrayItems = R.array.spinner_idiomaOriginal_array;
        cargarSpinner(idResLayout, idResSpinner, idResArrayItems);

        //spinner pais de origen
        idResLayout = R.id.spinner_paisOrigen_layout;
        idResSpinner = R.id.spinner_paisOrigen;
        idResArrayItems = R.array.spinner_paisOrigen_array;
        cargarSpinner(idResLayout, idResSpinner, idResArrayItems);

        //fecha de lanzamiento:
        fechaLanzamiento = findViewById(R.id.et_fechaLanzamiento);
        fechaLanzamiento.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                mostrarDatePicker(fechaLanzamiento);
            }
        });

        //Lógica Autores
        textInputLayoutAutores = findViewById(R.id.textInputLayoutAutores);
        autoCompleteTextViewAutores = findViewById(R.id.autoCompleteTextViewAutores);
        chipGroupAutores = findViewById(R.id.chipGroupAutores);
        manejarEventoTeclado(autoCompleteTextViewAutores, "autores");

        //Lógica categorias:
        textInputLayoutCategorias = findViewById(R.id.textInputLayoutCategorias);
        autoCompleteTextViewCategorias = findViewById(R.id.autoCompleteTextViewCategorias);
        chipGroupCategorias = findViewById(R.id.chipGroupCategorias);
        manejarEventoTeclado(autoCompleteTextViewCategorias, "categorias");

        //Inicializando el resto de campos:
        ivPortada = findViewById(R.id.iv_portada);
        etTitulo = findViewById(R.id.et_titulo);
        etSello = findViewById(R.id.et_selloEditorial);
        etDescripcion = findViewById(R.id.et_descripcion);
        etAudiencia = findViewById(R.id.spinner_audiencia);
        etPaisOrigen = findViewById(R.id.spinner_paisOrigen);
        etIdiomaOriginal = findViewById(R.id.spinner_idiomaOriginal);

        //Añadir comic:
        btnAnadirComic = findViewById(R.id.btn_anadirComic);
        btnAnadirComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirNuevoComic();
            }
        });
    }

    /*@Override
    protected void onResume() {
        setupMenus(R.id.nav_comics);
        super.onResume();
        //ImageButton btnAtras = findViewById(R.id.btn_atras);
        //if (btnAtras != null) {
        //    btnAtras.setVisibility(View.GONE);
        //}
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
    }*/

    private void manejarEventoTeclado(AutoCompleteTextView autoCompleteTextView, String campo) {
        if(campo.equals("autores")) {
            autoCompleteTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    String typedAuthor = autoCompleteTextView.getText().toString().trim();
                    if (!typedAuthor.isEmpty()) {
                        addAuthorChip(typedAuthor);
                        autoCompleteTextView.setText("");
                        return true; // Consumir el evento
                    }
                }
                return false;
            });
        } else if (campo.equals("categorias")) {
            // 1. Cargar las categorías del array.xml
            String[] categoriasArray = getResources().getStringArray(R.array.spinner_categorias_array);
            List<String> allAvailableCategorias = new ArrayList<>(Arrays.asList(categoriasArray));
            // 2. Configurar el ArrayAdapter para el AutoCompleteTextView (el "Spinner")
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.dropdown_item_layout, // Layout para los items del dropdown
                    allAvailableCategorias
            );
            autoCompleteTextViewCategorias.setAdapter(adapter);
            // 3. Manejar la selección de categorías
            autoCompleteTextViewCategorias.setOnItemClickListener((parent, view, position, id) -> {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                addCategoryChip(selectedCategory);
                // Opcional: Limpiar el texto si quieres que el "hint" vuelva a aparecer
                autoCompleteTextViewCategorias.setText("", false); // 'false' para no filtrar al limpiar
            });

            // Opcional: Si quieres que el usuario no pueda escribir nada en el campo del spinner
            // Aunque inputType="none" y focusable="false" ya deberían evitarlo,
            // esto es una seguridad extra si ves que el teclado aparece.
            autoCompleteTextViewCategorias.setKeyListener(null);

            // Manejar el evento de "Done" o "Enter" en el teclado para añadir autores
            autoCompleteTextViewCategorias.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    String typedCategory = autoCompleteTextViewCategorias.getText().toString().trim();
                    if (!typedCategory.isEmpty()) {
                        addCategoryChip(typedCategory);
                        autoCompleteTextViewCategorias.setText("");
                        return true; // Consumir el evento
                    }
                }
                return false;
            });
        }
    }
    private void startAgregarDetallesComicActivity() {

    }
    private void anadirNuevoComic() {
        NuevoComicRequest nuevoComicRequest = new NuevoComicRequest();

        nuevoComicRequest.setClienteId(idCliente);

        String titulo = etTitulo.getText().toString().trim();
        nuevoComicRequest.setTitulo(titulo);

        String sello = etSello.getText().toString().trim();
        nuevoComicRequest.setSelloEditorial(sello);

        String fechaLanz = fechaLanzamiento.getText().toString().trim();
        nuevoComicRequest.setFechaLanzamiento(fechaLanz);

        String audiencia = etAudiencia.getText().toString().trim();
        if(audiencia.contains("12")) {
            audiencia = "niños";
        } else if (audiencia.contains("13")) {
            audiencia = "jovenes";
        } else if (audiencia.contains("18")) {
            audiencia = "adultos";
        } else {
            audiencia = "";
        }
        nuevoComicRequest.setAudiencia(audiencia);

        int selectedRadioId = rgEstado.getCheckedRadioButtonId();
        RadioButton selectedOption = findViewById(selectedRadioId);
        String estado = selectedOption.getText().toString().trim().toLowerCase();
        nuevoComicRequest.setEstado(estado);

        String autores = getSelectedAuthorsList().toString();
        nuevoComicRequest.setAutores(autores);

        String descripcion = etDescripcion.getText().toString().trim();
        nuevoComicRequest.setDescripcion(descripcion);

        String paisOrigen = etPaisOrigen.getText().toString().trim();
        nuevoComicRequest.setPaisOrigen(paisOrigen);

        String idiomaOriginal = etIdiomaOriginal.getText().toString().trim();
        nuevoComicRequest.setIdiomaOriginal(idiomaOriginal);

        String categorias = getSelectedCategoriesList().toString();
        nuevoComicRequest.setCategorias(categorias);
        //Log.d("COMIC_DEBUG", "Comic: " + nuevoComicRequest);

        Call<NuevoComicResponseDTO> call = apiService.crearComic(nuevoComicRequest);
        call.enqueue(new Callback<NuevoComicResponseDTO>() {
            @Override
            public void onResponse(Call<NuevoComicResponseDTO> call, Response<NuevoComicResponseDTO> response) {
                if (response.isSuccessful()) {
                    //Log.d("LOGIN_DEBUG", "response is successful");
                    NuevoComicResponseDTO comicData = response.body();

                    SharedPreferences prefs = getSharedPreferences("comicPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("portada", comicData.getPortada());
                    editor.putString("titulo", comicData.getTitulo());
                    editor.putString("audiencia", comicData.getAudiencia());
                    editor.putString("descripcion", comicData.getDescripcion());
                    editor.putString("selloEditorial", comicData.getSelloEditorial());
                    editor.putString("fechaLanzamiento", comicData.getFechaLanzamiento());
                    editor.putString("estado", comicData.getEstado());
                    editor.putString("autores", comicData.getAutores());
                    editor.putString("paisOrigen", comicData.getPaisOrigen());
                    editor.putString("idiomaOriginal", comicData.getIdiomaOriginal());
                    editor.putString("categorias", comicData.getCategorias());
                    editor.putString("activity", "NuevoComicActivity");
                    editor.apply();

                    startActivity(new Intent(NuevoComicActivity.this, ComicActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al crear comic", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NuevoComicResponseDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void cargarSpinner(int idLayout, int idSpinner, int idArrayFile) {
        TextInputLayout spinnerLayout = findViewById(idLayout);
        AutoCompleteTextView spinner = (AutoCompleteTextView) spinnerLayout.findViewById(idSpinner);

        String[] arrayItems = getResources().getStringArray(idArrayFile);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item_layout, arrayItems);
        spinner.setAdapter(adapter);
        /*spinner.setOnItemClickListener((parent, view, position, id) -> {
            itemSeleccionado = (String) parent.getItemAtPosition(position);
            //Toast.makeText(this, "Seleccionado: " + itemSeleccionado, Toast.LENGTH_SHORT).show();
        });
        String itemSeleccionado = spinner.getText().toString();*/
    }

    private void mostrarDatePicker(TextInputEditText inputEditText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                NuevoComicActivity.this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    inputEditText.setText(fecha);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    // Método para añadir un chip de autor al ChipGroup
    private void addAuthorChip(String authorName) {
        // Evitar duplicados
        if (selectedAutores.contains(authorName)) {
            Toast.makeText(this, authorName + " ya ha sido añadido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo Chip
        Chip chip = new Chip(this);
        chip.setText(authorName);
        chip.setCloseIconVisible(true); // Permite que el usuario elimine el chip
        chip.setCheckable(false); // No se comportará como un checkbox
        chip.setClickable(false); // No se puede hacer clic para seleccionar/deseleccionar

        ColorStateList strokeColorList = ContextCompat.getColorStateList(this, R.color.chip_stroke_color);
        chip.setChipBackgroundColor(strokeColorList);
        chip.setChipStrokeColor(strokeColorList);

        // Añadir el listener para cuando se cierre el chip
        chip.setOnCloseIconClickListener(v -> {
            chipGroupAutores.removeView(chip); // Elimina el chip de la vista
            selectedAutores.remove(authorName); // Elimina el autor del conjunto de seleccionados
            Toast.makeText(this, authorName + " eliminado.", Toast.LENGTH_SHORT).show();
        });

        // Añadir el chip al ChipGroup
        chipGroupAutores.addView(chip);
        selectedAutores.add(authorName); // Añadir el autor al conjunto de seleccionados
    }

    // Método para obtener la lista final de autores seleccionados
    public String getSelectedAuthorsList() {
        List<String> listaAutores = new ArrayList<>(selectedAutores);
        return String.join(", ", listaAutores);
    }

    // Método para añadir un chip de autor al ChipGroup
    private void addCategoryChip(String categoryName) {
        // Evitar duplicados
        if (selectedCategorias.contains(categoryName)) {
            Toast.makeText(this, categoryName + " ya ha sido añadido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo Chip
        Chip chip = new Chip(this);
        chip.setText(categoryName);
        chip.setCloseIconVisible(true); // Permite que el usuario elimine el chip
        chip.setCheckable(false); // No se comportará como un checkbox
        chip.setClickable(false); // No se puede hacer clic para seleccionar/deseleccionar

        ColorStateList strokeColorList = ContextCompat.getColorStateList(this, R.color.chip_stroke_color);
        chip.setChipBackgroundColor(strokeColorList);
        chip.setChipStrokeColor(strokeColorList);

        // Añadir el listener para cuando se cierre el chip
        chip.setOnCloseIconClickListener(v -> {
            chipGroupCategorias.removeView(chip); // Elimina el chip de la vista
            selectedCategorias.remove(categoryName); // Elimina el autor del conjunto de seleccionados
            Toast.makeText(this, categoryName + " eliminado.", Toast.LENGTH_SHORT).show();
        });

        // Añadir el chip al ChipGroup
        chipGroupCategorias.addView(chip);
        selectedCategorias.add(categoryName); // Añadir el autor al conjunto de seleccionados
    }

    // Método para obtener la lista final de autores seleccionados
    public String getSelectedCategoriesList() {
        List<String> listaCategorias = new ArrayList<>(selectedCategorias);
        return String.join(", ", listaCategorias);
    }
}
