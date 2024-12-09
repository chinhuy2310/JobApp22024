package com.example.application22024.employee;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.application22024.APIService;

import com.example.application22024.R;
import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.SharedPrefManager;
import com.example.application22024.adapter.Page1AndSearchAdapter;
import com.example.application22024.model.CompanyJobItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Page3 extends Fragment {
    private APIService apiService;
    private RecyclerView recyclerView;
    private Page1AndSearchAdapter adapter;
    private List<CompanyJobItem> companyJobItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getMarkedJobs();
        // Cập nhật RecyclerView với adapter
        adapter = new Page1AndSearchAdapter(getContext(), companyJobItems, 1);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getMarkedJobs() {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        int userId = SharedPrefManager.getInstance(getContext()).getUserId();
        Call<List<CompanyJobItem>> call = apiService.getMarkedJobs(userId);

        call.enqueue(new Callback<List<CompanyJobItem>>() {
            @Override
            public void onResponse(Call<List<CompanyJobItem>> call, Response<List<CompanyJobItem>> response) {
                if (response.isSuccessful()) {
                    companyJobItems = response.body();
                    adapter = new Page1AndSearchAdapter(getContext(), companyJobItems, 1);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
                    Log.e("Page3", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CompanyJobItem>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                Log.e("Page3", "Network error: " + t.getMessage());
            }
        });
    }
    public void onResume() {
        super.onResume();
        // Gọi phương thức mỗi khi fragment được mở lại
        getMarkedJobs();
    }
}