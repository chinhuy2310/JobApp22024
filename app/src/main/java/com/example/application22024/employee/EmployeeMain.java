package com.example.application22024.employee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.application22024.R;
import com.example.application22024.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EmployeeMain extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private InputMethodManager inputMethodManager;
    private boolean isSwipingFromPage2 = false;
    private boolean backPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_employee);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new Page1());
        adapter.addFragment(new Page2());
        adapter.addFragment(new Page3());
        adapter.addFragment(new Page4());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        int[] tabIcons = {R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_bookmark, R.drawable.ic_menu};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setIcon(tabIcons[position]);
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                inputMethodManager.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    public void goToFragment(int fragmentIndex) {
        viewPager.setCurrentItem(fragmentIndex, true); // Chuyển tới fragment thứ fragmentIndex
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            moveTaskToBack(true); // Ẩn ứng dụng
//            finish(); // Kết thúc Activity (thoát hoàn toàn)
        } else {
            backPressedOnce = true;
            Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

            // Đặt lại trạng thái sau 2 giây
            new Handler().postDelayed(() -> backPressedOnce = false, 2000);
        }
    }
}
