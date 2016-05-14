package com.david.proyecto.ciclo.siguealciclista;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

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

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEnCabeza extends AppCompatActivity
{
    //private LocationManager locManager;
    // private LocationListener locListener;
    //  private Location loc;
    private Thread hilo;
    private MarcarRuta marcarRuta;
    private String nombreUsuario;


    private GoogleMap mMap;
    //Barcelona   41°23′20″N  02°09′32″E  UT+02:00   Barcelona
    //public static final LatLng SAGRADA_FAMILIA = new LatLng(41.40347, 2.17432);
    //http://www.coordenadas-gps.com/
    public static final LatLng ALQUERIAS = new LatLng(38.014215, -1.035408);
    GPS gpsActual;
    Mapa mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_en_cabeza);


        ButterKnife.bind(this);
        // GPS
        //comenzarLocalizacion();
        gpsActual = new GPS(getApplicationContext());
        marcarRuta = new MarcarRuta(this);
        hilo = new Thread(marcarRuta);
        hilo.start();

        //mapa
        LatLng latLng = new LatLng(gpsActual.getCoordenadas().getLatitud(), gpsActual.getCoordenadas().getLongitud());
        // inicializarMapa(latLng);
        //mapa = new Mapa(getApplicationContext(), mMap);
        mapa = new Mapa(getApplicationContext(), mMap, this);
        mapa.setMarker(ALQUERIAS, "Alquerías", " Murcia");
        mapa.setMarker(new LatLng(38.014215, -1.036), "PTO2", " Murcssssssia");

        mapa.drawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.RED));
        //setMarker(ALQUERIAS, "Alquerías", " Murcia"); // Agregamos el marcador
        //setMarker(new LatLng(38.014215, -1.036), "PTO2", " Murcia"); // Agregamos el marcador
        //setMarker(new LatLng(38.01422, -1.0365), "PTO3", " Murcia"); // Agregamos el marcador

    }


    @OnClick(R.id.btnCancelarSeguimiento)
    public void cancelarSeguimiento()
    {
        marcarRuta.setContinuaHilo(false);
        finish();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        marcarRuta.setContinuaHilo(false);
    }

    //TODO no usamos esto ahora, ya que tenemos la clase Mapa
    private void inicializarMapa(LatLng latLng)
    {
        // Configuramos el objeto GoogleMaps con valores iniciales.
        if (mMap == null)
        {
            //Instanciamos el objeto mMap a partir del MapFragment definido bajo el Id "map"
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Chequeamos si se ha obtenido correctamente una referencia al objeto GoogleMap
            if (mMap != null)
            {
                // El objeto GoogleMap ha sido referenciado correctamente
                //ahora podemos manipular sus propiedades

                //Seteamos el tipo de mapa
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                /*
GoogleMap.MAP_TYPE_NONE - Una cuadrícula vacía sin azulejos mapeo mostradas.
GoogleMap.MAP_TYPE_NORMAL - La vista estándar que consiste en la hoja de ruta clásica.
GoogleMap.MAP_TYPE_SATELLITE - Muestra las imágenes de satélite de la región del mapa.
GoogleMap.MAP_TYPE_HYBRID - Muestra las imágenes de satélite con los mapas de carreteras superpuestas.
GoogleMap.MAP_TYPE_TERRAIN - Muestra información topográfica como líneas de contorno y colores.
                 */

                // Permisos asociados
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
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
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
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

    //TODO no usamos esto ahora, ya que tenemos la clase Mapa

    /**
     * Agregar marcador en la posición
     *
     * @param position
     * @param titulo   título del punto resaltado
     * @param info     Información descriptiva del punto resaltado
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

    //TODO no usamos esto ahora, ya que tenemos la clase Mapa

    /**
     * Método para pintar las líneas marcadas
     *
     * @param options
     */
    private void drawPolilyne(PolylineOptions options)
    {
        Polyline polyline = mMap.addPolyline(options);
    }
}
