package com.daffafakhir.splashscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
            for (Map.Entry<String, String> entry : sortedHistory.descendingMap().entrySet()) {
                String tanggal = entry.getKey().replace("history_", "");
                String ibadah = entry.getValue();

                // Bungkus dengan CardView
                androidx.cardview.widget.CardView cardView = new androidx.cardview.widget.CardView(requireContext());
                cardView.setRadius(16);
                cardView.setCardElevation(8);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                cardParams.setMargins(0, 0, 0, 24);
                cardView.setLayoutParams(cardParams);

                // Isi CardView: LinearLayout Vertikal
                LinearLayout innerLayout = new LinearLayout(requireContext());
                innerLayout.setOrientation(LinearLayout.VERTICAL);
                innerLayout.setPadding(24, 24, 24, 24);

                // TextView isi history
                TextView itemText = new TextView(requireContext());
                itemText.setText("Tanggal: " + tanggal + "\nIbadah: " + ibadah);
                itemText.setTextSize(16);

                // Tombol hapus
                ImageButton btnHapus = new ImageButton(requireContext());
                btnHapus.setImageResource(R.drawable.baseline_delete_forever_24);
                btnHapus.setBackgroundResource(R.drawable.bg_delete_button);
                btnHapus.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                btnHapus.setPadding(16, 16, 16, 16);

                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                btnParams.topMargin = 16;
                btnParams.gravity = Gravity.END;
                btnHapus.setLayoutParams(btnParams);


                btnHapus.setOnClickListener(v -> {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Konfirmasi")
                            .setMessage("Hapus riwayat tanggal " + tanggal + "?")
                            .setPositiveButton("Ya", (dialog, which) -> {
                                prefs.edit().remove(entry.getKey()).apply();
                                tampilkanHistory();
                                Toast.makeText(requireContext(), "Riwayat " + tanggal + " dihapus", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Batal", null)
                            .show();
                });

                innerLayout.addView(itemText);
                innerLayout.addView(btnHapus);
                cardView.addView(innerLayout);
                layoutList.addView(cardView);
            }
        }
    }
}
