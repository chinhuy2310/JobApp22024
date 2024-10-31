package com.example.application22024.employer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.First_Activity;
import com.example.application22024.R;

public class RecruitmentManagementActivity extends AppCompatActivity {
    Button addButton,loguotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recruitment_management);

        addButton = findViewById(R.id.add);

        // Thiết lập sự kiện OnClickListener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng RecruitmentManagementActivity.this để tham chiếu đến Activity
                Intent intent = new Intent(RecruitmentManagementActivity.this, RegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        loguotButton = findViewById(R.id.logout);
        loguotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecruitmentManagementActivity.this, First_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa tất cả các hoạt động trước
                startActivity(intent);
            }
        });
    }
}
