package com.example.application22024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.adapter.ApplicantAdapter;
import com.example.application22024.employer.RegistrationActivity;
import com.example.application22024.model.Applicant;
import com.example.application22024.model.CompanyJobItem;
import com.example.application22024.model.Job;
import com.example.application22024.model.DataViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetails extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView dateTextview, titleTextview, companyTextview, salaryTypeTextview, salaryTextview, workDayTextview, startTimeTextview, endTimeTextview;
    private TextView recruitmentCountTextview, genderTextview, deadlineTextview, canNegotiableDayTextview, canNegotiableTimeTextview;
    private TextView salaryTypeTextview2, salaryTextview2, workPeriodTextview, workDaysTextview, startTime2Textview, endTime2Textview;
    private TextView detailsTextview, addressTextview, companyTextview2, contactTextview;
    private LinearLayout viewOfEmployee, viewOfEmployer, applyButton, bookmarkAndShare;
    private RelativeLayout numberOfApplicants;
    private TextView deleteTextView, editTextview, applyTextView, numberTextView;
    private ImageView applyImageView, bookmarkImageView, shareImageView;
    private APIService apiService;
    DataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details);
        viewModel = ((MyApplication) getApplication()).getDataViewModel();
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

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

            int saved = viewModel.getSelectedCompanyJobItem().getIs_saved();
            bookmarkImageView.setImageResource(saved == 1 ? R.drawable.ic_bookmark2 : R.drawable.ic_bookmark);
            bookmarkImageView.setOnClickListener(v -> {
                if (saved == 0) {
                    bookmarkImageView.setImageResource(R.drawable.ic_bookmark2);
                    updateBookmarkStatus();
                } else {
                    Toast.makeText(JobDetails.this, "Already saved", Toast.LENGTH_SHORT).show();
                }
            });

            applyButton.setOnClickListener(v -> {
                applyJob();
                applyImageView.setImageResource(R.drawable.ic_apply);
                applyTextView.setText("지원했다");
            });
        } else {
            viewOfEmployee.setVisibility(View.GONE); // Ẩn cho Employer
            viewOfEmployer.setVisibility(View.VISIBLE);
            bookmarkAndShare.setVisibility(View.GONE);
            if (viewModel.getSelectedJob().getNum_applicants() > 0) {
                numberOfApplicants.setVisibility(View.VISIBLE);
                numberOfApplicants.setOnClickListener(v -> {
                    int jobId = viewModel.getSelectedJob().getJobId();
                    fetchApplicantsAndShowDialog(jobId);
                });

            }


            editTextview.setOnClickListener(v -> {
                Intent intent = new Intent(JobDetails.this, RegistrationActivity.class);
                startActivity(intent);
            });

            deleteTextView.setOnClickListener(v -> {
                // Tạo AlertDialog để xác nhận việc xóa
                new AlertDialog.Builder(JobDetails.this)
                        .setMessage("Are you sure you want to delete?")  // Câu hỏi hiển thị trong hộp thoại
                        .setCancelable(false)  // Không thể hủy bỏ bằng cách nhấn ngoài hộp thoại
                        .setPositiveButton("Yes", (dialog, id) -> {
                            // Nếu người dùng nhấn "Có", thực hiện phương thức deleteRecrutment()
                            deleteRecrutment();
                        })
                        .setNegativeButton("No", (dialog, id) -> {
                            // Nếu người dùng nhấn "Không", chỉ cần đóng hộp thoại
                            dialog.dismiss();
                        })
                        .show();  // Hiển thị hộp thoại
            });
        }


        // Kích hoạt nút quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initViews() {
        bookmarkAndShare = findViewById(R.id.bookmarkAndShare);
        dateTextview = findViewById(R.id.dateTextview);
        titleTextview = findViewById(R.id.titleTextview);
        companyTextview = findViewById(R.id.companyTextview);
        salaryTypeTextview = findViewById(R.id.salaryTypeTextview);
        salaryTextview = findViewById(R.id.salaryTextview);
        workDayTextview = findViewById(R.id.work_day);
        canNegotiableDayTextview = findViewById(R.id.canNegotiableDay);
        startTimeTextview = findViewById(R.id.start_time);
        endTimeTextview = findViewById(R.id.end_time);
        canNegotiableTimeTextview = findViewById(R.id.canNegotiableTime);
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
        applyTextView = findViewById(R.id.applyTextView);
        applyImageView = findViewById(R.id.applyImageView);
        bookmarkImageView = findViewById(R.id.bookmarkImageView);
        numberOfApplicants = findViewById(R.id.Number_of_applicants);
