package com.daffafakhir.splashscreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PrayerReminderHelper {

    public static void setPrayerReminder(Context context, String prayerName, String prayerTime) {
        Log.d("PrayerReminderHelper", "Memanggil setPrayerReminder untuk: " + prayerName + " pada " + prayerTime);

        if (prayerName == null || prayerName.trim().isEmpty() || prayerTime == null || prayerTime.trim().isEmpty()) {
            Log.e("PrayerReminderHelper", "Nama sholat atau waktu tidak boleh kosong!");
            return;
        }

        // Konversi waktu dari API ke Calendar
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        try {
            Date date = sdf.parse(prayerTime);
            if (date != null) {
                calendar.setTime(date);
                calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            }
        } catch (ParseException e) {
            Log.e("PrayerReminderHelper", "Gagal mengonversi waktu sholat: " + prayerTime);
            return;
        }

        // Pastikan alarm diatur ke hari berikutnya jika waktu sudah lewat
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // AlarmManager & PendingIntent
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, PrayerReminderReceiver.class);
        intent.putExtra("prayer_name", prayerName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, prayerName.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Set alarm dengan waktu yang sudah dikonversi
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("PrayerReminderHelper", "Alarm untuk " + prayerName + " dijadwalkan pada: " + calendar.getTime());
        }
    }

    public static void cancelPrayerReminder(Context context, String prayerName) {
        Log.d("PrayerReminderHelper", "Membatalkan pengingat untuk: " + prayerName);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, PrayerReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, prayerName.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.d("PrayerReminderHelper", "Pengingat " + prayerName + " dibatalkan.");
        }
    }


    public static void setCustomReminder(Context context, String prayerName, long triggerTime) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, PrayerReminderReceiver.class);

        // Ubah dari "prayerName" menjadi "prayer_name" agar konsisten
        intent.putExtra("prayer_name", prayerName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, prayerName.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            Log.d("PrayerReminderHelper", "Pengingat " + prayerName + " diatur pada " + new Date(triggerTime));
        }
    }

}
