package com.example.application22024.employer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.DataViewModel;
import com.squareup.picasso.Picasso;


public class Step4Fragment extends Fragment {
    private EditText companyName, representativeName, registerNumber1, registerNumber2;
    private DataViewModel viewModel;
    private ImageView companyImage;

    public interface OnPostButtonClickListener {
        void onPostButtonClicked();  // Phương thức này sẽ được gọi khi nhấn nút
    }

    private OnPostButtonClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Kiểm tra xem Activity có implement interface này không
        if (context instanceof OnPostButtonClickListener) {
            listener = (OnPostButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnNextButtonClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ((MyApplication) getActivity().getApplication()).getDataViewModel();

        companyName = view.findViewById(R.id.company_name);
        representativeName = view.findViewById(R.id.name_of_representative);
        registerNumber1 = view.findViewById(R.id.register_number);
        registerNumber2 = view.findViewById(R.id.register_number2);
        companyImage = view.findViewById(R.id.image_view);

        LinearLayout selectImage = view.findViewById(R.id.selectImage);
        selectImage.setOnClickListener(v -> {
            viewModel.setSelectedImageUri(null);
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100); // 100 là mã yêu cầu để nhận kết quả
        });

        if (viewModel.getSelectedCompany() != null) {
            companyName.setText(viewModel.getSelectedCompany().getCompanyName());
            representativeName.setText(viewModel.getSelectedCompany().getNameOfRepresentative());
            registerNumber1.setText(viewModel.getSelectedCompany().getRegistrationNumber().split("-")[0]);
            registerNumber2.setText(viewModel.getSelectedCompany().getRegistrationNumber().split("-")[1]);

            viewModel.setCompanyName(companyName.getText().toString());
            viewModel.setRepresentativeName(representativeName.getText().toString());
            viewModel.setRegisterNumber(viewModel.getSelectedCompany().getRegistrationNumber());

            String baseUrl = "http://10.0.2.2:3000"; // Địa chỉ gốc
            String relativePath = viewModel.getSelectedCompany().getCompanyIamge();
            String fullImageUrl = baseUrl + relativePath;
            Picasso.get().load(fullImageUrl)
                    .placeholder(R.drawable.border_square)
                    .error(R.drawable.ic_launcher_background)
                    .into(companyImage);
        } else {
//            Log.e("selectedCompany", "is null");
        }



//        Log.e("combinedRegisterNumber", combinedRegisterNumber);

        setTextChangedListener(companyName, viewModel::setCompanyName);
        setTextChangedListener(representativeName, viewModel::setRepresentativeName);

//        Log.e("registerNumber", viewModel.getRegisterNumber());


        Button nextButton = view.findViewById(R.id.button_post);
        nextButton.setOnClickListener(v -> {
            String combinedRegisterNumber = registerNumber1.getText().toString() + "-" + registerNumber2.getText().toString();
//            Log.e("combinedRegisterNumber", combinedRegisterNumber);
            viewModel.setRegisterNumber(combinedRegisterNumber);
            if (listener != null) {
                listener.onPostButtonClicked(); // Gọi phương thức trong Activity
            }
            // Chuyển sang một Activity khác
            Intent intent = new Intent(getActivity(), EmployerMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK && data != null) {
            // Lấy URI của bức ảnh đã chọn
            Uri selectedImageUri = data.getData();

            // Hiển thị ảnh lên ImageView
            companyImage.setImageURI(selectedImageUri);
            viewModel.setSelectedImageUri(selectedImageUri);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Nếu cấp quyền thành công, mở trình chọn ảnh
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        } else {
            // Nếu không cấp quyền, bạn có thể hiển thị một thông báo
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
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
}
