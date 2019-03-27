package com.geoinformatica.wilsoncajisaca.diinlab.entidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class preferencesNeuroCiencia {

    public static final String STRING_PREFERENCE="wilsoncajisaca.diinlab";

    //Guardamos el nombre del modelo
    public static final String PREFERENCE_NOMBRE_MODELO="nombre_modelo";

    //Guardamos los estados de los C_BX Y RB pantalla "NeuroSegmentacion"
    public static final String PREFERENCE_CB_ninos="estado_ninos";
    public static final String PREFERENCE_CB_jovenes="estado_jovenes";
    public static final String PREFERENCE_CB_adultos="estado_adultos";
    public static final String PREFERENCE_CB_senior="estado_senior";
    public static final String PREFERENCE_J_masculino="estado_Jmasculino";
    public static final String PREFERENCE_J_femenino="estado_Jfemenino";
    public static final String PREFERENCE_A_mujer="estado_Amujer";
    public static final String PREFERENCE_A_MASCULINO="estado_Amasculino";
    public static final String PREFERENCE_A_madre="estado_Amadre";
    //////////////////////////////////////////////////////////////

    //Guardamos los estados de los C_BX pantalla "BtnInstitivos"
    public static final String PREFERENCE_cb_retos="estado_retos";
    public static final String PREFERENCE_cb_confort="estado_confort";
    public static final String PREFERENCE_cb_trasender="estado_trasender";
    public static final String PREFERENCE_cb_control="estado_control";
    public static final String PREFERENCE_cb_reconocimiento="estado_reconocimiento";
    public static final String PREFERENCE_cb_placer="estado_placer";
    public static final String PREFERENCE_cb_seguridad="estado_seguridad";
    public static final String PREFERENCE_cb_dominacion="estado_dominacion";
    public static final String PREFERENCE_cb_logro="estado_logro";
    public static final String PREFERENCE_cb_pertenecer="estado_pertenecer";
    public static final String PREFERENCE_cb_explorar="estado_explorar";
    public static final String PREFERENCE_cb_libertad="estado_libertad";
    //////////////////////////////////////////////////////////////////

    //Guardamos los miedos
    public static final String PREFERENCE_txtMiedo1="miedo1";
    public static final String PREFERENCE_txtMiedo2="miedo2";
    public static final String PREFERENCE_txtMiedo3="miedo3";
    public static final String PREFERENCE_txtMiedo4="miedo4";
    public static final String PREFERENCE_txtMiedo5="miedo5";
    public static final String PREFERENCE_txtMiedo6="miedo6";
    public static final String PREFERENCE_txtMiedo7="miedo7";
    public static final String PREFERENCE_txtMiedo8="miedo8";
    public static final String PREFERENCE_txtMiedo9="miedo9";
    public static final String PREFERENCE_txtMiedo10="miedo10";

    //Guardamos el texto escrito por el usuario pantalla Atencion...
    public static final String PREFERENCE_txtAtencion="nombre_atencion";

    //Guardamos el texto escrito por el usuario pantalla Emocion...
    public static final String PREFERENCE_txtEmocion="nombre_emocion";

    //Guardamos el texto escrito por el usuario pantalla Recordacion...
    public static final String PREFERENCE_txtRecordacion="nombre_recordacion";

    //Guardamos el texto escrito por el usuario pantalla Valor Simbolico...
    public static final String PREFERENCE_txtValor="nombre_valor";


    public static void savePreferenceBooleanC_BX(Context c, boolean b, String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        preferences.edit().putBoolean(key,b).apply();
    }

    public static void savePreferenceBooleanRB(Context c, boolean b, String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        preferences.edit().putBoolean(key,b).apply();
    }

    public static void savePreferenceString(Context c, String b,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }

    public static boolean obtenerPreferenceBooleanC_BX(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        return preferences.getBoolean(key,false);//si no se a guardado nada
    }

    public static boolean obtenerPreferenceBooleanRB(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        return preferences.getBoolean(key,false);//si no se a guardado nada
    }

    public static String obtenerPreferenceString(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        return preferences.getString(key,null);//si no se a guardado nada
    }

}
