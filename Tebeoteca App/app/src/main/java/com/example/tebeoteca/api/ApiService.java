package com.example.tebeoteca.api;

import com.example.tebeoteca.login.LoginRequest;
import com.example.tebeoteca.login.LoginResponseDTO;
import com.example.tebeoteca.registro.RegistroClienteRequest;
import com.example.tebeoteca.registro.RegistroClienteResponseDTO;
import com.example.tebeoteca.registro.RegistroLectorRequest;
import com.example.tebeoteca.registro.RegistroLectorResponseDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/login") // o el endpoint que hayas definido
    Call<LoginResponseDTO> login(@Body LoginRequest request);

    @POST("/api/v2/registro/lector")
    Call<RegistroLectorResponseDTO> registrarLector(@Body RegistroLectorRequest request);

    @POST("/api/v2/registro/cliente")
    Call<RegistroClienteResponseDTO> registrarCliente(@Body RegistroClienteRequest request);

    /* En caso de errores descomentar, junto con el del backend
    @POST("/api/login") // o el endpoint que hayas definido
    Call<ResponseBody> login(@Body LoginRequest request);

    @POST("/api/v2/registro/lector")
    Call<ResponseBody> registrarLector(@Body RegistroLectorRequest request);

    @POST("/api/v2/registro/cliente")
    Call<ResponseBody> registrarCliente(@Body RegistroClienteRequest request);*/

}
