package com.geoinformatica.wilsoncajisaca.diinlab.services;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.ui.EventDescription;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessageServices extends FirebaseMessagingService {

    int count=0;
    private NotificationManager manager;
    private final int SUMMARY_GROUP_ID= 1001;
    private final String SUMMARY_GROUP_NAME="GROUP_NOTIFICATION";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String id_evento=remoteMessage.getData().get("id_evento");

        showNotification(id_evento);

    }

    public NotificationManager getManager(){
        if (manager==null){
            manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    private void showNotification(String id_evento) {

        Intent i =new Intent(this,EventDescription.class);
        i.putExtra("id","89");
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_CANCEL_CURRENT);

        Uri sound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Builder builder= new Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("Prueba titulo");
        builder.setContentText(id_evento);
        builder.setSound(sound);
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        builder.setContentIntent(pendingIntent);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = getApplicationContext().getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId,   "Canal Oreo", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Dejar las notificaciones en su estado original, para evitar problemas con la app");
            getManager().createNotificationChannel(channel);
            builder.setChannelId(channelId);

        }

        getManager().notify(++count, builder.build());
    }

}