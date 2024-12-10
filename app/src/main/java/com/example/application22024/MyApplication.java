package com.example.application22024;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.example.application22024.model.RegistrationViewModel;

public class MyApplication extends Application {
    private static RegistrationViewModel registrationViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        // Tạo ViewModel chung cho toàn ứng dụng
        registrationViewModel = new ViewModelProvider.AndroidViewModelFactory(this)
                .create(RegistrationViewModel.class);
    }

    public static RegistrationViewModel getRegistrationViewModel() {
        return registrationViewModel;
    }
}
