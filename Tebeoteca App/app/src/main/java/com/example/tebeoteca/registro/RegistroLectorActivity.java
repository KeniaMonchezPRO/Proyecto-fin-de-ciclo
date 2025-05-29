package com.example.tebeoteca.registro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.cliente.PerfilClienteActivity;
import com.example.tebeoteca.lector.PerfilLectorActivity;
import com.example.tebeoteca.login.LoginActivity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroLectorActivity extends ComponentActivity {
    private EditText etNombreUsuario, etEmail, etContrasena, etFechaNac;
    private ImageButton btnRegistroLector, btnAtrasLogin;
    private ApiService apiService;
    private TextView tvRegistroCliente;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_lector);

        etNombreUsuario = findViewById(R.id.et_NombreUsuario);
        etEmail = findViewById(R.id.et_Email);
        etContrasena = findViewById(R.id.et_Contrasena);
        etFechaNac = findViewById(R.id.et_FechaNac);

        btnRegistroLector = findViewById(R.id.btn_Registro_Lector);
        btnAtrasLogin = findViewById(R.id.btn_atras_login);

        tvRegistroCliente = findViewById(R.id.tv_link_cliente);
        tvRegistroCliente.setOnClickListener(view -> startRegistroClienteActivity());

        etFechaNac.setOnClickListener(view -> mostrarDatePicker());
        etFechaNac.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                mostrarDatePicker();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnRegistroLector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroLector();
            }
        });

        btnAtrasLogin.setOnClickListener(view -> startLoginActivity());
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

    public void startRegistroClienteActivity() {
        Intent intent = new Intent(this, RegistroClienteActivity.class);
        startActivity(intent);
        finish();
    }

    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void mostrarDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegistroLectorActivity.this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    int diffAnos = 0;
                    int anoActual = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        anoActual = LocalDate.now().getYear();
                        diffAnos = anoActual - selectedYear;
                    }
                    if(diffAnos >= 0 && diffAnos <= 18) {
                        Toast.makeText(RegistroLectorActivity.this, "Debes ser mayor de edad para continuar", Toast.LENGTH_SHORT).show();
                        etFechaNac.setText("");
                    } else if (selectedYear >= anoActual) {
                        Toast.makeText(RegistroLectorActivity.this, "Selecciona una fecha correcta", Toast.LENGTH_SHORT).show();
                        etFechaNac.setText("");
                    } else {
                        etFechaNac.setText(fecha);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void registroLector() {
        String nombreUsuario = etNombreUsuario.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String fechaNac = etFechaNac.getText().toString().trim();

        if (nombreUsuario.isEmpty() || email.isEmpty() || contrasena.isEmpty() || fechaNac.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("LOGIN_DEBUG", "Usuario: " + nombreUsuario);
        Log.d("LOGIN_DEBUG", "Contraseña: " + contrasena);

        RegistroLectorRequest registroLectorRequest = new RegistroLectorRequest(nombreUsuario,email,contrasena,fechaNac);

        Call<RegistroLectorResponseDTO> call = apiService.registrarLector(registroLectorRequest);
        call.enqueue(new Callback<RegistroLectorResponseDTO>() {
            @Override
            public void onResponse(Call<RegistroLectorResponseDTO> call, Response<RegistroLectorResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("LOGIN_DEBUG", "response is successful");
                    //capturamos la response del proceso de registro:
                    RegistroLectorResponseDTO registroData = response.body();

                    // Guardar en SharedPreferences:
                    SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nombreUsuario", registroData.getNombreUsuario());
                    editor.apply();

                    //abre la pantalla de inicio:
                    startActivity(new Intent(RegistroLectorActivity.this, PerfilLectorActivity.class));
                } else {
                    Toast.makeText(RegistroLectorActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroLectorResponseDTO> call, Throwable t) {
                Toast.makeText(RegistroLectorActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
