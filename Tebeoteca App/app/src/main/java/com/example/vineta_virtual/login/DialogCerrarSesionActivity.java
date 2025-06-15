package com.example.vineta_virtual.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vineta_virtual.R;

public class DialogCerrarSesionActivity extends AppCompatActivity {

    ImageButton btnConfirmar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_cerrar_sesion);

        btnConfirmar = findViewById(R.id.btn_confirmarLogout);
        btnCancelar = findViewById(R.id.btn_cancelarLogout);

        btnConfirmar.setOnClickListener(v -> { cerrarSesion(); });
        btnCancelar.setOnClickListener(this::finishDialog);

    }

    public void finishDialog(View v) {
        Log.d("DialogCerrarSesionAct", "cancelar");
        DialogCerrarSesionActivity.this.finish();
    }

    private void cerrarSesion() {
        Log.d("DialogCerrarSesionAct", "cerrarSesion");
        SharedPreferences cleanPrefs1 = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = cleanPrefs1.edit();
        editor1.clear();
        editor1.apply();

        SharedPreferences cleanPrefs2 = getSharedPreferences("comicPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = cleanPrefs2.edit();
        editor2.clear();
        editor2.apply();

        SharedPreferences cleanPrefs3 = getSharedPreferences("perfilPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor3 = cleanPrefs3.edit();
        editor3.clear();
        editor3.apply();

        SharedPreferences cleanPrefs4 = getSharedPreferences("activityAndTabContext", MODE_PRIVATE);
        SharedPreferences.Editor editor4 = cleanPrefs4.edit();
        editor4.clear();
        editor4.apply();

        SharedPreferences cleanPrefs5 = getSharedPreferences("idComic", MODE_PRIVATE);
        SharedPreferences.Editor editor5 = cleanPrefs5.edit();
        editor5.clear();
        editor5.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
