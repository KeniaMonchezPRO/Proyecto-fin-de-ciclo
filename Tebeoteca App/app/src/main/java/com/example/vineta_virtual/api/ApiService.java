package com.example.vineta_virtual.api;

import com.example.vineta_virtual.cliente.comic.NuevoComicRequest;
import com.example.vineta_virtual.cliente.comic.NuevoComicResponseDTO;
import com.example.vineta_virtual.cliente.comic.Comic;
import com.example.vineta_virtual.general.BusquedaRequest;
import com.example.vineta_virtual.lector.Lector;
import com.example.vineta_virtual.login.LoginRequest;
import com.example.vineta_virtual.login.LoginResponseDTO;
import com.example.vineta_virtual.registro.RegistroClienteRequest;
import com.example.vineta_virtual.registro.RegistroClienteResponseDTO;
import com.example.vineta_virtual.registro.RegistroLectorRequest;
import com.example.vineta_virtual.registro.RegistroLectorResponseDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/login") // o el endpoint que hayas definido
    Call<LoginResponseDTO> login(@Body LoginRequest request);

    @POST("/api/v2/registro/lector")
    Call<RegistroLectorResponseDTO> registrarLector(@Body RegistroLectorRequest request);

    @POST("/api/v2/registro/cliente")
    Call<RegistroClienteResponseDTO> registrarCliente(@Body RegistroClienteRequest request);

    @POST("/api/comics/anadir")
    Call<NuevoComicResponseDTO> crearComic(@Body NuevoComicRequest request);

    @GET("/api/comics/categoria/{categoria}")
    Call<List<Comic>> obtenerComicsPorCategoria(@Path("categoria") String categoria);

    @GET("/api/comics/cliente/{clienteId}")
    Call<List<Comic>> obtenerComicsPorCliente(@Path("clienteId") int clienteId);

    @GET("/api/buscar/todo")
    Call<BusquedaRequest> buscarTodo(@Query("titulo") String titulo);

    @POST("/api/lector/{idLector}/favoritos/{idComic}")
    Call<ResponseBody> agregarFavorito(@Path("idLector") int idLector, @Path("idComic") int idComic);

    @DELETE("/api/lector/{idLector}/favoritos/{idComic}")
    Call<ResponseBody> eliminarFavorito(@Path("idLector") int idLector, @Path("idComic") int idComic);

    @GET("/api/lector/{idLector}/favoritos")
    Call<List<Comic>> obtenerFavoritos(@Path("idLector") int idLector);

    @POST("/api/lector/{idLector}/comprar/{idComic}")
    Call<ResponseBody> comprarComic(@Path("idLector") int idLector, @Path("idComic") int idComic);

    @GET("/api/lector/{idLector}/compras")
    Call<List<Comic>> obtenerCompras(@Path("idLector") int idLector);

    @PUT("/api/lector/editar/{idLector}")
    Call<Lector> editarLector(@Path("idLector")int idLector, @Body Lector lector);

    @DELETE("/api/comics/eliminar/{idComic}")
    Call<ResponseBody> eliminarComic(@Path("idComic") int idComic);
    //@GET("/api/usuarios/{id}")
    //Call<UsuarioResponseDTO> obtenerUsuario(@Path("id") int usuarioId);

    /* En caso de errores descomentar, junto con el del backend
    @POST("/api/login") // o el endpoint que hayas definido
    Call<ResponseBody> login(@Body LoginRequest request);

    @POST("/api/v2/registro/lector")
    Call<ResponseBody> registrarLector(@Body RegistroLectorRequest request);

    @POST("/api/v2/registro/cliente")
    Call<ResponseBody> registrarCliente(@Body RegistroClienteRequest request);*/

}
