package com.example.application22024.employee;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.application22024.R;
import com.example.application22024.model.User;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // 配置返回按钮
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("User Information");
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // 显示用户信息
        TextView userInfoTextView = findViewById(R.id.user_info_text);
        User user = getUserFromIntent(); // 模拟获取用户数据
        if (user != null) {
            userInfoTextView.setText(user.toString());
        } else {
            userInfoTextView.setText("No user data available.");
        }
    }

    private User getUserFromIntent() {
        return new User(1, "JohnDoe", "john.doe@example.com", "Admin");
    }
}
