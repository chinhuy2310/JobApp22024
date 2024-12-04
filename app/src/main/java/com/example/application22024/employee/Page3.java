package com.example.application22024.employee;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.application22024.R;
import com.example.application22024.RegionDataManager;
import com.example.application22024.adapter.LeftAdapter;
import com.example.application22024.adapter.RightAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Page3 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);
        view.findViewById(R.id.selectLocation).setOnClickListener(v -> showCustomDialog());

        return view;
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_select_location_layout);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        // Lấy dữ liệu từ RegionDataManager
        HashMap<String, ArrayList<String>> regionsData = RegionDataManager.getRegionsData();
        List<String> provinces = new ArrayList<>(regionsData.keySet());
        List<String> areas = new ArrayList<>();

        // RecyclerView
        RecyclerView leftRecyclerView = dialog.findViewById(R.id.left_recycler_view);
        RecyclerView rightRecyclerView = dialog.findViewById(R.id.right_recycler_view);

        leftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapters
        LeftAdapter leftAdapter = new LeftAdapter(provinces, province -> {
            areas.clear();
            areas.addAll(regionsData.get(province));
            rightRecyclerView.getAdapter().notifyDataSetChanged();
            RightAdapter rightAdapter = (RightAdapter) rightRecyclerView.getAdapter();
            rightAdapter.setSelectedPosition(0); // Đặt lại chọn mục đầu tiên
        });

        RightAdapter rightAdapter = new RightAdapter(areas);

        leftRecyclerView.setAdapter(leftAdapter);
        rightRecyclerView.setAdapter(rightAdapter);

        if (!provinces.isEmpty()) {
            // Chọn mục đầu tiên của LeftAdapter
            leftAdapter.setSelectedPosition(0);
            String firstProvince = provinces.get(0);
            areas.addAll(regionsData.get(firstProvince)); // Thêm dữ liệu cho RightAdapter
            rightAdapter.notifyDataSetChanged(); // Cập nhật RightAdapter
            rightAdapter.setSelectedPosition(0);
        }
        // Hiển thị dialog
        dialog.show();
    }
}
