package com.example.application22024.employer;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.application22024.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class Step2Fragment extends Fragment {

    private TextView textViewDate, arrow, arrow1, arrow2;
    private Button nextButton;
    private EditText startTime, endTime, workArrangement;
    private Spinner partsOfDay1, partsOfDay2, salaryType;
    private Calendar calendar;

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

        // Initialize views
        initializeViews(view);

        // Set up event listeners
        setEventListeners();

        // Set up Spinners
        setUpSpinners();

        // Setup time pickers for start and end time
        setupTimePicker();

        // Handle Work Arrangement
        workArrangement.setFocusable(false);
        workArrangement.setOnClickListener(v -> showBottomSheetDialog(workArrangement.getText().toString()));

    }

    private void initializeViews(View view) {
        textViewDate = view.findViewById(R.id.text_view_date);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        nextButton = view.findViewById(R.id.button_next);
        arrow = view.findViewById(R.id.arrow);
        arrow1 = view.findViewById(R.id.arrow1);
        arrow2 = view.findViewById(R.id.arrow2);
        partsOfDay1 = view.findViewById(R.id.time1);
        partsOfDay2 = view.findViewById(R.id.time2);
        salaryType = view.findViewById(R.id.salaryType);
        workArrangement = view.findViewById(R.id.work_arrangement);
    }

    private void setEventListeners() {
        // Set next button listener
        nextButton.setOnClickListener(v -> navigateToNextFragment());
        //
        textViewDate.setOnClickListener(v -> showDatePickerDialog());
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
        salaryType.setSelection(0);
        partsOfDay1.setSelection(0);
        partsOfDay2.setSelection(1);
    }

    private void navigateToNextFragment() {
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
    private void showBottomSheetDialog(String currentWorkArrangement) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ListView listView = bottomSheetView.findViewById(R.id.listView);
        String[] items = {"알바", "정규직", "계약직","인턴","주말알바","기타"}; // Options for work arrangement
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String newValue = items[position];
            workArrangement.setText(newValue);
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
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    textViewDate.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
}
