package com.david.proyecto.ciclo.siguealciclista.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by wizord on 14/05/16.
 */
public class preferencias
{
    //preferencias.getUsuario(getApplicationContext())
    public static String getUsuario(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("user", "defecto_nombre");

    }

    public static String getRuta(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("ruta", "defecto_ruta");
    }
}
