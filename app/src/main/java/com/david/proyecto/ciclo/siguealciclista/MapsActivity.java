package com.david.proyecto.ciclo.siguealciclista;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
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

    ArrayList<PuntoMapa> datosUnicos;

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
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        GPS gps = new GPS(getApplicationContext());
        LatLng latLng = new LatLng(gps.getCoordenadas().getLatitud(), gps.getCoordenadas().getLongitud());
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


        //gdrawPolilyne(new PolylineOptions().add(latLng).add(ALQUERIAS).color(Color.RED));
        pintarRuta();
    }

    /**
     * Método para pintar las líneas marcadas
     *
     * @param options
     */
    public void drawPolilyne(PolylineOptions options)
    {
        mMap.addPolyline(options);
    }


    private void pintarRuta()
    {
        ManejadorBD usdbh = new ManejadorBD(this, "SigueAlCiclista", null, UtilsBBDD.versionSQL());
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //  usdbh.verDatos(db);
        //  ArrayList<PuntoMapa> datos = usdbh.getDatos(db);
        //  usdbh.verDatosSinRepetir(db);
        //datosUnicos = usdbh.getPuntoMapaSinRepetir(db);
        datosUnicos = usdbh.getPuntoMapaRutaSinRepetir(db);
        if (datosUnicos.size() > 0)
            mMap.addMarker(new MarkerOptions().position(new LatLng(datosUnicos.get(0).getCoordenadas().getLatitud(), datosUnicos.get(0).getCoordenadas().getLongitud())).title("Salida"));
        for (int i = 0; i < datosUnicos.size() - 2; i++)
        {
            LatLng l1 = new LatLng(datosUnicos.get(i).getCoordenadas().getLatitud(), datosUnicos.get(i).getCoordenadas().getLongitud());
            LatLng l2 = new LatLng(datosUnicos.get(i + 1).getCoordenadas().getLatitud(), datosUnicos.get(i + 1).getCoordenadas().getLongitud());
            drawPolilyne(new PolylineOptions().add(l1).add(l2));
            System.out.println(datosUnicos.get(i) + " wwwww");
        }
    }
}
