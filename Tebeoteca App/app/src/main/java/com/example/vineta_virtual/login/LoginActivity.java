package com.example.vineta_virtual.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.example.vineta_virtual.R;
import com.example.vineta_virtual.api.ApiService;
import com.example.vineta_virtual.cliente.PerfilClienteActivity;
import com.example.vineta_virtual.lector.PerfilLectorActivity;
import com.example.vineta_virtual.registro.RegistroLectorActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends ComponentActivity {
    private EditText etUsuarioEmail, etContrasena;
    private ImageButton btnLogin;
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

        btnLogin.setOnClickListener(view -> loginUsuario());
        //btnLogin.setOnClickListener(view -> startPerfilCliente()); //solo para pruebas, comentar cuando termine
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

    public void startPerfilCliente() {
        SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("idUsuario", 1);
        editor.putString("nombreUsuario", "Pruebas");
        editor.putString("tipoUsuario", "CLIENTE");
        Intent intent = new Intent(this, PerfilClienteActivity.class);
        startActivity(intent);
        finish();
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
                    Log.d("RESPONSE_JSON", new Gson().toJson(response.body()));
                    Log.d("LOGIN_DEBUG", "response: " + loginData);

                    // Guardar en SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putInt("idUsuario", loginData.getId());
                    editor.putString("nombreUsuario", loginData.getNombreUsuario());
                    editor.putString("tipoUsuario", loginData.getTipoUsuario());


                    /*if ("CLIENTE".equals(loginData.getTipoUsuario())) {
                        editor.putString("nombreEmpresa", loginData.getNombreCliente());
                        editor.putString("nombreUsuario", loginData.getNombreUsuario());
                        editor.putString("descripcion", loginData.getDescripcion());
                        editor.putString("nif", loginData.getNif());
                        editor.putString("fechaCreacionEmpresa", loginData.getFechaCreacionEmpresa());
                    } else {
                        editor.putString("nombreLector", loginData.getNombreLector());
                        editor.putString("apellidos", loginData.getApellidos());
                        editor.putString("fechaNacimiento", loginData.getFechaNacimiento());
                    }

                    editor.apply();

                    // Redirigir a la Activity correspondiente
                    if ("CLIENTE".equals(loginData.getTipoUsuario())) {
                        startActivity(new Intent(LoginActivity.this, PerfilClienteActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, PerfilLectorActivity.class));
                    }
                    finish();*/

                    if (loginData.getTipoUsuario().equals("CLIENTE")) {
                        editor.putString("nombreEmpresa", loginData.getCliente().getNombreCliente());
                        editor.putString("nombreUsuario", loginData.getCliente().getNombreUsuario());
                        editor.putString("descripcion", loginData.getCliente().getDescripcion());
                        editor.putString("nif", loginData.getCliente().getNif());
                        editor.putString("fechaCreacionEmpresa", loginData.getCliente().getFechaCreacionEmpresa());

                        Intent intent = new Intent(LoginActivity.this, PerfilClienteActivity.class);
                        intent.putExtra("cliente", loginData.getCliente());
                        startActivity(intent);
                    } else {
                        editor.putString("nombreLector", loginData.getLector().getNombreLector());
                        editor.putString("apellidos", loginData.getLector().getApellidosLector());
                        editor.putString("fechaNacimiento", loginData.getFechaNacimiento());
                        editor.putString("contrasena", loginData.getLector().getContrasena());
                        editor.putString("email", loginData.getLector().getEmail());

                        Intent intent = new Intent(LoginActivity.this, PerfilLectorActivity.class);
                        intent.putExtra("lector", loginData.getLector());
                        Log.d("LoginAct", "Inició sesion de lector: id:" + loginData.getId() + ", nombre: " + loginData.getLector().getNombreLector() + ", apellidos: " + loginData.getLector().getApellidosLector());
                        startActivity(intent);
                    }
                    editor.apply();
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
