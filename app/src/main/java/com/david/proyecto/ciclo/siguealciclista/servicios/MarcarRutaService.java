package com.david.proyecto.ciclo.siguealciclista.servicios;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.firebase.ConectarFirebase;
import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.GPS;
import com.david.proyecto.ciclo.siguealciclista.helpers.GetContext;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;

import java.util.Date;

public class MarcarRutaService extends IntentService
{
    // TODO segundo plano
    private GPS gps;

    private ManejadorBD usdbh;
    private SQLiteDatabase db;
    private Coordenadas coordenadasAux;

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
        usdbh = new ManejadorBD(this, Preferencias.getNombreFirebase(), null, UtilsBBDD.versionSQL());
        db = usdbh.getWritableDatabase();

        while (true)
        {
            gps.actualizarCoordenadas();
            //TODO comprobar antes de subir
           // if (!coordenadasAux.compararCoordenadas(gps.getCoordenadas()))
            {
                // UtilsBBDD.insertSQL(db,new PuntoMapa(FechaHelper.converterFecha(new Date()), Preferencias.getRuta(context), Preferencias.getUsuario(context), gps.getCoordenadas()));
                conectarFirebase.subirDatos(gps.getCoordenadas(), new Date());
                coordenadasAux = gps.getCoordenadas();
            }
            SystemClock.sleep(3000);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //marcarRuta.setContinuaHilo(false);
    }
}
