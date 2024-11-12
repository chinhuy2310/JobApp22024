package com.example.application22024.db;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * 操作数据库！！！！！！！！！
 操作数据
 保存，读取
 */

public class AdminDao {

    public static SQLiteDatabase db = DBUntil.con;

    /**
     * 实现保存商家
     * @param id
     * @param pwd
     * @param name
     * @param describe
     * @param type
     * @param tx
     * @return
     */
    public static int saveEmployerUser(String id, String pwd, String name, String describe, String type, String tx){
        String data[] = {id,pwd,name,describe,type,tx};

        try {
            db.execSQL("INSERT INTO d_employer (s_id,s_pwd,s_name,s_describe,s_type,s_img)" +
                            "VALUES(?,?,?,?,?,?)",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 保存普通用户
     * @param id
     * @param pwd
     * @param name
     * @param sex
     * @param experience
     * @param phone
     * @param tx
     * @return
     */
    public static int saveEmployeeUser(String id, String pwd, String name, String sex, String experience, String phone, String tx){
        String data[] = {id,pwd,name,sex,experience,phone,tx};


        try {
            db.execSQL("INSERT INTO d_employee (s_id,s_pwd,s_name,s_sex,s_experience,s_phone,s_img)" +
                            "VALUES(?,?,?,?,?,?,?)",
                    data);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }


    /**
     * 登录商家
     * @param account
     * @param pwd
     * @return
     */
    public static int loginEmployer(String account, String pwd){
        String data[] = {account,pwd};
        String sql = "select * from d_employer where s_id=? and s_pwd=?";
        Cursor result = db.rawQuery(sql,data);
        while(result.moveToNext()){
            return 1;
        }
        return 0;
    }

    /**
     * 登录用户
     * @param account
     * @param pwd
     * @return
     */
    public static int loginEmployeeUser(String account,String pwd){
        String data[] = {account,pwd};
        String sql = "select * from d_employee where s_id=? and s_pwd=?";
        Cursor result = db.rawQuery(sql,data);
        while(result.moveToNext()){
            return 1;
        }
        return 0;
    }
}
