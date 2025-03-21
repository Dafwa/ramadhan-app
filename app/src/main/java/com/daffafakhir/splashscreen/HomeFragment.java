package com.daffafakhir.splashscreen;

import android.Manifest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    private TextView realTimeText, tvJadwalSubuh, tvJadwalDzuhur, tvJadwalAshar, tvJadwalMaghrib, tvJadwalIsya, tvJadwalSahur, tvJadwalBerbuka, tvTanggalHariIni; // Tambahkan variabel TextView
    private ProgressBar progressBar;
    private FusedLocationProviderClient fusedLocationClient;
    private Handler handler = new Handler();
    private Runnable timeRunnable;
    private CheckBox cbShalatSubuh, cbShalatDzuhur, cbShalatAshar, cbShalatMaghrib, cbShalatIsya, cbTadarus, cbShalatTarawih;
    private Button btnJadwalKegiatan, btnKhatamQuran, btnPengingat;
    private SharedPreferences sharedPreferences;

    public HomeFragment() {
        // Konstruktor kosong
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout fragment_home.xml
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = requireActivity().getSharedPreferences("PrayerTimes", Context.MODE_PRIVATE);

        // Inisialisasi elemen UI
        realTimeText = view.findViewById(R.id.realTimeText);
        tvTanggalHariIni = view.findViewById(R.id.tvTanggalHariIni);
        cbShalatSubuh = view.findViewById(R.id.cbShalatSubuh);
        cbShalatDzuhur = view.findViewById(R.id.cbShalatDzuhur);
        cbShalatAshar = view.findViewById(R.id.cbShalatAshar);
        cbShalatMaghrib = view.findViewById(R.id.cbShalatMaghrib);
        cbShalatIsya = view.findViewById(R.id.cbShalatIsya);
        cbTadarus = view.findViewById(R.id.cbTadarus);
        cbShalatTarawih = view.findViewById(R.id.cbShalatTarawih);
        progressBar = view.findViewById(R.id.progressBar);
        tvJadwalSubuh = view.findViewById(R.id.tvJadwalSubuh);
        tvJadwalDzuhur = view.findViewById(R.id.tvJadwalDzuhur);
        tvJadwalAshar = view.findViewById(R.id.tvJadwalAshar);
        tvJadwalMaghrib = view.findViewById(R.id.tvJadwalMaghrib);
        tvJadwalIsya = view.findViewById(R.id.tvJadwalIsya);
//        tvJadwalSahur = view.findViewById(R.id.tvJadwalSahur);
//        tvJadwalBerbuka = view.findViewById(R.id.tvJadwalBerbuka);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Ambil data dari SharedPreferences dan tampilkan di TextView
        tvJadwalSubuh.setText("Subuh: " + sharedPreferences.getString("subuh", "Belum tersedia"));
        tvJadwalDzuhur.setText("Dzuhur: " + sharedPreferences.getString("dzuhur", "Belum tersedia"));
        tvJadwalAshar.setText("Ashar: " + sharedPreferences.getString("ashar", "Belum tersedia"));
        tvJadwalMaghrib.setText("Maghrib: " + sharedPreferences.getString("maghrib", "Belum tersedia"));
        tvJadwalIsya.setText("Isya: " + sharedPreferences.getString("isya", "Belum tersedia"));

        // Cek apakah sudah ada data di SharedPreferences
        if (sharedPreferences.getString("subuh", null) == null) {
            getCurrentLocation();  // Hanya request API jika belum ada data
        }

        // Inisialisasi tombol di onCreateView()
        Button btnRefreshJadwal = view.findViewById(R.id.btnRefreshJadwal);

        // Event klik tombol untuk memanggil API secara manual
        btnRefreshJadwal.setOnClickListener(v -> {
            // Hapus data dari SharedPreferences
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("PrayerTimes", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Kosongkan tampilan waktu sholat
            tvJadwalSubuh.setText("");
            tvJadwalDzuhur.setText("");
            tvJadwalAshar.setText("");
            tvJadwalMaghrib.setText("");
            tvJadwalIsya.setText("");

            // Tampilkan ProgressBar
            progressBar.setVisibility(View.VISIBLE);

            // Panggil ulang API untuk mendapatkan data baru
            getCurrentLocation();
        });

        btnJadwalKegiatan = view.findViewById(R.id.btnJadwalKegiatan);
        btnKhatamQuran = view.findViewById(R.id.btnKhatamQuran);
        btnPengingat = view.findViewById(R.id.btnPengingat);

        // Membuat Runnable untuk mengupdate waktu setiap detik
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                // Format waktu dengan pola jam:menit:detik (24 jam)
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                // Set waktu yang sudah diformat ke TextView
                realTimeText.setText(currentTime);

                // Jadwalkan update kembali setiap 1000 ms (1 detik)
                handler.postDelayed(this, 1000);
            }
        };
        // Mulai menjalankan Runnable untuk pertama kali
        handler.post(timeRunnable);

        // Set tanggal saat ini
        SimpleDateFormat sdfTanggal = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        String currentDate = sdfTanggal.format(new Date());
        tvTanggalHariIni.setText(currentDate); // Set ke TextView

        // Aksi tombol "Jadwal Kegiatan" (nanti bisa diisi)
        btnJadwalKegiatan.setOnClickListener(v -> {
            // Tambahkan aksi jika sudah siap
        });

        // Aksi tombol "Pengingat" (nanti bisa diisi)
        btnPengingat.setOnClickListener(v -> {
            PengingatFragment pengingatFragment = new PengingatFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, pengingatFragment);
            transaction.addToBackStack(null); // Agar bisa kembali ke fragment sebelumnya
            transaction.commit();
        });

        // Aksi tombol "Khatam Qur'an"
        btnKhatamQuran.setOnClickListener(v -> {
            KhatamFragment khatamFragment = new KhatamFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, khatamFragment);
            transaction.addToBackStack(null); // Agar bisa kembali ke fragment sebelumnya
            transaction.commit();
        });


        return view;
    }

    private void saveLocationPermissionGranted(boolean isGranted) {
        SharedPreferences prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("location_permission_granted", isGranted);
        editor.apply();
    }

    private boolean isLocationPermissionGranted() {
        SharedPreferences prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("location_permission_granted", false);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Jika izin belum diberikan dan belum pernah disimpan di SharedPreferences
            if (!isLocationPermissionGranted()) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            return;
        }

        // Jika izin sudah diberikan, langsung dapatkan lokasi
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                fetchPrayerTimes(latitude, longitude);
            } else {
                Toast.makeText(getContext(), "Gagal mendapatkan lokasi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveLocationPermissionGranted(true);
                getCurrentLocation(); // Coba ambil lokasi lagi setelah izin diberikan
            } else {
                Toast.makeText(getContext(), "Izin lokasi diperlukan untuk mendapatkan jadwal sholat", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void fetchPrayerTimes(double lat, double lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.aladhan.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PrayerTimesAPI api = retrofit.create(PrayerTimesAPI.class);
        Call<PrayerResponse> call = api.getPrayerTimes(lat, lon, 20);

        call.enqueue(new Callback<PrayerResponse>() {
            @Override
            public void onResponse(Call<PrayerResponse> call, Response<PrayerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PrayerResponse.Timings timings = response.body().getData().getTimings();

                    // Log waktu sholat dari API
                    Log.d("PrayerTimes", "Waktu Sholat dari API: " +
                            "Subuh: " + timings.getFajr() + ", " +
                            "Dzuhur: " + timings.getDhuhr() + ", " +
                            "Ashar: " + timings.getAsr() + ", " +
                            "Maghrib: " + timings.getMaghrib() + ", " +
                            "Isya: " + timings.getIsha());

                    // Sembunyikan ProgressBar
                    progressBar.setVisibility(View.GONE);
                    // Set waktu sholat ke TextView
                    tvJadwalSubuh.setText("Subuh: " + timings.getFajr());
                    tvJadwalDzuhur.setText("Dzuhur: " + timings.getDhuhr());
                    tvJadwalAshar.setText("Ashar: " + timings.getAsr());
                    tvJadwalMaghrib.setText("Maghrib: " + timings.getMaghrib());
                    tvJadwalIsya.setText("Isya: " + timings.getIsha());

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("subuh", timings.getFajr());
                    editor.putString("dzuhur", timings.getDhuhr());
                    editor.putString("ashar", timings.getAsr());
                    editor.putString("maghrib", timings.getMaghrib());
                    editor.putString("isya", timings.getIsha());
                    editor.apply();

                    // setPrayerReminders(timings);
                }
            }

            @Override
            public void onFailure(Call<PrayerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mendapatkan data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPrayerReminders(PrayerResponse.Timings timings) {
        Context context = requireContext();

        PrayerReminderHelper.setPrayerReminder(context, "subuh", timings.getFajr());
        PrayerReminderHelper.setPrayerReminder(context, "dzuhur", timings.getDhuhr());
        PrayerReminderHelper.setPrayerReminder(context, "ashar", timings.getAsr());
        PrayerReminderHelper.setPrayerReminder(context, "maghrib", timings.getMaghrib());
        PrayerReminderHelper.setPrayerReminder(context, "isya", timings.getIsha());

        Log.d("PrayerReminder", "Pengingat sholat berhasil diatur.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hentikan update untuk menghindari memory leak
        handler.removeCallbacks(timeRunnable);
    }
}
