package com.example.application22024.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.application22024.APIService;
import com.example.application22024.DatabaseHelper;

import com.example.application22024.MapActivity;
import com.example.application22024.R;
import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.SharedPrefManager;
import com.example.application22024.adapter.Page1AndSearchAdapter;
import com.example.application22024.employer.EmployerMain;
import com.example.application22024.model.Company;
import com.example.application22024.model.CompanyJobItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Page1 extends Fragment {

    private RecyclerView recyclerView, recyclerView2;
    private Page1AndSearchAdapter adapter;
    private LinearLayout selectLocation;
    private List<CompanyJobItem> companyJobItems = new ArrayList<>();
    private APIService apiService;
    private static final int MAP_REQUEST_CODE = 1;

    private TextView locationTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1, container, false);

        selectLocation = view.findViewById(R.id.selectLocation);
        locationTextView = view.findViewById(R.id.locationTextView);
        // TextView để hiển thị địa chỉ
        selectLocation.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MapActivity.class);
            startActivityForResult(intent, MAP_REQUEST_CODE);
        });

        recyclerView = view.findViewById(R.id.SuggestedJob);
        recyclerView2 = view.findViewById(R.id.recentJob);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadCompanyJobData();
        getRecentlyViewed();
        adapter = new Page1AndSearchAdapter(getContext(), companyJobItems, 0);

        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);

        ViewPager2 viewPager = getActivity().findViewById(R.id.viewPager);
        setupRecyclerView(recyclerView, viewPager);
        setupRecyclerView(recyclerView2, viewPager);
        return view;
    }


    private void openMap() {
        // nhập vị trí cụ thể hoặc chọn trên bản đồ

    }

    private void loadCompanyJobData() {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        int userId = SharedPrefManager.getInstance(getContext()).getUserId();
        Call<List<CompanyJobItem>> call = apiService.getCompanyJobs(userId);

        call.enqueue(new Callback<List<CompanyJobItem>>() {
            @Override
            public void onResponse(Call<List<CompanyJobItem>> call, Response<List<CompanyJobItem>> response) {
                if (response.isSuccessful()) {
                    companyJobItems = response.body();
                    adapter = new Page1AndSearchAdapter(getContext(), companyJobItems, 0);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CompanyJobItem>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRecentlyViewed() {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        int userId = SharedPrefManager.getInstance(getContext()).getUserId();
        Call<List<CompanyJobItem>> call = apiService.getRecentlyViewed(userId);

        call.enqueue(new Callback<List<CompanyJobItem>>() {
            @Override
            public void onResponse(Call<List<CompanyJobItem>> call, Response<List<CompanyJobItem>> response) {
                if (response.isSuccessful()) {
                    companyJobItems = response.body();
                    adapter = new Page1AndSearchAdapter(getContext(), companyJobItems, 0);
                    recyclerView2.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CompanyJobItem>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView, ViewPager2 viewPager) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewPager.setUserInputEnabled(false); // Tắt vuốt ViewPager2 khi bắt đầu cuộn
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        viewPager.setUserInputEnabled(true);  // Bật lại vuốt ViewPager2 khi cuộn xong
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                // Không cần xử lý
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                // Không cần xử lý
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAP_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            // Nhận vị trí từ bản đồ
            String selectedLocation = data.getStringExtra("selected_location");
            locationTextView.setText("Vị trí: " + selectedLocation);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // Gọi phương thức mỗi khi fragment được mở lại
        getRecentlyViewed();
    }
}
