package com.david.proyecto.ciclo.siguealciclista.actividades;

import android.app.NotificationManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.ColeccionPuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.Mapa;
import com.david.proyecto.ciclo.siguealciclista.R;
import com.david.proyecto.ciclo.siguealciclista.firebase.eventos.MiChildEventListener;
import com.david.proyecto.ciclo.siguealciclista.firebase.eventos.MiValueEventListener;
import com.david.proyecto.ciclo.siguealciclista.helpers.GetContext;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;
import com.david.proyecto.ciclo.siguealciclista.preferencias.MisFragmentPreferencias;
import com.david.proyecto.ciclo.siguealciclista.servicios.MarcarUsuariosService;
import com.firebase.client.Firebase;


import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    private String FIREBASE_URL = "https://sigue-al-ciclista.firebaseio.com/";
    private String FIREBASE_RUTA;

    @Bind(R.id.btnEnCabeza)
    Button btnEnCabeza;


    private Firebase myFirebaseRef;
    private Firebase myFirebaseEvent;
    private MiChildEventListener miChildEventListener;

    private NotificationManager notificationManager;

    private Mapa mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        ButterKnife.bind(this);
        findViewById(R.id.relativeLayoutPrincipal).setBackgroundColor(Color.BLUE);
        // findViewById(R.id.relativeLayoutPrincipal).setBackground(getResources().getDrawable(R.drawable.boton_cuircular));


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // myFirebaseRef = new Firebase(FIREBASE_URL).child(Preferencias.getRuta(getApplicationContext()) + "/Actual");
        myFirebaseRef = new Firebase(FIREBASE_URL).child(Preferencias.getRutaActual(getApplicationContext()));
        FIREBASE_RUTA = FIREBASE_URL + Preferencias.getRuta(getApplicationContext());

        //actualizarRutaAct();

        // Servicio para subir los datos de todos los usuarios
        Intent intent = new Intent(getApplicationContext(), MarcarUsuariosService.class);
        GetContext.setContext(getApplicationContext());
        startService(intent);


        ManejadorBD usdbh = new ManejadorBD(this, Preferencias.getNombreFirebase(), null, UtilsBBDD.versionSQL());
        SQLiteDatabase db = usdbh.getWritableDatabase();
        usdbh.verDatos(db);
        List<PuntoMapa> lista = usdbh.getPuntoMapaRutaSinRepetir(db);
        ColeccionPuntoMapa coleccionPuntoMapa=new ColeccionPuntoMapa(lista);
        coleccionPuntoMapa.serializar();
        // Reset
        //UtilsBBDD.borrarDatosSQL(db);

        mapa = new Mapa(this, R.id.mapPrincipal);

        //Firebase myFirebaseEvent = new Firebase(FIREBASE_URL + Preferencias.getRuta(getApplicationContext()));
        //myFirebaseEvent.addChildEventListener(new MiChildEventListener(MainActivity.this));

    }

    // Menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.MnuOpc1:
                startActivity(new Intent(getApplicationContext(), MisFragmentPreferencias.class));
                break;
            case R.id.MnuOpc2:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                break;
            case R.id.MnuOpc3:
                startActivity(new Intent(getApplicationContext(), GestionarBBDD.class));
                break;
            case R.id.MnuOpc0:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();  // TODO finish daba errores (sin tener las lineas anteriores)
                // android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
        actualizarRutaAct();
        return true;
    }
    // Fin menú

    public void actualizarChildEventListener()
    {
        if (myFirebaseEvent == null)
        {
            myFirebaseEvent = new Firebase(FIREBASE_URL + Preferencias.getRuta(getApplicationContext()));
            miChildEventListener = new MiChildEventListener(MainActivity.this);
            myFirebaseEvent.addChildEventListener(miChildEventListener);
        }
        // Hemos cambiado de ruta y/o grupo
        if (!FIREBASE_RUTA.equals(FIREBASE_URL + Preferencias.getRuta(getApplicationContext())))
        {
            // Quitamos el eventListener
            myFirebaseEvent.removeEventListener(miChildEventListener);
            // Actualizamos la ruta a la conexión de Firebase
            FIREBASE_RUTA = FIREBASE_URL + Preferencias.getRuta(getApplicationContext());
            myFirebaseEvent = new Firebase(FIREBASE_RUTA);
            // Creamos el eventlistener
            miChildEventListener = new MiChildEventListener(MainActivity.this);
            // Asignamos el eventListener
            myFirebaseEvent.addChildEventListener(miChildEventListener);
        }
    }

    /**
     * Volvemos a la actividad
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        //actualizarRutaAct();
        actualizarChildEventListener();
        //TODO revisar esta mal, ya que cada vez que entra a la actividad crea el eventListener
        //Firebase myFirebaseRef2 = new Firebase(FIREBASE_URL + Preferencias.getRuta(getApplicationContext()));
        //myFirebaseRef2.addChildEventListener(new MiChildEventListener(MainActivity.this));
    }

    /**
     * Método de actualiza el MiValueEventListener
     * Versión antigua (donde hacía la trampa)
     */
    private void actualizarRutaAct()
    {
        // TODO: 15/05/16  intentar varias veces
        int i = 0;
        boolean ok = false;
        while (i < 3 && !ok)
        {
            try
            {
                Log.i("MainActivity", "Actualizamos datos en [MainActivity.actualizarRutaAct]");
                myFirebaseRef.addValueEventListener(new MiValueEventListener(MainActivity.this, FIREBASE_URL, notificationManager));
                ok = true;
            } catch (Exception e)
            {
                ok = false;
                Log.e("MainActivity", "Error en [MainActivity.actualizarRutaAct]");
            } finally
            {
                i++;
            }
        }
    }

    @OnClick(R.id.btnEnCabeza)
    void enCabeza()
    {
        startActivity(new Intent(getApplicationContext(), ActivityEnCabeza.class));
    }
}
