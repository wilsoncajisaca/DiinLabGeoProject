package com.geoinformatica.wilsoncajisaca.diinlab.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VerificationPackage {

    String nomPaquete="com.google.android.calendar";
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public VerificationPackage(Context context){
        this.context=context;
    }

    public boolean GoogleCalendar() {

        PackageManager pm = context.getPackageManager();

        try {
            pm.getPackageInfo(nomPaquete, PackageManager.GET_ACTIVITIES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void AppNoEncontrada() {

        new MaterialStyledDialog.Builder(context)
                .setTitle("¡ S U G E R E N C I A !")
                .setDescription("Para un correcto funcionamiento de la aplicacion es necesario que " +
                        "tenga instalado \"Google Calendar\"")
                .setIcon(R.drawable.googlecalendar)
                .setHeaderColor(R.color.shimmer_color)
                .withIconAnimation(true)
                .withDialogAnimation(true)
                .setPositiveText("Descargar Google Calendar")
                .onPositive((dialog, which) ->{
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + nomPaquete)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + nomPaquete)));
                    }
                })
                .setNegativeText("Omitir")
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    public void addEventGoogleCalendar(boolean allday, String title, String Descripcion, String lugar,
                                       String fStart, String hStart, String fStop, String hEnd) {

        try {
            ComponentName cnGoogleCalendar= new ComponentName(nomPaquete, "com.android.calendar.LaunchActivity");

            Calendar fDesde = Calendar.getInstance();
            fDesde.setTime(dateFormat.parse(fStart+" "+hStart));
            Calendar fHasta = Calendar.getInstance();
            fHasta.setTime(dateFormat.parse(fStop+" "+hEnd));

            long startTime = fDesde.getTimeInMillis();
            long endTime = fHasta.getTimeInMillis();

            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);

            intent.putExtra(CalendarContract.Events.ALL_DAY, allday);
            intent.putExtra(CalendarContract.Events.TITLE, title);
            intent.putExtra(CalendarContract.Events.DESCRIPTION, Descripcion);
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, lugar);
            intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE,"America/Guayaquil");

            intent.setComponent(cnGoogleCalendar);

            context.startActivity(intent);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "No se encontro Google Calendar...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
