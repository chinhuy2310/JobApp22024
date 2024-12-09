package com.example.application22024.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.R;
import com.example.application22024.adapter.AnswerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    private TextView postDetailTitle, postDetailContent;
    private RecyclerView answerRecyclerView;
    private EditText answerInput;
    private Button postAnswerButton;

    private List<String> answerList;  // 답변을 저장할 리스트
    private AnswerAdapter answerAdapter;  // RecyclerView의 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // UI 연결
        postDetailTitle = findViewById(R.id.postDetailTitle);
        postDetailContent = findViewById(R.id.postDetailContent);
        answerRecyclerView = findViewById(R.id.answerRecyclerView);
        answerInput = findViewById(R.id.answerInput);
        postAnswerButton = findViewById(R.id.postAnswerButton);

        // 데이터 받아오기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        // 텍스트 설정
        postDetailTitle.setText(title);
        postDetailContent.setText(content);

        // 답변 리스트와 어댑터 설정
        answerList = new ArrayList<>();
        answerAdapter = new AnswerAdapter(answerList);  // 어댑터 생성
        answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerRecyclerView.setAdapter(answerAdapter);

        // 답변 게시 버튼 클릭 리스너
        postAnswerButton.setOnClickListener(v -> {
            String answer = answerInput.getText().toString().trim();
            if (!answer.isEmpty()) {
                // 리스트에 답변 추가
                answerList.add(answer);

                // 어댑터 갱신
                answerAdapter.notifyItemInserted(answerList.size() - 1);

                // 답변 입력 필드 초기화
                answerInput.setText("");
            } else {
                Toast.makeText(this, "답변을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}