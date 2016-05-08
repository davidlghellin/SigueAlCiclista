package com.david.proyecto.ciclo.siguealciclista;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Created by wizord on 7/05/16.
 */
public class MarcarRuta implements Runnable
{
    private GPS gps;
    private boolean continuaHilo = true;
    private ConectarFirebase conectarFirebase;
    private Context context;
    private String nombreUsuario;
    private SharedPreferences prefs;

    public MarcarRuta(Context context)
    {
        this.context = context;
        this.gps = new GPS(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        nombreUsuario = prefs.getString("user", "user" + (int)(Math.random()*1000));
        conectarFirebase= new ConectarFirebase(context,prefs.getString("ruta", "rutaPorDefecto"));
    }

    public boolean isContinuaHilo()
    {
        return continuaHilo;
    }

    public void setContinuaHilo(boolean continuaHilo)
    {
        this.continuaHilo = continuaHilo;
    }

    @Override
    public void run()
    {
        while (continuaHilo)
        {
            try
            {
                gps.guardarLogCoordenadas();
                conectarFirebase.subirDatos(gps.getCoordenadas(),new Date());
                Thread.sleep(5000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
