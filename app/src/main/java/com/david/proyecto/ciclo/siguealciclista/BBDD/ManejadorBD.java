package com.david.proyecto.ciclo.siguealciclista.BBDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        System.out.println("create");
        db.execSQL(bbdd());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        System.out.println("actualizamos");
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS PuntoMapa");
        //Se crea la nueva versión de la tabla
        db.execSQL(bbdd());
    }

    public void borrarBBDD(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS PuntoMapa");
        db.execSQL(bbdd());
    }

    public String bbdd()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(" CREATE TABLE PuntoMapa");
        sb.append(" (");
        sb.append("     fecha 	    DATE PRIMARY KEY,");
        sb.append("     ruta 	    VARCHAR NOT NULL,");
        sb.append("     user 	    VARCHAR NOT NULL,");
        sb.append("     longitud 	REAL NOT NULL,");
        sb.append("     latitud 	REAL NOT NULL,");
        sb.append(" );");

        return sb.toString();
    }
}
