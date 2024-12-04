package com.example.application22024.employee;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.application22024.APIService;
import com.example.application22024.R;
import com.example.application22024.RegionDataManager;
import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.adapter.CompanyJobAdapter;
import com.example.application22024.adapter.LeftAdapter;
import com.example.application22024.adapter.RightAdapter;
import com.example.application22024.model.CompanyJobItem;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Page3 extends Fragment {

    private TextView selectedLocationText;
    private EditText searchEditText;
    private ImageView searchButton;
    private RecyclerView recyclerView;
    private CompanyJobAdapter adapter;
    private List<CompanyJobItem> companyJobItems = new ArrayList<>();
    private APIService apiService;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);
        view.findViewById(R.id.selectLocation).setOnClickListener(v -> showCustomDialog());

        selectedLocationText = view.findViewById(R.id.selectedLocationTextView);
        searchEditText = view.findViewById(R.id.searchEditText);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            String keyword = searchEditText.getText().toString().trim();
            String location;
            if (selectedLocationText.getText().toString().trim().equals("All")){
                location ="";
            }else{
                location = selectedLocationText.getText().toString().trim();
            }
            performSearch(keyword, location);
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu
        loadCompanyJobData();

        // Cập nhật RecyclerView với adapter
        adapter = new CompanyJobAdapter(getContext(), companyJobItems);
        recyclerView.setAdapter(adapter);

        return view;
    }
    private void performSearch(String keyword, String location) {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

        Call<List<CompanyJobItem>> call = apiService.searchCompanyJobs(keyword, location);

        call.enqueue(new Callback<List<CompanyJobItem>>() {
            @Override
            public void onResponse(Call<List<CompanyJobItem>> call, Response<List<CompanyJobItem>> response) {
                if (response.isSuccessful()) {
                    companyJobItems = response.body();
                    if (companyJobItems != null && !companyJobItems.isEmpty()) {
                        adapter = new CompanyJobAdapter(getContext(), companyJobItems);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "Không tìm thấy kết quả phù hợp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CompanyJobItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCompanyJobData() {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

        Call<List<CompanyJobItem>> call = apiService.getCompanyJobs();

        call.enqueue(new Callback<List<CompanyJobItem>>() {
            @Override
            public void onResponse(Call<List<CompanyJobItem>> call, Response<List<CompanyJobItem>> response) {
                if (response.isSuccessful()) {
                    companyJobItems = response.body();
                    adapter = new CompanyJobAdapter(getContext(), companyJobItems);
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

    private void showCustomDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_select_location_layout);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        // Lấy dữ liệu từ RegionDataManager
        HashMap<String, ArrayList<String>> regionsData = RegionDataManager.getRegionsData();
        List<String> provinces = new ArrayList<>(regionsData.keySet());
        List<String> areas = new ArrayList<>();

        // RecyclerView
        RecyclerView leftRecyclerView = dialog.findViewById(R.id.left_recycler_view);
        RecyclerView rightRecyclerView = dialog.findViewById(R.id.right_recycler_view);

        leftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapters
        LeftAdapter leftAdapter = new LeftAdapter(provinces, province -> {
            areas.clear();
            areas.addAll(regionsData.get(province));
            rightRecyclerView.getAdapter().notifyDataSetChanged();
            RightAdapter rightAdapter = (RightAdapter) rightRecyclerView.getAdapter();
            rightAdapter.setSelectedPosition(0); // Đặt lại chọn mục đầu tiên
        });

        RightAdapter rightAdapter = new RightAdapter(areas);

        leftRecyclerView.setAdapter(leftAdapter);
        rightRecyclerView.setAdapter(rightAdapter);

        if (!provinces.isEmpty()) {
            // Chọn mục đầu tiên của LeftAdapter
            leftAdapter.setSelectedPosition(0);
            String firstProvince = provinces.get(0);
            areas.addAll(regionsData.get(firstProvince)); // Thêm dữ liệu cho RightAdapter
            rightAdapter.notifyDataSetChanged(); // Cập nhật RightAdapter
            rightAdapter.setSelectedPosition(0);
        }
        // Xử lý khi nhấn nút "Xác nhận"
        dialog.findViewById(R.id.confirm_button).setOnClickListener(v -> {
            // Lấy tỉnh và khu vực đã chọn từ Adapter
            String selectedProvince = leftAdapter.getSelectedProvince();
            String selectedArea = rightAdapter.getSelectedArea();

            // Cập nhật TextView trong Fragment
            selectedLocationText.setText(selectedProvince + " " + selectedArea);

            // Đóng dialog
            dialog.dismiss();
        });

        // Hiển thị dialog
        dialog.show();
    }
}
