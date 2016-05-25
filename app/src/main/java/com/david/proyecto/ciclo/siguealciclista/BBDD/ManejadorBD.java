package com.david.proyecto.ciclo.siguealciclista.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.helpers.preferencias;

import java.util.ArrayList;

/**
 * Created by wizord on 14/05/16.
 */
public class ManejadorBD extends SQLiteOpenHelper
{
    private Context context;

    // Constructor obligatorio
    public ManejadorBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(bbdd());
            Log.i("ManejadorBD", "La base de datos se ha creado [ManejadorBD.onCreate]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "Error en [ManejadorBD.onCreate]");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        try
        {
            //Se elimina la versión anterior de la tabla
            db.execSQL("DROP TABLE IF EXISTS PuntoMapa;");
            //Se crea la nueva versión de la tabla
            db.execSQL(bbdd());
            Log.i("BBDD", "La base de datos se ha actualizado [ManejadorBD.onUpgrade]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "Error en [ManejadorBD.onUpgrade]");
        }
    }

    public void borrarBBDD(SQLiteDatabase db)
    {
        try
        {
            db.execSQL("DROP TABLE IF EXISTS PuntoMapa;");
            db.execSQL(bbdd());
            Log.i("ManejadorBD", "La base de datos se ha borrado [ManejadorBD.borrarBBDD]");
        } catch (Exception e)
        {
            Log.i("ManejadorBD", "Error en [ManejadorBD.borrarBBDD]");
        }
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

    public ArrayList<PuntoMapa> getDatos(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = new ArrayList<>();
        try
        {
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
                    punto.setCoordenadas(c.getFloat(i++), c.getFloat(i++));

                    datos.add(punto);
                } while (c.moveToNext());
            }
            Log.i("ManejadorBD", "Obtenemos datos [ManejadorBD.getDatos]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "Error en [ManejadorBD.getDatos]");
        }
        return datos;
    }

    public ArrayList<PuntoMapa> getDatosRuta(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = new ArrayList<>();
        try
        {
            Cursor c = db.rawQuery("SELECT * FROM PuntoMapa WHERE ruta= '" + context + "'", null);
            if (c.moveToFirst())
            {
                do
                {
                    int i = 0;
                    PuntoMapa punto = new PuntoMapa();
                    punto.setFecha(c.getString(i++));
                    punto.setRuta(c.getString(i++));
                    punto.setUser(c.getString(i++));
                    punto.setCoordenadas(c.getFloat(i++), c.getFloat(i++));

                    datos.add(punto);
                } while (c.moveToNext());
            }
            Log.i("ManejadorBD", "Obtenemos datos [ManejadorBD.getDatosRuta]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "Error en [ManejadorBD.getDatosRuta]");
        }

        return datos;
    }

    private boolean comprobarCoordenada(SQLiteDatabase db, PuntoMapa puntoMapa)
    {
        Cursor c = db.rawQuery("SELECT * FROM PuntoMapa WHERE " +
                "ruta = \"" + puntoMapa.getRuta() + "\" " +
                "AND longitud = \"" + puntoMapa.getCoordenadas().getLongitud() + "\" " +
                "AND latitud = \"" + puntoMapa.getCoordenadas().getLatitud() + "\"", null);
        if (c.getCount() <= 0)
            return false;

        return true;
    }

    public boolean insertarCoordenada(SQLiteDatabase db, PuntoMapa p)
    {
        if (!comprobarCoordenada(db, p))//no existe insertamos
        {
            if (p.getFecha() == null || p.getRuta() == null || p.getUser() == null)
                return false;
            try
            {
                insertarPuntoMapa(db, p);
                Log.i("ManejadorBD", "Añadimos registro a BBDD [ManejadorBD.insertarCoordenada]");
            } catch (Exception e)
            {
                Log.e("ManejadorBD", "Error en [ManejadorBD.insertarCoordenada]");
                return false;
            }
            return true;
        }
        return false;
    }

    private void insertarPuntoMapa(SQLiteDatabase db, PuntoMapa p)
    {
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
            Log.i("ManejadorBD", "Añadimos registro a BBDD [ManejadorBD.insertarPuntoMapa]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "Error en [ManejadorBD.insertarPuntoMapa]");
        }
    }

    public void verDatosSinRepetir(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = getDatos(db);
        PuntoMapa aux = new PuntoMapa(new Coordenadas(0.f, 0.f));
        for (PuntoMapa p : datos)
        {
            if (p.distintos(aux))
            {
                aux.setCoordenadas(p.getCoordenadas());
            }
        }
    }

    public ArrayList<PuntoMapa> getPuntoMapaSinRepetir(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = getDatos(db);
        ArrayList<PuntoMapa> datosReturn = new ArrayList<>();

        PuntoMapa aux = new PuntoMapa(new Coordenadas(0.f, 0.f));
        for (PuntoMapa p : datos)
        {
            if (p.distintos(aux))
            {
                datosReturn.add(p);
                aux.setCoordenadas(p.getCoordenadas());
            }
        }
        return datosReturn;
    }

    public void verDatos(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = getDatos(db);
        for (PuntoMapa p : datos)
        {
            System.out.println(p);
        }
    }
}
