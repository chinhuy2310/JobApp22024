package com.example.application22024.employee;

import android.app.DatePickerDialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.application22024.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Page4 extends Fragment {
    private EditText editTextDate;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page4, container, false);

        setupDatePicker(view);

        return view;
    }
 // Phương thức công khai để cài đặt DatePicker
    public void setupDatePicker(View view) {
        editTextDate = view.findViewById(R.id.editbirthday);
        calendar = Calendar.getInstance();

        // Vô hiệu hóa chế độ nhập liệu cho EditText
        editTextDate.setFocusable(false);

        // Xử lý sự kiện khi nhấn vào EditText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    // Hiển thị DatePickerDialog
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                editTextDate.setText(sdf.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(requireContext(), date, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}