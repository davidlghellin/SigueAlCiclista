package com.david.proyecto.ciclo.siguealciclista.firebase;

import com.david.proyecto.ciclo.siguealciclista.Coordenadas;

/**
 * @author David López González on 4/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 * @deprecated
 */
public class DatosFirebase
{
    /**
     * vbl donde almacenaremos las coordenadas (latitud,longitud)
     */
    private Coordenadas coordenadas;
    /**
     * vbl donde especificaremos el nombre de usuario
     */
    private String usuario;

    // constructores
    public DatosFirebase(){}

    public DatosFirebase(String usuario, Coordenadas coordenadas)
    {
        this.usuario = usuario;
        this.coordenadas = coordenadas;
    }

    // getters
    public Coordenadas getCoordenadas()
    {
        return coordenadas;
    }

    public String getUsuario()
    {
        return usuario;
    }

    // setters
    public void setCoordenadas(Coordenadas coordenadas)
    {
        this.coordenadas = coordenadas;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    @Override
    public String toString()
    {
        return "DatosFirebase{" + "coordenadas=" + coordenadas + ", usuario='" + usuario + '\'' + '}';
    }
}
