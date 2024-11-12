package com.example.application22024.employer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.application22024.R;
import com.example.application22024.db.AdminDao;
import com.example.application22024.db.FileImgUntil;
import com.example.application22024.employee.RegistrationEmployeeActivity;

public class RegistrationEmployerActivity extends AppCompatActivity {
    //创建文件选择器
    private ActivityResultLauncher<String> getContentLauncher;
    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_employer);

        //实现左上角按钮返回功能 Back previous page
        Toolbar toolbar = findViewById(R.id.register_employer_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ImageView imgText = findViewById(R.id.register_employer_img);
        //初始化文件选择器
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    imgText.setImageURI(result);
                    uri = result;
                }else {
                    Toast.makeText(RegistrationEmployerActivity.this,"프로필 사진이 선택되지 않았습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //获取默认头像
        Drawable defaultDrawable = ContextCompat.getDrawable(this,R.drawable.upimg);

        //获取注册数据
        EditText idText = findViewById(R.id.register_employer_id);
        EditText pwdText = findViewById(R.id.register_employer_pwd);
        EditText nameText = findViewById(R.id.register_employer_name);
        EditText describeText = findViewById(R.id.register_employer_describe);
        EditText typeText = findViewById(R.id.register_employer_type);
        Button reg = findViewById(R.id.register_employer_reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否符合条件
                String id = idText.getText().toString();
                String pwd = pwdText.getText().toString();
                String name = nameText.getText().toString();
                String describe = describeText.getText().toString();
                String type = typeText.getText().toString();

                //判断图片
                Drawable drawable = imgText.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//获取图片内容
                Bitmap bitmapDef = ((BitmapDrawable) defaultDrawable).getBitmap();//获取默认图片内容

                if(bitmap.sameAs(bitmapDef)){   //判断用户有没有上传图片
                    Toast.makeText(RegistrationEmployerActivity.this,"사진을 클릭하여 프로필 사진을 추가해 주세요",Toast.LENGTH_SHORT).show();
                }else if(id.isEmpty()){
                    Toast.makeText(RegistrationEmployerActivity.this,"사용자 계정을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(pwd.isEmpty()){
                    Toast.makeText(RegistrationEmployerActivity.this,"비밀번호를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(name.isEmpty()){
                    Toast.makeText(RegistrationEmployerActivity.this,"기업 이름을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(describe.isEmpty()){
                    Toast.makeText(RegistrationEmployerActivity.this,"회사 설명을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(type.isEmpty()){
                    Toast.makeText(RegistrationEmployerActivity.this,"회사 유형을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else{

                    //开始向数据库添加数据

                    //将bitmap存储为其它文件并输入保存路径
                    String path = FileImgUntil.getImgName();//获取图片路径名
                    FileImgUntil.saveImageBitmapToFileImg(uri, RegistrationEmployerActivity.this,path);//保存图片

                    //*****************************将信息保存数据库*********************************************************
                    int a = AdminDao.saveEmployerUser(id,pwd,name,describe,type,path);
                    if(a == 1){
                        Toast.makeText(RegistrationEmployerActivity.this,"고용주 등록이 성공적으로 완료되었습니다",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegistrationEmployerActivity.this,"고용주 등록에 실패했습니다",Toast.LENGTH_SHORT).show();
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
}