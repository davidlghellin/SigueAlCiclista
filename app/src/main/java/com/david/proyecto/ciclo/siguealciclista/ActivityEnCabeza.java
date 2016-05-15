package com.david.proyecto.ciclo.siguealciclista;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.Utils;
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

import java.util.ArrayList;
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
        final LatLng latLng = new LatLng(gpsActual.getCoordenadas().getLatitud(), gpsActual.getCoordenadas().getLongitud());
        // inicializarMapa(latLng);

        mapa = new Mapa(getApplicationContext(), this);
        mapa.setMarker(ALQUERIAS, "Alquerías", " Murcia");
        mapa.setMarker(new LatLng(38.014215, -1.036), "PTO2", " Murcssssssia");

        final ManejadorBD usdbh = new ManejadorBD(this, "SigueAlCiclista", null, Utils.versionSQL());
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        usdbh.verDatos(db);

        class MyAsync extends AsyncTask<Boolean, Mapa, ArrayList<PuntoMapa>>
        {
            Mapa mapa2;
            boolean color;

            @Override
            protected void onPreExecute()
            {
                mapa2 = mapa;
            }

            @Override
            protected void onProgressUpdate(Mapa... values)
            {
                //TODO actualizar el mapa
                super.onProgressUpdate(values);
                if (color)
                {
                    mapa2.drawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.RED));
                } else
                {
                    mapa2.drawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.BLUE));
                }
                color = !color;
            }

            @Override
            protected ArrayList<PuntoMapa> doInBackground(Boolean... params)
            {
                try
                {
                    while (true)
                    {
                        //TODO cada X tiempo consultamos la base de datos para refrescar el mapa
                        Thread.sleep(1000);
                        publishProgress();
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                ArrayList<PuntoMapa> puntoMapas = usdbh.getDatos(db);
                return puntoMapas;
            }

            @Override
            protected void onPostExecute(ArrayList<PuntoMapa> puntoMapas)
            {
                super.onPostExecute(puntoMapas);
                //TODO cuando ha terminado de cargar los datos
                mapa2.drawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.RED));
            }
        }

        MyAsync my = new MyAsync();
        my.execute();

        //mapa.drawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.RED));
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
