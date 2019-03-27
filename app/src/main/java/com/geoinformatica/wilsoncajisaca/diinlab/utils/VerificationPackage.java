package com.geoinformatica.wilsoncajisaca.diinlab.entidades;

import android.content.Context;
import android.content.pm.PackageManager;

public class VerificationPackage {

    Context context;

    public VerificationPackage(Context context){
        this.context=context;
    }

    public boolean GoogleCalendar(String nomPaquete) {

        PackageManager pm = context.getPackageManager();

        try {

            pm.getPackageInfo(nomPaquete, PackageManager.GET_ACTIVITIES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
