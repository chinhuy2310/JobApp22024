package com.example.application22024.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.R;
import com.example.application22024.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    private OnItemClickListener listener;
    private List<Post> postList;

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    public PostAdapter(List<Post> posts, OnItemClickListener listener) {
        this.posts = posts;
        this.listener = listener;
        this.postList = postList;
    }

    public void updatePosts(List<Post> newPosts) {
        postList.clear();
        postList.addAll(newPosts);
        notifyDataSetChanged();  // 어댑터에 데이터가 변경되었음을 알리고, RecyclerView 갱신
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(post));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postTitle);
        }
    }

}
