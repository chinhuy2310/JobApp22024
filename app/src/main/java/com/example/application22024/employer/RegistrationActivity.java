package com.example.application22024.employer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.application22024.R;

import androidx.appcompat.widget.Toolbar;

public class RegistrationActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Thiết lập Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Ẩn toolbar mặc định
//        toolbar.setVisibility(View.GONE);

        // Xử lý sự kiện quay lại từ toolbar
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Hiển thị fragment đầu tiên khi Activity được khởi tạo
        if (savedInstanceState == null) {
            showNextFragment(new Step1Fragment());
        }
    }

    // Phương thức để chuyển Fragment
    public void showNextFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            // Nếu có fragment trong back stack, trở về fragment trước đó
            getSupportFragmentManager().popBackStack();
        } else {
            // Nếu không còn fragment nào, hiển thị hộp thoại xác nhận thoát
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to cancel the recruitment content?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Khi nhấn OK, chuyển về RecruitmentManagementActivity
                        Intent intent = new Intent(RegistrationActivity.this, RecruitmentManagementActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Kết thúc RegistrationActivity
                    })
                    .setNegativeButton("Cancel", null)
                    .setCancelable(true)
                    .show();
        }
    }


}
