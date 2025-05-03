package com.example.tebeoteca.registro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.login.LoginActivity;
import com.example.tebeoteca.login.LoginRequest;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroLectorActivity extends ComponentActivity {
    private EditText etNombreUsuario, etEmail, etContrasena, etNombre, etApellidos, etFechaNac;
    private Button btnRegistroLector;
    private ApiService apiService;
    private TextView tvRegistroCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_lector1);

        etNombreUsuario = findViewById(R.id.et_NombreUsuario);
        etEmail = findViewById(R.id.et_Email);
        etContrasena = findViewById(R.id.et_Contrasena);
        etNombre = findViewById(R.id.et_Nombre);
        etApellidos = findViewById(R.id.et_Apellidos);
        etFechaNac = findViewById(R.id.et_FechaNac);
        btnRegistroLector = findViewById(R.id.btn_Registro_Lector1);

        tvRegistroCliente = findViewById(R.id.tv_link_cliente);
        tvRegistroCliente.setOnClickListener(view -> startRegistroClienteActivity());

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
    }

    public void startRegistroClienteActivity() {
        Intent intent = new Intent(this, RegistroClienteActivity.class);
        startActivity(intent);
        finish();
    }
    private void registroLector() {
        String nombreUsuario = etNombreUsuario.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String fechaNac = etFechaNac.getText().toString().trim();

        if (nombreUsuario.isEmpty() || email.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || fechaNac.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        RegistroLectorRequest registroLectorRequest = new RegistroLectorRequest(nombreUsuario,email,contrasena,nombre,apellidos,fechaNac);

        Call<ResponseBody> call = apiService.registrarLector(registroLectorRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroLectorActivity.this, "Registro exitoso 🎉", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroLectorActivity.this, "Hubo un error 😓", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistroLectorActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
