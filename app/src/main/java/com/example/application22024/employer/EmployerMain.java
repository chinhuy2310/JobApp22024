package com.example.application22024.employer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import com.example.application22024.DatabaseHelper;
import com.example.application22024.First_Activity;
import com.example.application22024.R;

import com.example.application22024.adapter.CompanyAdapter;
import com.example.application22024.model.Company;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class EmployerMain extends AppCompatActivity {
    // Views
    private ImageView openMenuButton;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
            TextView addNewRecruitment;

    // Data and Adapter
    private CompanyAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<Company> companyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_employer);

        // Initialize views
        initViews();

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Set up Navigation Drawer
        setupNavigationDrawer();

        // Set up RecyclerView
        setupRecyclerView();

        // Fetch data from database and update UI
        companyList = databaseHelper.fetchCompaniesFromDatabase2();
//        Log.e("CompanyListSize", "Fetched " + companyList.size() + " companies.");


        updateUI();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Tắt tiêu đề của ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    // Initialize all views
    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        openMenuButton = findViewById(R.id.menuButton);
        recyclerView = findViewById(R.id.companyView);
        emptyView = findViewById(R.id.emptyView);
        addNewRecruitment = findViewById(R.id.addNewRecruitment);

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
            Intent intent;
            if (item.getItemId() == R.id.addRecruitment) {
                showCompanySelectionDialog();
                return true;
            } else if (item.getItemId() == R.id.Logout) {
                // Đăng xuất và trở về First_Activity
                intent = new Intent(EmployerMain.this, First_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
//            Log.e("RecyclerViewDebug", "Showing company list in RecyclerView.");
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

            // Có thể chuyển đến màn hình đăng tuyển với thông tin công ty
            Intent intent = new Intent(EmployerMain.this, RegistrationActivity.class);
            intent.putExtra("companyName", selectedCompany.getCompanyName());
            startActivity(intent);

//            // Lấy thông tin công ty từ Intent ở activity tiếp theo
//            String companyName = getIntent().getStringExtra("companyName");
//            if (companyName != null) {
//                // Hiển thị hoặc xử lý thông tin công ty
//                TextView companyTextView = findViewById(R.id.companyNameTextView);
//                companyTextView.setText("Công ty: " + companyName);
//            }
        });

        builder.create().show();
    }

}
