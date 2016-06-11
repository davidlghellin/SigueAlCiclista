package com.david.proyecto.ciclo.siguealciclista.preferencias;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.david.proyecto.ciclo.siguealciclista.R;

/**
 * @author David López González on 8/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class Usuario extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.usuario);
    }
}
