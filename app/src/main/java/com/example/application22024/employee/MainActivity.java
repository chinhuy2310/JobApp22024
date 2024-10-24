package com.example.application22024.employee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.application22024.R;
import com.example.application22024.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Ẩn nút back
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        // Khởi tạo các Fragment và thêm vào Adapter
        adapter.addFragment(new Page1());
        adapter.addFragment(new Page2());
        adapter.addFragment(new Page3());
        adapter.addFragment(new Page4());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        int[] tabIcons = {R.drawable.ic_home, R.drawable.ic_taikhoan, R.drawable.ic_bookmark, R.drawable.ic_menu};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Xác định hình ảnh cho tab dựa trên vị trí
            tab.setIcon(tabIcons[position]);
        }).attach();

        // Xử lý sự kiện khi nhấn vào tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                // Ẩn bàn phím khi chuyển đổi tab
                inputMethodManager.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);

                // Lấy fragment hiện tại từ ViewPager
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
                if (currentFragment instanceof Page2) {
                    Page2 page2Fragment = (Page2) currentFragment;

                    // Kiểm tra xem có thay đổi trong Page2 hay không
                    if (page2Fragment.isEdited()) {
                        // Hiển thị hộp thoại lưu nếu có thay đổi
                        showSaveAlertDialog(page2Fragment, position);
                    } else {
                        // Chuyển sang tab mới nếu không có thay đổi
                        viewPager.setCurrentItem(position, true);
                    }
                } else {
                    // Chuyển sang tab mới nếu không phải là Page2
                    viewPager.setCurrentItem(position, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý khi tab bị bỏ chọn
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý khi tab được chọn lại
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Lấy fragment hiện tại từ ViewPager
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());

                if (currentFragment instanceof Page2) {
                    Page2 page2Fragment = (Page2) currentFragment;

                    // Kiểm tra nếu có thay đổi trong Page2
                    if (page2Fragment.isEdited()) {
                        // Hiển thị cảnh báo nếu có thay đổi chưa lưu
                        showSaveAlertDialog(page2Fragment, position);
                    } else {
                        // Nếu không có thay đổi, chuyển sang trang mới
                        viewPager.setCurrentItem(position, true);
                    }
                } else {
                    // Chuyển sang tab mới nếu không phải là Page2
                    viewPager.setCurrentItem(position, true);
                }
            }
        });

    }


    private void showSaveAlertDialog(Page2 page2Fragment, int newPosition) {
        new AlertDialog.Builder(this)
                .setTitle("Save?")
                .setMessage("You have unsaved changes. Do you want to save before switching pages?")
                .setPositiveButton("save", (dialog, which) -> {
                    // Gọi phương thức lưu thay đổi trong Page2
                    page2Fragment.saveChanges();
                    // Sau khi lưu xong, chuyển sang tab mới
                    viewPager.setCurrentItem(newPosition, true);
                })
                .setNegativeButton("don't save", (dialog, which) -> {
                    // Tiếp tục mà không lưu, chuyển sang tab mới
                    viewPager.setCurrentItem(newPosition, true);
                })
                .setCancelable(false)
                .show();
    }
}
