package com.david.proyecto.ciclo.siguealciclista;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.david.proyecto.ciclo.siguealciclista.preferencias.MisFragmentPreferencias;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    private String FIREBASE_URL = "https://sigue-al-ciclista.firebaseio.com/";
    private String DIA = (new Date()).toString();
    private String FIREBASE_COORDENADAS = "Coordenadas";
    private String FIREBASE_RUTA = "Ruta";
    // private String ULTIMA="ultima";
    private Coordenadas coordenadas;
    private Date fechaAux;

    @Bind(R.id.editText)
    EditText text;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.btnEnCabeza)
    Button btnEnCabeza;
    @Bind(R.id.editRutaNueva)
    EditText textRutaNueva;


    private Firebase myFirebaseRef;
    private Firebase myFireNombreRuta;
    private NotificationManager notificationManager;
    String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        ButterKnife.bind(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        coordenadas = new Coordenadas();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        myFirebaseRef = new Firebase(FIREBASE_URL).child(FIREBASE_COORDENADAS);
        myFireNombreRuta = new Firebase(FIREBASE_URL);

        myFirebaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                Toast.makeText(MainActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                Log.e(getLocalClassName(), snapshot.getValue().toString());
                System.out.println(snapshot.getValue());

                Notification notification = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_ciclista)
                        .setContentTitle("Coordenadas")
                        .setContentText("Se suben coordendas nuevas")
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setLights(Color.RED, 3000, 1000)
                        .setAutoCancel(true)
                        .build();

                notificationManager.notify(0, notification);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }

            @Override
            public void onCancelled(FirebaseError error)
            {
            }
        });
        //TODO revisar si aqui o en ActivityEnCabeza
        //new Thread(new MarcarRuta(this)).start();
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
                finish();  //TODO finish daba errores
                // android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }

        return true;
    }

    // Fin menú
   /*@OnClick(R.id.button)
    public void writeToFirebase()
    {
        fechaAux = new Date();
        coordenadas.setLatitud(2);
        coordenadas.setLongitud(23);
        myFirebaseRef.child(fechaAux.toString()).child("Longitud").setValue(coordenadas.getLongitud());
        myFirebaseRef.child(fechaAux.toString()).child("Latitud").setValue(coordenadas.getLatitud());
        text.setText("");

        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_ciclista)
                .setContentTitle("Coordenadas")
                .setContentText("Se suben coordendas nuevas")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setLights(Color.RED, 3000, 1000)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(0, notification);
    }*/

    @OnClick(R.id.btnEnCabeza)
    void enCabeza()
    {
        startActivity(new Intent(getApplicationContext(), ActivityEnCabeza.class));
    }

    @OnClick(R.id.btnAnyadirRutas)
    public void anyadirRutas()
    {
        String textoRuta = textRutaNueva.getText().toString();
        if (textoRuta != null && !textoRuta.equals(""))
        {
            String fecha = new Date().toString();
            myFireNombreRuta.child(textoRuta).child("Actual").setValue(fecha);
            myFireNombreRuta.child(textoRuta).child(fecha).child("Longitud").setValue("1");
            myFireNombreRuta.child(textoRuta).child(fecha).child("Latitud").setValue("2");
            //obtengo t0do
            String textoServidor = myFireNombreRuta.child(textoRuta).child("Actual").child(fecha).getKey();
            Log.e(getLocalClassName(), textoServidor);
            Toast.makeText(MainActivity.this, textoServidor, Toast.LENGTH_SHORT).show();
            //String textoPruebas = myFireNombreRuta.child(textoRuta).child("Actual").child(fecha).getKey().toString();


            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Latitud").getKey());
            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Longitud").getApp());
            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Longitud").getAuth());
            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Longitud").getParent());
            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Longitud").getRoot());
            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Longitud").getRef());
            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Longitud").getSpec().getParams());
            Log.e(getLocalClassName(), "--->" + myFireNombreRuta.child(textoRuta).child(textoServidor).
                    child("Longitud").getSpec().getParams());
        }
    }
}
