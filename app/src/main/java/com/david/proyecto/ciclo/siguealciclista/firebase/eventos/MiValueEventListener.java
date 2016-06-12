package com.david.proyecto.ciclo.siguealciclista.firebase.eventos;

import android.app.Activity;
import android.app.NotificationManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.firebase.ConectarFirebase;
import com.david.proyecto.ciclo.siguealciclista.firebase.DatosFirebase;
import com.david.proyecto.ciclo.siguealciclista.helpers.MisNotificaciones;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * @author David López González on 14/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class MiValueEventListener implements ValueEventListener
{
    private final String FIREBASE_URL;
    private Activity activity;
    private NotificationManager notificationManager;
    private DatosFirebase datosFirebase;
    private ManejadorBD usdbh;
    private SQLiteDatabase db;

    public MiValueEventListener(Activity activity)
    {
        this.activity = activity;
        FIREBASE_URL = "https://sigue-al-ciclista.firebaseio.com/";
    }

    public MiValueEventListener(String FIREBASE_URL, NotificationManager notificationManager)
    {
        this.FIREBASE_URL = FIREBASE_URL;
        this.notificationManager = notificationManager;
    }

    public MiValueEventListener(Activity activity, String FIREBASE_URL)
    {
        this.activity = activity;
        this.FIREBASE_URL = FIREBASE_URL;
    }

    public MiValueEventListener(Activity activity, String FIREBASE_URL, NotificationManager notificationManager)
    {
        this.activity = activity;
        this.FIREBASE_URL = FIREBASE_URL;
        this.notificationManager = notificationManager;
        constructorBBDD();
    }

    private void constructorBBDD()
    {
        usdbh = new ManejadorBD(activity.getApplicationContext(), "SigueAlCiclista", null, UtilsBBDD.versionSQL());
        db = usdbh.getWritableDatabase();
    }

    @Override
    public void onDataChange(DataSnapshot snapshot)
    {
        String fechaEnQueCambia = null;
        try
        {
            fechaEnQueCambia = snapshot.getValue().toString();

            Toast.makeText(activity, fechaEnQueCambia, Toast.LENGTH_SHORT).show();
            Log.i(activity.getLocalClassName(), "se produce cambio " + fechaEnQueCambia);

            //notificationManager.notify(0, MisNotificaciones.mostrarNotificacion(activity.getApplicationContext(), fechaEnQueCambia, "2"));
            System.out.println("ruta total " + new Firebase(FIREBASE_URL).child(snapshot.getValue().toString()));

            final String fechaCambios = (String) snapshot.getValue();
            if (fechaCambios != null)
            {
                //necesito la fecha para poner el valor en la linea de abajo y así obtener los datos
                Firebase f = new Firebase(FIREBASE_URL + "/" + Preferencias.getRuta(activity.getApplicationContext())
                        + "/" + fechaCambios);
                //Firebase f2 = new Firebase(FIREBASE_URL + "/" + Preferencias.getRuta(activity.getApplicationContext()));
                //27-05-2016 22:31:21
                //Query q =f2.startAt("27-05-2016 22:31:21").endAt();
                // System.out.println("ooo"+ q);
                f.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot snapshot)
                    {
                        try
                        {
                            float latitud = Float.parseFloat(snapshot.child("Latitud").getValue().toString());
                            float longitud = Float.parseFloat(snapshot.child("Longitud").getValue().toString());
                            String user = snapshot.child("User").getValue().toString();
                            Toast.makeText(activity.getApplicationContext(), snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                            usdbh.insertarCoordenada(db, new PuntoMapa(fechaCambios, Preferencias.getRuta(activity.getApplicationContext()), user, new Coordenadas(longitud, latitud)));
                            String critico = snapshot.child("Critico").getValue().toString();
                            if (critico.equals("si"))
                                notificationManager.notify(0, MisNotificaciones.mostrarNotificacion(activity.getApplicationContext(), user, fechaCambios));
                        } catch (Exception e)
                        {
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error)
                    {
                    }
                });
            }
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            Log.i("MiValueEventListener", "[MiValueEventListener.onDataChange]");
        } catch (Exception e)
        {
            // Si falla, crearemos la rama para poder trabajar
            Log.e("MiValueEventListener", "[MiValueEventListener.onDataChange]");
            new ConectarFirebase(activity.getApplicationContext(), Preferencias.getRuta(activity.getApplicationContext()))
                    .crearActual();
        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError)
    {
        try
        {
            Log.i("MiValueEventListener", "[MiValueEventListener.onCancelled]");
        } catch (Exception e)
        {
            Log.e("MiValueEventListener", "[MiValueEventListener.onCancelled]");
        }
    }
}
