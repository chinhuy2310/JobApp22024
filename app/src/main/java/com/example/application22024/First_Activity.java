package com.example.application22024;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.employer.RegistrationActivity;

public class First_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        // Tìm LinearLayout bằng ID
        LinearLayout employerLayout = findViewById(R.id.employer);
        // Thiết lập sự kiện OnClickListener
        employerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First_Activity.this, LoginActivity.class);
//                intent.putExtra("nextActivity", "Activity3"); // Chuyển đến Activity3 sau Activity2
                intent.putExtra("nextActivity", "RMActivity");
                startActivity(intent);
            }
        });


        LinearLayout employeeLayout = findViewById(R.id.employee);
        employeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First_Activity.this, LoginActivity.class);
                intent.putExtra("nextActivity", "MainActivity");
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

}
