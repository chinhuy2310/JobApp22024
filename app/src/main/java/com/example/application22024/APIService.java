package com.example.application22024;

import com.example.application22024.model.Company;
import com.example.application22024.model.Job;
import com.example.application22024.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("signup")
    Call<Void> signup(@Body SignupRequest signupRequest);
    @GET("companies/{userId}")
    Call<List<Company>> getCompanies(@Path("userId") int userId);
    @GET("companies/{companyId}/jobs")
    Call<List<Job>> getCompanyJobs(@Path("companyId") int companyId);
}

class LoginRequest {
    private String account;
    private String password;
    private String userType;

    public LoginRequest(String account, String password, String userType) {
        this.account = account;
        this.password = password;
        this.userType = userType;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}

class LoginResponse {
    private String message;
    private int user_id;
    private String user_type;


    public String getMessage() {
        return message;
    }
    public int getUserId() {
        return user_id;
    }
    public String getUser_type() {
        return user_type;
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