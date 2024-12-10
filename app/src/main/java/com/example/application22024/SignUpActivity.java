package com.example.application22024;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText createId, createPW, confirmPW, createName, createContact, verifyCode;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        TextView login = findViewById(R.id.logintext);
        createId = findViewById(R.id.createId);
        createPW = findViewById(R.id.createPW);
        confirmPW = findViewById(R.id.confirmPW);
//        createName = findViewById(R.id.createName);
        createContact = findViewById(R.id.createContact);
        linearLayout = findViewById(R.id.linearLayout);
//        verifyCode = findViewById(R.id.verifyCode);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
        ImageView eyeIcon1 = findViewById(R.id.eye_icon);
        eyeIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createPW.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    createPW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeIcon1.setImageResource(R.drawable.ic_eye);
                } else {
                    createPW.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeIcon1.setImageResource(R.drawable.ic_eyex);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = getIntent().getStringExtra("userType");
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.putExtra("userType", userType);
            }
        });

        TextView registerButton = findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = createId.getText().toString();
                String password = createPW.getText().toString();
                String confirmPw = confirmPW.getText().toString();
                String userName = "  ";
                String contact = createContact.getText().toString();
//                String Code = verifyCode.getText().toString();

                if (account.isEmpty() || password.isEmpty() || confirmPw.isEmpty() || contact.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPw)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userType = getIntent().getStringExtra("userType");

                SignupRequest signupRequest = new SignupRequest(account, password, userName, contact, userType);
                Call<Void> call = apiService.signup(signupRequest);  // Sending a new user signup request to the server
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {      // Registration successful
                            Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.putExtra("userType", userType);
                            startActivity(intent);
                            finish();
                        } else {
                            if (response.code() == 400) { // If the status code is 400 (Bad Request), the account ID is duplicate
                                Toast.makeText(SignUpActivity.this, "Account ID already exists", Toast.LENGTH_SHORT).show();
                            } else {    // If it's another status code, display the default error message
                                Toast.makeText(SignUpActivity.this, "Registration failed: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.e("err:", response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) { // Handle connection errors
                        Toast.makeText(SignUpActivity.this, "Registration failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("err:", t.getMessage());
                    }
                });
            }
        });
    }

    public void hideKeyboard() {
        // Lấy view hiện tại đang focus trong Activity
        View view = this.getCurrentFocus();
        if (view != null) {
            // Lấy dịch vụ InputMethodManager từ context
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                // Ẩn bàn phím cho view hiện tại
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
