package com.david.proyecto.ciclo.siguealciclista;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * David López González on 8/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class Mapa
{
    private Context context;
    private GoogleMap mMap;
    private Activity activity;
    private int intRMapa = 0;

    public Mapa(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;

        GPS gpsActual = new GPS(context);
        LatLng latLng = new LatLng(gpsActual.getCoordenadas().getLatitud(), gpsActual.getCoordenadas().getLongitud());
        inicializarMapa(latLng);
        Log.i("Mapa", "Mapa: " + gpsActual.getCoordenadas());
    }

    /**
     * Constructor especificando el elemento id del mapa
     *
     * @param activity
     * @param intRMapa
     */
    public Mapa(Activity activity, int intRMapa)
    {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.intRMapa = intRMapa;

        GPS gpsActual = new GPS(context);
        LatLng latLng = new LatLng(gpsActual.getCoordenadas().getLatitud(), gpsActual.getCoordenadas().getLongitud());
        inicializarMapa(latLng);
        Log.i("Mapa", "Mapa: " + gpsActual.getCoordenadas());
    }

    public Mapa(Context context, GoogleMap mMap, Activity activity)
    {
        this.mMap = mMap;
        this.context = context;
        this.activity = activity;

        GPS gpsActual = new GPS(context);
        LatLng latLng = new LatLng(gpsActual.getCoordenadas().getLatitud(), gpsActual.getCoordenadas().getLongitud());
        inicializarMapa(latLng);
        Log.i("Mapa", "Mapa: " + gpsActual.getCoordenadas());
    }

    private void inicializarMapa(LatLng latLng)
    {
        // Configuramos el objeto GoogleMaps con valores iniciales.
        if (mMap == null)
        {
            // Instanciamos el objeto mMap a partir del MapFragment definido bajo el Id "map"
            if (intRMapa == 0)  // comprobamos con 0, puede darse el caso q la comprobación sea fallida random
                mMap = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map)).getMap();
            else
                mMap = ((MapFragment) activity.getFragmentManager().findFragmentById(intRMapa)).getMap();

            // Chequeamos si se ha obtenido correctamente una referencia al objeto GoogleMap
            if (mMap != null)
            {
                // El objeto GoogleMap ha sido referenciado correctamente
                // ahora podemos manipular sus propiedades

                // Seteamos el tipo de mapa
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                /*
                GoogleMap.MAP_TYPE_NONE - Una cuadrícula vacía sin azulejos mapeo mostradas.
                GoogleMap.MAP_TYPE_NORMAL - La vista estándar que consiste en la hoja de ruta clásica.
                GoogleMap.MAP_TYPE_SATELLITE - Muestra las imágenes de satélite de la región del mapa.
                GoogleMap.MAP_TYPE_HYBRID - Muestra las imágenes de satélite con los mapas de carreteras superpuestas.
                GoogleMap.MAP_TYPE_TERRAIN - Muestra información topográfica como líneas de contorno y colores.
                */

                // Permisos asociados
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }

                //Activamos la capa o layer MyLocation
                mMap.setMyLocationEnabled(true);

                //http://www.joellipman.com/articles/google/android/application-development/basic-android-app-using-google-maps-and-current-location.html
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        //.target(ALQUERIAS)      // Sets the center of the map to LatLng (refer to previous snippet)
                        .target(latLng)
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }

    /**
     * Agregar marcador en la posición pasada como parámetro, con un título y una descrición
     *
     * @param position
     * @param titulo   título del punto resaltado
     * @param info     Información descriptiva del punto resaltado
     */
    public void setMarker(LatLng position, String titulo, String info)
    {
        // Agregamos marcadores para indicar sitios de interéses.
        Marker myMaker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(titulo)  //Agrega un titulo al marcador
                .snippet(info)   //Agrega información detalle relacionada con el marcador
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))); //Color del marcador
        Log.i("Mapa", "[Mapa.setMarker]");
    }

    /**
     * Método para pintar las líneas marcadas
     *
     * @param options
     */
    public void drawPolilyne(PolylineOptions options)
    {
        Polyline polyline = mMap.addPolyline(options);
        Log.i("Mapa", "[Mapa.drawPolilyne]");
        //    mapa.drawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.RED));
    }

    public void marcarRuta(LatLng puntoA, LatLng puntoB)
    {
        this.drawPolilyne(new PolylineOptions().add(puntoA).add(puntoB));
        Log.i("Mapa", "[Mapa.marcarRuta]");
    }

    public void marcarRuta(LatLng puntoA, LatLng puntoB, int color)
    {
        this.drawPolilyne(new PolylineOptions().add(puntoA).add(puntoB).color(color));
        Log.i("Mapa", "[Mapa.marcarRuta]");
    }
}
