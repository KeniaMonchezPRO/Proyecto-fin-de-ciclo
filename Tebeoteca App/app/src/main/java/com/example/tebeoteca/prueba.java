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
    private TextInputLayout textInputLayoutAutores;
    private AutoCompleteTextView autoCompleteTextViewAutores;
    private ChipGroup chipGroupAutores;


    // Conjunto para almacenar los autores seleccionados (usamos Set para evitar duplicados)
    private Set<String> selectedAutores = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba); // Asegúrate de que este sea tu layout

        textInputLayoutAutores = findViewById(R.id.textInputLayoutAutores);
        autoCompleteTextViewAutores = findViewById(R.id.autoCompleteTextViewAutores);
        chipGroupAutores = findViewById(R.id.chipGroupAutores);

        // Manejar el evento de "Done" o "Enter" en el teclado para añadir autores
        autoCompleteTextViewAutores.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                String typedAuthor = autoCompleteTextViewAutores.getText().toString().trim();
                if (!typedAuthor.isEmpty()) {
                    addAuthorChip(typedAuthor);
                    autoCompleteTextViewAutores.setText("");
                    return true; // Consumir el evento
                }
            }
            return false;
        });
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
    public List<String> getSelectedAuthorsList() {
        return new ArrayList<>(selectedAutores);
    }
}
