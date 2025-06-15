package com.example.vineta_virtual.registro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.example.vineta_virtual.R;
import com.example.vineta_virtual.api.ApiService;
import com.example.vineta_virtual.cliente.PerfilClienteActivity;
import com.example.vineta_virtual.general.TerminosCondicionesActivity;
import com.example.vineta_virtual.login.LoginActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroClienteActivity extends ComponentActivity {
    private EditText etNombreUsuario, etEmail, etContrasena, etNombreEmpresa;
    private RadioButton rbEditorial, rbDistribuidora, rbCreador;
    private RadioGroup rgTipo;
    private ImageButton btnRegistroCliente, btnAtrasLogin;
    private TextView tvTerminos;
    private ApiService apiService;
    private CheckBox cbTerminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);

        etNombreUsuario = findViewById(R.id.et_NombreUsuario);
        etEmail = findViewById(R.id.et_Email);
        etContrasena = findViewById(R.id.et_Contrasena);
        etNombreEmpresa = findViewById(R.id.et_NombreEmpresa);

        cbTerminos = findViewById(R.id.cb_terminos);
        tvTerminos = findViewById(R.id.tv_link_terminos);
        //tvTerminos.setOnClickListener(view -> startTyCActivity());

        btnRegistroCliente = findViewById(R.id.btn_Registro_Cliente);
        btnAtrasLogin = findViewById(R.id.btn_atras_login);

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

    public void startTyCActivity() {
        Intent intent = new Intent(this, TerminosCondicionesActivity.class);
        startActivity(intent);
    }
    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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

        if(!cbTerminos.isChecked()) {
            Toast.makeText(this, "Acepta los Términos y Condiciones para continuar", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("RegistroClienteAct", "Usuario: " + nombreUsuario);
        Log.d("RegistroClienteAct", "Contraseña: " + contrasena);

        RegistroClienteRequest registroClienteRequest = new RegistroClienteRequest(nombreUsuario,email,contrasena,nombreEmpresa,tipo);

        Call<RegistroClienteResponseDTO> call = apiService.registrarCliente(registroClienteRequest);
        call.enqueue(new Callback<RegistroClienteResponseDTO>() {
            @Override
            public void onResponse(Call<RegistroClienteResponseDTO> call, Response<RegistroClienteResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("RegistroClienteAct", "response is successful");
                    RegistroClienteResponseDTO registroData = response.body();

                    Log.d("RegistroClienteAct", new Gson().toJson(response.body()));
                    Log.d("RegClientAct", "response: " + registroData);
                    Log.d("RegClientAct", "response: " + registroData.getCliente());

                    // Guardar en SharedPreferences:
                    SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("idUsuario", registroData.getId());
                    editor.putString("nombreUsuario", registroData.getNombreUsuario());
                    editor.putString("nombreEmpresa", registroData.getNombreEmpresa());
                    editor.putString("email", registroData.getEmail());
                    editor.putString("contrasena", registroData.getContrasena());
                    editor.putString("tipoEmpresa", registroData.getTipo());
                    editor.putString("descripcion", "Añadir descripción");
                    editor.putString("nif", "Añadir NIF");
                    editor.putString("fechaCreacionEmpresa", "Añadir fecha de fundación");
                    editor.apply();
                    startActivity(new Intent(RegistroClienteActivity.this, PerfilClienteActivity.class));

                    /*Intent intent = new Intent(RegistroClienteActivity.this, PerfilClienteActivity.class);
                    intent.putExtra("cliente", registroData.getCliente());
                    startActivity(intent);*/
                } else {
                    Toast.makeText(RegistroClienteActivity.this, "Hubo un error al crear el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroClienteResponseDTO> call, Throwable t) {
                Toast.makeText(RegistroClienteActivity.this, "Hubo un error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
