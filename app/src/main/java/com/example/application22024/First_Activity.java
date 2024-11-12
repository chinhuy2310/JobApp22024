package com.example.application22024;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.db.DBUntil;
import com.example.application22024.employee.LoginEmployeeActivity;
import com.example.application22024.employer.LoginEmployerActivity;

public class First_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        //datebase 数据库
        DBUntil dbUntil = new DBUntil(this);
        DBUntil.con = dbUntil.getWritableDatabase();


        // Tìm LinearLayout bằng ID
        LinearLayout employerLayout = findViewById(R.id.employer);

        // Thiết lập sự kiện OnClickListener
        employerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang Activity khác                 跳转雇主登录页面LoginEmployerActivity
                Intent intent = new Intent(First_Activity.this, LoginEmployerActivity.class);
                startActivity(intent); // Bắt đầu chuyển sang Activity khác
            }
        });

        LinearLayout employeeLayout = findViewById(R.id.employee);
        employeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(First_Activity.this, LoginEmployeeActivity.class);
                startActivity(intent);
            }
        });

        TextView linearLayout = findViewById(R.id.mytextview);

        // Tính toán 10% chiều cao màn hình
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int marginTop = (int) (height * 0.1); // 10%

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMargins(0, marginTop, 0, 0);
        linearLayout.setLayoutParams(params);
    }

}
