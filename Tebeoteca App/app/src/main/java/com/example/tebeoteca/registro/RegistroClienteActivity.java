package com.example.tebeoteca.registro;

import android.os.Bundle;
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

        RegistroClienteRequest registroClienteRequest = new RegistroClienteRequest(nombreUsuario,email,contrasena,nombreEmpresa,tipo);

        Call<ResponseBody> call = apiService.registrarCliente(registroClienteRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroClienteActivity.this, "Registro exitoso 🎉", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroClienteActivity.this, "Hubo un error 😓", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistroClienteActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
