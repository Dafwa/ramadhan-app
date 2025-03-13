package com.daffafakhir.splashscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<String> postList;
    private EditText etMessage;
    private Button btnSendMessage;

    public CommunityFragment() {
        // Diperlukan konstruktor kosong
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // Inisialisasi UI
        recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts);
        etMessage = view.findViewById(R.id.etMessage);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);


        // Setup RecyclerView
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList);
        recyclerViewPosts.setAdapter(postAdapter);

        // Tombol Kirim Pesan
        btnSendMessage.setOnClickListener(v -> {
            String message = etMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                postList.add(0, message); // Tambah pesan ke atas daftar
                postAdapter.notifyItemInserted(0);
                etMessage.setText(""); // Kosongkan input setelah mengirim
                recyclerViewPosts.scrollToPosition(0);
            }
        });


        return view;
    }
}
