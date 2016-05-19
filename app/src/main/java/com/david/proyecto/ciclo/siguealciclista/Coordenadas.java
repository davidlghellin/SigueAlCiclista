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
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordenadas that = (Coordenadas) o;

        if (Float.compare(that.longitud, longitud) != 0) return false;
        return Float.compare(that.latitud, latitud) == 0;

    }



    @Override
    public int hashCode()
    {
        int result = (longitud != +0.0f ? Float.floatToIntBits(longitud) : 0);
        result = 31 * result + (latitud != +0.0f ? Float.floatToIntBits(latitud) : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Coordenadas{" + "longitud=" + longitud + ", latitud=" + latitud + '}';
    }
}
