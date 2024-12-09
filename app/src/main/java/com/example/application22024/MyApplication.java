package com.example.application22024;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.example.application22024.model.DataViewModel;

public class MyApplication extends Application {
    private static DataViewModel dataViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        // Tạo ViewModel chung cho toàn ứng dụng
        dataViewModel = new ViewModelProvider.AndroidViewModelFactory(this)
                .create(DataViewModel.class);
    }

    public static DataViewModel getDataViewModel() {
        return dataViewModel;
    }
}
