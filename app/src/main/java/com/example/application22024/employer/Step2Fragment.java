package com.example.application22024.employer;

import android.app.DatePickerDialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.RegistrationViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Step2Fragment extends Fragment {

    private TextView arrow, arrow1, arrow2;
    private Button nextButton;
    private EditText recruitmentEndTime, startTime, endTime, workType, salary, recruitmentCount, workPeriod, workDay;
    private Spinner partsOfDay1, partsOfDay2, salaryType;
    private Calendar calendar;
    private RegistrationViewModel viewModel;
    private CheckBox checkBoxOption1, checkBoxOption2, checkBoxOption3;


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
        viewModel = ((MyApplication) getActivity().getApplication()).getRegistrationViewModel();

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
            recruitmentEndTime.setText(viewModel.getSelectedJob().getRecruitmentEnd());

            viewModel.setRecruitmentCount(recruitmentCount.getText().toString());
            viewModel.setSalary(salary.getText().toString());
            viewModel.setStartTime(startTime.getText().toString());
            viewModel.setEndTime(endTime.getText().toString());
            viewModel.setWorkType(workType.getText().toString());
            viewModel.setWorkPeriod(workPeriod.getText().toString());
            viewModel.setWorkDay(workDay.getText().toString());
            viewModel.setRecruitmentEndTime(recruitmentEndTime.getText().toString());

            Log.e("", viewModel.getSelectedJob().getCanNegotiableTime());
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

        // Get the selected values from Spinners

//        String selectedPartOfDay1 = partsOfDay1.getSelectedItem().toString();
//        String selectedPartOfDay2 = partsOfDay2.getSelectedItem().toString();
//        viewModel.setPartOfDay1(selectedPartOfDay1);
//        viewModel.setPartOfDay2(selectedPartOfDay2);

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
        nextButton.setOnClickListener(v -> validateAndNavigate());
        //
        recruitmentEndTime.setOnClickListener(v -> showDatePickerDialog());
        // Set arrow click listeners to open Spinners
        arrow.setOnClickListener(v -> salaryType.performClick());
        arrow1.setOnClickListener(v -> partsOfDay1.performClick());
        arrow2.setOnClickListener(v -> partsOfDay2.performClick());
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
            Log.e("Step2Fragment", "Selected Salary Type: " + selectedSalaryType);
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
        viewModel.setSelectedSalaryType(selectedSalaryType);
        hideKeyboard();
        // Navigate to the next fragment
        ((RegistrationActivity) getActivity()).showNextFragment(new Step3Fragment());
    }


    // Setup time picker dialogs for start and end time
    public void setupTimePicker() {
        startTime.setFocusable(false);
        endTime.setFocusable(false);

        // Set time pickers for start and end time
        startTime.setOnClickListener(v -> showCustomTimePickerDialog(true)); // Start time
        endTime.setOnClickListener(v -> showCustomTimePickerDialog(false)); // End time
    }

    //        //muốn mã ngắn gọn thì sử dụng showTimePickerDialog() thay cho showCustomTimePickerDialog()
//        private void showTimePickerDialog(final boolean isStartTime) {
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
//                if (isStartTime) {
//                    startTime.setText(formattedTime);
//                    updateSpinnerAndEditText(hourOfDay, partsOfDay1); // Update Spinner and time for start
//                } else {
//                    endTime.setText(formattedTime);
//                    updateSpinnerAndEditText(hourOfDay, partsOfDay2); // Update Spinner and time for end
//                }
//            }
//        }, hour, minute, true); // Use 24-hour format
//        timePickerDialog.show();
//    }
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
            items = new String[]{"하루(1일)", "1주일이하", "1주일~1개월", "1개월~3개월", "3개월~6개월","6개월~1년","1년이상"}; // Danh sách cho workPeriod
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),R.layout.list_item_center, items);
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
        // 设置日期范围
        calendar.add(Calendar.DATE, 1); // 不允许选择今天之前的日期
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

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

    //验证输入字段
    private void validateAndNavigate() {
        // 获取输入值
        String recruitmentCountValue = recruitmentCount.getText().toString().trim();
        String salaryValue = salary.getText().toString().trim();
        String startTimeValue = startTime.getText().toString().trim();
        String endTimeValue = endTime.getText().toString().trim();

        // 检查必填字段是否为空
        if (recruitmentCountValue.isEmpty() || salaryValue.isEmpty() || startTimeValue.isEmpty() || endTimeValue.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 所有字段验证通过，跳转到下一页
        navigateToNextFragment();
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
