package com.david.proyecto.ciclo.siguealciclista.servicios;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.SystemClock;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.ConectarFirebase;
import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.GPS;
import com.david.proyecto.ciclo.siguealciclista.MarcarRuta;
import com.david.proyecto.ciclo.siguealciclista.helpers.GetContext;
import com.david.proyecto.ciclo.siguealciclista.helpers.fechaHelper;
import com.david.proyecto.ciclo.siguealciclista.helpers.preferencias;

import java.util.Date;

public class MarcarRutaService extends IntentService
{
    // TODO segundo plano
    private Thread hilo;
    private GPS gps;
    private boolean continuaHilo = true;

    private ManejadorBD usdbh;
    private SQLiteDatabase db;
    private Coordenadas coordenadasAux;
    //MarcarRuta marcarRuta;

    private ConectarFirebase conectarFirebase;

    public MarcarRutaService() throws InterruptedException
    {
        super("MarcarRutaService");
        coordenadasAux = new Coordenadas();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Context context = GetContext.getContext();
        this.gps = new GPS(context);
        conectarFirebase = new ConectarFirebase(context);
        usdbh = new ManejadorBD(this, "SigueAlCiclista", null, UtilsBBDD.versionSQL());
        db = usdbh.getWritableDatabase();

        //no hace falta usar el marcar ruta lo haremos aquí
        //marcarRuta = new MarcarRuta(context);
        //hilo = new Thread(marcarRuta);
        // hilo.start();
        while (true)
        {
            SystemClock.sleep(3000);
            gps.actualizarCoordenadas();
            //TODO comprobar antes de subir
            if (!coordenadasAux.compararCoordenadas(gps.getCoordenadas()))
            {
                // UtilsBBDD.insertSQL(db,new PuntoMapa(fechaHelper.converterFecha(new Date()), preferencias.getRuta(context), preferencias.getUsuario(context), gps.getCoordenadas()));
                conectarFirebase.subirDatos(gps.getCoordenadas(), new Date());
            }
            coordenadasAux = gps.getCoordenadas();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //marcarRuta.setContinuaHilo(false);
    }
}