//        shareImageView = findViewById(R.id.shareImageView);
        // Các View liên quan đến chức năng hiển thị/ẩn
        viewOfEmployee = findViewById(R.id.viewOfEmployee);
        applyButton = findViewById(R.id.applyButton);
        viewOfEmployer = findViewById(R.id.viewOfEmployer);
        deleteTextView = findViewById(R.id.deleteButton);
        editTextview = findViewById(R.id.editButton);
        numberTextView = findViewById(R.id.number);
    }

    private void applyJob() {
        int userId = SharedPrefManager.getInstance(JobDetails.this).getUserId();
        int jobId = viewModel.getSelectedCompanyJobItem().getJob_id();
        Log.e("userId", String.valueOf(userId));
        Log.e("jobId", String.valueOf(jobId));
        Call<Void> call = apiService.applyJob(userId, jobId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
//                    Log.e("Apply", "Response code: " + response.code());
                    Toast.makeText(JobDetails.this, "Already applied", Toast.LENGTH_SHORT).show();
                } else
                    Log.e("Apply", "Response code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Apply", "Network error: " + t.getMessage());
            }
        });
    }

    private void deleteRecrutment() {
        int job_id = viewModel.getSelectedJob().getJobId();
        Call<Void> call = apiService.deleteJobDetails(job_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Nếu xóa thành công, hiển thị thông báo thành công
                    Toast.makeText(JobDetails.this, "Successfully deleted!", Toast.LENGTH_SHORT).show();
                    viewModel.reset();
                    finish();
                } else {
                    // Nếu có lỗi trong quá trình xóa
                    Toast.makeText(JobDetails.this, "Failed to delete. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Nếu có lỗi mạng hoặc lỗi khi gọi API
                Toast.makeText(JobDetails.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayDetails() {
        if (viewModel.getSelectedJob() != null) {
            Job selectedJob = viewModel.getSelectedJob();
            dateTextview.setText(selectedJob.getPostDate());
            titleTextview.setText(selectedJob.getTitle());
            companyTextview.setText(viewModel.getSelectedCompany().getCompanyName());
            salaryTypeTextview.setText(selectedJob.getSalaryType());
            int number = selectedJob.getSalary();
            String formattedNumber = String.format("%,d", number) + " ₩";
            salaryTextview.setText(formattedNumber);
            workDayTextview.setText(selectedJob.getWorkDays());
            if (selectedJob.getCanNegotiableDays().equals("Yes")) {
                canNegotiableDayTextview.setVisibility(View.VISIBLE);
            } else {
                canNegotiableDayTextview.setVisibility(View.GONE);
            }
            startTimeTextview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursStart()));
            endTimeTextview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursEnd()));
            if (selectedJob.getCanNegotiableTime().equals("Yes")) {
                canNegotiableTimeTextview.setVisibility(View.VISIBLE);
            } else {
                canNegotiableTimeTextview.setVisibility(View.GONE);
            }
            deadlineTextview.setText(selectedJob.getRecruitmentEnd());
            recruitmentCountTextview.setText(String.valueOf(selectedJob.getRecruitmentCount()));
            genderTextview.setText(selectedJob.getRecruitmentGender());
            salaryTypeTextview2.setText(selectedJob.getSalaryType());
            salaryTextview2.setText(formattedNumber);
            workPeriodTextview.setText(selectedJob.getWorkPeriod());
            workDaysTextview.setText(selectedJob.getWorkDays());
            startTime2Textview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursStart()));
            endTime2Textview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursEnd()));
            detailsTextview.setText(selectedJob.getDetails());
            addressTextview.setText(viewModel.getSelectedCompany().getAddress());
            companyTextview2.setText(viewModel.getSelectedCompany().getCompanyName());
            contactTextview.setText(viewModel.getSelectedCompany().getContact());
            numberTextView.setText(String.valueOf(viewModel.getSelectedJob().getNum_applicants()));
        }
        if (viewModel.getSelectedCompanyJobItem() != null) {
            CompanyJobItem selectedJob = viewModel.getSelectedCompanyJobItem();
            dateTextview.setText(selectedJob.getPostDate());
            titleTextview.setText(selectedJob.getTitle());
            companyTextview.setText(selectedJob.getCompany_name());
            salaryTypeTextview.setText(selectedJob.getSalaryType());
            int number = selectedJob.getSalary();
            String formattedNumber = String.format("%,d", number) + " ₩";
            salaryTextview.setText(formattedNumber);
            workDayTextview.setText(selectedJob.getWorkDays());
//            Log.e("canNegotiableDays", selectedJob.getCanNegotiableDays());
            if (selectedJob.getCan_negotiable_days().equals("Yes")) {
                canNegotiableDayTextview.setVisibility(View.VISIBLE);
            } else {
                canNegotiableDayTextview.setVisibility(View.GONE);
            }
            startTimeTextview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursStart()));
            endTimeTextview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursEnd()));
            if (selectedJob.getCan_negotiable_time().equals("Yes")) {
                canNegotiableTimeTextview.setVisibility(View.VISIBLE);
            } else {
                canNegotiableTimeTextview.setVisibility(View.GONE);
            }
            deadlineTextview.setText(selectedJob.getRecruitmentEnd());
            recruitmentCountTextview.setText(String.valueOf(selectedJob.getRecruitmentCount()));
            genderTextview.setText(selectedJob.getRecruitmentGender());
            salaryTypeTextview2.setText(selectedJob.getSalaryType());
            salaryTextview2.setText(formattedNumber);
            workPeriodTextview.setText(selectedJob.getWorkPeriod());
            workDaysTextview.setText(selectedJob.getWorkDays());
            startTime2Textview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursStart()));
            endTime2Textview.setText(formatTimeToHoursAndMinutes(selectedJob.getWorkHoursEnd()));
            detailsTextview.setText(selectedJob.getDetails());
            addressTextview.setText(selectedJob.getAddress());
            companyTextview2.setText(selectedJob.getCompany_name());
            contactTextview.setText(selectedJob.getContact());
        }
    }

    private void fetchApplicantsAndShowDialog(int jobId) {
        // Gọi API để lấy danh sách ứng viên
        Call<List<Applicant>> call = apiService.getApplicants(jobId);

        call.enqueue(new Callback<List<Applicant>>() {
            @Override
            public void onResponse(Call<List<Applicant>> call, Response<List<Applicant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Applicant> applicants = response.body();

                    // Tạo RecyclerView trong Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(JobDetails.this);
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_applicant_list, null);

                    RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewApplicants);
                    recyclerView.setLayoutManager(new LinearLayoutManager(JobDetails.this));

                    // Tạo Adapter cho RecyclerView
                    ApplicantAdapter applicantAdapter = new ApplicantAdapter(JobDetails.this, applicants);
                    recyclerView.setAdapter(applicantAdapter);

                    // Thiết lập Dialog
                    builder.setTitle("지원 자")
                            .setView(dialogView)
                            .setPositiveButton("Close", (dialog, which) -> dialog.dismiss())
                            .create()
                            .show();
                } else {
                    Toast.makeText(JobDetails.this, "Không có ứng viên", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Applicant>> call, Throwable t) {
                Toast.makeText(JobDetails.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void updateBookmarkStatus() {
        int userId = SharedPrefManager.getInstance(JobDetails.this).getUserId();
        int jobId = viewModel.getSelectedCompanyJobItem().getJob_id();
        Call<Void> call = apiService.updateBookmarkStatus(userId, jobId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("Bookmark", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Bookmark", "Network error: " + t.getMessage());
            }
        });
    }

    private String formatTimeToHoursAndMinutes(String time) {
        try {
            // Định dạng chuỗi thời gian đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(time);

            // Định dạng lại chỉ hiển thị giờ và phút
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return time;  // Nếu có lỗi, trả về thời gian ban đầu
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.reset();
        super.onBackPressed();
    }
}