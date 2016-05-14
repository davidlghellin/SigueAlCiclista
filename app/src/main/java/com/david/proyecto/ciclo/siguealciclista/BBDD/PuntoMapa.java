package com.david.proyecto.ciclo.siguealciclista.BBDD;

import com.david.proyecto.ciclo.siguealciclista.Coordenadas;

import java.util.Date;

/**
 * Created by wizord on 14/05/16.
 */
public class PuntoMapa
{
    private Date fecha;
    private String ruta;
    private String user;
    private Coordenadas coordenadas;

    public PuntoMapa(){}
    public PuntoMapa(Date fecha, String ruta, String user, Coordenadas coordenadas)
    {
        this.fecha = fecha;
        this.ruta = ruta;
        this.user = user;
        this.coordenadas = coordenadas;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
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
}
