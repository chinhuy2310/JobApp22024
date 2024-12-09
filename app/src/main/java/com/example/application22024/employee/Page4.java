package com.example.application22024.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.application22024.First_Activity;
import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.DataViewModel;

public class Page4 extends Fragment {
    DataViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page4, container, false);
        viewModel = ((MyApplication) getActivity().getApplication()).getDataViewModel();
        // Khởi tạo Button
        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), First_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa tất cả các hoạt động trước
                startActivity(intent);
                viewModel.reset();
                // Gọi finish() trên Activity đang mở
                getActivity().finish();
            }
        });

        Button profile = view.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
