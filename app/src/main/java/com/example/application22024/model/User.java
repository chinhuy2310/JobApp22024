package com.example.application22024.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id") // Ánh xạ với cột `user_id` trong database
    private int user_id;

    @SerializedName("account") // Ánh xạ với cột `account` trong database
    private String account;

    @SerializedName("password") // Ánh xạ với cột `password` trong database
    private String password;

    @SerializedName("userName") // Ánh xạ với cột `userName` trong database
    private String userName;

    @SerializedName("contact") // Ánh xạ với cột `contact` trong database
    private String contact;

    @SerializedName("user_type") // Ánh xạ với cột `user_type` trong database
    private String user_type;

    @SerializedName("created_at") // Ánh xạ với cột `created_at` trong database
    private String createdAt;

    // Constructor mặc định
    public User() {}

    // Constructor đầy đủ
    public User(int user_id, String account, String contact, String userType) {
        this.user_id = user_id;
        this.account = account;
        this.contact = contact;
        this.user_type = userType;
    }

    // Getter và Setter cho từng thuộc tính
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserType() {
        return user_type;
    }

    public void setUserType(String user_type) {
        this.user_type = user_type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức `toString` để dễ dàng kiểm tra dữ liệu
    @Override
    public String toString() {
        return "User{" +
                "userId=" + user_id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", contact='" + contact + '\'' +
                ", userType='" + user_type + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
