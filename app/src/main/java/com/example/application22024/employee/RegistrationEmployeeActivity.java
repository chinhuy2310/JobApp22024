package com.example.application22024.employee;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.application22024.R;
import com.example.application22024.db.AdminDao;
import com.example.application22024.db.FileImgUntil;

import androidx.appcompat.widget.Toolbar;

public class RegistrationEmployeeActivity extends AppCompatActivity {
    //创建文件选择器
    private ActivityResultLauncher<String> getContentLauncher;
    Uri uri = null;
    private String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_employee);

        //实现左上角按钮返回功能 Back previous page
        Toolbar toolbar = findViewById(R.id.register_employee_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ImageView imgText = findViewById(R.id.register_employee_img);
        //初始化文件选择器
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    imgText.setImageURI(result);
                    uri = result;
                }else {
                    Toast.makeText(RegistrationEmployeeActivity.this,"프로필 사진이 선택되지 않았습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //获取默认头像
        Drawable defaultDrawable = ContextCompat.getDrawable(this,R.drawable.upimg);

        //获取注册数据
        EditText idText = findViewById(R.id.register_employee_id);
        EditText pwdText = findViewById(R.id.register_employee_pwd);
        EditText nameText = findViewById(R.id.register_employee_name);
        sex = "female";//性别
        RadioButton male = findViewById(R.id.register_employee_male);
        male.setChecked(true);
        if(male.isChecked()) {
            sex = "male";
        }
        EditText experienceText = findViewById(R.id.register_employee_experience);
        EditText phoneText = findViewById(R.id.register_employee_phone);
        Button reg = findViewById(R.id.register_employee_reg);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否符合条件
                String id = idText.getText().toString();
                String pwd = pwdText.getText().toString();
                String name = nameText.getText().toString();
                String experience = experienceText.getText().toString();
                String phone = phoneText.getText().toString();

                //判断图片
                Drawable drawable = imgText.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//获取图片内容
                Bitmap bitmapDef = ((BitmapDrawable) defaultDrawable).getBitmap();//获取默认图片内容

                if(bitmap.sameAs(bitmapDef)){   //判断用户有没有上传图片
                    Toast.makeText(RegistrationEmployeeActivity.this,"사진을 클릭하여 프로필 사진을 추가해 주세요",Toast.LENGTH_SHORT).show();
                }else if(id.isEmpty()){
                    Toast.makeText(RegistrationEmployeeActivity.this,"사용자 계정을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(pwd.isEmpty()){
                    Toast.makeText(RegistrationEmployeeActivity.this,"비밀번호를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(name.isEmpty()){
                    Toast.makeText(RegistrationEmployeeActivity.this,"이름을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(experience.isEmpty()){
                    Toast.makeText(RegistrationEmployeeActivity.this,"개인 경력을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(phone.isEmpty()){
                    Toast.makeText(RegistrationEmployeeActivity.this,"연락처를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else{

                    //开始向数据库添加数据

                    //将bitmap存储为其它文件并输入保存路径
                    String path = FileImgUntil.getImgName();//获取图片路径名
                    FileImgUntil.saveImageBitmapToFileImg(uri, RegistrationEmployeeActivity.this,path);//保存图片

                    //*****************************将信息保存数据库*********************************************************
                    int a = AdminDao.saveEmployeeUser(id,pwd,name,sex,experience,phone,path);
                    if(a == 1){
                        Toast.makeText(RegistrationEmployeeActivity.this,"사용자 등록이 성공적으로 완료되었습니다",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegistrationEmployeeActivity.this,"사용자 등록에 실패했습니다",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        //实现选择图片事件
        imgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });













    }

    //打开文件选择器
    private void openGallery(View v){
        // 打开相册等待用户选择图片
        getContentLauncher.launch("image/*");
    }


    // Phương thức để chuyển Fragment
    public void showNextFragment(Fragment fragment) {
//        // Hiển thị toolbar cho Fragment thứ 2 và ẩn cho Fragment thứ 1
//        if (fragment instanceof SecondStepFragment) {
//            toolbar.setVisibility(View.VISIBLE); // Hiển thị toolbar cho Fragment thứ 2
//        } else {
//            toolbar.setVisibility(View.GONE); // Ẩn toolbar cho Fragment thứ 1
//        }

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .addToBackStack(null)
//                .commit();
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
