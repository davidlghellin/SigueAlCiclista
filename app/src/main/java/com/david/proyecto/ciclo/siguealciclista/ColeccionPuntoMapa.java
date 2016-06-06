package com.david.proyecto.ciclo.siguealciclista;

import android.os.Environment;
import android.util.Log;

import com.david.proyecto.ciclo.siguealciclista.BBDD.PuntoMapa;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wizord on 6/06/16.
 */
public class ColeccionPuntoMapa implements Serializable
{
    private static final long serialVersionUID = 8799656478674716638L;
    List<PuntoMapa> lista;

    ColeccionPuntoMapa()
    {
        lista = new LinkedList<>();
    }

    public ColeccionPuntoMapa(List<PuntoMapa> lista)
    {
        this.lista = lista;
    }

    public List<PuntoMapa> getLista()
    {
        return lista;
    }

    public void setLista(List<PuntoMapa> lista)
    {
        this.lista = lista;
    }

    public void anyadir(PuntoMapa puntoMapa)
    {
        lista.add(puntoMapa);
    }

    public int tamanyo()
    {
        return lista.size();
    }

    public void serializar()
    {
       try
        {
            File directorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File fichero = new File(directorio + "/" + timeStamp);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));

            oos.writeObject(this);
            oos.close();
            Log.i("ColeccionPuntoMapa","[ColeccionPuntoMapa.serializar]");
        } catch (IOException e)
        {
            e.printStackTrace();
            Log.e("ColeccionPuntoMapa","[ColeccionPuntoMapa.serializar]");
        }
    }
}
