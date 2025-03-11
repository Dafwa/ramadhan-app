package com.daffafakhir.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class KhatamFragment extends Fragment implements JuzAdapter.OnJuzCheckedChangeListener {
    private ProgressBar progressBar;
    private TextView textProgress;
    private RecyclerView recyclerView;
    private JuzAdapter adapter;
    private List<JuzModel> juzList;
    private int totalChecked = 0;

    // Tambahkan variabel ViewModel jika ingin menyimpan state lain secara terpusat
    private JuzViewModel juzViewModel;
    private SharedPreferences prefs;

    public KhatamFragment() {
        // Konstruktor kosong wajib ada
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khatam, container, false);

        // Inisialisasi UI
        progressBar = view.findViewById(R.id.progressBar);
        textProgress = view.findViewById(R.id.textProgress);
        recyclerView = view.findViewById(R.id.recyclerViewJuz);
        Button btnResetJuz = view.findViewById(R.id.btnResetJuz);

        // Inisialisasi SharedPreferences
        prefs = requireContext().getSharedPreferences("juzPrefs", Context.MODE_PRIVATE);

        // Inisialisasi ViewModel
        juzViewModel = new ViewModelProvider(requireActivity()).get(JuzViewModel.class);
        // Ambil list juz dari ViewModel
        juzList = juzViewModel.getJuzList();

        // Muat status checklist dari SharedPreferences
        for (int i = 0; i < juzList.size(); i++) {
            boolean checked = prefs.getBoolean("juz" + i, false);
            juzList.get(i).setChecked(checked);
            if (checked) {
                totalChecked++;
            }
        }

        // Atur RecyclerView
        adapter = new JuzAdapter(juzList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Pastikan progressBar disesuaikan (misalnya, set max ke 30)
        progressBar.setMax(30);
        progressBar.setProgress(totalChecked);
        textProgress.setText(totalChecked + " / 30 Juz");

        // Tombol Reset untuk mengembalikan checklist ke keadaan awal (tidak dicentang)
        btnResetJuz.setOnClickListener(v -> {
            totalChecked = 0;
            for (int i = 0; i < juzList.size(); i++) {
                juzList.get(i).setChecked(false);
            }
            progressBar.setProgress(totalChecked);
            textProgress.setText(totalChecked + " / 30 Juz");
            adapter.notifyDataSetChanged();

            // Simpan perubahan ke SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            for (int i = 0; i < juzList.size(); i++) {
                editor.putBoolean("juz" + i, false);
            }
            editor.apply();
        });

        return view;
    }

    @Override
    public void onJuzCheckedChanged(boolean isChecked) {
        // Karena listener di adapter hanya mengirim status per item, kita hitung ulang totalChecked
        totalChecked = 0;
        for (JuzModel juz : juzList) {
            if (juz.isChecked()) {
                totalChecked++;
            }
        }
        progressBar.setProgress(totalChecked);
        textProgress.setText(totalChecked + " / 30 Juz");
    }

    // Simpan status checklist ke SharedPreferences ketika fragment berhenti
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < juzList.size(); i++) {
            editor.putBoolean("juz" + i, juzList.get(i).isChecked());
        }
        editor.apply();
    }
}
