package com.example.application22024.model;

public class User {
    private int userId;          // Mã người dùng
    private String username;     // Tên đăng nhập
    private String email;        // Email
    private String phone;        // Số điện thoại
    private String userType;     // Loại người dùng (Employer hoặc Employee)

    // Getter và Setter cho các trường
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
