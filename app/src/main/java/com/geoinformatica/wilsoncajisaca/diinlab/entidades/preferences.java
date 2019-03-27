package com.geoinformatica.wilsoncajisaca.diinlab.entidades;

import android.content.Context;
import android.content.SharedPreferences;

public class preferences {

    public static final String STRING_PREFERENCE="wilsoncajisaca.diinlab";
    public static final String PREFERENCE_ESTADO_BUTTOM="estado_boton";
    public static final String PREFERENCE_ESTADO_SYNC="estado_sync";
    public static final String USER_PREFERENCE_LOGIN="user.login";
    public static final String USER_EMAIL_LOGIN="user.email.login";

    public static void savePreferenceBoolean(Context c, boolean b,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        preferences.edit().putBoolean(key,b).apply();
    }

    public static void savePreferenceBooleanContact(Context c, boolean b,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        preferences.edit().putBoolean(key,b).apply();
    }

    public static void savePreferenceString(Context c, String b,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }

    public static boolean obtenerPreferenceBoolean(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        return preferences.getBoolean(key,false);//si no se a guardado nada
    }

    public static boolean obtenerPreferenceBooleanContact(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        return preferences.getBoolean(key,true);//si no se a guardado nada
    }

    public static String obtenerPreferenceString(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCE,c.MODE_PRIVATE);
        return preferences.getString(key,"");//si no se a guardado nada
    }

}
