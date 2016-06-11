package com.david.proyecto.ciclo.siguealciclista.hilos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.GPS;
import com.david.proyecto.ciclo.siguealciclista.firebase.ConectarFirebase;
import com.david.proyecto.ciclo.siguealciclista.helpers.FechaHelper;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;

import java.util.Date;

/**
 * @author David López González on 7/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 * @deprecated
 */
public class MarcarRuta implements Runnable
{
    private GPS gps;
    private boolean continuaHilo = true;
    private ConectarFirebase conectarFirebase;
    private Context context;

    //bbdd
    private SQLiteDatabase db;
    private ManejadorBD usdbh;

    public MarcarRuta(Context context)
    {
        this.context = context;
        this.gps = new GPS(context);

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
                        new PuntoMapa(FechaHelper.converterFecha(new Date()), Preferencias.getRuta(context), Preferencias.getUsuario(context), gps.getCoordenadas()));
                conectarFirebase.subirDatos(gps.getCoordenadas(), new Date());
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
