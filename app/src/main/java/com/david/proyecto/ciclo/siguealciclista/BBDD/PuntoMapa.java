package com.david.proyecto.ciclo.siguealciclista.BBDD;

import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.helpers.fechaHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wizord on 14/05/16.
 */
public class PuntoMapa
{
    private String fecha;
    private String ruta;
    private String user;
    private Coordenadas coordenadas;

    public PuntoMapa()
    {
    }

    public PuntoMapa(String fecha, String ruta, String user, Coordenadas coordenadas)
    {
        this.fecha = fecha;
        this.ruta = ruta;
        this.user = user;
        this.coordenadas = coordenadas;
    }

    public String getFecha()
    {
        return fecha;
    }

    public void setFecha(Date strFecha)
    {
        this.fecha = fechaHelper.converterFecha(strFecha);
    }

    public void setFecha(String fecha)
    {
        this.fecha = fecha;
    }

    public String getRuta()
    {
        return ruta;
    }

    public void setRuta(String ruta)
    {
        this.ruta = ruta;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public Coordenadas getCoordenadas()
    {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas)
    {
        this.coordenadas = coordenadas;
    }

    public void setCoordenadas(float longitud, float latitud)
    {
        this.coordenadas = new Coordenadas(longitud, latitud);
    }

    @Override
    public String toString()
    {
        return "PuntoMapa {" + "fecha=" + fecha + ", ruta='" + ruta + '\'' + ", user='" + user + '\'' + ", coordenadas=" + coordenadas + '}';
    }
}
