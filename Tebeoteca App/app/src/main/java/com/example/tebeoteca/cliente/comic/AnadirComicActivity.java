package com.example.tebeoteca.cliente.comic;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.tebeoteca.registro.RegistroLectorActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AnadirComicActivity extends BaseActivity {

    Calendar calendar = Calendar.getInstance();
    TextInputEditText fechaLanzamiento;
    TextInputEditText fechaPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_anadir_comic);
        setupMenus(R.id.nav_comics);

        //spinner idiomaOriginal
        TextInputLayout spinnerLayoutIdiomaOriginal = findViewById(R.id.spinner_idiomaOriginal_layout);
        AutoCompleteTextView spinnerIdiomaOriginal = (AutoCompleteTextView) spinnerLayoutIdiomaOriginal.findViewById(R.id.spinner_idiomaOriginal);

        String[] idiomasOriginal = getResources().getStringArray(R.array.spinner_idiomaOriginal_array);

        ArrayAdapter<String> idiomaOriginalAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item_layout, idiomasOriginal);
        spinnerIdiomaOriginal.setAdapter(idiomaOriginalAdapter);
        spinnerIdiomaOriginal.setOnItemClickListener((parent, view, position, id) -> {
            String idiomaOriginalSeleccionado = (String) parent.getItemAtPosition(position);
            //Toast.makeText(this, "Seleccionado: " + idiomaOriginalSeleccionado, Toast.LENGTH_SHORT).show();
        });
        String idiomaOriginalActual = spinnerIdiomaOriginal.getText().toString();

        //spinner categorias
        TextInputLayout spinnerCategoriasLayout = findViewById(R.id.spinner_categorias_layout);
        AutoCompleteTextView spinnerCategorias = (AutoCompleteTextView) spinnerCategoriasLayout.findViewById(R.id.spinner_categorias);

        String[] categorias = getResources().getStringArray(R.array.spinner_categorias_array);

        ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item_layout, categorias);
        spinnerCategorias.setAdapter(categoriasAdapter);
        spinnerCategorias.setOnItemClickListener((parent, view, position, id) -> {
            String categoriaSeleccionada = (String) parent.getItemAtPosition(position);
            //Toast.makeText(this, "Seleccionado: " + categoriaSeleccionada, Toast.LENGTH_SHORT).show();
        });
        String categoriaActual = spinnerCategorias.getText().toString();

        //fecha de lanzamiento:
        fechaLanzamiento = findViewById(R.id.et_fechaLanzamiento);
        fechaLanzamiento.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                mostrarDatePicker(fechaLanzamiento);
            }
        });

        //fecha de publicación:
        fechaPublicacion = findViewById(R.id.et_fechaPublicacion);
        fechaPublicacion.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                mostrarDatePicker(fechaPublicacion);
            }
        });
    }

    private void mostrarDatePicker(TextInputEditText inputEditText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AnadirComicActivity.this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    inputEditText.setText(fecha);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
