package com.david.proyecto.ciclo.siguealciclista.actividades;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.ColeccionPuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.R;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;
import com.firebase.client.Firebase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author David López González.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class GestionarBBDD extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_bbdd);

        Firebase.setAndroidContext(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnEnviarDatosACorreo)
    public void enviarCorreo()
    {
        try
        {
            ManejadorBD usdbh = new ManejadorBD(this, Preferencias.getNombreFirebase(), null, UtilsBBDD.versionSQL());
            SQLiteDatabase db = usdbh.getWritableDatabase();
            //usdbh.verDatos(db);
            List<PuntoMapa> lista = usdbh.getPuntoMapaRutaSinRepetir(db);
            ColeccionPuntoMapa coleccionPuntoMapa = new ColeccionPuntoMapa(lista);
            coleccionPuntoMapa.serializar();

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_CC, Preferencias.getCorreo(getApplicationContext()));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ruta ciclista");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "El fichero adjuntado son los datos de la carrera " +
                    "perteneciente a la ruta" + Preferencias.getRuta(getApplicationContext()) + ".");
            //emailIntent.setType("message/rfc822");
            emailIntent.setType("text/plain");

            File directorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File archivo = new File(directorio + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(archivo));

            startActivity(Intent.createChooser(emailIntent, "Email "));

            Log.i("GestionarBBDD", "[GestionarBBDD.enviarCorreo]");
        } catch (Exception e)
        {
            Log.e("GestionarBBDD", "[GestionarBBDD.enviarCorreo]");
        }
    }

    @OnClick(R.id.btnResetBBDD)
    public void resetBBDD()
    {
        try
        {
            ManejadorBD usdbh = new ManejadorBD(this, Preferencias.getNombreFirebase(), null, UtilsBBDD.versionSQL());
            SQLiteDatabase db = usdbh.getWritableDatabase();
            usdbh.borrarBBDD(db);
            Log.i("GestionarBBDD", "[GestionarBBDD.resetBBDD]");
        } catch (Exception e)
        {
            Log.e("GestionarBBDD", "[GestionarBBDD.resetBBDD]");
        }
    }
}
