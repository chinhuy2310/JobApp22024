package com.example.application22024.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Applicant {
    private int employee_id;
    private String avatar_url;
    private String full_name;
    private String gender;
    private String date_of_birth;
    private String phone_number;
    private String education_level;
    private String education_status;
    private String experience;
    private String introduction;
    private String preferred_work_location;
    private String preferred_work_duration;
    private String work_type;
    private String salary_type;
    private int expected_salary;

    // Getters và Setters
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEducation_level() {
        return education_level;
    }

    public void setEducation_level(String education_level) {
        this.education_level = education_level;
    }

    public String getEducation_status() {
        return education_status;
    }

    public void setEducation_status(String education_status) {
        this.education_status = education_status;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPreferred_work_location() {
        return preferred_work_location;
    }

    public void setPreferred_work_location(String preferred_work_location) {
        this.preferred_work_location = preferred_work_location;
    }

    public String getPreferred_work_duration() {
        return preferred_work_duration;
    }

    public void setPreferred_work_duration(String preferred_work_duration) {
        this.preferred_work_duration = preferred_work_duration;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getSalary_type() {
        return salary_type;
    }

    public void setSalary_type(String salary_type) {
        this.salary_type = salary_type;
    }

    public int getExpected_salary() {
        return expected_salary;
    }

    public void setExpected_salary(int expected_salary) {
        this.expected_salary = expected_salary;
    }

    public int getAge() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date birthDate = sdf.parse(date_of_birth);

            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(birthDate);

            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

            // Điều chỉnh tuổi nếu sinh nhật chưa qua trong năm nay
            if (today.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                    (today.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }

            return age;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;  // Trả về 0 nếu có lỗi trong quá trình tính toán
        }
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
