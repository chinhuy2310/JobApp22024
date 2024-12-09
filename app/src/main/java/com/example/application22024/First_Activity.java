package com.example.application22024;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.application22024.employer.RegistrationActivity;

public class First_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        // Kiểm tra quyền truy cập bộ nhớ
        checkStoragePermission();

        // Tìm LinearLayout bằng ID
        LinearLayout employerLayout = findViewById(R.id.employer);
        // Thiết lập sự kiện OnClickListener
        employerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First_Activity.this, LoginActivity.class);
                intent.putExtra("userType", "Employer");
                startActivity(intent);
            }
        });

        LinearLayout employeeLayout = findViewById(R.id.employee);
        employeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First_Activity.this, LoginActivity.class);
                intent.putExtra("userType", "Employee");
                startActivity(intent);
            }
        });

        TextView linearLayout = findViewById(R.id.textview);

        // Tính toán 10% chiều cao màn hình
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int marginTop = (int) (height * 0.1); // 10%

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMargins(0, marginTop, 0, 0);
        linearLayout.setLayoutParams(params);
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Nếu quyền chưa được cấp, yêu cầu quyền
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
        } else {
            // Quyền đã được cấp
            Toast.makeText(this, "Quyền truy cập bộ nhớ đã được cấp", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp
                Toast.makeText(this, "Quyền truy cập bộ nhớ đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                // Quyền bị từ chối
                Toast.makeText(this, "Quyền truy cập bộ nhớ bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}