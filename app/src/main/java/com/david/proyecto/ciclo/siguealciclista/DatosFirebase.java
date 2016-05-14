package com.david.proyecto.ciclo.siguealciclista;

/**
 * Created by wizord on 14/05/16.
 */
public class DatosFirebase
{
    private Coordenadas coordenadas;
    private String usuario;

    public DatosFirebase(String usuario, Coordenadas coordenadas)
    {
        this.usuario = usuario;
        this.coordenadas = coordenadas;
    }

    public DatosFirebase()
    {
    }

    public Coordenadas getCoordenadas()
    {
        return coordenadas;
    }

    public String getUsuario()
    {
        return usuario;
    }

    @Override
    public String toString()
    {
        return "DatosFirebase{" + "coordenadas=" + coordenadas + ", usuario='" + usuario + '\'' + '}';
    }
}
