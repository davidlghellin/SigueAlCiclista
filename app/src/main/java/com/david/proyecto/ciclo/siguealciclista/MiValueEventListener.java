package com.david.proyecto.ciclo.siguealciclista;

import android.app.Activity;
import android.app.NotificationManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.david.proyecto.ciclo.siguealciclista.helpers.preferencias;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by wizord on 14/05/16.
 */
public class MiValueEventListener implements ValueEventListener
{
    private final String FIREBASE_URL;
    private Activity activity;
    private NotificationManager notificationManager;
    private DatosFirebase datosFirebase;

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
    }

    @Override
    public void onDataChange(DataSnapshot snapshot)
    {
        String fechaEnQueCambia = null;
        try
        {
            fechaEnQueCambia = snapshot.getValue().toString();

            //Toast.makeText(activity, fechaEnQueCambia, Toast.LENGTH_SHORT).show();
            Log.i(activity.getLocalClassName(), "se produce cambio " + fechaEnQueCambia);

            //notificationManager.notify(0, MisNotificaciones.mostrarNotificacion(activity.getApplicationContext(), fechaEnQueCambia, "2"));
            System.out.println("ruta total " + new Firebase(FIREBASE_URL).child(snapshot.getValue().toString()));

            Log.i("CAMBIOSSS", "onDataChange: " + snapshot);
            Log.i("CAMBIOSSS", "onDataChange: " + snapshot.getKey());
            Log.i("CAMBIOSSS", "onDataChange: " + snapshot.getChildrenCount());
            Log.i("CAMBIOSSS", "onDataChangeruta: " + snapshot.getValue());
            String fechaCambios = (String) snapshot.getValue();
            if (fechaCambios != null)
            {
                //TODO cuando se produzca el cambio hay q buscar ese valor en la raiz de la ruta
                //necesito la fecha para poner el valor en la linea de abajo y así obtener los datos
                Firebase f = new Firebase(FIREBASE_URL + "/" + preferencias.getRuta(activity.getApplicationContext()) + "/" + fechaCambios);
                f.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot snapshot)
                    {
                        try
                        {
                            Toast.makeText(activity.getApplicationContext(), snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                            //TODO aqui meteriamos en la BBDD
                        } catch (Exception e)
                        {
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error){}
                });
            }
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e)
        {
            // Si falla, crearemos la rama para poder trabajar
            Log.e(activity.getLocalClassName(), "Error en [MiValueEventListener.onDataChange].  Creamos la rama");
            new ConectarFirebase(activity.getApplicationContext(), preferencias.getRuta(activity.getApplicationContext()))
                    .crearActual();
        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError)
    {
        Log.e(activity.getLocalClassName(), "Error en [MiValueEventListener.onCancelled]");
    }
}
