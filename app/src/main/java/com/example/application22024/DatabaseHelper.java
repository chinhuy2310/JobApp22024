package com.example.application22024;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.application22024.model.Job;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Phiên bản database và tên
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "JobApp.db";

    // Tên các bảng
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_PROFILES = "Profiles";
    private static final String TABLE_JOBS = "Jobs";
    private static final String TABLE_JOB_DURATION = "Job_Duration";
    private static final String TABLE_JOB_WORK_DAYS = "Job_Work_Days";
    private static final String TABLE_JOB_WORK_HOURS = "Job_Work_Hours";

    // Cột của bảng Users
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Cột của bảng Profiles
    private static final String COLUMN_PROFILE_ID = "profile_id";
    private static final String COLUMN_PROFILE_USER_ID = "user_id"; // Khóa ngoại
    private static final String COLUMN_PROFILE_PHONE = "profile_phone";
    private static final String COLUMN_PROFILE_ADDRESS = "profile_address";
    private static final String COLUMN_PROFILE_DOB = "profile_dob"; // Ngày sinh


    // Cột của bảng Jobs
    private static final String COLUMN_JOB_ID = "job_id";
    private static final String COLUMN_EMPLOYER_NAME = "employer_name";  // Nhà tuyển dụng
    private static final String COLUMN_JOB_TITLE = "job_title";  // Tên công việc
    private static final String COLUMN_JOB_DESCRIPTION = "job_description";  // Mô tả công việc
    private static final String COLUMN_JOB_LOCATION = "job_location";  // Địa điểm làm việc
    private static final String COLUMN_WORKPLACE_TYPE = "workplace_type";
    private static final String COLUMN_JOB_SALARY = "job_salary";  // Mức lương
    private static final String COLUMN_JOB_TYPE = "job_type";  // Loại hình công việc (toàn thời gian, bán thời gian,...)
    private static final String COLUMN_POST_DATE = "post_date";  // Ngày đăng công việc
    private static final String COLUMN_JOB_DURATION = "job_duration";  // Thời hạn công việc


    // Cột của bảng Job_Duration
    private static final String COLUMN_DURATION_ID = "duration_id";
    private static final String COLUMN_DURATION_TYPE = "duration_type"; // VD: "Theo giờ", "Theo ngày", "Theo tháng"
    private static final String COLUMN_DURATION_VALUE = "duration_value"; // Giá trị (số giờ, số ngày, số tháng)

    // Cột của bảng Job_Work_Days
    private static final String COLUMN_WORK_DAY_ID = "work_day_id";
    private static final String COLUMN_DAY = "day"; // VD: "Thứ Hai", "Thứ Ba", ...

    // Cột của bảng Job_Work_Hours
    private static final String COLUMN_WORK_HOUR_ID = "work_hour_id";
    private static final String COLUMN_START_TIME = "start_time"; // Giờ bắt đầu
    private static final String COLUMN_END_TIME = "end_time"; // Giờ kết thúc


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Users
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_USERS);

        // Tạo bảng Profiles
        String CREATE_TABLE_PROFILES = "CREATE TABLE " + TABLE_PROFILES + "("
                + COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PROFILE_USER_ID + " INTEGER,"
                + COLUMN_PROFILE_PHONE + " TEXT,"
                + COLUMN_PROFILE_ADDRESS + " TEXT,"
                + COLUMN_PROFILE_DOB + " TEXT,"
                + " FOREIGN KEY (" + COLUMN_PROFILE_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        db.execSQL(CREATE_TABLE_PROFILES);

        // Tạo bảng Jobs
        String CREATE_TABLE_JOBS = "CREATE TABLE " + TABLE_JOBS + "("
                + COLUMN_JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMPLOYER_NAME + " TEXT,"
                + COLUMN_JOB_TITLE + " TEXT,"
                + COLUMN_JOB_DESCRIPTION + " TEXT,"
                + COLUMN_JOB_LOCATION + " TEXT,"
                + COLUMN_WORKPLACE_TYPE + " TEXT,"
                + COLUMN_JOB_SALARY + " REAL,"
                + COLUMN_JOB_TYPE + " TEXT,"
                + COLUMN_POST_DATE + " TEXT,"
                + COLUMN_JOB_DURATION + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_JOBS);


        // Tạo bảng Job_Duration
        String CREATE_TABLE_JOB_DURATION = "CREATE TABLE " + TABLE_JOB_DURATION + "("
                + COLUMN_DURATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_JOB_ID + " INTEGER,"
                + COLUMN_DURATION_TYPE + " TEXT,"
                + COLUMN_DURATION_VALUE + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_JOB_ID + ") REFERENCES " + TABLE_JOBS + "(" + COLUMN_JOB_ID + ") ON DELETE CASCADE"
                + ")";
        db.execSQL(CREATE_TABLE_JOB_DURATION);
        // Tạo bảng Job_Work_Days
        String CREATE_TABLE_JOB_WORK_DAYS = "CREATE TABLE " + TABLE_JOB_WORK_DAYS + "("
                + COLUMN_WORK_DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_JOB_ID + " INTEGER,"
                + COLUMN_DAY + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_JOB_ID + ") REFERENCES " + TABLE_JOBS + "(" + COLUMN_JOB_ID + ") ON DELETE CASCADE"
                + ")";
        db.execSQL(CREATE_TABLE_JOB_WORK_DAYS);

        // Tạo bảng Job_Work_Hours
        String CREATE_TABLE_JOB_WORK_HOURS = "CREATE TABLE " + TABLE_JOB_WORK_HOURS + "("
                + COLUMN_WORK_HOUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_JOB_ID + " INTEGER,"
                + COLUMN_START_TIME + " TEXT,"
                + COLUMN_END_TIME + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_JOB_ID + ") REFERENCES " + TABLE_JOBS + "(" + COLUMN_JOB_ID + ") ON DELETE CASCADE"
                + ")";
        db.execSQL(CREATE_TABLE_JOB_WORK_HOURS);
        // Thêm công việc vào bảng Jobs
        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_EMPLOYER_NAME, " ABC company");
        values1.put(COLUMN_JOB_TITLE, " Android DEV");
        values1.put(COLUMN_JOB_DESCRIPTION, "Phát triển ứng dụng Android cho thiết bị di động.");
        values1.put(COLUMN_JOB_LOCATION, "Seoul, Korea");
        values1.put(COLUMN_JOB_SALARY, 2000);
        values1.put(COLUMN_JOB_TYPE, "정규직");
        values1.put(COLUMN_POST_DATE, "2024-10-01"); // Ngày đăng
        values1.put(COLUMN_WORKPLACE_TYPE, "사무직"); // Loại hình nơi làm việc


        long jobId1 = db.insert(TABLE_JOBS, null, values1);

