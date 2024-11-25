package com.example.application22024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.employee.EmployeeMain;
import com.example.application22024.employer.EmployerMain;
import com.example.application22024.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    APIService apiService;
    EditText loginAccount,loginPassword;
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
                // Lấy thông tin từ Intent
                String nextActivity = getIntent().getStringExtra("userType");

                Intent intent = null;

                // Tạo Intent để chuyển sang Activity khác
                if ("Employer".equals(nextActivity)) {
                    intent = new Intent(LoginActivity.this, EmployerMain.class);
                } else if ("Employee".equals(nextActivity)) {
                    intent = new Intent(LoginActivity.this, EmployeeMain.class);
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

        // Check if username and password are not empty
        if (!account.isEmpty() && !password.isEmpty()) {
            // Create a login request object
            LoginRequest loginRequest = new LoginRequest(account, password);

            // Make an asynchronous network call to login API
            Call<User> call = apiService.login(loginRequest);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        // If login is successful, fetch user info
//                        getUserInfo(loginAccount);
                    } else {
                        // Handle unsuccessful login response
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Handle network connection failure
                    Toast.makeText(LoginActivity.this, "Login failed: Cannot connect to server" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Show a toast message if username or password is empty
            Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }
}