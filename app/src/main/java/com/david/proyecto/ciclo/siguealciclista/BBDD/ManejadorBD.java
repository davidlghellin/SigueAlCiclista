package com.david.proyecto.ciclo.siguealciclista.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by wizord on 14/05/16.
 */
public class ManejadorBD extends SQLiteOpenHelper
{
    // Constructor obligatorio
    public ManejadorBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("BBDD","La base de datos se ha creado");
        db.execSQL(bbdd());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("BBDD","La base de datos se ha actualizado");
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS PuntoMapa;");
        //Se crea la nueva versión de la tabla
        db.execSQL(bbdd());
    }

    public void borrarBBDD(SQLiteDatabase db)
    {
        Log.i("BBDD","La base de datos se ha borrado");
        db.execSQL("DROP TABLE IF EXISTS PuntoMapa;");
        db.execSQL(bbdd());
    }


    public String bbdd()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(" CREATE TABLE PuntoMapa");
        sb.append(" (");
        sb.append("     fecha 	    VARCHAR PRIMARY KEY,");
        sb.append("     ruta 	    VARCHAR NOT NULL,");
        sb.append("     user 	    VARCHAR NOT NULL,");
        sb.append("     longitud 	REAL NOT NULL,");
        sb.append("     latitud 	REAL NOT NULL");
        sb.append(" );");

        return sb.toString();
    }

    public void verDatos(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = new ArrayList<>();
        System.out.println("DATOSSSS");
        Cursor c = db.rawQuery("SELECT * FROM PuntoMapa", null);
        if (c.moveToFirst())
        {
            do
            {
                int i = 0;
                PuntoMapa punto = new PuntoMapa();
                punto.setFecha(c.getString(i++));
                punto.setRuta(c.getString(i++));
                punto.setUser(c.getString(i++));
                punto.setCoordenadas(c.getFloat(i++),c.getFloat(i++));

                datos.add(punto);
            } while (c.moveToNext());
        }
        for (PuntoMapa p : datos)
        {
            System.out.println(p);
        }
    }
}
