package com.david.proyecto.ciclo.siguealciclista.preferencias;

import android.preference.PreferenceActivity;

import com.david.proyecto.ciclo.siguealciclista.R;

import java.util.List;

/**
 * @author Created by David López González on 8/05/16.
 *         Proyecto ciclo DAM I.E.S Alquerías
 */
public class MisFragmentPreferencias extends PreferenceActivity
{
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.prefereces_header, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        if (Usuario.class.getName().equals(fragmentName))
            return true;
        else if (Ruta.class.getName().equals(fragmentName))
            return true;

        return false;
    }
}
