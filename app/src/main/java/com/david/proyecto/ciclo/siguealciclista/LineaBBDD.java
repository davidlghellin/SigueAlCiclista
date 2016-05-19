package com.david.proyecto.ciclo.siguealciclista;

import java.util.Date;

/**
 * Created by wizord on 7/05/16.
 */
public class LineaBBDD
{
    //TODO de momento no se usa, era para que fuera las tuplas de la bbdd
    private String carrera;
    private Date fecha;
    private String usuario;
    private float latitud;
    private float longitud;

    public LineaBBDD()
    {
    }

    public LineaBBDD(String carrera, Date fecha, String usuario, float latitud, float longitud)
    {
        this.carrera = carrera;
        this.fecha = fecha;
        this.usuario = usuario;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getCarrera()
    {
        return carrera;
    }

    public void setCarrera(String carrera)
    {
        this.carrera = carrera;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public String getUsuario()
    {
        return usuario;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    public float getLatitud()
    {
        return latitud;
    }

    public void setLatitud(float latitud)
    {
        this.latitud = latitud;
    }

    public float getLongitud()
    {
        return longitud;
    }

    public void setLongitud(float longitud)
    {
        this.longitud = longitud;
    }
}
