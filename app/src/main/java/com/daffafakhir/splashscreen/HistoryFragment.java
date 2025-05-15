package com.daffafakhir.splashscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerViewHistory;
    private TextView tvEmptyHistory;
    private SwipeRefreshLayout swipeRefreshHistory;
    private HistoryAdapter historyAdapter;
    private List<HistoryItem> historyItems;

    public HistoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Inisialisasi komponen UI
        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
        tvEmptyHistory = view.findViewById(R.id.tvEmptyHistory);
        swipeRefreshHistory = view.findViewById(R.id.swipeRefreshHistory);
        MaterialButton btnClearHistory = view.findViewById(R.id.btnClearHistory);

        // Setup RecyclerView
        historyItems = new ArrayList<>();
        historyAdapter = new HistoryAdapter(requireContext(), historyItems, this::hapusRiwayat);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewHistory.setAdapter(historyAdapter);

        // Setup Swipe Refresh
        if (swipeRefreshHistory != null) {
            swipeRefreshHistory.setColorSchemeResources(R.color.maincolor, R.color.hijau_muda);
            swipeRefreshHistory.setOnRefreshListener(this::loadHistoryData);
        }

        // Ambil data riwayat
        loadHistoryData();

        // Setup Clear Button
        btnClearHistory.setOnClickListener(v -> {
            if (historyItems.isEmpty()) {
                Toast.makeText(requireContext(), "Tidak ada riwayat untuk dihapus", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(requireContext())
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah kamu yakin ingin menghapus semua riwayat ibadah?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        SharedPreferences prefs = requireContext().getSharedPreferences("IbadahHistory", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        for (String key : prefs.getAll().keySet()) {
                            if (key.startsWith("history_")) {
                                editor.remove(key);
                            }
                        }

                        editor.apply();
                        loadHistoryData();
                        Toast.makeText(getContext(), "Riwayat ibadah berhasil dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        return view;
    }

    private void loadHistoryData() {
        historyItems.clear();

        SharedPreferences prefs = requireContext().getSharedPreferences("IbadahHistory", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        TreeMap<String, String> sortedHistory = new TreeMap<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("history_")) {
                sortedHistory.put(entry.getKey(), entry.getValue().toString());
            }
        }

        if (sortedHistory.isEmpty()) {
            tvEmptyHistory.setVisibility(View.VISIBLE);
            recyclerViewHistory.setVisibility(View.GONE);
        } else {
            tvEmptyHistory.setVisibility(View.GONE);
            recyclerViewHistory.setVisibility(View.VISIBLE);

            for (Map.Entry<String, String> entry : sortedHistory.descendingMap().entrySet()) {
                String tanggal = entry.getKey().replace("history_", "");
                String ibadah = entry.getValue();
                historyItems.add(new HistoryItem(entry.getKey(), tanggal, ibadah));
            }
        }

        historyAdapter.notifyDataSetChanged();

        // Penting: Null check untuk menghindari NullPointerException
        if (swipeRefreshHistory != null && swipeRefreshHistory.isRefreshing()) {
            swipeRefreshHistory.setRefreshing(false);
        }
    }

    private void hapusRiwayat(String key, String tanggal) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Hapus riwayat tanggal " + tanggal + "?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    SharedPreferences prefs = requireContext().getSharedPreferences("IbadahHistory", Context.MODE_PRIVATE);
                    prefs.edit().remove(key).apply();
                    loadHistoryData();
                    Toast.makeText(requireContext(), "Riwayat " + tanggal + " dihapus", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    // Model class untuk data riwayat
    static class HistoryItem {
        String key;
        String tanggal;
        String ibadah;

        HistoryItem(String key, String tanggal, String ibadah) {
            this.key = key;
            this.tanggal = tanggal;
            this.ibadah = ibadah;
        }
    }
}