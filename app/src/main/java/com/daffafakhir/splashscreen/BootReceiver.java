package com.daffafakhir.splashscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("BootReceiver", "Perangkat telah dihidupkan ulang.");

            SharedPreferences sharedPreferences = context.getSharedPreferences("PrayerReminderPrefs", Context.MODE_PRIVATE);
            SharedPreferences prayerTimesPrefs = context.getSharedPreferences("PrayerTimes", Context.MODE_PRIVATE);

            if (sharedPreferences.getBoolean("subuh", false)) {
                String subuhTime = prayerTimesPrefs.getString("subuh", "05:00");
                PrayerReminderHelper.setPrayerReminder(context, "subuh", subuhTime);
            }
            if (sharedPreferences.getBoolean("dzuhur", false)) {
                String dzuhurTime = prayerTimesPrefs.getString("dzuhur", "12:00");
                PrayerReminderHelper.setPrayerReminder(context, "dzuhur", dzuhurTime);
            }
            if (sharedPreferences.getBoolean("ashar", false)) {
                String asharTime = prayerTimesPrefs.getString("ashar", "15:00");
                PrayerReminderHelper.setPrayerReminder(context, "ashar", asharTime);
            }
            if (sharedPreferences.getBoolean("maghrib", false)) {
                String maghribTime = prayerTimesPrefs.getString("maghrib", "18:00");
                PrayerReminderHelper.setPrayerReminder(context, "maghrib", maghribTime);
            }
            if (sharedPreferences.getBoolean("isya", false)) {
                String isyaTime = prayerTimesPrefs.getString("isya", "19:30");
                PrayerReminderHelper.setPrayerReminder(context, "isya", isyaTime);
            }
        }
    }

}
