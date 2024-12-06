package com.example.application22024;

import com.example.application22024.model.Company;
import com.example.application22024.model.CompanyJobItem;
import com.example.application22024.model.Job;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<List<Job>> getJobsForCompany(@Path("companyId") int companyId);

    @GET("api/companyjobs")
    Call<List<CompanyJobItem>> getCompanyJobs(@Query("userId") int userId);

    @POST("/api/saved-job")
    Call<Void> updateBookmarkStatus(
            @Query("user_id") int userId,
            @Query("job_id") int jobId);

    @POST("api/recently-viewed")
    Call<Void> saveRecentlyViewed(@Query("user_id") int userId,
                                  @Query("job_id") int jobId);

    @GET("api/get-recently-viewed")
    Call<List<CompanyJobItem>> getRecentlyViewed(@Query("userId") int userId);

    @GET("searchCompanyJobs")
    Call<List<CompanyJobItem>> searchCompanyJobs(
            @Query("keyword") String keyword,
            @Query("location") String location,
            @Query("user_id") int userId
    );

    @DELETE("/api/deleteJob/{job_id}")
    Call<Void> deleteJobDetails(@Path("job_id") int jobId);

    @DELETE("deleteCompany/{companyId}")
    Call<Void> deleteCompany(@Path("companyId") int companyId);

    @Multipart
    @POST("submit_registration")
    Call<ResponseBody> submitRegistration(
            @Part("employer_id") RequestBody employerId,
            @Part("company_id") RequestBody companyId,
            @Part("job_id") RequestBody jobId,
            @Part("Title") RequestBody title,
            @Part("company_name") RequestBody companyName,
            @Part("contact") RequestBody contact,
            @Part("WorkField") RequestBody workField,
            @Part("RecruitmentGender") RequestBody recruitmentGender,
            @Part("RecruitmentCount") RequestBody recruitmentCount,
            @Part("SalaryType") RequestBody salaryType,
            @Part("Salary") RequestBody salary,
            @Part("WorkHoursStart") RequestBody workHoursStart,
            @Part("WorkHoursEnd") RequestBody workHoursEnd,
            @Part("can_negotiable_time") RequestBody canNegotiableTime,
            @Part("WorkType") RequestBody workType,
            @Part("WorkPeriod") RequestBody workPeriod,
            @Part("WorkDays") RequestBody workDays,
            @Part("can_negotiable_days") RequestBody canNegotiableDays,
            @Part("RecruitmentEnd") RequestBody recruitmentEnd,
            @Part("address") RequestBody address,
            @Part("Details") RequestBody details,
            @Part("name_of_representative") RequestBody representativeName,
            @Part("registration_number") RequestBody registrationNumber,
            @Part MultipartBody.Part image // Hình ảnh nếu có
    );

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
