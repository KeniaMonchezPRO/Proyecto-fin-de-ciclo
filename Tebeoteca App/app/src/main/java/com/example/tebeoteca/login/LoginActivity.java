package com.example.tebeoteca.login;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.tebeoteca.cliente.PerfilClienteActivity;
import com.example.tebeoteca.lector.PerfilLectorActivity;
import com.example.tebeoteca.registro.RegistroLectorActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends ComponentActivity {
    private EditText etUsuarioEmail, etContrasena;
    private Button btnLogin;
    private ApiService apiService;
    private TextView sinCuenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuarioEmail = findViewById(R.id.et_UsuarioEmail);
        etContrasena = findViewById(R.id.et_Contrasena);
        btnLogin = findViewById(R.id.btn_Login);

        sinCuenta = findViewById(R.id.tv_link_cliente);
        sinCuenta.setOnClickListener(view -> startRegistroLectorActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
            }
        });
    }

    public void startRegistroLectorActivity() {
        Intent intent = new Intent(this, RegistroLectorActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginUsuario() {
        String usuarioEmail = etUsuarioEmail.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (usuarioEmail.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("LOGIN_DEBUG", "Usuario: " + usuarioEmail);
        Log.d("LOGIN_DEBUG", "Contraseña: " + contrasena);

        LoginRequest loginRequest = new LoginRequest(usuarioEmail, contrasena);
        Call<LoginResponseDTO> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("LOGIN_DEBUG", "response is successful");
                    LoginResponseDTO loginData = response.body();

                    // Guardar en SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putInt("id", loginData.getId());
                    editor.putString("nombreUsuario", loginData.getNombreUsuario());
                    editor.putString("tipoUsuario", loginData.getTipoUsuario());

                    if ("CLIENTE".equals(loginData.getTipoUsuario())) {
                        editor.putString("nombreCliente", loginData.getNombreCliente());
                    } else {
                        editor.putString("nombreLector", loginData.getNombreLector());
                        //editor.putString("apellidos", loginData.getApellidos());
                    }

                    editor.apply();

                    // Redirigir a la Activity correspondiente
                    if ("CLIENTE".equals(loginData.getTipoUsuario())) {
                        startActivity(new Intent(LoginActivity.this, PerfilClienteActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, PerfilLectorActivity.class));
                    }
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });

        /* En caso de error descomentar junto con ApiService y backend

        LoginRequest request = new LoginRequest(usuarioEmail, contrasena);

        Call<ResponseBody> call = apiService.login(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login exitoso 🎉", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas 😓", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
    }
}
