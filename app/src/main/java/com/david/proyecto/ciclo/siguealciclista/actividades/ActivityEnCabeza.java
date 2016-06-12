package com.david.proyecto.ciclo.siguealciclista.actividades;

import android.app.NotificationManager;
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
import com.david.proyecto.ciclo.siguealciclista.helpers.MisNotificaciones;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;
import com.david.proyecto.ciclo.siguealciclista.hilos.MarcarRuta;
import com.david.proyecto.ciclo.siguealciclista.servicios.MarcarRutaService;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
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
    private GPS gpsActual;
    private Mapa mapa;
    private Firebase myFirebaseEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_en_cabeza);

        ButterKnife.bind(this);

        // GPS
        gpsActual = new GPS(getApplicationContext());
        // Activar servicio
        final Intent intent = new Intent(getApplicationContext(), MarcarRutaService.class);
        GetContext.setContext(getApplicationContext());
        startService(intent);
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Controlar el cambio de usuario
        myFirebaseEvent = new Firebase("https://sigue-al-ciclista.firebaseio.com/" + Preferencias.getRuta(getApplicationContext()));
        final ChildEventListener childEventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                String datos = dataSnapshot.getValue().toString();
                String[] array = datos.replace("{", "").replace("}", "").replace(" ", "").split(",");
                for (String valores : array)
                {
                    if (valores.contains("User"))
                    {
                        String user = valores.split("=")[1];
                        if (!user.equals(Preferencias.getUsuario(getApplicationContext())))
                        {
                            // Notificación de cambio de la cabeza
                            notificationManager.notify(0, MisNotificaciones.mostrarNotificacion(getApplicationContext(), user, "Se produce cambio de cabeza"));
                            cancelarSeguimiento();
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
            }
        };
        myFirebaseEvent.addChildEventListener(childEventListener);
        // FIN Controlar el cambio de usuario

        mapa = new Mapa(getApplicationContext(), this);
    }

    @OnClick(R.id.btnCancelarSeguimiento)
    public void cancelarSeguimiento()
    {
        mapa = null;
        System.gc();    // Recolector de basura

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
    protected void onResume(){super.onResume();}
}
