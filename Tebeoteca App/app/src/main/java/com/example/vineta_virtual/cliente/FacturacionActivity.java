package com.example.vineta_virtual.cliente;

import android.os.Bundle;
import android.util.Log;

import com.example.vineta_virtual.BaseActivity;
import com.example.vineta_virtual.R;

public class FacturacionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ComicsAct","onCreate()");
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_facturacion);
        setupMenus(R.id.nav_buscar,"cliente");
    }

}
