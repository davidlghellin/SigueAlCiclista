package com.david.proyecto.ciclo.siguealciclista.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author David López González on 8/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class FechaHelper
{
    /**
     * Método de ayuda para transformar una fecha en formato específico
     *
     * @param date Fecha que queremos convertir
     * @return String con el formato especificado "dd-MM-yyyy HH:mm:SS"
     */
    public static String converterFecha(Date date)
    {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
        String strdate = DATE_FORMAT.format(date);
        return strdate;
    }

    /**
     * Permite convertir un String en fecha (Date).
     *
     * @param fecha Cadena de fecha HH:mm:SS dd-MM-yyyy
     * @return Objeto Date
     */
    public static Date ParseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
        Date fechaDate = null;
        try
        {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }
}
