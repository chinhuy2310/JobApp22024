package com.example.application22024.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.application22024.FAQActivity;
import com.example.application22024.First_Activity;
import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.DataViewModel;

public class Page4 extends Fragment {
    private LinearLayout customerServiceLayout;
    private LinearLayout calendarLayout;  // 캘린더 레이아웃 추가
    private LinearLayout profileLayout;   // 프로필 레이아웃 추가
    private LinearLayout appliedLayout;   // 내 지원 현황 레이아웃 추가
    private DataViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page4, container, false);
        viewModel = ((MyApplication) getActivity().getApplication()).getDataViewModel();

        customerServiceLayout = view.findViewById(R.id.CustomerServiceLayout); // 고객센터 레이아웃
        customerServiceLayout.setOnClickListener(v -> openCustomerServicePage()); // 고객센터 클릭 시 Q&A 페이지로 이동

        // 캘린더 클릭 시 CalendarActivity로 이동
        calendarLayout = view.findViewById(R.id.calendarLayout); // 캘린더 레이아웃
        calendarLayout.setOnClickListener(v -> openCalendarPage()); // 캘린더 클릭 시 CalendarActivity로 이동

        // 프로필 클릭 시 ProfileActivity로 이동
        profileLayout = view.findViewById(R.id.ProfileLayout); // 프로필 레이아웃
        profileLayout.setOnClickListener(v -> openProfilePage()); // 프로필 클릭 시 ProfileActivity로 이동

        appliedLayout = view.findViewById(R.id.applied);
        appliedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyApplicationsActivity.class);
                startActivity(intent);
            }
        });
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

        return view;
    }

    // 고객센터 페이지로 이동하는 메서드
    private void openCustomerServicePage() {
        Intent intent = new Intent(getContext(), FAQActivity.class);
        startActivity(intent);
    }

    // 캘린더 페이지로 이동하는 메서드
    private void openCalendarPage() {
        Intent intent = new Intent(getActivity(), CalendarActivity.class);
        startActivity(intent);
    }

    // 프로필 페이지로 이동하는 메서드
    private void openProfilePage() {
        Intent intent = new Intent(getActivity(), Profile.class);
        startActivity(intent);
    }
}
