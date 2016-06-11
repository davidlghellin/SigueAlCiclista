package com.david.proyecto.ciclo.siguealciclista.actividades;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.GPS;
import com.david.proyecto.ciclo.siguealciclista.Mapa;
import com.david.proyecto.ciclo.siguealciclista.R;
import com.david.proyecto.ciclo.siguealciclista.firebase.ConectarFirebase;
import com.david.proyecto.ciclo.siguealciclista.helpers.GetContext;
import com.david.proyecto.ciclo.siguealciclista.hilos.MarcarRuta;
import com.david.proyecto.ciclo.siguealciclista.servicios.MarcarRutaService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author David López González.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class ActivityEnCabeza extends AppCompatActivity
{
    public static final LatLng ALQUERIAS = new LatLng(38.014215, -1.035408);
    private GPS gpsActual;
    private Mapa mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_en_cabeza);

        ButterKnife.bind(this);

        // GPS
        gpsActual = new GPS(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), MarcarRutaService.class);
        GetContext.setContext(getApplicationContext());
        startService(intent);

        //mapa
        final LatLng latLng = new LatLng(gpsActual.getCoordenadas().getLatitud(), gpsActual.getCoordenadas().getLongitud());
        // inicializarMapa(latLng);

        mapa = new Mapa(getApplicationContext(), this);

    }


    @OnClick(R.id.btnCancelarSeguimiento)
    public void cancelarSeguimiento()
    {
        mapa = null;
        System.gc();

        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
    }

    @OnClick(R.id.btnGrabarPunto)
    public void marcarPunto()
    {
        new ConectarFirebase(getApplicationContext()).subirDatos();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //TODO probar a poner el onCreate en un método e incluirlo aquí
    }
}
