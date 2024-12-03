package com.example.application22024.employer;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.RegistrationViewModel;

public class Step3Fragment extends Fragment {
    private RegistrationViewModel viewModel;
    private EditText addressEditText, detailAddressEditText, descriptionEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ((MyApplication) getActivity().getApplication()).getRegistrationViewModel();

        addressEditText = view.findViewById(R.id.editText_address);
        detailAddressEditText = view.findViewById(R.id.editText_detail_address);
        descriptionEditText = view.findViewById(R.id.editText_description);
        if (viewModel.getSelectedJob() != null){
            descriptionEditText.setText(viewModel.getSelectedJob().getDetails());
            viewModel.setDescription(descriptionEditText.getText().toString());
        }
        if (viewModel.getSelectedCompany() != null) {
            addressEditText.setText(viewModel.getSelectedCompany().getAddress());
            viewModel.setAddress(addressEditText.getText().toString());
        }

        setTextChangedListener(addressEditText, viewModel::setAddress);
        setTextChangedListener(detailAddressEditText, viewModel::setDetailAddress);
        setTextChangedListener(descriptionEditText, viewModel::setDescription);

        Button nextButton = view.findViewById(R.id.button_next);
        nextButton.setOnClickListener(v -> {
            hideKeyboard();
            // Chuyển sang Fragment tiếp theo
            ((RegistrationActivity) getActivity()).showNextFragment(new Step4Fragment());
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
