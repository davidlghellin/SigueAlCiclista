package com.david.proyecto.ciclo.siguealciclista.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.GoogleMap;

/**
 * David López González on 14/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class Preferencias
{
    /**
     * Método que devuelve el nombre padre de la BBDD Firebase
     *
     * @return Directorio padre de Firebase
     */
    public static String getNombreFirebase()
    {
        return "SigueAlCiclista";
    }

    /**
     * Método que devuelve el nombre del usuario de la aplicación definidas en las Preferencias
     *
     * @param context Contexto de la actividad donde se hace la llamada
     * @return Nombre de usuario
     */
    public static String getUsuario(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("user", "defecto_nombre");
    }

    /**
     * Método que devuelve el nombre de la ruta, definidas en las Preferencias
     *
     * @param context Contexto de la actividad donde se hace la llamada
     * @return String de la ruta en Firebase
     */
    public static String getRuta(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return getGrupo(context) +" "+ prefs.getString("ruta", "defecto_ruta");
    }

    /**
     * Método que devuelve el nombre del grupo de carrera, definidads en las Preferencias
     *
     * @param context
     * @return
     */
    private static String getGrupo(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("grupo", "grupo_ruta");
    }

    /**
     * Método que devuelve el nombre de la ruta y el child  concatenado "Actual"
     *
     * @param context Contexto de la actividad donde se hace la llamada
     * @return String de la ruta en Firebase
     */
    public static String getRutaActual(Context context)
    {
        return  getRuta(context) + "/Actual";
    }
}
