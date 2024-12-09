package com.example.application22024.employer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.application22024.APIService;
import com.example.application22024.First_Activity;
import com.example.application22024.MyApplication;
import com.example.application22024.R;

import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.SharedPrefManager;
import com.example.application22024.adapter.CompanyAdapter;
import com.example.application22024.model.Company;
import com.example.application22024.model.DataViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerMain extends AppCompatActivity {
    // Views
    private ImageView openMenuButton;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    TextView addNewRecruitment;

    // Data and Adapter
    private CompanyAdapter adapter;
    APIService apiService;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Company> companyList;
    private boolean backPressedOnce = false;
    DataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_employer);
        viewModel = ((MyApplication) getApplication()).getDataViewModel();
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

        // Initialize views
        initViews();

        // Set up Navigation Drawer
        setupNavigationDrawer();

        // Set up RecyclerView
        setupRecyclerView();


//        int userId = getIntent().getIntExtra("user_id", -1); // Nếu không có user_id thì mặc định là -1
        int userId = SharedPrefManager.getInstance(this).getUserId();
        Log.e("userid", String.valueOf(userId));
        if (userId != -1) {
            // Gọi API để lấy thông tin công ty của Employer dựa trên userId
            getCompanies(userId);
        } else {
            // Xử lý nếu không có user_id (lỗi)
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCompanies(userId);
            }
        });
//        Log.e("CompanyListSize", "Fetched " + companyList.size() + " companies.");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Tắt tiêu đề của ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }


    private void getCompanies(int userId) {
        // Gọi API lấy các công ty của employer
        Call<List<Company>> call = apiService.getCompanies(userId);
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                Log.e("get company with userid: ", String.valueOf(userId));
                if (response.isSuccessful() && response.body() != null) {
                    List<Company> companies = response.body();
                    // Cập nhật danh sách công ty và thông báo adapter thay đổi dữ liệu
                    companyList.clear();  // Xóa danh sách công ty cũ
                    companyList.addAll(companies);
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("CompanyListSize", "Fetched2 " + companyList.size() + " companies.");

                    adapter.notifyDataSetChanged();  // Thông báo adapter cập nhật lại dữ liệu
                    updateUI();  // Cập nhật giao diện (nếu cần)

                } else {
//                    if (companyList.isEmpty()) {
//                        Toast.makeText(EmployerMain.this, "No companies found for this user", Toast.LENGTH_SHORT).show();
//                    }
                    updateUI();
//                    Log.e("API Error", "Response body is empty or error occurred");
                    Toast.makeText(EmployerMain.this, "No companies found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                Toast.makeText(EmployerMain.this, "Failed to load companies: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        openMenuButton = findViewById(R.id.menuButton);
        recyclerView = findViewById(R.id.companyView);
        emptyView = findViewById(R.id.emptyView);
        addNewRecruitment = findViewById(R.id.addNewRecruitment);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Set listener
        openMenuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        addNewRecruitment.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerMain.this, RegistrationActivity.class);
            startActivity(intent);
        });

    }

    // Set up Navigation Drawer and its item click handling
    private void setupNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
            if (item.getItemId() == R.id.addRecruitment) {
                showCompanySelectionDialog();
                return true;
            } else if (item.getItemId() == R.id.Logout) {
                showLogoutConfirmation();
                return true;
            }
            return false;
        });
    }


    // Set up RecyclerView with its layout and adapter
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        companyList = new ArrayList<>();
        adapter = new CompanyAdapter(this, companyList);
        recyclerView.setAdapter(adapter);
    }


    // Update the UI based on the company list
    private void updateUI() {
        if (companyList.isEmpty()) {
//            Log.e("RecyclerViewDebug", "No companies to show. Showing empty view.");
            swipeRefreshLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
//            Log.e("RecyclerViewDebug", "Showing company list in RecyclerView.");
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        adapter.updateData(companyList);
//        Log.e("AdapterDebug", "Adapter Notified");

    }

    private void showCompanySelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Choose an existing company", "Create new"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                // Hiển thị danh sách công ty
                showCompanyPickerDialog();
            } else if (which == 1) {
                viewModel.reset();
                // Chuyển đến màn hình tạo mới công ty
                Intent intent = new Intent(EmployerMain.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        builder.create().show();
    }

    private void showCompanyPickerDialog() {
        if (companyList.isEmpty()) {
            Toast.makeText(this, "There are no companies to choose from.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo danh sách tên công ty
        String[] companyNames = new String[companyList.size()];
        for (int i = 0; i < companyList.size(); i++) {
            companyNames[i] = companyList.get(i).getCompanyName();
        }

        // Hiển thị AlertDialog để chọn công ty
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Company");


        builder.setItems(companyNames, (dialog, which) -> {
            // Công ty được chọn
            Company selectedCompany = companyList.get(which);
            Toast.makeText(this, "Selected: " + selectedCompany.getCompanyName(), Toast.LENGTH_SHORT).show();
            viewModel.setSelectedCompany(selectedCompany);
            // Có thể chuyển đến màn hình đăng tuyển với thông tin công ty
            Intent intent = new Intent(EmployerMain.this, RegistrationActivity.class);
            startActivity(intent);

        });

        builder.create().show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            moveTaskToBack(true); // Ẩn ứng dụng
//            finish(); // Kết thúc Activity (thoát hoàn toàn)
        } else {
            backPressedOnce = true;
            Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

            // Đặt lại trạng thái sau 2 giây
            new Handler().postDelayed(() -> backPressedOnce = false, 2000);
        }
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    SharedPrefManager.getInstance(this).clear();
                    Intent intent = new Intent(EmployerMain.this, First_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

}
