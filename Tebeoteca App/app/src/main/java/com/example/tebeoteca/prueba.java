package com.example.tebeoteca;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.*;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class prueba extends AppCompatActivity { // Asegúrate de que herede de AppCompatActivity
    private TextInputLayout textInputLayoutCategorias;
    private AutoCompleteTextView autoCompleteTextViewCategorias;
    private ChipGroup chipGroupCategorias;


    // Conjunto para almacenar los autores seleccionados (usamos Set para evitar duplicados)
    private Set<String> selectedCategorias = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba); // Asegúrate de que este sea tu layout

        textInputLayoutCategorias = findViewById(R.id.textInputLayoutCategorias);
        autoCompleteTextViewCategorias = findViewById(R.id.autoCompleteTextViewCategorias);
        chipGroupCategorias = findViewById(R.id.chipGroupCategorias);

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
    public List<String> getSelectedCategoriesList() {
        return new ArrayList<>(selectedCategorias);
    }
}
