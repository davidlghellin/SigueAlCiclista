package com.david.proyecto.ciclo.siguealciclista;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;


    public static final LatLng ALQUERIAS = new LatLng(38.014215, -1.035408);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pintarRuta();
    }



    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        GPS gps = new GPS(getApplicationContext());
        LatLng latLng=new LatLng(gps.getCoordenadas().getLatitud(), gps.getCoordenadas().getLongitud());
        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(ALQUERIAS).title("Alqerías"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(ALQUERIAS));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                //.target(ALQUERIAS)      // Sets the center of the map to LatLng (refer to previous snippet)
                .target(latLng)
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    /**
     * Método para pintar las líneas marcadas
     *
     * @param options
     */
    public void drawPolilyne(PolylineOptions options)
    {
        Polyline polyline = mMap.addPolyline(options);
    }


    private void pintarRuta()
    {
        ManejadorBD usdbh = new ManejadorBD(this, "SigueAlCiclista", null, Utils.versionSQL());
        SQLiteDatabase db = usdbh.getWritableDatabase();
        usdbh.verDatos(db);
        ArrayList<PuntoMapa> datos=usdbh.getDatos(db);
        usdbh.verDatosSinRepetir(db);

    }
}
