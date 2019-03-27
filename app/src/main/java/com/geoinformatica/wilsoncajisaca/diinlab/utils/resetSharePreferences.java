package com.geoinformatica.wilsoncajisaca.diinlab.utils;

import android.content.Context;

import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;

public class resetSharePreferences {

    Context context;

    public resetSharePreferences (Context context){
        this.context=context;
    }

    public void resetPreferences(){
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtAtencion);

        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtEmocion);

        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo1);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo2);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo3);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo4);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo5);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo6);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo7);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo8);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo9);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtMiedo10);

        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtRecordacion);

        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_txtValor);

        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_NOMBRE_MODELO);

        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_retos);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_confort);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_trasender);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_control);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_reconocimiento);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_placer);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_seguridad);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_dominacion);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_logro);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_pertenecer);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_explorar);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_cb_libertad);

        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_CB_ninos);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_CB_jovenes);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_J_masculino);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_J_femenino);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_A_MASCULINO);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_A_mujer);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_A_madre);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_CB_adultos);
        preferencesNeuroCiencia.savePreferenceString(context,null,preferencesNeuroCiencia.PREFERENCE_CB_senior);

    }

}
