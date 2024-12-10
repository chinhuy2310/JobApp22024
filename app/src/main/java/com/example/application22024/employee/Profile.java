package com.example.application22024.employee;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.APIService;
import com.example.application22024.MyApplication;
import com.example.application22024.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.example.application22024.RegionDataManager;
import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.SharedPrefManager;
import com.example.application22024.adapter.LeftAdapter;
import com.example.application22024.adapter.RightAdapter;
import com.example.application22024.model.Applicant;
import com.example.application22024.model.CircleTransform;
import com.example.application22024.model.DataViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    private ImageView imageView;
    private EditText editName, editbirthday, editIntroduce, editExperience, editPhoneNumber, editLocation, editPeriod, editWorkType, salary;
    private Calendar calendar;
    private TextView educationStatus, levelOfEducation, startTime, endTime, salaryType, male, female;
    Button saveButton;
    private boolean isEdited = false;
    private int selectedGenderPosition = -1; // Vị trí ô giới tính được chọn
    private int initialGenderPosition = -1; // Lưu trạng thái giới tính ban đầu
    private DataViewModel viewModel;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri; // Để lưu URI của ảnh đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);  // Gọi layout cho Activity
        viewModel = ((MyApplication) getApplication()).getDataViewModel();

        String userType = getIntent().getStringExtra("userType");

        // Cấu hình Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Gán Toolbar làm ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Hiển thị nút back
        // initViews
        initViews();

        editPeriod.setFocusable(false);
        editLocation.setFocusable(false);
        editWorkType.setFocusable(false);

        setupTimePicker();

        int employeeId = SharedPrefManager.getInstance(this).getUserId();
        fetchProfile(employeeId);

        viewModel.getSelectedApplicant().observe(this, applicant -> {
            if (applicant != null) {
                // Cập nhật UI sau khi dữ liệu được tải
                updateUI(applicant);
            } else {
                Log.e("Profile", "Selected applicant is null");
            }
        });

        // Initial values for textviews
        levelOfEducation.setText("학교");
        educationStatus.setText("상태");
        salaryType.setText("시급");

        setUpClicklistener();

        // Khởi tạo sự kiện cho RadioButt
        addTextWatcher(editName);
        addTextWatcher(editbirthday);
        addTextWatcher(editIntroduce);
        addTextWatcher(editExperience);
        addTextWatcher(editPhoneNumber);
        addTextWatcher(editLocation);
        addTextWatcher(editPeriod);
        addTextWatcher(editWorkType);
        addTextWatcher(salary);

        setupGenderClick(R.id.male, 0);
        setupGenderClick(R.id.female, 1);
        initialGenderPosition = selectedGenderPosition; // Ghi lại trạng thái ban đầu

        setupDatePicker();

        if ("Employer".equals(userType)) {
            saveButton.setVisibility(View.GONE);
            setViewOnlyMode(); // Tắt các chức năng chỉnh sửa nếu chế độ chỉ xem
        }

    }

    private void initViews() {
        imageView = findViewById(R.id.image_view);
        editName = findViewById(R.id.editName);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        editbirthday = findViewById(R.id.editbirthday);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        educationStatus = findViewById(R.id.educationStatus);
        levelOfEducation = findViewById(R.id.levelOfEducation);
        editExperience = findViewById(R.id.editexperience);
        editIntroduce = findViewById(R.id.editIntroduce);
        editLocation = findViewById(R.id.editLocation);
        editPeriod = findViewById(R.id.editPeriod);
        editWorkType = findViewById(R.id.editWorkType);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        salaryType = findViewById(R.id.salaryType);
        salary = findViewById(R.id.salary);
        saveButton = findViewById(R.id.saveButton);
    }

    private void setUpClicklistener() {
        imageView.setOnClickListener(v -> openImageChooser());
        editLocation.setOnClickListener(v -> showCustomDialog());
        saveButton.setOnClickListener(v -> saveChanges());
        educationStatus.setOnClickListener(v ->
                showBottomSheetDialog(this, new String[]{"재학", "졸업", "휴학", "중퇴", "수료"},
                        educationStatus.getText().toString(), educationStatus, () -> isEdited = true));

        levelOfEducation.setOnClickListener(v ->
                showBottomSheetDialog(this, new String[]{"중학교", "고등학교", "대학(2~3년제)", "대학(4년제)", "대학원"},
                        levelOfEducation.getText().toString(), levelOfEducation, () -> isEdited = true));
        salaryType.setOnClickListener(v ->
                showBottomSheetDialog(this, new String[]{"시급", "월급", "일당", "연봉", "주급", "계약금", "커미션"},
                        salaryType.getText().toString(), salaryType, () -> isEdited = true));

        editPeriod.setOnClickListener(v ->
                showBottomSheetDialog(this, new String[]{"하루(1일)", "1주일이하", "1주일~1개월", "1개월~3개월", "3개월~6개월", "6개월~1년", "1년이상"},
                        editPeriod.getText().toString(), editPeriod, () -> isEdited = true));
        editWorkType.setOnClickListener(v ->
                showBottomSheetDialog(this, new String[]{"알바", "정규직", "계약직", "인턴", "주말알바", "기타"},
                        editWorkType.getText().toString(), editWorkType, () -> isEdited = true));
    }

    private void updateGender() {
        Applicant applicant = viewModel.getSelectedApplicant().getValue();
        if (applicant != null) {
            String selectedGender = applicant.getGender();
            if ("남자".equals(selectedGender)) {
                if ("Employer".equals(getIntent().getStringExtra("userType"))) {
                    findViewById(R.id.female).setVisibility(View.GONE);
                } else {
                    selectedGenderPosition = 0;
                    male.setBackgroundColor(Color.BLUE);
                    male.setTextColor(Color.WHITE);
                }
            } else if ("여자".equals(selectedGender)) {
                if ("Employer".equals(getIntent().getStringExtra("userType"))) {
                    findViewById(R.id.male).setVisibility(View.GONE);
                } else {
                    selectedGenderPosition = 1;
                    female.setBackgroundColor(Color.BLUE);
                    female.setTextColor(Color.WHITE);
                }
            }
        } else {
            Log.e("Profile", "Applicant is null");
        }
    }

    public void setupTimePicker() {
        startTime.setFocusable(false);
        endTime.setFocusable(false);

        // Set time pickers for start and end time
        startTime.setOnClickListener(v -> showCustomTimePickerDialog(true)); // Start time
        endTime.setOnClickListener(v -> showCustomTimePickerDialog(false)); // End time
    }

    private void showCustomTimePickerDialog(final boolean isStartTime) {
        // Inflate the custom time picker layout
        View timePickerView = LayoutInflater.from(this).inflate(R.layout.time_picker_dialog, null);
        NumberPicker hourPicker = timePickerView.findViewById(R.id.hourPicker);
        NumberPicker minutePicker = timePickerView.findViewById(R.id.minutePicker);
        Button confirmButton = timePickerView.findViewById(R.id.confirmButton);

        // Cấu hình NumberPickers
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        // Định dạng để hiển thị số với 2 chữ số (00, 01, 02, ...)
        hourPicker.setFormatter(value -> String.format("%02d", value));
        minutePicker.setFormatter(value -> String.format("%02d", value));

        hourPicker.setValue(0);
        minutePicker.setValue(0);

        // Set up the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(timePickerView);
        builder.setCancelable(false); // Không cho phép đóng bằng cách bấm ngoài Dialog

        AlertDialog dialog = builder.create();
        dialog.show();

        // Xử lý sự kiện cho nút "OK"
        confirmButton.setOnClickListener(v -> {
            int selectedHour = hourPicker.getValue();
            int selectedMinute = minutePicker.getValue();

            String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);

            // Chỉ cập nhật TextView tương ứng
            if (isStartTime) {
                startTime.setText(formattedTime);
            } else {
                endTime.setText(formattedTime);
            }

            // Đóng Dialog sau khi chọn giờ
            dialog.dismiss();
        });
    }

    private void setViewOnlyMode() {
        setTouchListenerForViews(imageView, editName, editbirthday, editIntroduce, editExperience, editPhoneNumber, editLocation, editPeriod, editWorkType, salary, findViewById(R.id.male),
                findViewById(R.id.female), educationStatus, levelOfEducation, startTime, endTime, salaryType);
    }

    private void fetchProfile(int employeeId) {
        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<Applicant> call = apiService.getProfile(employeeId);

        call.enqueue(new Callback<Applicant>() {
            @Override
            public void onResponse(Call<Applicant> call, Response<Applicant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    viewModel.setSelectedApplicant(response.body());
                } else {
                    Log.e("API", "Không tìm thấy thông tin hồ sơ.");
                }
            }

            @Override
            public void onFailure(Call<Applicant> call, Throwable t) {
                Log.e("API", "Lỗi khi gọi API: " + t.getMessage());
            }
        });
    }

    private void updateUI(Applicant applicant) {
        String baseUrl = "http://192.168.0.3:3000";
        String fullImageUrl = baseUrl + applicant.getAvatar_url();
        if (!applicant.getAvatar_url().isEmpty()) {
            Picasso.get().load(fullImageUrl)
                    .transform(new CircleTransform())
                    .into(imageView);
        } else {
            // Load a default image if the avatar URL is empty
            Picasso.get().load(R.drawable.user_icon2).into(imageView);
        }

        editName.setText(applicant.getFull_name());
        editbirthday.setText(applicant.getDate_of_birth());
        editPhoneNumber.setText(applicant.getPhone_number());
        educationStatus.setText(applicant.getEducation_status());
        levelOfEducation.setText(applicant.getEducation_level());
        editExperience.setText(applicant.getExperience());
        editIntroduce.setText(applicant.getIntroduction());
        editLocation.setText(applicant.getPreferred_work_location());
        editPeriod.setText(applicant.getPreferred_work_duration());
        editWorkType.setText(applicant.getWork_type());
        String expectedWorkTime = applicant.getWork_time();
        startTime.setText(formatTimeToHoursAndMinutes(expectedWorkTime.split("-")[0]));
        endTime.setText(formatTimeToHoursAndMinutes(expectedWorkTime.split("-")[1]));
        salaryType.setText(applicant.getSalary_type());
        salary.setText(String.format("%,d", applicant.getExpected_salary()) + " ₩");
        updateGender();
    }


    public void showBottomSheetDialog(Context context, String[] items, String previousValue, TextView targetView, Runnable onValueChanged) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ListView listView = bottomSheetView.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String newValue = items[position];
            if (!newValue.equals(previousValue)) {
                targetView.setText(newValue);
                onValueChanged.run(); // Gọi hành động khi thay đổi
            }
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }


    public void setupDatePicker() {

        calendar = Calendar.getInstance();
        // Vô hiệu hóa chế độ nhập liệu cho EditText
        editbirthday.setFocusable(false);
        // Xử lý sự kiện khi nhấn vào EditText
        editbirthday.setOnClickListener(v -> showDatePickerAlertDialog());
    }

    private void showDatePickerAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.date_picker_alert_dialog, null);
        builder.setView(dialogView);

        NumberPicker yearPicker = dialogView.findViewById(R.id.yearPicker);
        NumberPicker monthPicker = dialogView.findViewById(R.id.monthPicker);
        NumberPicker dayPicker = dialogView.findViewById(R.id.dayPicker);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(currentYear);
        yearPicker.setValue(currentYear);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateDayPickerMax(dayPicker, yearPicker.getValue(), monthPicker.getValue()));
        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateDayPickerMax(dayPicker, yearPicker.getValue(), monthPicker.getValue()));

        AlertDialog alertDialog = builder.create();

        dialogView.findViewById(R.id.confirmButton).setOnClickListener(v -> {
            int selectedYear = yearPicker.getValue();
            int selectedMonth = monthPicker.getValue();
            int selectedDay = dayPicker.getValue();

            String selectedDate = String.format(Locale.getDefault(), "%04d.%02d.%02d", selectedYear, selectedMonth, selectedDay);
            editbirthday.setText(selectedDate);

            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    private void updateDayPickerMax(NumberPicker dayPicker, int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayPicker.setMaxValue(maxDay);
    }

    private void addTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                isEdited = true; // Đánh dấu rằng có thay đổi khi EditText nào đó thay đổi
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần xử lý
            }
        });
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void saveChanges() {
        isEdited = false;

        String fullName = editName.getText().toString();
        String gender = selectedGenderPosition == 0 ? "남자" : selectedGenderPosition == 1 ? "여자" : "";
        String birthday = editbirthday.getText().toString();
        String phoneNumber = editPhoneNumber.getText().toString();
        String educationStatusText = educationStatus.getText().toString();
        String educationLevel = levelOfEducation.getText().toString();
        String experience = editExperience.getText().toString();
        String introduction = editIntroduce.getText().toString();
        String preferredLocation = editLocation.getText().toString();
        String workDuration = editPeriod.getText().toString();
        String workType = editWorkType.getText().toString();
        String startTimeText = startTime.getText().toString();
        String endTimeText = endTime.getText().toString();
        String salaryText = salary.getText().toString();
        String salaryTypeText = salaryType.getText().toString();

        //        // Tạo đối tượng Applicant mới
//        Applicant applicant = new Applicant();
//        applicant.setFull_name(fullName);
//        applicant.setDate_of_birth(birthday);
//        applicant.setPhone_number(phoneNumber);
//        applicant.setEducation_status(educationStatusText);
//        applicant.setEducation_level(educationLevel);
//        applicant.setExperience(experience);
//        applicant.setIntroduction(introduction);
//        applicant.setPreferred_work_location(preferredLocation);
//        applicant.setPreferred_work_duration(workDuration);
//        applicant.setWork_type(workType);
//        applicant.setWork_time(startTimeText + "-" + endTimeText); // Cần ghép thời gian làm việc
//        applicant.setSalary_type(salaryTypeText);
//        applicant.setExpected_salary(Integer.parseInt(salaryText.replace(" ₩", "").replace(",", ""))); // Chuyển đổi lương
//
//        // Cập nhật vào ViewModel hoặc cơ sở dữ liệu
//        viewModel.setSelectedApplicant(applicant);  // Nếu bạn dùng ViewModel

        // Đọc ảnh từ bộ nhớ
        // Tạo MultipartBody.Part cho ảnh nếu có
        MultipartBody.Part imagePart = null;
        if (selectedImageUri != null) {
            try {
                // Chuyển đổi URI sang File
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
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
                imagePart = MultipartBody.Part.createFormData("avatar", tempFile.getName(), requestBody);

            } catch (IOException e) {
                Log.e("RegistrationActivity", "Lỗi khi xử lý tệp ảnh: " + e.getMessage());
                Toast.makeText(this, "Không thể xử lý ảnh. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }
        }

        // Chuyển các dữ liệu thành RequestBody
        RequestBody employeeId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(this).getUserId()));
        RequestBody fullNameBody = RequestBody.create(MediaType.parse("text/plain"), fullName);
        RequestBody genderBody = RequestBody.create(MediaType.parse("text/plain"), gender);
        RequestBody dateOfBirthBody = RequestBody.create(MediaType.parse("text/plain"), birthday);
        RequestBody phoneNumberBody = RequestBody.create(MediaType.parse("text/plain"), phoneNumber);
        RequestBody educationStatusBody = RequestBody.create(MediaType.parse("text/plain"), educationStatusText);
        RequestBody educationLevelBody = RequestBody.create(MediaType.parse("text/plain"), educationLevel);
        RequestBody experienceBody = RequestBody.create(MediaType.parse("text/plain"), experience);
        RequestBody introductionBody = RequestBody.create(MediaType.parse("text/plain"), introduction);
        RequestBody preferredLocationBody = RequestBody.create(MediaType.parse("text/plain"), preferredLocation);
        RequestBody preferredDurationBody = RequestBody.create(MediaType.parse("text/plain"), workDuration);
        RequestBody workTypeBody = RequestBody.create(MediaType.parse("text/plain"), workType);
        RequestBody workTimeBody = RequestBody.create(MediaType.parse("text/plain"), startTimeText + "-" + endTimeText);
        RequestBody salaryTypeBody = RequestBody.create(MediaType.parse("text/plain"), salaryTypeText);
        RequestBody expectedSalaryBody = RequestBody.create(MediaType.parse("text/plain"), salaryText.replace(" ₩", "").replace(".", ""));

        // Gửi dữ liệu qua Retrofit
        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<Void> call = apiService.saveApplicant(employeeId, fullNameBody, genderBody, dateOfBirthBody, phoneNumberBody,
                educationStatusBody, educationLevelBody, experienceBody, introductionBody, preferredLocationBody,
                preferredDurationBody, workTypeBody, workTimeBody, salaryTypeBody, expectedSalaryBody, imagePart);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Applicant saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to save applicant.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Mở trình chọn ảnh
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Xử lý kết quả khi người dùng chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            isEdited = true;
            imageView.setImageURI(selectedImageUri);  // Hiển thị ảnh đã chọn lên ImageView
        }
    }

    private void setupGenderClick(int itemId, int position) {
        TextView cell = findViewById(itemId);
        cell.setOnClickListener(v -> {
            if (selectedGenderPosition != -1) {
                resetGenderColor(selectedGenderPosition);
            }
            selectedGenderPosition = position;
            if (selectedGenderPosition != initialGenderPosition) {
                isEdited = true;
            }
            cell.setBackgroundColor(Color.BLUE);
            cell.setTextColor(Color.WHITE);
        });
    }

    private void resetGenderColor(int position) {
        int itemId;
        switch (position) {
            case 0:
                itemId = R.id.male;
                break;
            case 1:
                itemId = R.id.female;
                break;
            default:
                return;
        }
        TextView cell = findViewById(itemId);
        cell.setBackgroundResource(R.drawable.border_square);
        cell.setTextColor(Color.BLACK);
    }

    @Override
    public void onBackPressed() {
        if (isEdited && !"Employer".equals(getIntent().getStringExtra("userType"))) {
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to save the changes?")
                    .setPositiveButton("Save", (dialog, id) -> saveChanges())
                    .setNegativeButton("Discard", (dialog, id) -> super.onBackPressed())
                    .setNeutralButton("Cancel", (dialog, id) -> dialog.dismiss())
                    .show();
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // Kiểm tra thay đổi và xử lý lưu khi quay lại
                if (isEdited && !"Employer".equals(getIntent().getStringExtra("userType"))) {
                    new AlertDialog.Builder(this)
                            .setMessage("Do you want to save the changes?")
                            .setCancelable(false)
                            .setPositiveButton("Save", (dialog, id) -> {
                                // Lưu thay đổi và quay lại
                                saveChanges();
                                super.onBackPressed();
                            })
                            .setNegativeButton("Don't Save", (dialog, id) -> {
                                // Không lưu thay đổi và quay lại
                                super.onBackPressed();
                            })
                            .setNeutralButton("Cancel", (dialog, id) -> {
                                // Hủy hành động quay lại
                                dialog.dismiss();
                            })
                            .show();
                } else {
                    super.onBackPressed();  // Nếu không có thay đổi, cho phép thoát
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_location_layout);
        dialog.setCanceledOnTouchOutside(false);
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

        leftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            editLocation.setText(selectedProvince + " " + selectedArea);
            // Đóng dialog
            dialog.dismiss();
        });
        dialog.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            dialog.dismiss();
        });
        // Hiển thị dialog
        dialog.show();
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

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    // Hàm giúp vô hiệu hóa sự kiện chạm cho các View con
    private void setTouchListenerForViews(View... views) {
        for (View view : views) {
            view.setOnTouchListener((v, event) -> true); // Ngăn chặn mọi thao tác chạm trên các View này
        }
    }
}