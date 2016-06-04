package com.david.proyecto.ciclo.siguealciclista.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.GPS;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;
import com.firebase.client.Firebase;
import com.david.proyecto.ciclo.siguealciclista.helpers.FechaHelper;

import java.util.Date;

/**
 * David López González on 8/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class ConectarFirebase
{
    /**
     * Dirección donde se encuentra la BBDD firebase
     */
    private String FIREBASE_URL = "https://sigue-al-ciclista.firebaseio.com/";
    private Firebase myFireNombreRuta;
    private Context context;
    private String textoRuta;
    //private GPS gps;


    private String nombreUsuario;
    //private SharedPreferences prefs;

    /**
     * @param context
     * @param rutaBBDD nombre de la ruta
     */
    public ConectarFirebase(Context context, String rutaBBDD)
    {
        this.textoRuta = rutaBBDD;
        this.context = context;
        Firebase.setAndroidContext(context);
        myFireNombreRuta = new Firebase(FIREBASE_URL);

        //prefs = PreferenceManager.getDefaultSharedPreferences(context);
        nombreUsuario = Preferencias.getUsuario(context);
    }

    /**
     *
     * @param context
     */
    public ConectarFirebase(Context context)
    {
        this.textoRuta = Preferencias.getRuta(context);
        this.context = context;
        Firebase.setAndroidContext(context);
        myFireNombreRuta = new Firebase(FIREBASE_URL);

        //prefs = PreferenceManager.getDefaultSharedPreferences(context);
        nombreUsuario = Preferencias.getUsuario(context);
    }

    public void subirDatosPunto(Coordenadas coordenadas)
    {
        String strfecha = FechaHelper.converterFecha(new Date());
        if (coordenadas != null)
        {
            myFireNombreRuta.child(textoRuta).child("Actual").setValue(strfecha);
            myFireNombreRuta.child(textoRuta).child(strfecha).child("Longitud").setValue(coordenadas.getLongitud());
            myFireNombreRuta.child(textoRuta).child(strfecha).child("Latitud").setValue(coordenadas.getLatitud());
            myFireNombreRuta.child(textoRuta).child(strfecha).child("User").setValue(nombreUsuario);
            myFireNombreRuta.child(textoRuta).child(strfecha).child("Critico").setValue("si");

            Log.i("ConectarFirebase","[ConectarFirebase.subirDatosPunto]");
        }
    }

    public void subirDatos(Coordenadas coordenadas, Date fecha)
    {
        String strfecha = FechaHelper.converterFecha(fecha);
        if (coordenadas != null)
        {
            myFireNombreRuta.child(textoRuta).child("Actual").setValue(strfecha);
            myFireNombreRuta.child(textoRuta).child(strfecha).child("Longitud").setValue(coordenadas.getLongitud());
            myFireNombreRuta.child(textoRuta).child(strfecha).child("Latitud").setValue(coordenadas.getLatitud());
            myFireNombreRuta.child(textoRuta).child(strfecha).child("User").setValue(nombreUsuario);

            Log.i("ConectarFirebase","[ConectarFirebase.subirDatos]");
        }
    }

    public void subirPosicionUsuario(Coordenadas coordenadas)
    {
        if (coordenadas != null)
        {
           // myFireNombreRuta.child(textoRuta).child("Usuarios").setValue(nombreUsuario);
            myFireNombreRuta.child(textoRuta).child("Usuarios").child(nombreUsuario).child("Longitud").setValue(coordenadas.getLongitud());
            myFireNombreRuta.child(textoRuta).child("Usuarios").child(nombreUsuario).child("Latitud").setValue(coordenadas.getLatitud());

            Log.i("ConectarFirebase","[ConectarFirebase.subirPosicionUsuario]");
        }
    }

    public void subirDatos(Coordenadas coordenadas, String fecha)
    {
        if (coordenadas != null)
        {
            myFireNombreRuta.child(textoRuta).child("Actual").setValue(fecha);
            myFireNombreRuta.child(textoRuta).child(fecha).child("Longitud").setValue(coordenadas.getLongitud());
            myFireNombreRuta.child(textoRuta).child(fecha).child("Latitud").setValue(coordenadas.getLatitud());
            myFireNombreRuta.child(textoRuta).child(fecha).child("User").setValue(nombreUsuario);

            Log.i("ConectarFirebase","[ConectarFirebase.subirDatos]");
        }
    }

    public void subirDatos()
    {
        GPS gps = new GPS(context);
        Coordenadas coordenadas = gps.getCoordenadas();
        subirDatosPunto(coordenadas);
    }

    /**
     * Método que crea en Firebase la Key = "Actual" con valor la fecha y hora con el siguiente formato "dd-MM-yyyy HH:mm:SS"
     */
    public void crearActual()
    {
        myFireNombreRuta.child(textoRuta).child("Actual").setValue(FechaHelper.converterFecha(new Date()));

        Log.i("ConectarFirebase","[ConectarFirebase.crearActual]");
    }

    /**
     * Método que elimina todos los datos de Firebase
     */
    public void resetFirebase()
    {
        myFireNombreRuta.removeValue();

        Log.i("ConectarFirebase","[ConectarFirebase.resetFirebase]");
    }
}
