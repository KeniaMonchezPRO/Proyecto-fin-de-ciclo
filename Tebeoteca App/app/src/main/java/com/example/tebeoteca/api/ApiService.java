package com.example.tebeoteca.api;

import com.example.tebeoteca.login.LoginRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/login") // o el endpoint que hayas definido
    Call<ResponseBody> login(@Body LoginRequest request);

    /*@POST("registro/lector")
    Call<String> registrarLector(@Body RegistroLector request);*/

}
