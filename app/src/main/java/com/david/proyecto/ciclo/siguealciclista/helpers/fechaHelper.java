package com.david.proyecto.ciclo.siguealciclista.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wizord on 8/05/16.
 */
public class fechaHelper
{
    /**
     * Método de ayuda para transformar una fecha en formato específico
     * @param date Fecha que queremos convertir
     * @return String con el formato especificado "HH:mm:SS dd-MM-yyyy"
     */
    public static String converterFecha(Date date)
    {
        SimpleDateFormat  DATE_FORMAT = new SimpleDateFormat("HH:mm:SS dd-MM-yyyy");
        String strdate = DATE_FORMAT.format(date);
        return strdate;
    }
}
