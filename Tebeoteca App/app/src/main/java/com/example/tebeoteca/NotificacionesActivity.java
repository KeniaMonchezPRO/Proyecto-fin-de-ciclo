package com.example.tebeoteca;

import android.os.Bundle;
import android.view.View;

public class NotificacionesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_notificaciones);
        setupMenus(R.id.nav_inicio, "lector");
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

    public void volverAtras(View view) {
        this.finish();
    }
}
