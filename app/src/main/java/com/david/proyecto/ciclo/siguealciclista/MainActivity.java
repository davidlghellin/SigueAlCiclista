package com.david.proyecto.ciclo.siguealciclista;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.david.proyecto.ciclo.siguealciclista.BBDD.ManejadorBD;
import com.david.proyecto.ciclo.siguealciclista.BBDD.UtilsBBDD;
import com.david.proyecto.ciclo.siguealciclista.helpers.preferencias;
import com.david.proyecto.ciclo.siguealciclista.preferencias.MisFragmentPreferencias;
import com.firebase.client.Firebase;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    private String FIREBASE_URL = "https://sigue-al-ciclista.firebaseio.com/";

    @Bind(R.id.editText)
    EditText text;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.btnEnCabeza)
    Button btnEnCabeza;


    private Firebase myFirebaseRef;
    private NotificationManager notificationManager;

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

        //myFirebaseRef = new Firebase(FIREBASE_URL).child(FIREBASE_COORDENADAS);
        // myFirebaseRef = new Firebase(FIREBASE_URL).child(preferencias.getRuta(getApplicationContext()) + "/Actual");
        myFirebaseRef = new Firebase(FIREBASE_URL).child(preferencias.getRutaActual(getApplicationContext()));

        actualizarRutaAct();
        //myFirebaseRef.addValueEventListener(new MiValueEventListener(MainActivity.this, FIREBASE_URL, notificationManager));

        ManejadorBD usdbh = new ManejadorBD(this, "SigueAlCiclista", null, UtilsBBDD.versionSQL());
        SQLiteDatabase db = usdbh.getWritableDatabase();
        usdbh.verDatos(db);
        // UtilsBBDD.borrarDatosSQL(db);
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


    /**
     * Volvemos a la actividad
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        actualizarRutaAct();
    }

    /**
     * Método de actualiza el MiValueEventListener
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
                Log.i("Listener", "Actualizamos datos en actualizarRutaAct");
                myFirebaseRef.addValueEventListener(new MiValueEventListener(MainActivity.this, FIREBASE_URL, notificationManager));
                ok = true;
            } catch (Exception e)
            {
                ok = false;
                Log.e("Listener", "Error en actualizarRutaAct");
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
