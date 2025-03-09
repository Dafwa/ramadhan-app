package com.daffafakhir.splashscreen;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private TextView realTimeText, tvJadwalSahur, tvJadwalBerbuka;
    private Handler handler = new Handler();
    private Runnable timeRunnable;
    private CheckBox cbShalatSubuh, cbTadarus, cbShalatTarawih;
    private Button btnJadwalKegiatan, btnKhatamQuran, btnPengingat;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public HomeFragment() {
        // Konstruktor kosong
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout fragment_home.xml
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi SharedPreferencesHelper
        sharedPreferencesHelper = new SharedPreferencesHelper(requireContext());

        // Inisialisasi elemen UI
        realTimeText = view.findViewById(R.id.realTimeText);
        cbShalatSubuh = view.findViewById(R.id.cbShalatSubuh);
        cbTadarus = view.findViewById(R.id.cbTadarus);
        cbShalatTarawih = view.findViewById(R.id.cbShalatTarawih);
        tvJadwalSahur = view.findViewById(R.id.tvJadwalSahur);
        tvJadwalBerbuka = view.findViewById(R.id.tvJadwalBerbuka);

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

        // Tampilkan jadwal yang tersimpan
        tvJadwalSahur.setText("Jadwal Sahur: " + sharedPreferencesHelper.getJadwal("sahur"));
        tvJadwalBerbuka.setText("Jadwal Berbuka: " + sharedPreferencesHelper.getJadwal("berbuka"));

        // Aksi tombol "Jadwal Kegiatan" (nanti bisa diisi)
        btnJadwalKegiatan.setOnClickListener(v -> {
            // Tambahkan aksi jika sudah siap
        });

        // Aksi tombol "Pengingat" (nanti bisa diisi)
        btnPengingat.setOnClickListener(v -> {
            // Tambahkan aksi jika sudah siap
        });

        // Aksi tombol "Khatam Qur'an"
        btnKhatamQuran.setOnClickListener(v -> {
            KhatamFragment khatamFragment = new KhatamFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, khatamFragment);
            transaction.addToBackStack(null); // Agar bisa kembali ke fragment sebelumnya
            transaction.commit();
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hentikan update untuk menghindari memory leak
        handler.removeCallbacks(timeRunnable);
    }
}
