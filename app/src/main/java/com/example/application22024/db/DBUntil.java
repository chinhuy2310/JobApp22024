package com.example.application22024.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.application22024.R;

/**
 * 连接数据库
 * connect DataBase
 */

public class DBUntil extends SQLiteOpenHelper {
    private  static final int version = 6;//版本号，每次更改表结构需要加1，否则不生效
    private static final String databaseName = "db_jobApp.db";//数据库名，.db结尾
    private Context context;
    public static SQLiteDatabase con;//链接数据库，通过他操作数据库

    public DBUntil(Context context) {
        super(context, databaseName, null, version,null);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL("PRAGMA foreign_keys = false");

        String path = FileImgUntil.getImgName();//获取图片路径名
        Drawable defaultDrawable = ContextCompat.getDrawable(context, R.drawable.upimg); //获取默认头像
        Bitmap bitmap = ((BitmapDrawable) defaultDrawable).getBitmap();//获取图片内容
        FileImgUntil.saveBitmapAsync(bitmap, path);//保存图片

        //------------------------------------------------------------------------
        //雇主数据库
        db.execSQL("drop table if exists d_employer");//如果这表存在则删
        //雇主id，密码，描述...
        db.execSQL("create table d_employer(s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_describe varchar(200)," +
                "s_type varchar(20)," +
                "s_img varchar(255))");//存储图片路径

        db.execSQL("INSERT INTO d_employer (s_id,s_pwd,s_name,s_describe,s_type,s_img)" +
                        "VALUES(?,?,?,?,?,?)",
                new Object[]{"root","123456","AA Company","Computer components","store",path});

        //---------------------------------------------------------------


        //用户数据库
        db.execSQL("drop table if exists d_employee");//如果这表存在则删
        //用户信息....
        db.execSQL("create table d_employee(s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_sex varchar(200)," +
                "s_experience varchar(200)," +
                "s_phone varchar(20)," +
                "s_img varchar(255))");//存储图片路径

        db.execSQL("INSERT INTO d_employee (s_id,s_pwd,s_name,s_sex,s_experience,s_phone,s_img)" +
                        "VALUES(?,?,?,?,?,?,?)",
                new Object[]{"admin","123456","name","male","AAAAAA","12344321",path});

        //------------------------------------------------------------------------

        db.execSQL("PRAGMA foreign_keys = true");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
