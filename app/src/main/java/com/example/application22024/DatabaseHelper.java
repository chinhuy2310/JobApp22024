package com.example.application22024;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.application22024.model.Company;
import com.example.application22024.model.Job;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Phiên bản database và tên
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "JobApp.db";

    // Tên các bảng
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_PROFILES = "Profiles";
    private static final String TABLE_JOBS = "Jobs";
    private static final String TABLE_JOB_DURATION = "Job_Duration";
    public static final String TABLE_COMPANY = "company";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_JOB_POSITIONS = "job_positions";

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

        String createTable = "CREATE TABLE " + TABLE_COMPANY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_JOB_POSITIONS + " INTEGER)";
        db.execSQL(createTable);

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

        // Thêm công việc vào bảng Jobs
        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_EMPLOYER_NAME, "Tech Innovators");
        values1.put(COLUMN_JOB_TITLE, " Android DEV");
        values1.put(COLUMN_JOB_DESCRIPTION, "Phát triển ứng dụng Android cho thiết bị di động.");
        values1.put(COLUMN_JOB_LOCATION, "Seoul, Korea");
        values1.put(COLUMN_JOB_SALARY, 2000);
        values1.put(COLUMN_JOB_TYPE, "정규직");
        values1.put(COLUMN_POST_DATE, "2024-10-01"); // Ngày đăng
        values1.put(COLUMN_WORKPLACE_TYPE, "사무직"); // Loại hình nơi làm việc

        long jobId1 = db.insert(TABLE_JOBS, null, values1);

        // Thêm dữ liệu mẫu vào bảng company
        ContentValues values4 = new ContentValues();
        values4.put(COLUMN_NAME, "Tech Innovators");
        values4.put(COLUMN_CITY, "San Francisco");
        values4.put(COLUMN_JOB_POSITIONS, 5);
        db.insert(TABLE_COMPANY, null, values4);

        ContentValues values6 = new ContentValues();
        values6.put(COLUMN_NAME, "Global Enterprises");
        values6.put(COLUMN_CITY, "New York");
        values6.put(COLUMN_JOB_POSITIONS, 8);
        db.insert(TABLE_COMPANY, null, values6);

        ContentValues values3 = new ContentValues();
        values3.put(COLUMN_NAME, "Future Solutions");
        values3.put(COLUMN_CITY, "Los Angeles");
        values3.put(COLUMN_JOB_POSITIONS, 3);
        db.insert(TABLE_COMPANY, null, values3);

        ContentValues values5 = new ContentValues();
        values5.put(COLUMN_NAME, "EcoWorld");
        values5.put(COLUMN_CITY, "Chicago");
        values5.put(COLUMN_JOB_POSITIONS, 7);
        db.insert(TABLE_COMPANY, null, values5);

// Thêm công việc thứ 2 vào bảng Jobs
        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_EMPLOYER_NAME, "Global Enterprises");
        values2.put(COLUMN_JOB_TITLE, "판매 직원");
        values2.put(COLUMN_JOB_DESCRIPTION, "Bán hàng và tư vấn khách hàng.");
        values2.put(COLUMN_JOB_LOCATION, "Busan,Korea");
        values2.put(COLUMN_WORKPLACE_TYPE, "가게");
        values2.put(COLUMN_JOB_SALARY, 10000000);
        values2.put(COLUMN_JOB_TYPE, "알바");
        values2.put(COLUMN_POST_DATE, "2024-10-02");

        long jobId2 = db.insert(TABLE_JOBS, null, values2);

        ContentValues values8 = new ContentValues();
        values8.put(COLUMN_EMPLOYER_NAME, "Global Enterprises");
        values8.put(COLUMN_JOB_TITLE, "주차 관리원");
        values8.put(COLUMN_JOB_DESCRIPTION, "");
        values8.put(COLUMN_JOB_LOCATION, "2342");
        values8.put(COLUMN_WORKPLACE_TYPE, "df");
        values8.put(COLUMN_JOB_SALARY, 110);
        values8.put(COLUMN_JOB_TYPE, "알바");
        values8.put(COLUMN_POST_DATE, "2024-10-02"); // Ngày đăng

        long jobId8 = db.insert(TABLE_JOBS, null, values8);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu cần
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_DURATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);


        // Tạo lại các bảng
        onCreate(db);
    }

    // Fetch all companies
    public Cursor getAllCompanies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_COMPANY, null);
    }
