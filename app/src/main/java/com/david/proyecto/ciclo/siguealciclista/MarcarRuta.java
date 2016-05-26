package com.david.proyecto.ciclo.siguealciclista;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.helpers.fechaHelper;
import com.david.proyecto.ciclo.siguealciclista.helpers.preferencias;

import java.util.Date;

/**
 * David López González on 7/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class MarcarRuta implements Runnable
{
    private GPS gps;
    private boolean continuaHilo = true;
    private ConectarFirebase conectarFirebase;
    private Context context;
    //private String nombreUsuario;

    //bbdd
    private SQLiteDatabase db;
    //private SharedPreferences prefs;
    private ManejadorBD usdbh;
    //private Mapa mapa;

    public MarcarRuta(Context context)
    {
        this.context = context;
        this.gps = new GPS(context);
        //prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //conectarFirebase = new ConectarFirebase(context, prefs.getString("ruta", "rutaPorDefecto"));
        conectarFirebase = new ConectarFirebase(context);

        //BBDD
        usdbh = new ManejadorBD(context, "SigueAlCiclista", null, UtilsBBDD.versionSQL());
        db = usdbh.getWritableDatabase();
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
                gps.actualizarCoordenadas();
                System.out.println(gps.getCoordenadas());
                UtilsBBDD.insertSQL(db,
                        new PuntoMapa(fechaHelper.converterFecha(new Date()), preferencias.getRuta(context), preferencias.getUsuario(context), gps.getCoordenadas()));
                conectarFirebase.subirDatos(gps.getCoordenadas(), new Date());
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
