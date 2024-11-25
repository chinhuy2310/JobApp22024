package com.example.application22024.employer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.application22024.R;

public class Step1Fragment extends Fragment {

    private int selectedPosition = -1; // Vị trí ô được chọn
    private int selectedGenderPosition = -1; // Vị trí ô giới tính được chọn
    private EditText otherRecruitmentField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        EditText editText = view.findViewById(R.id.edit_text);
//        editText.setHintTextColor(Color.parseColor("#B0B0B0")); // Màu xám nhạt
        otherRecruitmentField = view.findViewById(R.id.otherRecruitmentField);
        Button nextButton = view.findViewById(R.id.button_next);
        nextButton.setOnClickListener(v -> {
            // Chuyển sang Fragment tiếp theo
            ((RegistrationActivity) getActivity()).showNextFragment(new Step2Fragment());
        });


        setupCellClick(view, R.id.item1, 0);
        setupCellClick(view, R.id.item2, 1);
        setupCellClick(view, R.id.item3, 2);
        setupCellClick(view, R.id.item4, 3);
        setupCellClick(view, R.id.item5, 4);
        setupCellClick(view, R.id.item6, 5);

        // Setup for gender selection cells
        setupGenderClick(view, R.id.Non_binary, 0);
        setupGenderClick(view, R.id.male, 1);
        setupGenderClick(view, R.id.female, 2);

        // Khởi tạo sự kiện cho RadioButton
        RadioButton radioButton = view.findViewById(R.id.radio_button);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Hủy chọn ô đã chọn trong bảng nếu có
                if (selectedPosition != -1) {
                    resetCellColor(view, selectedPosition);
                    selectedPosition = -1; // Đặt lại giá trị của selectedPosition
                }
            }
        });
        otherRecruitmentField.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nếu có nội dung, chọn RadioButton
                if (s.length() > 0) {
                    radioButton.setChecked(true);
                    // Hủy chọn ô bảng nếu trước đó được chọn
                    if (selectedPosition != -1) {
                        resetCellColor(view, selectedPosition);
                        selectedPosition = -1; // Đặt lại giá trị
                    }
                } else {
                    // Nếu không có nội dung, bỏ chọn RadioButton
                    radioButton.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                // Không cần xử lý
            }
        });
    }

    private void setupCellClick(View view, int itemId, int position) {
        TextView cell = view.findViewById(itemId);
        cell.setOnClickListener(v -> {
            // bỏ chọn  RadioButton
            RadioButton radioButton = view.findViewById(R.id.radio_button);
            radioButton.setChecked(false);

            // Xóa chữ đã nhập trong EditText
            otherRecruitmentField.setText("");  // Xóa nội dung đã nhập

            // Đặt lại màu cho ô đã chọn trước đó
            if (selectedPosition != -1) {
                resetCellColor(view, selectedPosition);
            }
            // Cập nhật vị trí được chọn
            selectedPosition = position;
            // Thay đổi màu ô được chọn
            cell.setBackgroundColor(Color.BLUE);
            cell.setTextColor(Color.WHITE);
            // Ẩn bàn phím
            hideKeyboard();
        });
    }

    private void resetCellColor(View view, int position) {
        int itemId;
        switch (position) {
            case 0:
                itemId = R.id.item1;
                break;
            case 1:
                itemId = R.id.item2;
                break;
            case 2:
                itemId = R.id.item3;
                break;
            case 3:
                itemId = R.id.item4;
                break;
            case 4:
                itemId = R.id.item5;
                break;
            case 5:
                itemId = R.id.item6;
                break;
            default:
                return;
        }
        TextView cell = view.findViewById(itemId);
        // Khôi phục màu nền về drawable
        cell.setBackgroundResource(R.drawable.border_square);
        cell.setTextColor(Color.BLACK); // Màu chữ mặc định
    }

    private void setupGenderClick(View view, int itemId, int position) {
        TextView cell = view.findViewById(itemId);
        cell.setOnClickListener(v -> {
            // Đặt lại màu cho ô giới tính đã chọn trước đó
            if (selectedGenderPosition != -1) {
                resetGenderColor(view, selectedGenderPosition);
            }
            // Cập nhật vị trí giới tính được chọn
            selectedGenderPosition = position;
            // Thay đổi màu ô giới tính được chọn
            cell.setBackgroundColor(Color.BLUE);
            cell.setTextColor(Color.WHITE);
        });
    }

    private void resetGenderColor(View view, int position) {
        int itemId;
        switch (position) {
            case 0:
                itemId = R.id.Non_binary;
                break;
            case 1:
                itemId = R.id.male;
                break;
            case 2:
                itemId = R.id.female;
                break;
            default:
                return;
        }
        TextView cell = view.findViewById(itemId);
        // Khôi phục màu nền về drawable
        cell.setBackgroundResource(R.drawable.border_square);
        cell.setTextColor(Color.BLACK); // Màu chữ mặc định
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