//    public List<Company> fetchCompaniesFromDatabase1() {
//        List<Company> companyList = new ArrayList<>();
//        Cursor cursor = null;
//        try {
//            cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_COMPANY, null);
//
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
//                    String city = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY));
//                    int jobPositions = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_JOB_POSITIONS));
//
////                    Log.e("CompanyData", "Name: " + name + ", City: " + city + ", Job Positions: " + jobPositions);
//
//                    List<Job> jobs= null;
//                    companyList.add(new Company(name, city, jobPositions,jobs));
//                } while (cursor.moveToNext());
//            } else {
////                Log.e("CompanyData", "No companies found in the database.");
//            }
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return companyList;
//    }
//    public List<Company> fetchCompaniesFromDatabase2() {
//        List<Company> companyList = new ArrayList<>();
//        Cursor cursor = null;
//        try {
//            // Fetch all companies
//            cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_COMPANY, null);
//
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
//                    String city = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY));
//                    int jobPositions = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_JOB_POSITIONS));
//
//                    // Fetch jobs for the current company
//                    List<Job> jobs = fetchJobsForCompany(name);
//
//                    // Add company to the list with jobs populated
//                    companyList.add(new Company(name, city, jobPositions, jobs));
//                } while (cursor.moveToNext());
//            }
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return companyList;
//    }
    // Phương thức để lấy danh sách công việc cho một công ty
//    public List<Job> fetchJobsForCompany(String companyName) {
//        List<Job> jobList = new ArrayList<>();
//        Cursor cursor = null;
//        try {
//            // Truy vấn bảng công việc nơi công ty = companyName
//            String query = "SELECT * FROM " + TABLE_JOBS + " WHERE " + COLUMN_EMPLOYER_NAME + " = ?";
//            cursor = this.getReadableDatabase().rawQuery(query, new String[]{companyName});
//
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    // Lấy thông tin công việc từ cơ sở dữ liệu
//                    String jobTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_TITLE));
//                    String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_LOCATION));
//                    String salary = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_SALARY));
//
//                    // Thêm công việc vào danh sách
//                    jobList.add(new Job(jobTitle, location, salary));
//                } while (cursor.moveToNext());
//            }
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return jobList;
//    }

//    public List<Job> getAllJobs() {
//        List<Job> jobs = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // Sử dụng JOIN để lấy thông tin từ bảng Jobs và Job_Duration
//        String query = "SELECT " + TABLE_JOBS + "." + COLUMN_JOB_TITLE + ", " +
//                TABLE_JOBS + "." + COLUMN_EMPLOYER_NAME + ", " +
//                TABLE_JOBS + "." + COLUMN_JOB_LOCATION + ", " +
//                TABLE_JOBS + "." + COLUMN_JOB_TYPE + ", " +
//                TABLE_JOBS + "." + COLUMN_WORKPLACE_TYPE + ", " +
//                TABLE_JOBS + "." + COLUMN_JOB_DURATION + ", " +
//                TABLE_JOBS + "." + COLUMN_JOB_SALARY + ", " +
//                TABLE_JOB_DURATION + "." + COLUMN_DURATION_TYPE +
//                " FROM " + TABLE_JOBS +
//                " LEFT JOIN " + TABLE_JOB_DURATION +
//                " ON " + TABLE_JOBS + "." + COLUMN_JOB_ID + " = " + TABLE_JOB_DURATION + "." + COLUMN_JOB_ID;
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                // Sử dụng constructor để tạo đối tượng Job với các trường từ cursor
//                Job job = new Job(
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_TITLE)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYER_NAME)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_LOCATION)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_TYPE)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKPLACE_TYPE)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_DURATION)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_SALARY)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION_TYPE)) // Lấy từ bảng Job_Duration
//                );
//
//                jobs.add(job);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return jobs;
//    }
}
