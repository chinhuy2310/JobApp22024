package com.example.application22024.employer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.R;

public class RecruitmentManagementActivity extends AppCompatActivity {
    Button addButton;

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
                startActivity(intent);
            }
        });
    }
}
