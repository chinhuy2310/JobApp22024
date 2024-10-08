package com.example.application22024.employer;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.application22024.R;
import com.example.application22024.employer.FirstStepFragment;
import com.example.application22024.employer.SecondStepFragment;
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
        toolbar.setVisibility(View.GONE);

        // Xử lý sự kiện quay lại từ toolbar
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Hiển thị fragment đầu tiên khi Activity được khởi tạo
        if (savedInstanceState == null) {
            showNextFragment(new FirstStepFragment());
        }
    }

    // Phương thức để chuyển Fragment
    public void showNextFragment(Fragment fragment) {
//        // Hiển thị toolbar cho Fragment thứ 2 và ẩn cho Fragment thứ 1
//        if (fragment instanceof SecondStepFragment) {
//            toolbar.setVisibility(View.VISIBLE); // Hiển thị toolbar cho Fragment thứ 2
//        } else {
//            toolbar.setVisibility(View.GONE); // Ẩn toolbar cho Fragment thứ 1
//        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        // Cập nhật trạng thái toolbar khi quay lại
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        if (currentFragment instanceof FirstStepFragment) {
//            toolbar.setVisibility(View.GONE); // Ẩn toolbar khi ở fragment đầu tiên
//        } else {
//            toolbar.setVisibility(View.VISIBLE); // Hiển thị toolbar khi ở fragment thứ hai
//        }
    }
}
