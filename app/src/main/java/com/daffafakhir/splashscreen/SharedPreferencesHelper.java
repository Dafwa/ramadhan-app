package com.daffafakhir.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String PREFS_NAME = "JadwalPrefs";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Simpan jadwal berdasarkan key (misal: "sahur", "berbuka")
    public void setJadwal(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Ambil jadwal yang tersimpan, jika belum ada default "00:00"
    public String getJadwal(String key) {
        return sharedPreferences.getString(key, "00:00");
    }
}
