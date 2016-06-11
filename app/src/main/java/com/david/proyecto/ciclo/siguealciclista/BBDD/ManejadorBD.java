package com.david.proyecto.ciclo.siguealciclista.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.david.proyecto.ciclo.siguealciclista.Coordenadas;
import com.david.proyecto.ciclo.siguealciclista.helpers.Preferencias;

import java.util.ArrayList;

/**
 * @author David López González on 14/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
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
            Log.i("ManejadorBD", "[ManejadorBD.onCreate]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.onCreate]");
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
            Log.i("BBDD", "[ManejadorBD.onUpgrade]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.onUpgrade]");
        }
    }

    public void borrarBBDD(SQLiteDatabase db)
    {
        try
        {
            db.execSQL("DROP TABLE IF EXISTS PuntoMapa;");
            db.execSQL(bbdd());
            Log.i("ManejadorBD", "[ManejadorBD.borrarBBDD]");
        } catch (Exception e)
        {
            Log.i("ManejadorBD", "[ManejadorBD.borrarBBDD]");
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
            Log.i("ManejadorBD", "[ManejadorBD.getDatos]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.getDatos]");
        }
        return datos;
    }

    public ArrayList<PuntoMapa> getDatosRuta(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = new ArrayList<>();
        try
        {
            Cursor c = db.rawQuery("SELECT * FROM PuntoMapa WHERE ruta= '" + Preferencias.getRuta(context) + "' ORDER BY fecha", null);
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
            Log.i("ManejadorBD", "[ManejadorBD.getDatosRuta]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.getDatosRuta]");
        }

        return datos;
    }

    /**
     * comprueba si hay con esa ruta(grupo)
     *
     * @param db
     * @param puntoMapa
     * @return
     */
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

    private boolean comprobarRutaGrupoHora(SQLiteDatabase db, PuntoMapa puntoMapa)
    {
        Cursor c = db.rawQuery("SELECT * FROM PuntoMapa WHERE " +
                "ruta = \"" + puntoMapa.getRuta() + "\" " +
                "AND fecha = \"" + puntoMapa.getFecha() + "\"", null);
        if (c.getCount() <= 0)
            return false;

        return true;
    }

    public boolean insertarCoordenadaComprobandoBBDD(SQLiteDatabase db, PuntoMapa p)
    {
        if (!comprobarCoordenada(db, p))//no existe insertamos
        {
            if (p.getFecha() == null || p.getRuta() == null || p.getUser() == null)
                return false;
            try
            {
                insertarPuntoMapa(db, p);
                Log.i("ManejadorBD", "[ManejadorBD.insertarCoordenadaComprobandoBBDD]");
            } catch (Exception e)
            {
                Log.e("ManejadorBD", "[ManejadorBD.insertarCoordenadaComprobandoBBDD]");
                return false;
            }
            return true;
        }
        return false;
    }

    public void insertarCoordenada(SQLiteDatabase db, PuntoMapa p)
    {
        if (p.getFecha() == null || p.getRuta() == null || p.getUser() == null || comprobarRutaGrupoHora(db, p))
            return;
        try
        {
            insertarPuntoMapa(db, p);
            Log.i("ManejadorBD", "[ManejadorBD.insertarCoordenada]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.insertarCoordenada]");
            return;
        }
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
            Log.i("ManejadorBD", "[ManejadorBD.insertarPuntoMapa]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.insertarPuntoMapa]");
        }
    }

    public void verDatosSinRepetir(SQLiteDatabase db)
    {
        try
        {
            ArrayList<PuntoMapa> datos = getDatosRuta(db);
            PuntoMapa aux = new PuntoMapa(new Coordenadas(0.f, 0.f));
            for (PuntoMapa p : datos)
            {
                if (p.distintos(aux))
                {
                    aux.setCoordenadas(p.getCoordenadas());
                }
            }
            Log.i("ManejadorBD", "[ManejadorBD.verDatosSinRepetir]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.verDatosSinRepetir]");
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

    public ArrayList<PuntoMapa> getPuntoMapaRutaSinRepetir(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = getDatosRuta(db);
        ArrayList<PuntoMapa> datosReturn = new ArrayList<>();

        for (PuntoMapa p : datos)
        {
            datosReturn.add(p);
        }
        return datosReturn;
    }

    /**
     * Método para ver en la consola todos los registros de la base de datos
     *
     * @param db Base de datos
     */
    public void verDatos(SQLiteDatabase db)
    {
        try
        {
            ArrayList<PuntoMapa> datos = getDatos(db);
            for (PuntoMapa p : datos)
            {
                System.out.println(p);
            }
            Log.i("ManejadorBD", "[ManejadorBD.verDatos]");
        } catch (Exception e)
        {
            Log.e("ManejadorBD", "[ManejadorBD.verDatos]");
        }
    }

    /**
     * Método para ver en la consola los registros de la base de datos de la ruta configurada
     *
     * @param db Base de datos
     */
    public void verDatosRuta(SQLiteDatabase db)
    {
        ArrayList<PuntoMapa> datos = getDatosRuta(db);
        for (PuntoMapa p : datos)
        {
            System.out.println( "----"+ p);
        }
    }

    public String getUltimoUsuario(SQLiteDatabase db)
    {
        Cursor c = db.rawQuery("SELECT user FROM PuntoMapa WHERE " +
                "ruta = \"" + Preferencias.getRuta(context) + "\" " +
                "ORDER BY \"" + Preferencias.getRuta(context) + "\" DESC LIMIT 1", null);

        c.moveToFirst();
        return c.getString(0);
    }
    public String getUltimoUsuario2(SQLiteDatabase db)
    {
        Cursor c = db.rawQuery("SELECT user FROM PuntoMapa WHERE " +
                "ruta = \"" + Preferencias.getRuta(context) + "\" " +
                "ORDER BY \"" + Preferencias.getRuta(context) + "\" LIMIT 1", null);

        c.moveToFirst();
        return c.getString(0);
    }
}
