package com.example.application22024.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.application22024.R;
import com.example.application22024.db.AdminDao;

public class LoginEmployeeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_employee);

        // Tìm LinearLayout bằng ID               login button
        Button loginbutton = findViewById(R.id.buttonLogin);
        EditText idText = findViewById(R.id.login_employee_id);//获取输入的id
        EditText pwdText = findViewById(R.id.login_employee_pwd);//pwd

        //Login
        // Thiết lập sự kiện OnClickListener
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                String pwd = pwdText.getText().toString();

                if(id.isEmpty()){//判断是否输入
                    Toast.makeText(LoginEmployeeActivity.this,"계정을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(pwd.isEmpty()){
                    Toast.makeText(LoginEmployeeActivity.this,"비밀번호를 입력해 주세요",Toast.LENGTH_SHORT).show();
                } else{//用户登录
                    int a = AdminDao.loginEmployeeUser(id,pwd);
                    if(a == 1){
                        Toast.makeText(LoginEmployeeActivity.this,"사용자 로그인에 성공했습니다",Toast.LENGTH_SHORT).show();

                        // Tạo Intent để chuyển sang Activity khác     登陆成功-->跳转页面
                        Intent intent = new Intent(LoginEmployeeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent); // Bắt đầu chuyển sang Activity khác
                        finish();
                    }else{
                        Toast.makeText(LoginEmployeeActivity.this,"계정 또는 비밀번호가 올바르지 않습니다",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //Register
        Button registerButton = findViewById(R.id.login_page_employee_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转注册用户界面
                Intent intent = new Intent(LoginEmployeeActivity.this, RegistrationEmployeeActivity.class);
                startActivity(intent);
            }
        });

    }
}