package com.example.application22024.employee;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.application22024.First_Activity;
import com.example.application22024.R;

public class Page4 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page4, container, false);

        Button userInfoButton = view.findViewById(R.id.user_info);
        Button editInfoButton = view.findViewById(R.id.edit_info);
        Button aboutUsButton = view.findViewById(R.id.about_us);
        Button privacyButton = view.findViewById(R.id.privacy);
        Button generalButton = view.findViewById(R.id.general);
        Button logoutButton = view.findViewById(R.id.logout);

        // 用户信息按钮点击事件
        userInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            intent.putExtra("user_id", 1); // 传递用户 ID
            startActivity(intent);
        });

        // 修改信息按钮点击事件
        editInfoButton.setOnClickListener(v -> {
            // 跳转到编辑信息页面
            Toast.makeText(getActivity(), "Edit Information clicked", Toast.LENGTH_SHORT).show();
        });

        // 关于我们按钮点击事件
        aboutUsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        });

        // 隐私按钮点击事件
        privacyButton.setOnClickListener(v -> {
            // 显示隐私政策
            new AlertDialog.Builder(getActivity())
                    .setTitle("Privacy Policy")
                    .setMessage("This is the privacy policy content.")
                    .setPositiveButton("OK", null)
                    .show();
        });

        // 通用设置按钮点击事件
        generalButton.setOnClickListener(v -> {
            // 跳转到通用设置页面
            new AlertDialog.Builder(getActivity())
                    .setTitle("General Settings")
                    .setMessage("Change language, notifications, and other settings.")
                    .setPositiveButton("OK", null)
                    .show();
        });

        // 注销按钮点击事件
        logoutButton.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(getActivity(), First_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 清除所有活动
                        startActivity(intent);
                        getActivity().finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        return view;
    }
}