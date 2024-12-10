package com.example.application22024.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.R;
import com.example.application22024.adapter.PostAdapter;
import com.example.application22024.model.Post;

import java.util.ArrayList;
import java.util.List;

public class CustomerService extends AppCompatActivity {

    private RecyclerView boardRecyclerView;
    private Button addPostButton;
    private PostAdapter postAdapter;
    private List<Post> postList;

    /*@Override
    protected void onResume() {
        super.onResume();
        loadPosts(); // 데이터가 추가된 후 RecyclerView 갱신
    }

    private void loadPosts() {
        postList.clear(); // 기존 리스트 초기화
        DatabaseHelper db = new DatabaseHelper(this);
        postList.addAll(db.getAllPosts()); // 데이터 추가
        postAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4_customer_service);

        // UI 연결
        boardRecyclerView = findViewById(R.id.boardRecyclerView);
        addPostButton = findViewById(R.id.addPostButton);

        // 더미 데이터 준비
        postList = new ArrayList<>();
        postList.add(new Post("제목 1", "첫 번째 게시물입니다.", System.currentTimeMillis()));
        postList.add(new Post("제목 2", "두 번째 게시물입니다.", System.currentTimeMillis()));
        postList.add(new Post("제목 3", "세 번째 게시물입니다.", System.currentTimeMillis()));

        // RecyclerView 설정
        postAdapter = new PostAdapter(postList, post -> {
            // 게시물 클릭 이벤트 처리
            Intent intent = new Intent(CustomerService.this, PostDetailActivity.class);
            intent.putExtra("title", post.getTitle());
            intent.putExtra("content", post.getContent());
            startActivity(intent);
        });

        boardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardRecyclerView.setAdapter(postAdapter);

        // "Add Post" 버튼 클릭 이벤트
        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerService.this, AddPostActivity.class);
            startActivityForResult(intent, 1);  // 1은 요청 코드
        });
    }

    // onActivityResult에서 새 게시물을 받아와 RecyclerView 갱신
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // 새 게시물 데이터 받아오기
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            long timestamp = System.currentTimeMillis();  // 현재 시간으로 설정

            // 새 게시물 리스트에 추가 (맨 앞에 추가)
            Post newPost = new Post(title, content, timestamp);
            postList.add(0, newPost);  // 리스트의 맨 앞에 추가

            // 어댑터 갱신
            postAdapter.notifyItemInserted(0);  // 새로 추가된 위치 알리기
        }
    }

}