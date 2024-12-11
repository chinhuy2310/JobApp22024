package com.example.application22024.employer;

import android.app.DatePickerDialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.DataViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Step2Fragment extends Fragment {

    private TextView arrow, arrow1, arrow2;
    private Button nextButton;
    private EditText recruitmentEndTime, startTime, endTime, workType, salary, recruitmentCount, workPeriod, workDay;
    private Spinner partsOfDay1, partsOfDay2, salaryType;
    private Calendar calendar;
    private DataViewModel viewModel;
    private CheckBox checkBoxOption1, checkBoxOption2, checkBoxOption3;
    private List<TextView> selectedTextViews = new ArrayList<>();
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;

    // Inflate layout for fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step2, container, false);
    }

    // Initialize views and set up event listeners
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ((MyApplication) getActivity().getApplication()).getDataViewModel();
        if (getActivity() != null) {
            // Lấy ActionBar từ Activity
            androidx.appcompat.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false); // Ẩn tiêu đề
            }
        }

        // Initialize views
        initializeViews(view);

        // Set up event listeners
        setEventListeners();

        // Set up Spinners
        setUpSpinners();

        // Setup time pickers for start and end time
        setupTimePicker();

        if (viewModel.getSelectedJob() != null) {
            recruitmentCount.setText(String.valueOf(viewModel.getSelectedJob().getRecruitmentCount()));
            salary.setText(String.valueOf(viewModel.getSelectedJob().getSalary()));
            startTime.setText(formatTimeToHoursAndMinutes(viewModel.getSelectedJob().getWorkHoursStart()));
            endTime.setText(formatTimeToHoursAndMinutes(viewModel.getSelectedJob().getWorkHoursEnd()));
            workType.setText(viewModel.getSelectedJob().getWorkType());
            workPeriod.setText(viewModel.getSelectedJob().getWorkPeriod());
            workDay.setText(viewModel.getSelectedJob().getWorkDays());
            String returnedData = viewModel.getSelectedJob().getWorkDays();
            selectPredefinedOptions(returnedData);
            recruitmentEndTime.setText(viewModel.getSelectedJob().getRecruitmentEnd());

            viewModel.setRecruitmentCount(recruitmentCount.getText().toString());
            viewModel.setSalary(salary.getText().toString());
            viewModel.setStartTime(startTime.getText().toString());
            viewModel.setEndTime(endTime.getText().toString());
            viewModel.setWorkType(workType.getText().toString());
            viewModel.setWorkPeriod(workPeriod.getText().toString());
            viewModel.setWorkDay(workDay.getText().toString());
            viewModel.setRecruitmentEndTime(recruitmentEndTime.getText().toString());

//            Log.e("", viewModel.getSelectedJob().getCanNegotiableTime());
            if ("Yes".equals(viewModel.getSelectedJob().getCanNegotiableTime())) {
                checkBoxOption1.setChecked(true);
                viewModel.setOption1Checked(true);
            } else {
                checkBoxOption1.setChecked(false);
            }
            if ("Yes".equals(viewModel.getSelectedJob().getCanNegotiableDays())) {
                checkBoxOption2.setChecked(true);
                viewModel.setOption2Checked(true);
            } else {
                checkBoxOption2.setChecked(false);
            }

        }

        // Handle Work Arrangement
        workType.setFocusable(false);
        workType.setOnClickListener(v -> showBottomSheetDialog("workType"));

        workPeriod.setFocusable(false);
        workPeriod.setOnClickListener(v -> showBottomSheetDialog("workPeriod"));

        recruitmentEndTime.setFocusable(false);

        // Lắng nghe sự thay đổi trạng thái checkbox và cập nhật vào ViewModel
        checkBoxOption1.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setOption1Checked(isChecked));
        checkBoxOption2.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setOption2Checked(isChecked));
        checkBoxOption3.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setOption3Checked(isChecked));

        // Cập nhật giá trị từ các trường vào ViewModel
        setTextChangedListener(recruitmentCount, viewModel::setRecruitmentCount);
        setTextChangedListener(salary, viewModel::setSalary);
        setTextChangedListener(startTime, viewModel::setStartTime);
        setTextChangedListener(endTime, viewModel::setEndTime);
        setTextChangedListener(workType, viewModel::setWorkType);
        setTextChangedListener(workPeriod, viewModel::setWorkPeriod);
        setTextChangedListener(workDay, viewModel::setWorkDay);
        setTextChangedListener(recruitmentEndTime, viewModel::setRecruitmentEndTime);
    }

    // Hàm toggle để chọn hoặc bỏ chọn một ô
    private void toggleOption(TextView textView) {
        if (selectedTextViews.contains(textView)) {
            // Nếu đã chọn, bỏ chọn và đổi lại màu nền
            selectedTextViews.remove(textView);
            textView.setBackgroundResource(R.drawable.border_rounded_small);
            textView.setTextColor(getResources().getColor(android.R.color.black));

        } else {
            // Nếu chưa chọn, chọn và đổi màu nền
            selectedTextViews.add(textView);
            textView.setBackgroundResource(R.drawable.border_rounded_small3);
            textView.setTextColor(getResources().getColor(android.R.color.white));
        }

        // Hiển thị kết quả của tất cả các ô đã chọn vào một TextView khác (nếu cần)
        displaySelectedOptions();
    }

    // Hàm hiển thị các ô được chọn
    private void displaySelectedOptions() {
        StringBuilder selectedText = new StringBuilder();

        // Lặp qua danh sách các TextView đã chọn và lấy text
        for (TextView textView : selectedTextViews) {
            selectedText.append(textView.getText().toString()).append(", ");
        }

        // Hiển thị text vào một TextView khác (nếu cần)

        if (selectedText.length() > 0) {
            // Loại bỏ dấu ", " thừa ở cuối
            selectedText.delete(selectedText.length() - 2, selectedText.length());
        }
        workDay.setText(selectedText.toString());
    }

    // Hàm để chọn sẵn các ô dựa trên dữ liệu trả về
    private void selectPredefinedOptions(String returnedData) {
        // Chia chuỗi dữ liệu trả về thành các phần tử
        String[] options = returnedData.split(", ");

        // Kiểm tra và chọn các ô tương ứng với dữ liệu trả về
        if (Arrays.asList(options).contains(textView1.getText().toString())) {
            toggleOption(textView1);  // Chọn ô 1
        }
        if (Arrays.asList(options).contains(textView2.getText().toString())) {
            toggleOption(textView2);  // Chọn ô 2
        }
        if (Arrays.asList(options).contains(textView3.getText().toString())) {
            toggleOption(textView3);  // Chọn ô 3
        }
        if (Arrays.asList(options).contains(textView4.getText().toString())) {
            toggleOption(textView4);  // Chọn ô 4
        }
        if (Arrays.asList(options).contains(textView5.getText().toString())) {
            toggleOption(textView5);  // Chọn ô 5
        }
        if (Arrays.asList(options).contains(textView6.getText().toString())) {
            toggleOption(textView6);  // Chọn ô 6
        }
        if (Arrays.asList(options).contains(textView7.getText().toString())) {
            toggleOption(textView7);  // Chọn ô 7
        }
    }

    private void initializeViews(View view) {
        recruitmentEndTime = view.findViewById(R.id.RecruitmentEndTime);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        nextButton = view.findViewById(R.id.button_next);
        arrow = view.findViewById(R.id.arrow);
        arrow1 = view.findViewById(R.id.arrow1);
        arrow2 = view.findViewById(R.id.arrow2);
        partsOfDay1 = view.findViewById(R.id.time1);
        partsOfDay2 = view.findViewById(R.id.time2);
        salaryType = view.findViewById(R.id.salaryType);
        workType = view.findViewById(R.id.work_type);
        salary = view.findViewById(R.id.salary);
        recruitmentCount = view.findViewById(R.id.RecruitmentCount);
        workPeriod = view.findViewById(R.id.work_period);
        workDay = view.findViewById(R.id.work_day);
        checkBoxOption1 = view.findViewById(R.id.checkBoxOption1);
        checkBoxOption2 = view.findViewById(R.id.checkBoxOption2);
        checkBoxOption3 = view.findViewById(R.id.checkBoxOption3);

        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        textView5 = view.findViewById(R.id.textView5);
        textView6 = view.findViewById(R.id.textView6);
        textView7 = view.findViewById(R.id.textView7);

    }

    private void setTextChangedListener(EditText editText, Consumer<String> setValueMethod) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                setValueMethod.accept(s.toString()); // Gọi phương thức setValue từ ViewModel
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void setEventListeners() {
        // Set next button listener
        nextButton.setOnClickListener(v -> navigateToNextFragment());
        //
        recruitmentEndTime.setOnClickListener(v -> showDatePickerDialog());
        // Set arrow click listeners to open Spinners
        arrow.setOnClickListener(v -> salaryType.performClick());
        arrow1.setOnClickListener(v -> partsOfDay1.performClick());
        arrow2.setOnClickListener(v -> partsOfDay2.performClick());
        // Lắng nghe sự kiện click của các ô
        textView1.setOnClickListener(v -> toggleOption(textView1));
        textView2.setOnClickListener(v -> toggleOption(textView2));
        textView3.setOnClickListener(v -> toggleOption(textView3));
        textView4.setOnClickListener(v -> toggleOption(textView4));
        textView5.setOnClickListener(v -> toggleOption(textView5));
        textView6.setOnClickListener(v -> toggleOption(textView6));
        textView7.setOnClickListener(v -> toggleOption(textView7));
    }

    private void setUpSpinners() {
        String[] options1 = {"시급", "월급", "일당", "연봉", "주급", "계약금", "커미션"};
        String[] options2 = {"오전", "오후"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, options1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, options2);


        salaryType.setAdapter(adapter1);
        partsOfDay1.setAdapter(adapter2);
        partsOfDay2.setAdapter(adapter2);

        // Set default selection for Spinners
        if (viewModel.getSelectedJob() != null) {
            // Lấy workField từ ViewModel
            String selectedSalaryType = viewModel.getSelectedJob().getSalaryType();
//            Log.e("Step2Fragment", "Selected Salary Type: " + selectedSalaryType);
            // Tìm chỉ số của workField trong mảng options1
            for (int i = 0; i < options1.length; i++) {
                if (options1[i].equals(selectedSalaryType)) {
                    salaryType.setSelection(i);
                    break;
                }
            }
        } else {
            salaryType.setSelection(0);
        }

        partsOfDay1.setSelection(0);
        partsOfDay2.setSelection(1);
    }

    private void navigateToNextFragment() {
        String selectedSalaryType = salaryType.getSelectedItem().toString();
        if (viewModel.getRecruitmentCount() == null ||
                viewModel.getSalary() == null || viewModel.getStartTime() == null || viewModel.getEndTime() == null ||
                viewModel.getWorkType() == null || viewModel.getWorkPeriod() == null ||
                viewModel.getWorkDay() == null || viewModel.getRecruitmentCount() == null) {
            Toast.makeText(getContext(), "Please fill in all the required information.", Toast.LENGTH_SHORT).show();
        }else{

            viewModel.setSelectedSalaryType(selectedSalaryType);
            hideKeyboard();
            // Navigate to the next fragment
            ((RegistrationActivity) getActivity()).showNextFragment(new Step3Fragment());
        }
    }


    // Setup time picker dialogs for start and end time
    public void setupTimePicker() {
        startTime.setFocusable(false);
        endTime.setFocusable(false);

        // Set time pickers for start and end time
        startTime.setOnClickListener(v -> showCustomTimePickerDialog(true)); // Start time
        endTime.setOnClickListener(v -> showCustomTimePickerDialog(false)); // End time
    }

    private void showCustomTimePickerDialog(final boolean isStartTime) {
        // Inflate the custom time picker layout
        View timePickerView = LayoutInflater.from(requireContext()).inflate(R.layout.time_picker_dialog, null);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(timePickerView);
        builder.setCancelable(false); // Không cho phép đóng bằng cách bấm ngoài Dialog

        AlertDialog dialog = builder.create();
        dialog.show();


        // Xử lý sự kiện cho nút "OK"
        confirmButton.setOnClickListener(v -> {
            int selectedHour = hourPicker.getValue();
            int selectedMinute = minutePicker.getValue();

            String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            if (isStartTime) {
                startTime.setText(formattedTime);
                updateSpinnerAndEditText(selectedHour, partsOfDay1); // Cập nhật Spinner và giờ cho Start Time
            } else {
                endTime.setText(formattedTime);
                updateSpinnerAndEditText(selectedHour, partsOfDay2); // Cập nhật Spinner và giờ cho End Time
            }
            // Đóng Dialog sau khi chọn giờ
            dialog.dismiss();
        });
    }


    // Update Spinner based on AM/PM
    private void updateSpinnerAndEditText(int hourOfDay, Spinner spinner) {
        if (hourOfDay >= 12) { // PM
            spinner.setSelection(1); // Set to PM
        } else { // AM
            spinner.setSelection(0); // Set to AM
        }
    }

    // Show BottomSheet dialog for Work Arrangement
    private void showBottomSheetDialog(String clickedItem) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ListView listView = bottomSheetView.findViewById(R.id.listView);

        // Thay đổi danh sách dựa trên mục được nhấn
        String[] items;
        if ("workType".equals(clickedItem)) {
            items = new String[]{"알바", "정규직", "계약직", "인턴", "주말알바", "기타"}; // Danh sách cho workType
        } else {
            items = new String[]{"하루(1일)", "1주일이하", "1주일~1개월", "1개월~3개월", "3개월~6개월", "6개월~1년", "1년이상"}; // Danh sách cho workPeriod
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item_center, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String newValue = items[position];

            // Cập nhật TextView tương ứng
            if ("workType".equals(clickedItem)) {
                workType.setText(newValue);
            } else {
                workPeriod.setText(newValue);
            }

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    recruitmentEndTime.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
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
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
