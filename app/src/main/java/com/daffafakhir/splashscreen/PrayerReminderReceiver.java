package com.daffafakhir.splashscreen;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class PrayerReminderReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "prayer_reminder_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String prayerName = intent.getStringExtra("prayer_name"); // Nama sholat

        // Debugging log
        if (prayerName == null) {
            Log.e("PrayerReminderReceiver", "ERROR: prayerName null! Intent tidak memiliki extra.");
            return; // Hindari crash jika prayerName tetap null
        }

        Log.d("PrayerReminderReceiver", "Menerima pengingat untuk: " + prayerName);
        showNotification(context, prayerName);
    }


    private void showNotification(Context context, String prayerName) {
        if (prayerName == null) {
            Log.e("PrayerReminderReceiver", "ERROR: prayerName masih null di showNotification!");
            return; // Hindari crash jika null
        }

        // Ubah huruf pertama jadi kapital
        String capitalizedPrayerName = capitalizeFirstLetter(prayerName);

        Log.d("PrayerReminderReceiver", "Menampilkan notifikasi untuk: " + capitalizedPrayerName);

        // Buat channel notifikasi
        createNotificationChannel(context);

        // Intent untuk membuka aplikasi saat notifikasi diklik
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Bangun notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_add_alert_24)
                .setContentTitle("Waktunya Sholat!")
                .setContentText("Saatnya sholat " + capitalizedPrayerName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Tampilkan notifikasi
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED) {

            notificationManager.notify(prayerName.hashCode(), builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Prayer Reminder Channel";
            String description = "Notifikasi pengingat waktu sholat";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Mendaftarkan channel ke sistem
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
