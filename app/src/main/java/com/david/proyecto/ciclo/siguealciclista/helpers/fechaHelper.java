package com.david.proyecto.ciclo.siguealciclista.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wizord on 8/05/16.
 */
public class fechaHelper
{
    public static String converterFecha(Date date)
    {
        SimpleDateFormat  DATE_FORMAT = new SimpleDateFormat("HH:mm:SS dd-MM-yyyy");
        String strdate = DATE_FORMAT.format(date);
        return strdate;
    }
}
