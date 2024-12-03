package com.example.application22024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.application22024.employer.EmployerMain;
import com.example.application22024.employer.RegistrationActivity;
import com.example.application22024.model.Job;
import com.example.application22024.model.RegistrationViewModel;

public class JobDetails extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView dateTextview, titleTextview, companyTextview, salaryTypeTextview, salaryTextview,workDayTextview, startTimeTextview, endTimeTextview;
    private TextView recruitmentCountTextview, genderTextview, deadlineTextview;
    private TextView salaryTypeTextview2, salaryTextview2, workPeriodTextview, workDaysTextview,startTime2Textview, endTime2Textview;
    private TextView detailsTextview, addressTextview,companyTextview2, contactTextview;
    private LinearLayout viewOfEmployee, viewOfEmployer;
    private TextView deleteTextView, editTextview;

    RegistrationViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details);
        viewModel = ((MyApplication) getApplication()).getRegistrationViewModel();
//        if (viewModel.getSelectedJob() != null) {
//          Log.e("selectedJob", String.valueOf(viewModel.getSelectedJob().getSalary()));
//        } else {
//            Log.e("selectedCompany", "is null");
//        }

        initViews();
        displayDetails();

        // Tìm và thiết lập Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        // Lấy thông tin loại người dùng từ Intent
        String userType = getIntent().getStringExtra("userType");
        if ("Employee".equals(userType)) {
            viewOfEmployee.setVisibility(View.VISIBLE); // Hiển thị cho Employee
            viewOfEmployer.setVisibility(View.GONE);
        } else {
            viewOfEmployee.setVisibility(View.GONE); // Ẩn cho Employer
            viewOfEmployer.setVisibility(View.VISIBLE);
        }



        // Kích hoạt nút quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    private void initViews() {
        dateTextview = findViewById(R.id.dateTextview);
        titleTextview = findViewById(R.id.titleTextview);
        companyTextview = findViewById(R.id.companyTextview);
        salaryTypeTextview = findViewById(R.id.salaryTypeTextview);
        salaryTextview = findViewById(R.id.salaryTextview);
        workDayTextview = findViewById(R.id.work_day);
        startTimeTextview = findViewById(R.id.start_time);
        endTimeTextview = findViewById(R.id.end_time);
        deadlineTextview = findViewById(R.id.deadlineTextview);
        recruitmentCountTextview = findViewById(R.id.recruitment_count);
        genderTextview = findViewById(R.id.genderTextview);
        salaryTypeTextview2 = findViewById(R.id.salaryTypeTextview2);
        salaryTextview2 = findViewById(R.id.salaryTextview2);
        workPeriodTextview = findViewById(R.id.work_period);
        workDaysTextview = findViewById(R.id.work_days2);
        startTime2Textview = findViewById(R.id.start_time2);
        endTime2Textview = findViewById(R.id.end_time2);
        detailsTextview = findViewById(R.id.detailsTextview);
        addressTextview = findViewById(R.id.addressTextview);
        companyTextview2 = findViewById(R.id.companyTextview2);
        contactTextview = findViewById(R.id.contactTextview);

        // Các View liên quan đến chức năng hiển thị/ẩn
        viewOfEmployee = findViewById(R.id.viewOfEmployee);
        viewOfEmployer = findViewById(R.id.viewOfEmployer);

        editTextview = findViewById(R.id.editButton);
        editTextview.setOnClickListener(v -> {
            Intent intent = new Intent(JobDetails.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
    private void displayDetails() {
//        if (viewModel.getSelectedCompany() != null){
//            Log.e("selectedCompany", viewModel.getSelectedCompany().getCompanyName());
//        }else{
//            Log.e("selectedCompany", "is null");
//        }
        if (viewModel.getSelectedJob() != null) {
            Job selectedJob = viewModel.getSelectedJob();
            dateTextview.setText(selectedJob.getPostDate());
            titleTextview.setText(selectedJob.getTitle());
            companyTextview.setText(viewModel.getSelectedCompany().getCompanyName());
            salaryTypeTextview.setText(selectedJob.getSalaryType());
            salaryTextview.setText(String.valueOf(selectedJob.getSalary()));
            workDayTextview.setText(selectedJob.getWorkDays());
            startTimeTextview.setText(selectedJob.getWorkHoursStart());
            endTimeTextview.setText(selectedJob.getWorkHoursEnd());
            deadlineTextview.setText(selectedJob.getRecruitmentEnd());
            recruitmentCountTextview.setText(String.valueOf(selectedJob.getRecruitmentCount()));
            genderTextview.setText(selectedJob.getRecruitmentGender());
            salaryTypeTextview2.setText(selectedJob.getSalaryType());
            salaryTextview2.setText(String.valueOf(selectedJob.getSalary()));
            workPeriodTextview.setText(selectedJob.getWorkPeriod());
            workDaysTextview.setText(selectedJob.getWorkDays());
            startTime2Textview.setText(selectedJob.getWorkHoursStart());
            endTime2Textview.setText(selectedJob.getWorkHoursEnd());
            detailsTextview.setText(selectedJob.getDetails());
            addressTextview.setText(selectedJob.getWorkLocation());
            companyTextview2.setText(viewModel.getSelectedCompany().getCompanyName());
            contactTextview.setText(viewModel.getSelectedCompany().getContact());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Xử lý khi người dùng nhấn nút quay lại
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}