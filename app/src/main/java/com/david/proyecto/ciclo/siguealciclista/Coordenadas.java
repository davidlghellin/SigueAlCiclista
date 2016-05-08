package com.david.proyecto.ciclo.siguealciclista;

/**
 * Created by wizord on 26/04/16.
 */
public class Coordenadas
{
    private float longitud;
    private float latitud;

    public Coordenadas()
    {
    }

    public Coordenadas(float longitud, float latitud)
    {
        this.longitud = longitud;
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

    public float getLatitud()
    {
        return latitud;
    }

    public void setLatitud(float latitud)
    {
        this.latitud = latitud;
    }

    @Override
    public String toString()
    {
        return "Coordenadas{" + "longitud=" + longitud + ", latitud=" + latitud + '}';
    }
}
