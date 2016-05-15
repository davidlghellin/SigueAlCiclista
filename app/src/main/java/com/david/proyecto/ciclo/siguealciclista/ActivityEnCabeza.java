package com.david.proyecto.ciclo.siguealciclista;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEnCabeza extends AppCompatActivity
{
    private Thread hilo;
    private MarcarRuta marcarRuta;


    //private GoogleMap mMap;
    //Barcelona   41°23′20″N  02°09′32″E  UT+02:00   Barcelona
    //public static final LatLng SAGRADA_FAMILIA = new LatLng(41.40347, 2.17432);
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
        //comenzarLocalizacion();
        gpsActual = new GPS(getApplicationContext());
        marcarRuta = new MarcarRuta(this);
        hilo = new Thread(marcarRuta);
        hilo.start();

        //mapa
        LatLng latLng = new LatLng(gpsActual.getCoordenadas().getLatitud(), gpsActual.getCoordenadas().getLongitud());
        // inicializarMapa(latLng);

        mapa = new Mapa(getApplicationContext(),  this);
        mapa.setMarker(ALQUERIAS, "Alquerías", " Murcia");
        mapa.setMarker(new LatLng(38.014215, -1.036), "PTO2", " Murcssssssia");

        mapa.drawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.RED));
        //setMarker(ALQUERIAS, "Alquerías", " Murcia"); // Agregamos el marcador
        //setMarker(new LatLng(38.014215, -1.036), "PTO2", " Murcia"); // Agregamos el marcador
        //setMarker(new LatLng(38.01422, -1.0365), "PTO3", " Murcia"); // Agregamos el marcador

    }


    @OnClick(R.id.btnCancelarSeguimiento)
    public void cancelarSeguimiento()
    {
        marcarRuta.setContinuaHilo(false);
        finish();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        marcarRuta.setContinuaHilo(false);
    }
}
