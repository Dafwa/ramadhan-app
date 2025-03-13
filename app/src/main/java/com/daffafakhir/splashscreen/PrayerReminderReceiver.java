package com.daffafakhir.splashscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;

public class PrayerReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            showNotification(context);
        }
    }

    private void showNotification(Context context) {
        String channelId = "prayer_reminder";
        String channelName = "Pengingat Sholat";

        NotificationManager manager = (NotificationManager) context.getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Ganti dengan icon sendiri jika ada
                .setContentTitle("Waktunya Sholat!")
                .setContentText("Jangan lupa sholat tepat waktu.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (manager != null) {
            manager.notify(1, builder.build());
        }
    }
}
