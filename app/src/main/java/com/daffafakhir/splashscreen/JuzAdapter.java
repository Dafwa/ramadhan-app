package com.daffafakhir.splashscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JuzAdapter extends RecyclerView.Adapter<JuzAdapter.ViewHolder> {
    private List<JuzModel> juzList;
    private OnJuzCheckedChangeListener listener;

    public interface OnJuzCheckedChangeListener {
        void onJuzCheckedChanged(boolean isChecked);
    }

    public JuzAdapter(List<JuzModel> juzList, OnJuzCheckedChangeListener listener) {
        this.juzList = juzList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_juz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JuzModel juz = juzList.get(position);
        holder.tvJuzName.setText(juz.getNamaJuz());
        holder.checkBox.setChecked(juz.isChecked());

        // Listener untuk checkbox
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            juz.setChecked(isChecked);
            listener.onJuzCheckedChanged(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return juzList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJuzName;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJuzName = itemView.findViewById(R.id.tvJuzName);
            checkBox = itemView.findViewById(R.id.cbJuz);
        }
    }
}
