package com.daffafakhir.splashscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.TreeMap;

public class HistoryFragment extends Fragment {

    private LinearLayout layoutList;

    public HistoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        layoutList = view.findViewById(R.id.layoutListHistory);
        Button btnClear = view.findViewById(R.id.btnClearHistory);

        tampilkanHistory();

        btnClear.setOnClickListener(v -> {
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
                        layoutList.removeAllViews();
                        tampilkanHistory();
                        Toast.makeText(getContext(), "Riwayat ibadah berhasil dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        return view;
    }

    private void tampilkanHistory() {
        layoutList.removeAllViews();

        SharedPreferences prefs = requireContext().getSharedPreferences("IbadahHistory", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        TreeMap<String, String> sortedHistory = new TreeMap<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("history_")) {
                sortedHistory.put(entry.getKey(), entry.getValue().toString());
            }
        }

        if (sortedHistory.isEmpty()) {
            TextView emptyText = new TextView(requireContext());
            emptyText.setText("Belum ada history ibadah yang tersimpan.");
            emptyText.setTextSize(16);
            layoutList.addView(emptyText);
        } else {
            int padding = (int) (16 * getResources().getDisplayMetrics().density);
            for (Map.Entry<String, String> entry : sortedHistory.descendingMap().entrySet()) {
                String tanggal = entry.getKey().replace("history_", "");
                String ibadah = entry.getValue();

                TextView item = new TextView(requireContext());
                item.setText("Tanggal: " + tanggal + "\nIbadah: " + ibadah);
                item.setTextSize(16);
                item.setPadding(0, 0, 0, padding / 2);
                layoutList.addView(item);
            }
        }
    }
}
