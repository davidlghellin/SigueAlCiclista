package com.david.proyecto.ciclo.siguealciclista.BBDD;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * David López González on 14/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class UtilsBBDD
{
    /**
     * Método para que sea coherente los números de versiones y tengamos en todos las llamadas el mismo número de versión
     *
     * @return
     */
    public static int versionSQL()
    {
        return 3;
    }

    /**
     * Método para insertar un dato en la BBDD
     *
     * @param db BBDD con la que trabajamos SQLiteDatabase
     * @param p PuntoMapa que queremos insertar en la BBDD
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
            Log.i("BBDD","Añadimos registro a BBDD");
        } catch (Exception e)
        {
            Log.e("BBDD","Error al añadir registro a BBDD"+db);
            return false;
        }
        return true;
    }



    /**
     * método para borrar/limpiar los datos de la base de datos
     *
     * @param db Referencia a la BBDD del tipo SQLiteDatabase
     * @return
     */
    public static boolean borrarDatosSQL(SQLiteDatabase db)
    {
        try
        {
            Log.i("BBDD","Eliminamos registros BBDD");
            db.execSQL("DELETE FROM PuntoMapa;");
        } catch (Exception e)
        {
            Log.e("BBDD","Error al eliminar registros BBDD");
            return false;
        }
        return true;
    }
}
