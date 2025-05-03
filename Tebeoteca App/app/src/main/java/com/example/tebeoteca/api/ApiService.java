package com.example.tebeoteca.api;

import com.example.tebeoteca.login.LoginRequest;
import com.example.tebeoteca.registro.RegistroClienteRequest;
import com.example.tebeoteca.registro.RegistroLectorRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/login") // o el endpoint que hayas definido
    Call<ResponseBody> login(@Body LoginRequest request);

    @POST("/api/v2/registro/lector")
    Call<ResponseBody> registrarLector(@Body RegistroLectorRequest request);

    @POST("/api/v2/registro/cliente")
    Call<ResponseBody> registrarCliente(@Body RegistroClienteRequest request);

}