// Thêm thời gian làm việc cho công việc 1
        ContentValues durationValues1 = new ContentValues();
        durationValues1.put(COLUMN_DURATION_ID, jobId1);
        durationValues1.put(COLUMN_DURATION_TYPE, "Theo tháng");
        durationValues1.put(COLUMN_DURATION_VALUE, 6); // 6 tháng
        db.insert(TABLE_JOB_DURATION, null, durationValues1);

// Thêm ngày làm việc cho công việc 1
        ContentValues workDaysValues1 = new ContentValues();
        workDaysValues1.put(COLUMN_WORK_DAY_ID, jobId1);
        workDaysValues1.put(COLUMN_DAY, "월");
        db.insert(TABLE_JOB_WORK_DAYS, null, workDaysValues1);
        workDaysValues1.put(COLUMN_DAY,"화");
        db.insert(TABLE_JOB_WORK_DAYS, null, workDaysValues1);
        workDaysValues1.put(COLUMN_DAY, "수");
        db.insert(TABLE_JOB_WORK_DAYS, null, workDaysValues1);
        workDaysValues1.put(COLUMN_DAY, "Thứ Năm");
        db.insert(TABLE_JOB_WORK_DAYS, null, workDaysValues1);
        workDaysValues1.put(COLUMN_DAY, "Thứ Sáu");
        db.insert(TABLE_JOB_WORK_DAYS, null, workDaysValues1);

