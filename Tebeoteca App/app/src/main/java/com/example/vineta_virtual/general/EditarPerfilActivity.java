package com.example.vineta_virtual.general;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vineta_virtual.R;
import com.example.vineta_virtual.api.ApiService;
import com.example.vineta_virtual.cliente.Cliente;
import com.example.vineta_virtual.lector.Lector;
import com.example.vineta_virtual.lector.PerfilLectorActivity;

import java.time.LocalDate;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText etNombreUsuario, etEmail, etNombreEmpresa, etNombreLector, etApellidosLector, etContrasena, etFechaNac;
    private Button btnEditarPerfil;
    private ImageButton btnCerrar;
    private String tipoUsuario;
    private Cliente cliente;
    private Lector lector;
    private ApiService apiService;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        //ambos:
        etNombreUsuario = findViewById(R.id.et_nombreUsuario);
        etContrasena = findViewById(R.id.et_contrasena);
        etEmail = findViewById(R.id.et_email);
        btnEditarPerfil = findViewById(R.id.btn_editarPerfil);
        btnCerrar = findViewById(R.id.btn_cerrar);
        btnCerrar.setOnClickListener(v -> finish());

        //campos cliente:
        //etNombreEmpresa = findViewById(R.id.et_nombreEmpresa);

        //campos lector:
        etNombreLector = findViewById(R.id.et_nombreLector);
        etApellidosLector = findViewById(R.id.et_apellidosLector);
        etFechaNac = findViewById(R.id.et_fechaNac);
        etFechaNac.setOnClickListener(view -> mostrarDatePicker());
        etFechaNac.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                mostrarDatePicker();
            }
        });

        tipoUsuario = getIntent().getStringExtra("tipoUsuario");

        if ("CLIENTE".equals(tipoUsuario)) {
            Log.d("EditarPerfAct", "Un cliente editará su perfil");
            /*cliente = (Cliente) getIntent().getSerializableExtra("usuario");
            etNombreUsuario.setText(cliente.getNombreUsuario());
            etEmail.setText(cliente.getEmail());
            etNombreEmpresa.setText(cliente.getNombreCliente());

            //etNombre.setVisibility(View.GONE);

            btnGuardar.setOnClickListener(v -> actualizarCliente());*/
        } else {
            Log.d("EditarPerfAct", "Un lector editará su perfil");
            lector = (Lector) getIntent().getSerializableExtra("usuario");
            etNombreUsuario.setText(lector.getNombreUsuario());
            etEmail.setText(lector.getEmail());
            etContrasena.setText(lector.getContrasena());
            etNombreLector.setText(lector.getNombreLector());
            etApellidosLector.setText(lector.getApellidosLector());
            etFechaNac.setText(lector.getFechaNac());
            //etNombreEmpresa.setVisibility(View.GONE);
            Log.d("EditarPerfAct", "lector actual: " + lector);
            btnEditarPerfil.setOnClickListener(v -> actualizarLector(lector));
        }
    }

    private void actualizarCliente() {

    }

    private void mostrarDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                EditarPerfilActivity.this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    int diffAnos = 0;
                    int anoActual = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        anoActual = LocalDate.now().getYear();
                        diffAnos = anoActual - selectedYear;
                    }
                    if(diffAnos >= 0 && diffAnos <= 18) {
                        Toast.makeText(EditarPerfilActivity.this, "Debes ser mayor de edad para continuar", Toast.LENGTH_SHORT).show();
                        etFechaNac.setText("");
                    } else if (selectedYear >= anoActual) {
                        Toast.makeText(EditarPerfilActivity.this, "Selecciona una fecha correcta", Toast.LENGTH_SHORT).show();
                        etFechaNac.setText("");
                    } else {
                        etFechaNac.setText(fecha);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void actualizarLector(Lector lector) {
        int idLector = lector.getId();

        Lector lectorEditado = new Lector();
        lectorEditado.setNombreUsuario(etNombreUsuario.getText().toString());
        lectorEditado.setEmail(etEmail.getText().toString());
        lectorEditado.setContrasena(etContrasena.getText().toString());
        lectorEditado.setNombreLector(etNombreLector.getText().toString());
        lectorEditado.setApellidosLector(etApellidosLector.getText().toString());
        lectorEditado.setFechaNac(etFechaNac.getText().toString());

        if (lectorEditado.getNombreUsuario().isEmpty() || lectorEditado.getEmail().isEmpty() || lectorEditado.getContrasena().isEmpty() || lectorEditado.getFechaNac().isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Lector> call = apiService.editarLector(idLector, lectorEditado);
        call.enqueue(new Callback<Lector>() {
            @Override
            public void onResponse(Call<Lector> call, Response<Lector> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarPerfilActivity.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();

                    SharedPreferences activityAndTabContext = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
                    SharedPreferences.Editor editorAct = activityAndTabContext.edit();
                    editorAct.putString("activity", "EditarPerfilActivity");
                    editorAct.apply();

                    Intent intent = new Intent(EditarPerfilActivity.this, PerfilLectorActivity.class);
                    intent.putExtra("lector", lectorEditado);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(EditarPerfilActivity.this, "Error al actualizar lector", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Lector> call, Throwable t) {
                Toast.makeText(EditarPerfilActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
