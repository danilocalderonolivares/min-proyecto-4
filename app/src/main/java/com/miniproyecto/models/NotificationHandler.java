package com.miniproyecto.models;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

public class NotificationHandler extends ContextWrapper {
    private NotificationManager notificationManager;
    public static final String CHANNEL_HIGH_ID = "1";
    private final String CHANNEL_HIGH_NAME = "HIGH CHANNEL";

    public NotificationHandler(Context context) {
        super(context);
        createChannels();
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= 29) {
            NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);
            highChannel.setShowBadge(true); // Este lo que hace es colocar un punto sobre el icono de la app cuando nuevas notificaciones
            highChannel.enableVibration(true);
            highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(highChannel);
        }
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public Notification.Builder createNotification(Reminder reminder) {
        return new Notification.Builder(getApplicationContext(), CHANNEL_HIGH_ID)
                .setContentTitle("Your reminder")
                .setContentText(reminder.description)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true);
    }
}
