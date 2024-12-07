package com.example.application22024.employer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.application22024.APIService;
import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.SharedPrefManager;
import com.example.application22024.model.DataViewModel;

import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements Step4Fragment.OnPostButtonClickListener {
    private Toolbar toolbar;
    private DataViewModel viewModel;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Trong RegistrationActivity
        viewModel = ((MyApplication) getApplication()).getDataViewModel();
        if (viewModel.getSelectedCompany() == null) {
            Log.e("RegistrationActivity", "SelectedCompany is null");
        } else {
            Log.e("RegistrationActivity", viewModel.getSelectedCompany().toString());
        }
        // Thiết lập Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Ẩn toolbar mặc định
        //        toolbar.setVisibility(View.GONE);

        // Xử lý sự kiện quay lại từ toolbar
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Hiển thị fragment đầu tiên khi Activity được khởi tạo
        if (savedInstanceState == null) {
            showNextFragment(new Step1Fragment());
        }


    }

    // Phương thức để chuyển Fragment
    public void showNextFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onPostButtonClicked() {

        RequestBody employerId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(this).getUserId()));
        RequestBody companyId = RequestBody.create(MediaType.parse("text/plain"), (viewModel.getSelectedCompany() != null)
                ? String.valueOf(viewModel.getSelectedCompany().getCompanyId()) : "");
        RequestBody jobId = RequestBody.create(MediaType.parse("text/plain"), (viewModel.getSelectedJob() != null)
                ? String.valueOf(viewModel.getSelectedJob().getJobId()) : "");
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), viewModel.getRecruitmentTitle());
        RequestBody companyName = RequestBody.create(MediaType.parse("text/plain"), viewModel.getCompanyName());
        RequestBody contact = RequestBody.create(MediaType.parse("text/plain"), viewModel.getContact());
        RequestBody workField = RequestBody.create(MediaType.parse("text/plain"),
                (viewModel.getSelectedRecruitmentField() != null) ? viewModel.getSelectedRecruitmentField() : viewModel.getOtherRecruitmentField());
        RequestBody recruitmentGender = RequestBody.create(MediaType.parse("text/plain"), viewModel.getSelectedGender());
        RequestBody recruitmentCount = RequestBody.create(MediaType.parse("text/plain"), viewModel.getRecruitmentCount());
        RequestBody salaryType = RequestBody.create(MediaType.parse("text/plain"), viewModel.getSelectedSalaryType());
        RequestBody salary = RequestBody.create(MediaType.parse("text/plain"), viewModel.getSalary());
        RequestBody workHoursStart = RequestBody.create(MediaType.parse("text/plain"), viewModel.getStartTime());
        RequestBody workHoursEnd = RequestBody.create(MediaType.parse("text/plain"), viewModel.getEndTime());
        RequestBody canNegotiableTime = RequestBody.create(MediaType.parse("text/plain"), viewModel.isOption1Checked());
        RequestBody workType = RequestBody.create(MediaType.parse("text/plain"), viewModel.getWorkType());
        RequestBody workPeriod = RequestBody.create(MediaType.parse("text/plain"), viewModel.getWorkPeriod());
        RequestBody workDays = RequestBody.create(MediaType.parse("text/plain"), viewModel.getWorkDay());
        RequestBody canNegotiableDays = RequestBody.create(MediaType.parse("text/plain"), viewModel.isOption2Checked());
        RequestBody recruitmentEnd = RequestBody.create(MediaType.parse("text/plain"), viewModel.getRecruitmentEndTime());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), viewModel.getAddress()+" "+viewModel.getDetailAddress());
        RequestBody details = RequestBody.create(MediaType.parse("text/plain"), viewModel.getDescription());
        RequestBody representativeName = RequestBody.create(MediaType.parse("text/plain"), viewModel.getRepresentativeName());
        RequestBody registrationNumber = RequestBody.create(MediaType.parse("text/plain"), viewModel.getRegisterNumber());

        // Tạo MultipartBody.Part cho ảnh nếu có
        MultipartBody.Part imagePart = null;
        if (viewModel.getSelectedImageUri() != null) {
            try {
                // Chuyển đổi URI sang File
                InputStream inputStream = getContentResolver().openInputStream(viewModel.getSelectedImageUri());
                File tempFile = new File(getCacheDir(), "temp_image");
//                File tempFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "company_image.png");// lưu ảnh dạng png
                try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                // Tạo MultipartBody.Part từ File
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), tempFile);
                imagePart = MultipartBody.Part.createFormData("company_image", tempFile.getName(), requestBody);

            } catch (IOException e) {
                Log.e("RegistrationActivity", "Lỗi khi xử lý tệp ảnh: " + e.getMessage());
                Toast.makeText(this, "Không thể xử lý ảnh. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }
        }


        // Gọi API
        submitRegistration(employerId, companyId, jobId, title, companyName, contact, workField, recruitmentGender, recruitmentCount,
                salaryType, salary, workHoursStart, workHoursEnd, canNegotiableTime, workType, workPeriod, workDays,
                canNegotiableDays, recruitmentEnd, address, details, representativeName, registrationNumber, imagePart);
    }


    private void submitRegistration(
            RequestBody employerId, RequestBody companyId, RequestBody jobId, RequestBody title,
            RequestBody companyName, RequestBody contact, RequestBody workField, RequestBody recruitmentGender,
            RequestBody recruitmentCount, RequestBody salaryType, RequestBody salary, RequestBody workHoursStart,
            RequestBody workHoursEnd, RequestBody canNegotiableTime, RequestBody workType, RequestBody workPeriod,
            RequestBody workDays, RequestBody canNegotiableDays, RequestBody recruitmentEnd, RequestBody address,
            RequestBody details, RequestBody representativeName, RequestBody registrationNumber,
            MultipartBody.Part imagePart) {

        // Tạo đối tượng APIService
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

        // Gọi API
        Call<ResponseBody> call = apiService.submitRegistration(
                employerId, companyId, jobId, title, companyName, contact, workField, recruitmentGender, recruitmentCount,
                salaryType, salary, workHoursStart, workHoursEnd, canNegotiableTime, workType, workPeriod, workDays,
                canNegotiableDays, recruitmentEnd, address, details, representativeName, registrationNumber, imagePart
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    viewModel.reset();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegistrationActivity", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            // Nếu có fragment trong back stack, trở về fragment trước đó
            getSupportFragmentManager().popBackStack();
        } else {
            // Nếu không còn fragment nào, hiển thị hộp thoại xác nhận thoát
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to cancel the recruitment content?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        viewModel.reset();
                        // Khi nhấn OK, chuyển về RecruitmentManagementActivity
                        Intent intent = new Intent(RegistrationActivity.this, EmployerMain.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Kết thúc RegistrationActivity
                    })
                    .setNegativeButton("Cancel", null)
                    .setCancelable(true)
                    .show();
        }
    }


}
