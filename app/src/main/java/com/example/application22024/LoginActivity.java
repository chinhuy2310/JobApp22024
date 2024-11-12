package com.example.application22024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.employee.MainActivity;
import com.example.application22024.employer.RecruitmentManagementActivity;
import com.example.application22024.employer.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Tìm LinearLayout bằng ID
        Button loginbutton = findViewById(R.id.buttonLogin);

        // Thiết lập sự kiện OnClickListener
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ Intent
                String nextActivity = getIntent().getStringExtra("nextActivity");

                Intent intent = null;

                // Tạo Intent để chuyển sang Activity khác
                if ("RMActivity".equals(nextActivity)) {
                    intent = new Intent(LoginActivity.this, RecruitmentManagementActivity.class);
                } else if ("MainActivity".equals(nextActivity)) {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent); // Bắt đầu chuyển sang Activity khác
                finish();
            }
        });
        Button registerbutton = findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}