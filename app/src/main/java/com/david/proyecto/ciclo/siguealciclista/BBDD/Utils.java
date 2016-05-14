package com.david.proyecto.ciclo.siguealciclista.BBDD;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wizord on 14/05/16.
 */
public class Utils
{

    /**
     * método para que sea coherente los números de versiones
     *
     * @return
     */
    public static int versionSQL()
    {
        return 1;
    }

    /**
     * método para insertar un dato en la BBDD
     *
     * @param db
     * @param p
     * @return
     */
    public static boolean insertSQL(SQLiteDatabase db, PuntoMapa p)
    {
        if (p.getFecha() == null || p.getRuta() == null || p.getUser() == null)
            return false;
        try
        {
            db.execSQL("INSERT INTO PuntoMapa " +
                    " (fecha,ruta,user,longitud,latitud)" +
                    " VALUES ('" + p.getFecha() + "','" +
                    p.getRuta() + "','" +
                    p.getUser() + "','" +
                    p.getCoordenadas().getLongitud() + "','" +
                    p.getCoordenadas().getLatitud() +
                    "');");
        } catch (Exception e)
        {
            return false;
        }
        return true;
    }

    /**
     * método para borrar/limpiar los datos de la base de datos
     *
     * @param db
     * @return
     */
    public static boolean borrarDatosSQL(SQLiteDatabase db)
    {
        try
        {
            db.execSQL("DELETE FROM PuntoMapa;");
        } catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
