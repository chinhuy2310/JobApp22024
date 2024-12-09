package com.example.application22024;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.employee.EmployeeMain;
import com.example.application22024.employer.EmployerMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    APIService apiService;
    EditText loginAccount, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

        loginAccount = findViewById(R.id.account);
        loginPassword = findViewById(R.id.password);

        // Tìm LinearLayout bằng ID
        Button loginbutton = findViewById(R.id.buttonLogin);
        // Thiết lập sự kiện OnClickListener
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        Button registerbutton = findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userType = getIntent().getStringExtra("userType");
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra("userType", userType);
                startActivity(intent);
            }
        });

    }

    private void Login() {
        String account = loginAccount.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        // Lấy userType từ Intent
        String expectedUserType = getIntent().getStringExtra("userType"); // "Employer" hoặc "Employee"
//        Log.e("user type: ", expectedUserType);
        if (!account.isEmpty() && !password.isEmpty()) {
            LoginRequest loginRequest = new LoginRequest(account, password, expectedUserType);

            // Gọi API đăng nhập
            Call<LoginResponse> call = apiService.login(loginRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
//                        Log.e("API Response Body", new Gson().toJson(response.body()));

                    } else {
//                        Log.e("API Response Error", "Response code: " + response.code());
                    }
                    if (response.isSuccessful() && response.body() != null) {
//                        String message = response.body().getMessage();
                        String userType = response.body().getUser_type();
                        int userId = response.body().getUserId();
                        // Lưu userId vào SharedPreferences
                        SharedPrefManager.getInstance(LoginActivity.this).saveUserId(userId);

                        String actualUserType = userType; // Lấy userType từ server
                        // Kiểm tra nếu loại tài khoản từ server không khớp với loại tài khoản người dùng đã chọn
                        if (expectedUserType != null && !expectedUserType.equals(actualUserType)) {
                            Toast.makeText(LoginActivity.this, "You cannot log in as " + actualUserType + ". Please check your account type.", Toast.LENGTH_SHORT).show();
                            return; // Ngăn chặn đăng nhập tiếp tục
                        }
                        // Nếu userType khớp, chuyển hướng đến giao diện chính tương ứng
                        Intent intent;
                        if ("Employer".equals(actualUserType)) {
                            intent = new Intent(LoginActivity.this, EmployerMain.class);
//                            intent.putExtra("user_id", userId);
                        } else if ("Employee".equals(actualUserType)) {
                            intent = new Intent(LoginActivity.this, EmployeeMain.class);
//                            intent.putExtra("user_id", userId);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid user type", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }

}