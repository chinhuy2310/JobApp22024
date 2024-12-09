package com.example.application22024;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    //cấu hình và tạo một phiên bản Retrofit cho ứng dụng
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:3000/";


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
