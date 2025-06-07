package com.example.tebeoteca.lector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tebeoteca.R;
import com.example.tebeoteca.cliente.evento.NuevoEventoActivity;

public class DialogMetodoPagoActivity extends Activity {

    ImageView paypal, gpay, redsys;
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

    }

    public void finishDialog(View v) {
        DialogMetodoPagoActivity.this.finish();
    }

    public void startColeccionActivity(View view) {
        Toast.makeText(this,"Añadido a \"Mi Colección\"",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ColeccionActivity.class);
        startActivity(intent);
        finishDialog(view);

    }
}
