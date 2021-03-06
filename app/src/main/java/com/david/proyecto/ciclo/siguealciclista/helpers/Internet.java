package com.david.proyecto.ciclo.siguealciclista.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author David López González on 1/06/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class Internet
{
    /**
     * Método que comprueba si tenemos conexión a internet.
     *
     * @param context Contexto de la actividad.
     * @return True si tenemos conexión a internet, False en caso contrario.
     */
    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }

        return false;
    }
}