// Thêm giờ làm việc cho công việc 1
        ContentValues workHoursValues1 = new ContentValues();
        workHoursValues1.put(COLUMN_WORK_HOUR_ID, jobId1);
        workHoursValues1.put(COLUMN_START_TIME, "08:00");
        workHoursValues1.put(COLUMN_END_TIME, "17:00");
        db.insert(TABLE_JOB_WORK_HOURS, null, workHoursValues1);

// ---------------------------------------------------

// Thêm công việc thứ 2 vào bảng Jobs
        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_EMPLOYER_NAME, "가게 XYZ");
        values2.put(COLUMN_JOB_TITLE, "판매 직원");
        values2.put(COLUMN_JOB_DESCRIPTION, "Bán hàng và tư vấn khách hàng.");
        values2.put(COLUMN_JOB_LOCATION, "Busan,Korea");
        values1.put(COLUMN_WORKPLACE_TYPE, "가게"); // Loại hình nơi làm việc
        values2.put(COLUMN_JOB_SALARY, 10000000); // Mức lương 10 triệu VNĐ
        values2.put(COLUMN_JOB_TYPE, "알바");
        values2.put(COLUMN_POST_DATE, "2024-10-02"); // Ngày đăng

        long jobId2 = db.insert(TABLE_JOBS, null, values2);

// Thêm thời gian làm việc cho công việc 2
        ContentValues durationValues2 = new ContentValues();
        durationValues2.put(COLUMN_DURATION_ID, jobId2);
        durationValues2.put(COLUMN_DURATION_TYPE, "Theo tháng");
        durationValues2.put(COLUMN_DURATION_VALUE, 3); // 3 tháng
        db.insert(TABLE_JOB_DURATION, null, durationValues2);

// Thêm ngày làm việc cho công việc 2
        ContentValues workDaysValues2 = new ContentValues();
        workDaysValues2.put(COLUMN_WORK_DAY_ID, jobId2);
        workDaysValues2.put(COLUMN_DAY, "Thứ Bảy");
        db.insert(TABLE_JOB_WORK_DAYS, null, workDaysValues2);
        workDaysValues2.put(COLUMN_DAY, "Chủ Nhật");
        db.insert(TABLE_JOB_WORK_DAYS, null, workDaysValues2);

// Thêm giờ làm việc cho công việc 2
        ContentValues workHoursValues2 = new ContentValues();
        workHoursValues2.put(COLUMN_WORK_HOUR_ID, jobId2);
        workHoursValues2.put(COLUMN_START_TIME, "09:00");
        workHoursValues2.put(COLUMN_END_TIME, "18:00");
        db.insert(TABLE_JOB_WORK_HOURS, null, workHoursValues2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu cần
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_DURATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_WORK_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_WORK_HOURS);

        // Tạo lại các bảng
        onCreate(db);
    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Sử dụng JOIN để lấy thông tin từ bảng Jobs và Job_Duration
        String query = "SELECT " + TABLE_JOBS + "." + COLUMN_JOB_TITLE + ", " +
                TABLE_JOBS + "." + COLUMN_EMPLOYER_NAME + ", " +
                TABLE_JOBS + "." + COLUMN_JOB_LOCATION + ", " +
                TABLE_JOBS + "." + COLUMN_JOB_TYPE + ", " +
                TABLE_JOBS + "." + COLUMN_WORKPLACE_TYPE + ", " +
                TABLE_JOBS + "." + COLUMN_JOB_DURATION + ", " +
                TABLE_JOBS + "." + COLUMN_JOB_SALARY + ", " +
                TABLE_JOB_DURATION + "." + COLUMN_DURATION_TYPE +
                " FROM " + TABLE_JOBS +
                " LEFT JOIN " + TABLE_JOB_DURATION +
                " ON " + TABLE_JOBS + "." + COLUMN_JOB_ID + " = " + TABLE_JOB_DURATION + "." + COLUMN_JOB_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Sử dụng constructor để tạo đối tượng Job với các trường từ cursor
                Job job = new Job(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYER_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKPLACE_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_SALARY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION_TYPE)) // Lấy từ bảng Job_Duration
                );

                jobs.add(job);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return jobs;
    }

}
