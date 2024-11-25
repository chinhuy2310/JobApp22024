package com.example.application22024;

import com.example.application22024.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("login")
    Call<User> login(@Body LoginRequest loginRequest);
    @POST("signup")
    Call<Void> signup(@Body SignupRequest signupRequest);
}
class LoginRequest {
    private String account;
    private String password;

    public LoginRequest(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
class SignupRequest {

    private String account;
    private String password;
    private String userName;
    private String contact;
    private String userType;

    public SignupRequest(String account, String password, String userName, String contact, String userType) {
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.contact = contact;
        this.userType = userType;
    }
}