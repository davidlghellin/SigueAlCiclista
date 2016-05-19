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
    /**
     * Método que devuelve el nombre de la ruta
     * @param context Contexto de la actividad donde se hace la llamada
     * @return String de la ruta en Firebase
     */
    public static String getRuta(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("ruta", "defecto_ruta");
    }

    /**
     * Método que devuelve el nombre de la ruta y el child  concatenado "Actual"
     * @param context Contexto de la actividad donde se hace la llamada
     * @return String de la ruta en Firebase
     */
    public static String getRutaActual(Context context)
    {
        return getRuta(context) + "/Actual";
    }
}
