package com.david.proyecto.ciclo.siguealciclista.firebase.eventos;

import android.app.Activity;
import android.app.NotificationManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;


/**
 * David López González on 3/06/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class MiChildEventListener implements ChildEventListener
{
    private final String FIREBASE_URL;
    private Activity activity;
    private ManejadorBD usdbh;
    private SQLiteDatabase db;

    public MiChildEventListener(Activity activity)
    {
        this.activity = activity;
        FIREBASE_URL = "https://sigue-al-ciclista.firebaseio.com/" + Preferencias.getRuta(activity.getApplicationContext());
        constructorBBDD();
        Log.i("MiChildEventListener", FIREBASE_URL + "[MiChildEventListener(Activity)]");
    }


    public MiChildEventListener(Activity activity, String FIREBASE_URL)
    {
        this.activity = activity;
        this.FIREBASE_URL = FIREBASE_URL;
        constructorBBDD();
    }

    public MiChildEventListener(Activity activity, String FIREBASE_URL, NotificationManager notificationManager)
    {
        this.activity = activity;
        this.FIREBASE_URL = FIREBASE_URL;
        constructorBBDD();
    }


    private void constructorBBDD()
    {
        usdbh = new ManejadorBD(activity.getApplicationContext(), Preferencias.getNombreFirebase(), null, UtilsBBDD.versionSQL());
        db = usdbh.getWritableDatabase();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s)
    {
        insertarDatos(dataSnapshot);
        //Toast.makeText(activity.getApplicationContext(),"",Toast.LENGTH_SHORT).show();
        // añadimos ruta
        Log.i("MiChildEventListener", "[MiChildEventListener.onChildAdded]" );

        ManejadorBD usdbh = new ManejadorBD(activity.getApplicationContext(), Preferencias.getNombreFirebase(), null, UtilsBBDD.versionSQL());
        SQLiteDatabase db = usdbh.getWritableDatabase();
        System.out.println("punto________");
        usdbh.verDatos(db);
//        String key = dataSnapshot.getKey();
//        if (key.equals("Actual"))
//        {
//            Log.i("MiChildEventListener", "[MiChildEventListener.onChildAdded] " + key);
//        } else if (key.equals("Usuarios"))
//        {
//            Log.i("MiChildEventListener", "[MiChildEventListener.onChildAdded] " + key);
//        } else
//        {
//            Log.i("MiChildEventListener", "[MiChildEventListener.onChildAdded] " + key);
//        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s)
    {
        insertarDatos(dataSnapshot);
        Log.i("MiChildEventListener", "[MiChildEventListener.onChildChanged] " + s);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot)
    {
        Log.i("MiChildEventListener", "[MiChildEventListener.onChildRemoved]");
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s)
    {
        Log.i("MiChildEventListener", "[MiChildEventListener.onChildMoved]");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError)
    {
        Log.i("MiChildEventListener", "[MiChildEventListener.onCancelled]");
    }

    private String[] arrayDatos(String datos)
    {
        // Estructura de los datos
        // {Longitud=-1.7102094888687134, User=David, Critico=si, Latitud=38.50489807128906}
        return datos.replace("{", "").replace("}", "").replace(" ", "").split(",");
    }

    private void insertarDatos(DataSnapshot dataSnapshot)
    {
        String datos = dataSnapshot.getValue().toString();
        // Estructura de los datos
        // {Longitud=-1.7102094888687134, User=David, Critico=si, Latitud=38.50489807128906}
        String[] array = arrayDatos(datos);
        String user = null;
        String horaFecha = dataSnapshot.getKey();
        float latitud = 0.f;
        float longitud = 0.f;
        boolean critico;
        try
        {
            System.out.println("ppp_____________");
            for (String valores : array)
            {
                if (valores.contains("User"))
                {
                    user = valores.split("=")[1];
                    System.out.println("usuario: pppp " + user);
                } else if (valores.contains("Critico"))
                {
                    if ((valores.split("=")[1]).equalsIgnoreCase("si"))
                    {
                        critico = true;
                        //notificacion
                        System.out.println("Critico: pppp " + valores.split("=")[1]);
                    }
                } else if (valores.contains("Latitud"))
                {
                    latitud = Float.parseFloat(valores.split("=")[1]);
                    System.out.println("Latitud: pppp " + latitud);
                } else if (valores.contains("Longitud"))
                {
                    longitud = Float.parseFloat(valores.split("=")[1]);
                    System.out.println("Longitud: pppp " + longitud);
                }
            }
            //TODO insertar en bbdd
            usdbh.insertarCoordenada(db, new PuntoMapa(horaFecha, Preferencias.getRuta(activity.getApplicationContext()), user, new Coordenadas(longitud, latitud)));
            Log.i("MiChildEventListener", "[MiChildEventListener.insertarDatos]");
            usdbh.verDatosSinRepetir(db);
        } catch (Exception e)
        {
            Log.e("MiChildEventListener", "[MiChildEventListener.insertarDatos] " + e.getMessage());
        }
    }
}
