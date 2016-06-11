package com.david.proyecto.ciclo.siguealciclista.helpers;

import android.content.Context;

/**
 * @author David López González on 21/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class GetContext
{
    private static Context context;

    public static Context getContext()
    {
        return context;
    }

    public static void setContext(Context context)
    {
        GetContext.context = context;
    }
}
