package com.daffafakhir.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Calendar;

public class PengingatFragment extends Fragment {

    private Switch switchSubuh, switchDzuhur, switchAshar, switchMaghrib, switchIsya;
    private SharedPreferences sharedPreferences;

    public PengingatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengingat, container, false);

        // Inisialisasi Switch
        switchSubuh = view.findViewById(R.id.switch_subuh);
        switchDzuhur = view.findViewById(R.id.switch_dzuhur);
        switchAshar = view.findViewById(R.id.switch_ashar);
        switchMaghrib = view.findViewById(R.id.switch_maghrib);
        switchIsya = view.findViewById(R.id.switch_isya);

        // Inisialisasi SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("PrayerReminderPrefs", Context.MODE_PRIVATE);

        // Panggil fungsi ini untuk memuat status switch
        loadSwitchStates();

        // Tambahkan listener untuk menyimpan status saat switch diubah
        switchSubuh.setOnCheckedChangeListener(this::saveSwitchState);
        switchDzuhur.setOnCheckedChangeListener(this::saveSwitchState);
        switchAshar.setOnCheckedChangeListener(this::saveSwitchState);
        switchMaghrib.setOnCheckedChangeListener(this::saveSwitchState);
        switchIsya.setOnCheckedChangeListener(this::saveSwitchState);

        Button btnTestReminder = view.findViewById(R.id.btn_test_reminder);
        btnTestReminder.setOnClickListener(v -> setDummyReminder());


        return view;
    }

    private void loadSwitchStates() {
        switchSubuh.setChecked(sharedPreferences.getBoolean("subuh", false));
        switchDzuhur.setChecked(sharedPreferences.getBoolean("dzuhur", false));
        switchAshar.setChecked(sharedPreferences.getBoolean("ashar", false));
        switchMaghrib.setChecked(sharedPreferences.getBoolean("maghrib", false));
        switchIsya.setChecked(sharedPreferences.getBoolean("isya", false));
    }

    private void saveSwitchState(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int id = buttonView.getId();
        String resourceName = getResources().getResourceEntryName(id);
        Log.d("PengingatFragment", "Switch diubah: " + resourceName + " (ID: " + id + ")");

        String prayerName = null;

        if (id == R.id.switch_subuh) {
            prayerName = "subuh";
        } else if (id == R.id.switch_dzuhur) {
            prayerName = "dzuhur";
        } else if (id == R.id.switch_ashar) {
            prayerName = "ashar";
        } else if (id == R.id.switch_maghrib) {
            prayerName = "maghrib";
        } else if (id == R.id.switch_isya) {
            prayerName = "isya";
        }

        if (prayerName != null) {
            editor.putBoolean(prayerName, isChecked);
            editor.apply();

            SharedPreferences prayerTimesPrefs = requireActivity().getSharedPreferences("PrayerTimes", Context.MODE_PRIVATE);
            String prayerTime = prayerTimesPrefs.getString(prayerName, "00:00"); // Default waktu jika tidak ditemukan

            if (isChecked) {
                PrayerReminderHelper.setPrayerReminder(requireContext(), prayerName, prayerTime);
                Log.d("PengingatFragment", "Pengingat untuk " + prayerName + " diaktifkan pada " + prayerTime);
            } else {
                PrayerReminderHelper.cancelPrayerReminder(requireContext(), prayerName);
                Log.d("PengingatFragment", "Pengingat untuk " + prayerName + " dinonaktifkan");
            }
        } else {
            Log.e("PengingatFragment", "Switch ID tidak dikenali: " + resourceName + " (ID: " + id + ")");
        }
    }

    private void setDummyReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5); // Set alarm 5 detik dari sekarang

        PrayerReminderHelper.setCustomReminder(requireContext(), "dummy", calendar.getTimeInMillis());
        Log.d("PengingatFragment", "Pengingat Dummy diatur dalam 5 detik.");
    }
}