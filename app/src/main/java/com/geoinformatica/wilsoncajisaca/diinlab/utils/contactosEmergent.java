package com.geoinformatica.wilsoncajisaca.diinlab.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.geoinformatica.wilsoncajisaca.diinlab.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class contactosEmergent {
    Context context;

    public contactosEmergent(Context context) {
        this.context = context;
    }

    public void llamar(String mobile,String nombre) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setPackage("com.android.server.telecom");
            intent.setData(Uri.parse("tel:" + mobile));
            context.startActivity(intent);
            Toast.makeText(context, "Llamando a: "+nombre, Toast.LENGTH_LONG).show();
    }

    public void sendEmail(String correo) {

        try {

            String[] TO = {correo}; //aquí pon tu correo
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enviado desde DiinLab App");
            context.startActivity(Intent.createChooser(emailIntent, "Enviar email..."));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }

    }

    public void whatsapp(String phone){

        String mobile = phone.replace(" ", "+");

        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + mobile);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public void perfilpersona(String nombre, String telefono, String correo) {

        String mobile = telefono.replace(" ", "+");
        Dialog MyDialog = new Dialog(context);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MyDialog.setContentView(R.layout.dialog_profile_menu);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(MyDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        MyDialog.getWindow().setAttributes(lp);

        ImageView perfil = MyDialog.findViewById(R.id.perfil);
        TextView TopLlamar=MyDialog.findViewById(R.id.TopLlamar);
        TextView txtcorreo = MyDialog.findViewById(R.id.correo);
        TextView txttelefono = MyDialog.findViewById(R.id.telefono);
        TextView txtnombre = MyDialog.findViewById(R.id.nombre);
        TextView txtwhatsapp = MyDialog.findViewById(R.id.whatsapp);

        //generador de colores material desing
        //ColorGenerator generator = ColorGenerator.MATERIAL;

        Drawable round_drawable = TextDrawable.builder().buildRound("" + nombre.charAt(0), Color.rgb(189, 195, 199));

        perfil.setImageDrawable(round_drawable);

        txtcorreo.setText(correo);
        txtnombre.setText(nombre);
        txttelefono.setText(mobile);

        TopLlamar.setOnClickListener(v -> llamar(telefono,nombre));
        txtwhatsapp.setOnClickListener(v ->whatsapp(telefono));
        txtcorreo.setOnClickListener(v -> sendEmail(correo));

        MyDialog.show();
    }

    //Convierto el primer caracter en Mayuscula
    public static String StringsEvent(String mTitle) {
        if (mTitle == null || mTitle.isEmpty()) {
            return mTitle;
        } else {
            return  Character.toUpperCase(mTitle.charAt(0)) + mTitle.substring(1, mTitle.length()).toLowerCase();
        }
    }

    //Obtengo el mes y el año para mostrar en el calendario dinamico
    public static String getEventTitle(Calendar date) {
        return String.format(date.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault())+" "
                +date.get(Calendar.YEAR));
    }

    public String getDiferencia(Date fechaInicial, Date fechaFinal){

        long diferencia = fechaFinal.getTime() - fechaInicial.getTime();

        Log.i("Invitados", "fechaInicial : " + fechaInicial);
        Log.i("Invitados", "fechaFinal : " + fechaFinal);

        long segsMilli = 1000;
        long minsMilli = segsMilli * 60;
        long horasMilli = minsMilli * 60;
        long diasMilli = horasMilli * 24;

        long diasTranscurridos = diferencia / diasMilli;
        diferencia = diferencia % diasMilli;

        long horasTranscurridos = diferencia / horasMilli;
        diferencia = diferencia % horasMilli;

        long minutosTranscurridos = diferencia / minsMilli;
        diferencia = diferencia % minsMilli;

        long segsTranscurridos = diferencia / segsMilli;

        return horasTranscurridos+"."+minutosTranscurridos;
    }

}

