package com.example.application22024.employee;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.application22024.R;

import java.util.Calendar;
import java.util.Locale;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Profile extends AppCompatActivity {
    private EditText editTextDate, editbirthday, editText, editText1, editText2, editText3;
    private Calendar calendar;
    private TextView educationStatus, levelOfEducation;
    private boolean isEdited = false;
    private int selectedGenderPosition = -1; // Vị trí ô giới tính được chọn
    private int initialGenderPosition = -1; // Lưu trạng thái giới tính ban đầu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);  // Gọi layout cho Activity

        String userType = getIntent().getStringExtra("userType");
        // Cấu hình Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Gán Toolbar làm ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Hiển thị nút back

        setupDatePicker();
        educationStatus = findViewById(R.id.educationStatus);
        levelOfEducation = findViewById(R.id.levelOfEducation);

        // Initial values for textviews
        educationStatus.setText("Option 2");
        levelOfEducation.setText("Option 1");

        String previousEducationStatus = educationStatus.getText().toString();
        String previousLevelOfEducation = levelOfEducation.getText().toString();

        educationStatus.setOnClickListener(v -> showBottomSheetDialog1(previousEducationStatus));
        levelOfEducation.setOnClickListener(v -> showBottomSheetDialog2(previousLevelOfEducation));

        editbirthday = findViewById(R.id.editbirthday);
        addTextWatcher(editbirthday);

        setupGenderClick(R.id.male, 0);
        setupGenderClick(R.id.female, 1);
        initialGenderPosition = selectedGenderPosition; // Ghi lại trạng thái ban đầu

        if (userType.equals("Employer")) {
            setViewOnlyMode(); // Tắt các chức năng chỉnh sửa nếu chế độ chỉ xem
        }
    }
    private void setViewOnlyMode() {
        // Vô hiệu hóa tất cả các EditText để không thể chỉnh sửa
        editTextDate.setFocusable(false);
        editbirthday.setFocusable(false);

        // Vô hiệu hóa các TextView nếu cần
        educationStatus.setClickable(false);
        levelOfEducation.setClickable(false);

        // Vô hiệu hóa các sự kiện của các button, TextView hoặc các item khác
        findViewById(R.id.male).setClickable(false);
        findViewById(R.id.female).setClickable(false);
    }

    private void showBottomSheetDialog1(String previousValue) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ListView listView = bottomSheetView.findViewById(R.id.listView);
        String[] items = {"재학", "졸업", " ....."}; // Các tùy chọn
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String newValue = items[position];
            educationStatus.setText(newValue);
            if (!newValue.equals(previousValue)) {
                isEdited = true; // Đánh dấu là đã thay đổi
            }
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void showBottomSheetDialog2(String previousValue) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ListView listView = bottomSheetView.findViewById(R.id.listView);
        String[] items = {"고등", "대학", "1학년", " ....."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String newValue = items[position];
            levelOfEducation.setText(newValue);
            if (!newValue.equals(previousValue)) {
                isEdited = true; // Đánh dấu là đã thay đổi
            }
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    public void setupDatePicker() {
        editTextDate = findViewById(R.id.editbirthday);
        calendar = Calendar.getInstance();
        // Vô hiệu hóa chế độ nhập liệu cho EditText
        editTextDate.setFocusable(false);
        // Xử lý sự kiện khi nhấn vào EditText
        editTextDate.setOnClickListener(v -> showDatePickerAlertDialog());
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
            editTextDate.setText(selectedDate);

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
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Kiểm tra thay đổi và xử lý lưu khi quay lại
                if (isEdited) {
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

}
