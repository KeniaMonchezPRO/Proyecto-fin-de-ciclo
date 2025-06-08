package com.example.tebeoteca.lector;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tebeoteca.R;
import com.example.tebeoteca.api.ApiService;
import com.example.tebeoteca.cliente.comic.Comic;
import com.example.tebeoteca.cliente.comic.ComicsActivity;
import com.example.tebeoteca.cliente.evento.NuevoEventoActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogMetodoPagoActivity extends Activity {

    ImageView paypal, gpay, redsys;
    int idUsuario, idComic;
    ApiService apiService;
    SharedPreferences perfilPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_metodo_pago);

        paypal = findViewById(R.id.iv_logoPaypal);
        gpay = findViewById(R.id.iv_logoGPay);
        redsys = findViewById(R.id.iv_logoRedsys);

        paypal.setImageResource(R.drawable.paypal2);
        gpay.setImageResource(R.drawable.gpay2);
        redsys.setImageResource(R.drawable.redsys2);

        //Conexion con api:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        SharedPreferences usuarioPrefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        idUsuario = usuarioPrefs.getInt("idUsuario",2);

        SharedPreferences idComicPref = getSharedPreferences("idComic", MODE_PRIVATE);
        idComic = idComicPref.getInt("comicId",1);
        Log.d("DEBUG LECTOR", "Cómic recibido en sharedprefs: " + idComic);

    }

    public void finishDialog(View v) {
        DialogMetodoPagoActivity.this.finish();
    }

    public void startColeccionActivity(View view) {
        comprarComic();
        Intent intent = new Intent(this, ColeccionActivity.class);
        startActivity(intent);
        finishDialog(view);
    }

    private void comprarComic() {
        Call<ResponseBody> call = apiService.comprarComic(idUsuario, idComic);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(DialogMetodoPagoActivity.this, "Comic añadido a \"Mi Colección\"", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DialogMetodoPagoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
