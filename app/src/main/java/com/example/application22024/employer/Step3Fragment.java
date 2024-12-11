package com.example.application22024.employer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.RegionDataManager;
import com.example.application22024.adapter.LeftAdapter;
import com.example.application22024.adapter.RightAdapter;
import com.example.application22024.model.DataViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Step3Fragment extends Fragment {
    private DataViewModel viewModel;
    private EditText addressEditText, detailAddressEditText, descriptionEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ((MyApplication) getActivity().getApplication()).getDataViewModel();

        addressEditText = view.findViewById(R.id.editText_address);
        detailAddressEditText = view.findViewById(R.id.editText_detail_address);
        descriptionEditText = view.findViewById(R.id.editText_description);
        addressEditText.setFocusable(false);
        addressEditText.setOnClickListener(v -> showCustomDialog());

        if (viewModel.getSelectedJob() != null){
            descriptionEditText.setText(viewModel.getSelectedJob().getDetails());
            viewModel.setDescription(descriptionEditText.getText().toString());
        }
        if (viewModel.getSelectedCompany() != null) {

//            addressEditText.setText(viewModel.getSelectedCompany().getAddress());
            String fulladdress =viewModel.getSelectedCompany().getAddress(); // Chuỗi ban đầu
            // Tách chuỗi thành mảng các phần tử dựa trên ký tự " - "
            String[] parts = fulladdress.split(" ");
            // Lấy phần 1 và 2 để hiển thị trong TextView1
            if (parts.length >= 2) {
                String textView1Content = parts[0] + " " + parts[1];
                addressEditText.setText(textView1Content);
                viewModel.setAddress(textView1Content);
            } else {
                addressEditText.setText(""); // Nếu không đủ phần tử, hiển thị chuỗi rỗng
            }
            // Ghép các phần tử còn lại để hiển thị trong TextView2
            if (parts.length > 2) {
                StringBuilder textView2Content = new StringBuilder();
                for (int i = 2; i < parts.length; i++) {
                    if (i > 2) {
                        textView2Content.append(" "); // Thêm khoảng trắng giữa các phần tử
                    }
                    textView2Content.append(parts[i]);
                }
                detailAddressEditText.setText(textView2Content.toString());
                viewModel.setDetailAddress(textView2Content.toString());
            } else {
                detailAddressEditText.setText(""); // Nếu không còn phần tử nào, hiển thị chuỗi rỗng
            }
        }
//        setTextChangedListener(addressEditText, viewModel::setAddress);
        setTextChangedListener(detailAddressEditText, viewModel::setDetailAddress);
        setTextChangedListener(descriptionEditText, viewModel::setDescription);

        Button nextButton = view.findViewById(R.id.button_next);
        nextButton.setOnClickListener(v -> {
            hideKeyboard();
            if( viewModel.getAddress() == null ||
                    viewModel.getDetailAddress() == null || viewModel.getDescription() == null){
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }else{
                // Chuyển sang Fragment tiếp theo
                ((RegistrationActivity) getActivity()).showNextFragment(new Step4Fragment());
            }
        });
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
    private void showCustomDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_select_location_layout);
        dialog.setCanceledOnTouchOutside(false);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Lấy dữ liệu từ RegionDataManager
        HashMap<String, ArrayList<String>> regionsData = RegionDataManager.getRegionsData();
        List<String> provinces = new ArrayList<>(regionsData.keySet());
        List<String> areas = new ArrayList<>();

        // Xóa mục đầu tiên khỏi danh sách provinces
        if (!provinces.isEmpty()) {
            provinces.remove(0);
        }

        // RecyclerView
        RecyclerView leftRecyclerView = dialog.findViewById(R.id.left_recycler_view);
        RecyclerView rightRecyclerView = dialog.findViewById(R.id.right_recycler_view);

        leftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapters
        LeftAdapter leftAdapter = new LeftAdapter(provinces, province -> {
            areas.clear();
            if (regionsData.get(province) != null) {
                areas.addAll(regionsData.get(province));
                // Xóa mục đầu tiên khỏi danh sách areas
                if (!areas.isEmpty()) {
                    areas.remove(0);
                }
            }
            rightRecyclerView.getAdapter().notifyDataSetChanged();
        });

        RightAdapter rightAdapter = new RightAdapter(areas);

        leftRecyclerView.setAdapter(leftAdapter);
        rightRecyclerView.setAdapter(rightAdapter);

        // Xử lý khi nhấn nút "Xác nhận"
        dialog.findViewById(R.id.confirm_button).setOnClickListener(v -> {
            // Lấy tỉnh và khu vực đã chọn từ Adapter
            String selectedProvince = leftAdapter.getSelectedProvince();
            String selectedArea = rightAdapter.getSelectedArea();

            // Kiểm tra xem có tỉnh và khu vực được chọn không
            if (selectedProvince == null || selectedArea == null || selectedProvince.isEmpty() || selectedArea.isEmpty()) {
                // Nếu không có tỉnh hoặc khu vực nào được chọn, hiển thị Toast thông báo
                Toast.makeText(getContext(), "Vui lòng chọn cả tỉnh và khu vực.", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu có tỉnh và khu vực được chọn, cập nhật TextView và ViewModel
                addressEditText.setText(selectedProvince + " " + selectedArea);
                viewModel.setAddress(selectedProvince + " " + selectedArea);
                // Đóng dialog
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.cancel_button).setOnClickListener(v -> {
                    dialog.dismiss();
        });

        // Hiển thị dialog
        dialog.show();
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
