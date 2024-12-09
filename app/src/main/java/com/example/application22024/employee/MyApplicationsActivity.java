package com.example.application22024.employee;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.APIService;
import com.example.application22024.R;
import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.SharedPrefManager;
import com.example.application22024.adapter.ApplicationAdapter;
import com.example.application22024.model.CompanyJobItem;
import com.example.application22024.model.Job;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplicationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplicationAdapter applicationAdapter;
    private List<Job> appliedJobs;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);

        recyclerView = findViewById(R.id.recyclerViewApplications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

//        fetchAppliedJobs();
    }

    // 수정
//    private void fetchAppliedJobs() {
//        int userId = SharedPrefManager.getInstance(this).getUserId();
//
//        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
//        Call<List<CompanyJobItem>> call = apiService.getAppliedJobs(userId);
//        call.enqueue(new Callback<List<CompanyJobItem>>(){
//            @Override
//            public void onResponse(Call<List<CompanyJobItem>> call, Response<List<CompanyJobItem>> response) {
//                if (response.isSuccessful()) {
//                    List<CompanyJobItem> appliedJobs = response.body();
//                    // Adapter에 데이터 설정
//                    applicationAdapter = new ApplicationAdapter(appliedJobs);
//                    recyclerView.setAdapter(applicationAdapter);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<CompanyJobItem>> call, Throwable t) {
//
//            }
//        });
//    }
}
