package com.david.proyecto.ciclo.siguealciclista.actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.david.proyecto.ciclo.siguealciclista.R;
import com.firebase.client.Firebase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GestionarBBDD extends AppCompatActivity
{
    @Bind(R.id.btnEnviarDatosACorreo)
    Button btnEnCabeza;

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
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        //String[] to = direccionesEmail;
        //String[] cc = copias;
        //emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        emailIntent.putExtra(Intent.EXTRA_CC, new String[] {""});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "texto");
        //emailIntent.setType("message/rfc822");
        emailIntent.setType("text/plain");
        //emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(Environment.DIRECTORY_PICTURES+"/a.txt"));
        //emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file://storage/emulated/0/a.txt"));
        File directorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File archivo = new File(directorio + "/"+ new SimpleDateFormat("yyyyMMdd").format(new Date()));
        emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(archivo));
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }

}
