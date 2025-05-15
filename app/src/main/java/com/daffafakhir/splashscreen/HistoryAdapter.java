package com.daffafakhir.splashscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final Context context;
    private final List<HistoryFragment.HistoryItem> historyItems;
    private final OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(String key, String tanggal);
    }

    public HistoryAdapter(Context context, List<HistoryFragment.HistoryItem> historyItems, OnDeleteClickListener listener) {
        this.context = context;
        this.historyItems = historyItems;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryFragment.HistoryItem item = historyItems.get(position);

        holder.tvHistoryDate.setText(item.tanggal);
        holder.tvHistoryContent.setText(item.ibadah);

        holder.btnDeleteHistory.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(item.key, item.tanggal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvHistoryDate;
        TextView tvHistoryContent;
        ImageButton btnDeleteHistory;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryDate = itemView.findViewById(R.id.tvHistoryDate);
            tvHistoryContent = itemView.findViewById(R.id.tvHistoryContent);
            btnDeleteHistory = itemView.findViewById(R.id.btnDeleteHistory);
        }
    }
}