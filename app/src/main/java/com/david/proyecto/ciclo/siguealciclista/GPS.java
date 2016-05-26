package com.david.proyecto.ciclo.siguealciclista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * David López González on 7/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class GPS
{
    //http://www.coordenadas-gps.com/
    /**
     * vbl para el manejo de las coordenadas
     */
    private Coordenadas coordenadas;

    private LocationManager locManager;
    private LocationListener locListener;
    private Location loc;
    private Context context;

    /**
     * Constructor pasando el contexto de la actividad
     *
     * @param context
     */
    public GPS(Context context)
    {
        this.context = context;
        coordenadas = new Coordenadas();
        comenzarLocalizacion();
        Log.i("GPS", "Constructor GPS: " + coordenadas);
    }

    /**
     * Método que devuelve las coordenadas actualizadas
     * @return Las coordenadas actualizadas
     */
    public Coordenadas getCoordenadas()
    {
        actualizarCoordenadas();
        return coordenadas;
    }

    /**
     * Método que inicializa y prepara lo necesario para usar el GPS
     */
    private void comenzarLocalizacion()
    {
        //Obtenemos una referencia al LocationManager
        locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // Criteria req = new Criteria();
        // req.setAccuracy(Criteria.ACCURACY_FINE);
        //req.setAltitudeRequired(true);

        // String provedor = locManager.getBestProvider(req, false);
        //Location loc = locManager.getLastKnownLocation(provedor);

        //Mostramos la última posición conocida
        guardarLogCoordenadas();

        //Nos registramos para recibir actualizaciones de la posición
        locListener = new LocationListener()
        {
            public void onLocationChanged(Location location)
            {//no actualizamos
                //guardarLogCoordenadas(location);
            }

            public void onProviderDisabled(String provider)
            {
                //lblEstado.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider)
            {
                //lblEstado.setText("Provider ON ");
            }

            public void onStatusChanged(String provider, int status, Bundle extras)
            {
                Log.i("", "Provider Status: " + status);
                // lblEstado.setText("Provider Status: " + status);
            }
        };
        //tiempo de actualizacion 2000
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locListener);
        //locManager.requestLocationUpdates(provedor, 2000, 0, locListener);
    }

    /**
     * Guarda las coordenadas en el log
     */
    private void guardarLogCoordenadas()
    {
        Log.i("GPS", "Las coordenadas son:" + coordenadas.toString());
    }

    /**
     * Actualiza las coordenadas
     */
    public void actualizarCoordenadas()
    {
        if (loc != null)
        {
            coordenadas.setLatitud(Float.parseFloat(String.valueOf(loc.getLatitude())));
            coordenadas.setLongitud(Float.parseFloat(String.valueOf(loc.getLongitude())));
            guardarLogCoordenadas();
        }
    }
}
