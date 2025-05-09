package com.daffafakhir.splashscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private final List<Post> postList;
    private final Context context;
    private final String currentUserEmail;

    public PostAdapter(Context context, List<Post> postList, String currentUserEmail) {
        this.context = context;
        this.postList = postList;
        this.currentUserEmail = currentUserEmail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.tvUser.setText(post.getUserEmail());
        holder.tvPost.setText(post.getMessage());

        // Hanya tampilkan tombol delete jika post dari user yang login
        if (post.getUserEmail().equals(currentUserEmail)) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Hapus Postingan")
                        .setMessage("Yakin ingin menghapus postingan ini?")
                        .setPositiveButton("Hapus", (dialog, which) -> {
                            FirebaseFirestore.getInstance()
                                    .collection("posts")
                                    .document(post.getId())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(context, "Post dihapus", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("Batal", null)
                        .show();
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser, tvPost;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvPost = itemView.findViewById(R.id.tvPost);
            btnDelete = itemView.findViewById(R.id.btnDelete); // Tambahkan ke item_post.xml
        }
    }
}
