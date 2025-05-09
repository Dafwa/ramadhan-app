package com.daffafakhir.splashscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private EditText etMessage;
    private Button btnSendMessage;

    FirebaseAuth auth;
    FirebaseUser user;

    public CommunityFragment() {
        // Diperlukan konstruktor kosong
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null){
            replaceFragment(new LoginFragment());
        } else {

            // Inisialisasi UI
            recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts);
            etMessage = view.findViewById(R.id.etMessage);
            btnSendMessage = view.findViewById(R.id.btnSendMessage);

            // Setup RecyclerView
            recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            postList = new ArrayList<>();
            postAdapter = new PostAdapter(requireContext(), postList, user.getEmail());
            recyclerViewPosts.setAdapter(postAdapter);

            // Ambil data dari Firestore
            db.collection("posts")
                    .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .addSnapshotListener((value, error) -> {
                        if (error != null || value == null) return;

                        postList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Post post = doc.toObject(Post.class);
                            post.setId(doc.getId()); // opsional, jika kamu pakai delete
                            postList.add(post);
                        }
                        postAdapter.notifyDataSetChanged();
                    });


            btnSendMessage.setOnClickListener(v -> {
                String message = etMessage.getText().toString().trim();
                if (!message.isEmpty() && user != null) {
                    Post newPost = new Post(user.getEmail(), message, System.currentTimeMillis());

                    db.collection("posts")
                            .add(newPost)
                            .addOnSuccessListener(documentReference -> {
                                etMessage.setText("");
                                Toast.makeText(getContext(), "Post terkirim!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Gagal mengirim post", Toast.LENGTH_SHORT).show();
                            });
                }
            });
        }




        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);  // Pastikan ID ini ada di activity_main.xml
        fragmentTransaction.commit();
    }
}
