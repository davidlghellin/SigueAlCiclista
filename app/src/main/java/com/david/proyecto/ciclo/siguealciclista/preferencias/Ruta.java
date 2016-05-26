package com.david.proyecto.ciclo.siguealciclista.preferencias;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.david.proyecto.ciclo.siguealciclista.R;

/**
 * David López González on 8/05/16.
 * Proyecto ciclo DAM I.E.S Alquerías
 */
public class Ruta extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ruta);
    }
}
