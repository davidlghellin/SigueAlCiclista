package com.david.proyecto.ciclo.siguealciclista.helpers;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;

import com.david.proyecto.ciclo.siguealciclista.R;

/**
 * David López González on 11/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class MisNotificaciones
{
    public static Notification mostrarNotificacion(Context context, String titulo, String texto)
    {
        System.out.println("Titulo:::   " + titulo);
        System.out.println("Texto:::   " + texto);


        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_ciclista)
                .setContentTitle(titulo)
                .setContentText(texto)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setLights(Color.RED, 3000, 1000)
                .setAutoCancel(true)
                .build();
    }
}
