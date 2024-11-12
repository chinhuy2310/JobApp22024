package com.example.application22024.employer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application22024.R;
import com.example.application22024.db.AdminDao;
import com.example.application22024.employee.LoginEmployeeActivity;
import com.example.application22024.employee.MainActivity;
import com.example.application22024.employee.RegistrationEmployeeActivity;

public class LoginEmployerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_employer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tìm LinearLayout bằng ID               login button
        Button loginbutton = findViewById(R.id.employerbuttonLogin);
        EditText idText = findViewById(R.id.login_employer_id);//获取输入的id
        EditText pwdText = findViewById(R.id.login_employer_pwd);//pwd

        //Login
        // Thiết lập sự kiện OnClickListener
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                String pwd = pwdText.getText().toString();

                if(id.isEmpty()){//判断是否输入
                    Toast.makeText(LoginEmployerActivity.this,"계정을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(pwd.isEmpty()){
                    Toast.makeText(LoginEmployerActivity.this,"비밀번호를 입력해 주세요",Toast.LENGTH_SHORT).show();
                } else{//雇主登录
                    int a = AdminDao.loginEmployer(id,pwd);
                    if(a == 1){
                        Toast.makeText(LoginEmployerActivity.this,"고용주 로그인에 성공했습니다",Toast.LENGTH_SHORT).show();


                        // Tạo Intent để chuyển sang Activity khác     登陆成功-->跳转页面
                        //11111111111111111111111111111   添加后续页面    1111111111111111111111111111111111111111111111111111111
                        Intent intent = new Intent(LoginEmployerActivity.this, RegistrationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent); // Bắt đầu chuyển sang Activity khác
                        finish();
                    }else{
                        Toast.makeText(LoginEmployerActivity.this,"계정 또는 비밀번호가 올바르지 않습니다",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //Register
        Button registerButton = findViewById(R.id.login_page_employer_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转注册雇主界面
                Intent intent = new Intent(LoginEmployerActivity.this, RegistrationEmployerActivity.class);
                startActivity(intent);
            }
        });
    }
}