package com.david.proyecto.ciclo.siguealciclista.preferencias;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.david.proyecto.ciclo.siguealciclista.R;
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
 * Created by wizord on 8/05/16.
 */
public class Mapa
{

    private GoogleMap mMap;



    /**
     * Agregar marcador en la posición
     * @param position
     * @param titulo título del punto resaltado
     * @param info Información descriptiva del punto resaltado
     */
    private void setMarker(LatLng position, String titulo, String info)
    {
        // Agregamos marcadores para indicar sitios de interéses.
        Marker myMaker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(titulo)  //Agrega un titulo al marcador
                .snippet(info)   //Agrega información detalle relacionada con el marcador
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))); //Color del marcador
    }
    /**
     * Método para pintar las líneas marcadas
     * @param options
     */
    private void drawPolilyne(PolylineOptions options)
    {
        Polyline polyline = mMap.addPolyline(options);
    }
}
