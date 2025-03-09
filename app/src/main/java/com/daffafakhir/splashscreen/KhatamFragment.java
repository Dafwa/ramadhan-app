package com.daffafakhir.splashscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        // Inisialisasi ViewModel
        juzViewModel = new ViewModelProvider(requireActivity()).get(JuzViewModel.class);
        // Ambil list juz dari ViewModel
        juzList = juzViewModel.getJuzList();

        // Hitung total item yang sudah di-checklist dari data model
        totalChecked = 0;
        for (JuzModel juz : juzList) {
            if (juz.isChecked()) {
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

        return view;
    }

    @Override
    public void onJuzCheckedChanged(boolean isChecked) {
        if (isChecked) {
            totalChecked++;
        } else {
            totalChecked--;
        }

        // Update Progress
        progressBar.setProgress(totalChecked);
        textProgress.setText(totalChecked + " / 30 Juz");
    }
}
