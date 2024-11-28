package com.example.application22024;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class JobDetails extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details);

        // Tìm và thiết lập Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Lấy thông tin loại người dùng từ Intent
        String userType = getIntent().getStringExtra("userType");
       LinearLayout viewOfEmployee = findViewById(R.id.viewOfEmployee);
        LinearLayout viewOfEmployer = findViewById(R.id.viewOfEmployer);
        if ("Employee".equals(userType)) {
            viewOfEmployee.setVisibility(View.VISIBLE); // Hiển thị cho Employee
            viewOfEmployer.setVisibility(View.GONE);
        } else {
            viewOfEmployee.setVisibility(View.GONE); // Ẩn cho Employer
            viewOfEmployer.setVisibility(View.VISIBLE);
        }



        // Kích hoạt nút quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Xử lý khi người dùng nhấn nút quay lại
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}