package com.daffafakhir.splashscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private TextView tvCountdown, tvJadwalSahur, tvJadwalBerbuka;
    private CheckBox cbShalatSubuh, cbTadarus, cbShalatTarawih;
    private Button btnJadwalKegiatan, btnKhatamQuran, btnPengingat, btnManajemenAktivitas;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout fragment_home.xml
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi SharedPreferencesHelper
        sharedPreferencesHelper = new SharedPreferencesHelper(requireContext());

        // Inisialisasi elemen UI
        tvCountdown = view.findViewById(R.id.tvCountdown);
        cbShalatSubuh = view.findViewById(R.id.cbShalatSubuh);
        cbTadarus = view.findViewById(R.id.cbTadarus);
        cbShalatTarawih = view.findViewById(R.id.cbShalatTarawih);
        tvJadwalSahur = view.findViewById(R.id.tvJadwalSahur);
        tvJadwalBerbuka = view.findViewById(R.id.tvJadwalBerbuka);

        // Ganti tombol lama dengan tombol baru
        btnJadwalKegiatan = view.findViewById(R.id.btnJadwalKegiatan);
        btnKhatamQuran = view.findViewById(R.id.btnKhatamQuran);
        btnPengingat = view.findViewById(R.id.btnPengingat);


        // Atur teks countdown secara manual (bisa pakai Timer nanti)
        tvCountdown.setText("03:45:22");

        // Tampilkan jadwal yang tersimpan
        tvJadwalSahur.setText("Jadwal Sahur: " + sharedPreferencesHelper.getJadwal("sahur"));
        tvJadwalBerbuka.setText("Jadwal Berbuka: " + sharedPreferencesHelper.getJadwal("berbuka"));

        // Set aksi tombol baru
        btnJadwalKegiatan.setOnClickListener(v -> {
            // Tambahkan aksi ketika tombol "Jadwal Kegiatan" ditekan
        });

        btnKhatamQuran.setOnClickListener(v -> {
            // Tambahkan aksi ketika tombol "Khatam Al-Quran" ditekan
        });

        btnPengingat.setOnClickListener(v -> {
            // Tambahkan aksi ketika tombol "Pengingat" ditekan
        });



        return view;
    }
}
