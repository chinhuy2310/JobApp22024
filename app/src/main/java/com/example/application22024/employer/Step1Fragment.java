package com.example.application22024.employer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.DataViewModel;

public class Step1Fragment extends Fragment {

    private int selectedPosition = -1; // Vị trí ô được chọn
    private int selectedGenderPosition = -1; // Vị trí ô giới tính được chọn
    private EditText jobTitleEditText, companyNameEditText, contactEditText, otherRecruitmentField;
    private TextView nonBinary, male, female, item1, item2, item3, item4, item5, item6;
    private Button nextButton;

    private DataViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ((MyApplication) getActivity().getApplication()).getDataViewModel();


        // Các trường nhập liệu
        jobTitleEditText = view.findViewById(R.id.title_edit_text);
        companyNameEditText = view.findViewById(R.id.company_name_edit_text);
        contactEditText = view.findViewById(R.id.contact_edit_text);
        otherRecruitmentField = view.findViewById(R.id.otherRecruitmentField);

        nonBinary = view.findViewById(R.id.Non_binary);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        if (viewModel.getSelectedCompany() != null) {
            companyNameEditText.setText(viewModel.getSelectedCompany().getCompanyName());
            contactEditText.setText(viewModel.getSelectedCompany().getContact());
            viewModel.setCompanyName(companyNameEditText.getText().toString());
            viewModel.setContact(contactEditText.getText().toString());
        } else {
//            Log.e("selectedCompany", "is null");
        }
        if (viewModel.getSelectedJob() != null) {
            jobTitleEditText.setText(viewModel.getSelectedJob().getTitle());
            viewModel.setRecruitmentTitle(jobTitleEditText.getText().toString());
            String workField = viewModel.getSelectedJob().getWorkField();
            String[] recruitmentFields = {"농장", "공장", "식당", "사무직", "건설", "야외"};
            boolean foundMatch = false;  // Flag để kiểm tra sự khớp
            for (int i = 0; i < recruitmentFields.length; i++) {
                if (recruitmentFields[i].equals(workField)) {
                    // Cập nhật ô được chọn trong bảng
                    updateSelectedPosition(view, i);
                    foundMatch = true;  // Đánh dấu đã tìm thấy sự khớp
                    break;
                }
            }
            if (!foundMatch) {
                // Nếu không tìm thấy sự khớp, điền vào ô "other"
                otherRecruitmentField.setText(workField);
                viewModel.setSelectedRecruitmentField(workField);
            }

            String selectedGender = viewModel.getSelectedJob().getRecruitmentGender();
//            Log.e("selectedGender", selectedGender);
            if (selectedGender != null) {
                switch (selectedGender) {
                    case "성별 무관" :  // Non-binary
                        selectedGenderPosition = 0;
                        viewModel.setSelectedGender("성별 무관"); // Gửi giá trị vào ViewModel
                        break;
                    case "남자":  // Male
                        selectedGenderPosition = 1;
                        viewModel.setSelectedGender("남자");
                        break;
                    case "여자":  // Female
                        selectedGenderPosition = 2;
                        viewModel.setSelectedGender("여자");
                        break;
                }
                updateGenderSelection(view);

            }

        } else {
//            Log.e("selectedJob", "is null");
        }
        // Gọi phương thức chung cho từng trường
        setTextChangedListener(jobTitleEditText, viewModel::setRecruitmentTitle);
        setTextChangedListener(companyNameEditText, viewModel::setCompanyName);
        setTextChangedListener(contactEditText, viewModel::setContact);
        setTextChangedListener(otherRecruitmentField, viewModel::setOtherRecruitmentField);

