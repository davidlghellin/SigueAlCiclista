package com.david.proyecto.ciclo.siguealciclista.helpers;

import android.content.Context;

/**
 * Created by wizord on 21/05/16.
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
