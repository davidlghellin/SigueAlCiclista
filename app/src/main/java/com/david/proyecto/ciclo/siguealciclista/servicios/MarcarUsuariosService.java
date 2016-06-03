package com.david.proyecto.ciclo.siguealciclista.servicios;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.david.proyecto.ciclo.siguealciclista.ConectarFirebase;
import com.david.proyecto.ciclo.siguealciclista.GPS;
import com.david.proyecto.ciclo.siguealciclista.helpers.GetContext;

import java.util.Date;

public class MarcarUsuariosService extends IntentService
{
    private ConectarFirebase conectarFirebase;
    private GPS gps;

    public MarcarUsuariosService()
    {
        super("MarcarUsuariosService");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Context context = GetContext.getContext();
        this.gps = new GPS(context);
        conectarFirebase = new ConectarFirebase(context);
        while (true)
        {
            try
            {
                gps.actualizarCoordenadas();
                conectarFirebase.subirPosicionUsuario(gps.getCoordenadas());
                Thread.sleep(60000);    // a cada minuto subimos
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