        // Các ô bảng
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);
        item4 = view.findViewById(R.id.item4);
        item5 = view.findViewById(R.id.item5);
        item6 = view.findViewById(R.id.item6);



        // Thiết lập sự kiện click cho các ô bảng
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

        nonBinary.setOnClickListener(v -> {
            selectedGenderPosition = 0; // Cập nhật vị trí đã chọn
            viewModel.setSelectedGender("성별 무관"); // Gửi giá trị vào ViewModel
            nonBinary.setBackgroundColor(Color.BLUE); // Thay đổi màu cho ô đã chọn
            nonBinary.setTextColor(Color.WHITE);
            resetGenderColor(male); // Đặt lại màu cho các ô giới tính khác
            resetGenderColor(female);
        });

        male.setOnClickListener(v -> {
            selectedGenderPosition = 1;
            viewModel.setSelectedGender("남자");
            male.setBackgroundColor(Color.BLUE);
            male.setTextColor(Color.WHITE);
            resetGenderColor(nonBinary);
            resetGenderColor(female);
        });

        female.setOnClickListener(v -> {
            selectedGenderPosition = 2;
            viewModel.setSelectedGender("여자");
            female.setBackgroundColor(Color.BLUE);
            female.setTextColor(Color.WHITE);
            resetGenderColor(nonBinary);
            resetGenderColor(male);
        });
        // Khởi tạo sự kiện cho RadioButton
        RadioButton radioButton = view.findViewById(R.id.radio_button);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Hủy chọn ô đã chọn trong bảng nếu có
                if (selectedPosition != -1) {
                    resetCellColor(view.findViewById(getItemId(selectedPosition)));
                    selectedPosition = -1;
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
                        resetCellColor(view.findViewById(getItemId(selectedPosition)));
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


        Button nextButton = view.findViewById(R.id.button_next);
        nextButton.setOnClickListener(v -> {
            if(viewModel.getSelectedJob() == null ||viewModel.getSelectedRecruitmentField() == null||
            viewModel.getSelectedGender() == null || viewModel.getRecruitmentTitle() == null ||
            viewModel.getCompanyName() == null || viewModel.getContact() == null){
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }else {
            // Chuyển sang Fragment tiếp theo
            ((RegistrationActivity) getActivity()).showNextFragment(new Step2Fragment());}
        });
    }

    private void updateGenderSelection(View view) {
        // Reset lại màu sắc cho tất cả các ô giới tính
        resetGenderColor(nonBinary);
        resetGenderColor(male);
        resetGenderColor(female);

        // Tùy vào selectedGenderPosition, thay đổi màu sắc của ô tương ứng
        switch (selectedGenderPosition) {
            case 0: // Non-binary
                nonBinary.setBackgroundColor(Color.BLUE);
                nonBinary.setTextColor(Color.WHITE);
                break;
            case 1: // Male
                male.setBackgroundColor(Color.BLUE);
                male.setTextColor(Color.WHITE);
                break;
            case 2: // Female
                female.setBackgroundColor(Color.BLUE);
                female.setTextColor(Color.WHITE);
                break;
        }
    }

    // Phương thức chung để xử lý TextWatcher cho bất kỳ EditText nào
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

    private void updateSelectedPosition(View view, int position) {
        // Cập nhật lại màu sắc cho ô đã chọn trước đó
        if (selectedPosition != -1) {
            resetCellColor(view.findViewById(getItemId(selectedPosition)));
        }

        // Cập nhật lại selectedPosition
        selectedPosition = position;

        // Cập nhật màu sắc cho ô được chọn
        TextView selectedCell = view.findViewById(getItemId(selectedPosition));
        selectedCell.setBackgroundColor(Color.BLUE);
        selectedCell.setTextColor(Color.WHITE);

        // Cập nhật vào ViewModel
        String[] recruitmentFields = {"농장", "공장", "식당", "사무직", "건설", "야외"};
        viewModel.setSelectedRecruitmentField(recruitmentFields[position]);
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
                resetCellColor(view.findViewById(getItemId(selectedPosition)));
            }
            // Cập nhật vị trí được chọn
            selectedPosition = position;
            // Thay đổi màu ô được chọn
            cell.setBackgroundColor(Color.BLUE);
            cell.setTextColor(Color.WHITE);

            String[] recruitmentFields = {"농장", "공장", "식당", "사무직", "건설", "야외"};
            if (position >= 0 && position < recruitmentFields.length) {
                viewModel.setSelectedRecruitmentField(recruitmentFields[position]);
            }
            // Ẩn bàn phím
            hideKeyboard();
        });
    }

    private int getItemId(int position) {
        int[] itemIds = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6};
        return position >= 0 && position < itemIds.length ? itemIds[position] : -1;
    }

    private void resetCellColor(TextView cell) {
        cell.setBackgroundResource(R.drawable.border_square);
        cell.setTextColor(Color.BLACK);
    }

    private void setupGenderClick(View view, int itemId, int position) {
        TextView cell = view.findViewById(itemId);
        cell.setOnClickListener(v -> {
            if (selectedGenderPosition != -1) {
                resetGenderColor(view.findViewById(getGenderId(selectedGenderPosition)));
            }

            selectedGenderPosition = position;
            cell.setBackgroundColor(Color.BLUE);
            cell.setTextColor(Color.WHITE);
        });
    }

    private int getGenderId(int position) {
        int[] genderIds = {R.id.Non_binary, R.id.male, R.id.female};
        return position >= 0 && position < genderIds.length ? genderIds[position] : -1;
    }

    private void resetGenderColor(TextView cell) {
        cell.setBackgroundResource(R.drawable.border_square);
        cell.setTextColor(Color.BLACK);
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
