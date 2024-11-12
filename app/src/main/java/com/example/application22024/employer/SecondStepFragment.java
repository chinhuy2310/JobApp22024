package com.example.application22024.employer;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.application22024.R;
import com.example.application22024.employee.RegistrationEmployeeActivity;

import java.util.Calendar;

public class SecondStepFragment extends Fragment {

    private TextView textViewDate,buttonSelectDate;
    private Button nextButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewDate = view.findViewById(R.id.text_view_date);
        buttonSelectDate = view.findViewById(R.id.button_select_date);
        nextButton = view.findViewById(R.id.button_next);

        buttonSelectDate.setOnClickListener(v -> showDatePickerDialog());

        nextButton.setOnClickListener(v -> {
            // Chuyển sang Fragment tiếp theo
            ((RegistrationEmployeeActivity) getActivity()).showNextFragment(new ThirdStepFragment());
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Thay đổi 'view' thành 'dateView'
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    textViewDate.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
}
