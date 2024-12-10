package com.example.application22024.employee;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.application22024.R;

import java.util.Calendar;
import java.util.Locale;

import com.google.android.material.bottomsheet.BottomSheetDialog;


public class Page3 extends Fragment {
    private EditText editTextDate, editbirthday, editText, editText1, editText2, editText3;
    private Calendar calendar;
    private TextView educationStatus, levelOfEducation;
    private boolean isEdited = false;
    private int selectedGenderPosition = -1; // Vị trí ô giới tính được chọn
    private int initialGenderPosition = -1; // Lưu trạng thái giới tính ban đầu

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);

        setupDatePicker(view);

//        textView2 = view.findViewById(R.id.educationStatus);
//        textView2.setText("Option1");
//        textView2.setOnClickListener(v -> {
//            // Dữ liệu để hiển thị trong dialog
//            String[] items = {"Option 1", "Option 2", "Option 3"};
//
//            // Tạo AlertDialog
//            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
////            builder.setTitle("Chọn một tùy chọn");
//            builder.setItems(items, (dialog, which) -> {
//                // Cập nhật TextView khi người dùng chọn một mục
//                textView2.setText(items[which]);
//            });
//
//            // Hiển thị dialog
//            builder.show();
//        });

        //1
        educationStatus = view.findViewById(R.id.educationStatus);
        levelOfEducation = view.findViewById(R.id.levelOfEducation);
        //2
        educationStatus.setText("Option 2");
        levelOfEducation.setText("Option 1");
        //3
        String previousEducationStatus = educationStatus.getText().toString();
        String previousLevelOfEducation = levelOfEducation.getText().toString();
        //4
        educationStatus.setOnClickListener(v -> showBottomSheetDialog1(previousEducationStatus));
        levelOfEducation.setOnClickListener(v -> showBottomSheetDialog2(previousLevelOfEducation));


        editbirthday = view.findViewById(R.id.editbirthday);
        // Thêm TextWatcher cho các EditText
        addTextWatcher(editbirthday);


        setupGenderClick(view, R.id.male, 0);
        setupGenderClick(view, R.id.female, 1);
        initialGenderPosition = selectedGenderPosition; // Ghi lại trạng thái ban đầu

        return view;
    }


//----------------------------------------------------------------------------------------


    private void showBottomSheetDialog1(String previousValue) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ListView listView = bottomSheetView.findViewById(R.id.listView);
        String[] items = {"재학", "졸업", " ....."}; // Các tùy chọn
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String newValue = items[position];
            educationStatus.setText(newValue);
            if (!newValue.equals(previousValue)) {
                isEdited = true; // Đánh dấu là đã thay đổi
            }
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setOnShowListener(dialog -> {
            // 动画效果
            bottomSheetView.animate().translationY(0).setDuration(500);
        });

        bottomSheetDialog.show();
    }

    private void showBottomSheetDialog2(String previousValue) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ListView listView = bottomSheetView.findViewById(R.id.listView);
        String[] items = {"고등", "대학", "1학년", " ....."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, items);
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

    public void setupDatePicker(View view) {
        editTextDate = view.findViewById(R.id.editbirthday);
        calendar = Calendar.getInstance();
        // Vô hiệu hóa chế độ nhập liệu cho EditText
        editTextDate.setFocusable(false);
        // Xử lý sự kiện khi nhấn vào EditText
        editTextDate.setOnClickListener(v -> showDatePickerAlertDialog());

    }

    // Hiển thị DatePickerDialog
    private void showDatePickerAlertDialog() {
        // Khởi tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.date_picker_alert_dialog, null);
        builder.setView(dialogView);

        // Tìm các NumberPicker và nút OK trong dialog layout
        NumberPicker yearPicker = dialogView.findViewById(R.id.yearPicker);
        NumberPicker monthPicker = dialogView.findViewById(R.id.monthPicker);
        NumberPicker dayPicker = dialogView.findViewById(R.id.dayPicker);

        // Thiết lập giá trị cho NumberPicker
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(currentYear);
        yearPicker.setValue(currentYear);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        // Cập nhật ngày tối đa khi thay đổi năm hoặc tháng
        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateDayPickerMax(dayPicker, yearPicker.getValue(), monthPicker.getValue()));
        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateDayPickerMax(dayPicker, yearPicker.getValue(), monthPicker.getValue()));

        // Tạo AlertDialog và hiển thị
        AlertDialog alertDialog = builder.create();

        // Xử lý khi nhấn nút "OK"
        dialogView.findViewById(R.id.confirmButton).setOnClickListener(v -> {
            int selectedYear = yearPicker.getValue();
            int selectedMonth = monthPicker.getValue();
            int selectedDay = dayPicker.getValue();

            //日期选择的范围验证，确保生日不能设置为未来的日期
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(selectedYear, selectedMonth - 1, selectedDay);
            if (selectedCalendar.after(Calendar.getInstance())) {
                Toast.makeText(getActivity(), "Please select a valid date.", Toast.LENGTH_SHORT).show();
            }else {
                // Cập nhật EditText với ngày đã chọn
                String selectedDate = String.format(Locale.getDefault(), "%04d.%02d.%02d", selectedYear, selectedMonth, selectedDay);
                editTextDate.setText(selectedDate);

                // Đóng dialog
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void updateDayPickerMax(NumberPicker dayPicker, int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);  // Calendar tháng bắt đầu từ 0
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayPicker.setMaxValue(maxDay);
    }


//--------------------------------------------------------------------------------------------


    // Phương thức thêm TextWatcher vào EditText
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

    // Phương thức kiểm tra xem có thay đổi gì không
    public boolean isEdited() {
        return isEdited;
    }

    // Phương thức lưu thay đổi
    public void saveChanges() {
        // Xử lý lưu dữ liệu ở đây
        isEdited = false;  // Sau khi lưu, đánh dấu rằng không còn thay đổi nào nữa
    }

    //------------------------------------------------------------
//chọn giới tính
    private void setupGenderClick(View view, int itemId, int position) {
        TextView cell = view.findViewById(itemId);
        // 如果没有选择性别，默认选择
        if (selectedGenderPosition == -1 && position == 0) {
            cell.setBackgroundColor(Color.BLUE);
            cell.setTextColor(Color.WHITE);
            selectedGenderPosition = position;
        }
        cell.setOnClickListener(v -> {
            // Đặt lại màu cho ô giới tính đã chọn trước đó
            if (selectedGenderPosition != -1) {
                resetGenderColor(view, selectedGenderPosition);
            }
            // Cập nhật vị trí giới tính được chọn
            selectedGenderPosition = position;
            // So sánh giá trị mới với giá trị ban đầu
            if (selectedGenderPosition != initialGenderPosition) {
                isEdited = true;
            }
            // Thay đổi màu ô giới tính được chọn
            cell.setBackgroundColor(Color.BLUE);
            cell.setTextColor(Color.WHITE);
        });
    }

    private void resetGenderColor(View view, int position) {
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
        TextView cell = view.findViewById(itemId);
        // Khôi phục màu nền về drawable
        cell.setBackgroundResource(R.drawable.border_square);
        cell.setTextColor(Color.BLACK); // Màu chữ mặc định
    }
//-------------------------------------------------------------------
}
