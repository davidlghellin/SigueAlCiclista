package com.david.proyecto.ciclo.siguealciclista;

import android.app.Activity;
import android.app.NotificationManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by wizord on 14/05/16.
 */
public class MiValueEventListener implements ValueEventListener
{
    private Activity activity;
    private String FIREBASE_URL;
    NotificationManager notificationManager;

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
        String fechaEnQueCambia = snapshot.getValue().toString();
        Toast.makeText(activity, fechaEnQueCambia, Toast.LENGTH_SHORT).show();
        Log.e(activity.getLocalClassName(), "se produce cambio " + fechaEnQueCambia);
        System.out.println(fechaEnQueCambia);
        notificationManager.notify(0, MisNotificaciones.mostrarNotificacion(activity.getApplicationContext(), fechaEnQueCambia, "2"));
        System.out.println("ruta total " + new Firebase(FIREBASE_URL).child(snapshot.getValue().toString()));
//                System.out.println("query: "+new Firebase(FIREBASE_URL).child(FIREBASE_RUTA).);

//                DatosFirebase datosFirebase=snapshot.getValue(DatosFirebase.class);
//                System.out.println(datosFirebase);
//                System.out.println(datosFirebase.getUsuario()+"------");

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError)
    {

    }
}
