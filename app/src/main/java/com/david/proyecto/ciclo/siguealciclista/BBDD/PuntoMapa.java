package com.david.proyecto.ciclo.siguealciclista.BBDD;

import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.helpers.FechaHelper;

import java.io.Serializable;
import java.util.Date;

/**
 * David López González on 14/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class PuntoMapa implements Serializable
{
    private static final long serialVersionUID = 799656478674716638L;
    private String fecha;
    private String ruta;
    private String user;
    private Coordenadas coordenadas;

    public PuntoMapa()
    {
    }

    public PuntoMapa(Coordenadas coordenadas)
    {
        this.coordenadas = coordenadas;
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
        this.fecha = FechaHelper.converterFecha(strFecha);
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

    public boolean distintos(PuntoMapa puntoMapa)
    {
        boolean res = false;

        if (this.getCoordenadas() != null && puntoMapa.getCoordenadas() != null)
            if (this.getCoordenadas().getLongitud() != puntoMapa.getCoordenadas().getLongitud() &&
                    this.getCoordenadas().getLatitud() != puntoMapa.getCoordenadas().getLatitud())
                res = true;
        return res;
    }

    @Override
    public String toString()
    {
        return "PuntoMapa {" + "fecha=" + fecha + ", ruta='" + ruta + '\'' + ", user='" + user + '\'' + ", coordenadas=" + coordenadas + '}';
    }
}
