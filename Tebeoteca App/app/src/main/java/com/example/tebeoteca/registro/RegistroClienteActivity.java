package com.example.tebeoteca.registro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.cliente.PerfilClienteActivity;
import com.example.tebeoteca.lector.PerfilLectorActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroClienteActivity extends ComponentActivity {
    private EditText etNombreUsuario, etEmail, etContrasena, etNombreEmpresa;
    private RadioButton rbEditorial, rbDistribuidora, rbCreador;
    private RadioGroup rgTipo;
    private ImageButton btnRegistroCliente;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);

        etNombreUsuario = findViewById(R.id.et_NombreUsuario);
        etEmail = findViewById(R.id.et_Email);
        etContrasena = findViewById(R.id.et_Contrasena);
        etNombreEmpresa = findViewById(R.id.et_NombreEmpresa);

        btnRegistroCliente = findViewById(R.id.btn_Registro_Cliente);

        rgTipo = findViewById(R.id.rg_Tipo);
        rbCreador = findViewById(R.id.rb_Creador);
        rgTipo.check(rbCreador.getId());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnRegistroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroCliente();
            }
        });
    }

    private void registroCliente() {
        String nombreUsuario = etNombreUsuario.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String nombreEmpresa = etNombreEmpresa.getText().toString().trim();

        int selectedId = rgTipo.getCheckedRadioButtonId();
        RadioButton selectedOption = findViewById(selectedId);
        String tipo = selectedOption.getText().toString().trim().toLowerCase();

        if (nombreUsuario.isEmpty() || email.isEmpty() || contrasena.isEmpty() || nombreEmpresa.isEmpty() || tipo.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("LOGIN_DEBUG", "Usuario: " + nombreUsuario);
        Log.d("LOGIN_DEBUG", "Contraseña: " + contrasena);

        RegistroClienteRequest registroClienteRequest = new RegistroClienteRequest(nombreUsuario,email,contrasena,nombreEmpresa,tipo);

        Call<RegistroClienteResponseDTO> call = apiService.registrarCliente(registroClienteRequest);
        call.enqueue(new Callback<RegistroClienteResponseDTO>() {
            @Override
            public void onResponse(Call<RegistroClienteResponseDTO> call, Response<RegistroClienteResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("LOGIN_DEBUG", "response is successful");
                    //capturamos la response del proceso de registro:
                    RegistroClienteResponseDTO registroData = response.body();

                    // Guardar en SharedPreferences:
                    SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nombreEmpresa", registroData.getNombreEmpresa());
                    editor.putString("nombreUsuario", registroData.getNombreUsuario());
                    editor.apply();

                    //abre la pantalla de inicio:
                    startActivity(new Intent(RegistroClienteActivity.this, PerfilClienteActivity.class));
                } else {
                    Toast.makeText(RegistroClienteActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroClienteResponseDTO> call, Throwable t) {
                Toast.makeText(RegistroClienteActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
