package com.example.vineta_virtual;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ConfiguracionActivity extends BaseActivity {
    boolean esLector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_configuracion);

        SharedPreferences activityContext = getSharedPreferences("activityAndTabContext",MODE_PRIVATE);
        String activity = activityContext.getString("activity","");
        String tab = activityContext.getString("tab","");
        boolean lector = activityContext.getBoolean("esLector",false);

        esLector = getIntent().getBooleanExtra("esLector", false);

        Log.d("DEBUG ConfigAct", "esLectorExtra? " + esLector + "; activity context: " + activity + "; tab: " + tab + "; lector?: " + lector );

        if(esLector || lector) {
            setupMenus(R.id.nav_inicio,"lector");
            btnConfig.setVisibility(View.GONE);
        } else {
            setupMenus(R.id.nav_inicio,"cliente");
            btnConfig.setVisibility(View.GONE);
        }


        /*if(lector && activity.equals("BusquedaActivity")) {
            setupMenus(R.id.nav_buscar, "lector"); //done
            Log.d("DEBUG ConfigAct", "es lector desde busqueda");
        } else if(!esLector && activity.equals("BusquedaActivity")) {
            setupMenus(R.id.nav_buscar, "cliente");
            Log.d("DEBUG ConfigAct", "es cliente desde busqueda"); //done
        } else if(lector && (activity.equals("ColeccionActivity") || activity.equals("ComicActivity"))) {
            setupMenus(R.id.nav_comics, "lector"); //done
            Log.d("DEBUG ConfigAct", "es lector desde coleccion o comic");
        } else if(!esLector && (activity.equals("ComicActivity") || activity.equals("ComicsActivity") || activity.equals("NuevoComicActivity"))) {
            setupMenus(R.id.nav_comics, "cliente");
            Log.d("DEBUG ConfigAct", "es cliente desde comic, comics o nuevo comic"); //DONE
        } else if((lector && activity.equals("PerfilLectorActivity"))) {
            setupMenus(R.id.nav_inicio, "lector");
            Log.d("DEBUG ConfigAct", "es lector desde su propio perfil"); //done
        } else if(!esLector && (activity.equals("PerfilLectorActivity") && tab.equals("buscar"))) {
            setupMenus(R.id.nav_buscar, "cliente");
            Log.d("DEBUG ConfigAct", "es cliente desde busqueda y desde perfil lector"); //done
        } else if((!esLector && !lector) && (activity.equals("PerfilClienteActivity"))) {
            Log.d("DEBUG ConfigAct", "es cliente desde su propio perfil"); //done
            setupMenus(R.id.nav_inicio, "cliente");
        } else if(esLector && (activity.equals("PerfilLectorActivity") && tab.equals("buscar"))) {
            setupMenus(R.id.nav_buscar, "lector");
            Log.d("DEBUG ConfigAct", "es lector desde su busqueda para ver su propio perfil"); //(?)
        } else if(lector && (activity.equals("PerfilClienteActivity") && tab.equals("buscar"))) {
            setupMenus(R.id.nav_buscar, "lector");
            Log.d("DEBUG ConfigAct", "es lector desde su busqueda para ver perfil de cliente"); //DONE
        }*/






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

}
