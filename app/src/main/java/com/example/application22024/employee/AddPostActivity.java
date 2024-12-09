package com.example.application22024.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.DatabaseHelper;
import com.example.application22024.R;

public class AddPostActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        titleEditText = findViewById(R.id.postTitleInput);
        contentEditText = findViewById(R.id.postContentInput);
        saveButton = findViewById(R.id.savePostButton);

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String content = contentEditText.getText().toString().trim();

            if (!title.isEmpty() && !content.isEmpty()) {
                // 결과를 반환 (데이터를 CustomerService로 전달)
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", title);
                resultIntent.putExtra("content", content);
                setResult(RESULT_OK, resultIntent); // 결과 코드와 데이터를 전달
                finish(); // Activity 종료
            } else {
                // 필수 입력 사항이 비어 있으면 사용자에게 알림
                Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
