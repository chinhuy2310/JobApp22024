package com.example.application22024.employee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
    private boolean isSwipingFromPage2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        viewPager.setOffscreenPageLimit(3);

        int[] tabIcons = {R.drawable.ic_home, R.drawable.ic_taikhoan, R.drawable.ic_bookmark, R.drawable.ic_menu};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setIcon(tabIcons[position]);
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                inputMethodManager.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                checkForUnsavedChangesAndSwitch(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING && viewPager.getCurrentItem() == 1) {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f1");
                    if (currentFragment instanceof Page2) {
                        Page2 page2Fragment = (Page2) currentFragment;
                        if (page2Fragment.isEdited()) {
                            isSwipingFromPage2 = true;
                        }
                    }
                } else if (state == ViewPager2.SCROLL_STATE_IDLE && isSwipingFromPage2) {
                    isSwipingFromPage2 = false;
                    showSaveAlertDialog((Page2) getSupportFragmentManager().findFragmentByTag("f1"), viewPager.getCurrentItem());
                }
            }
        });
    }

    private void checkForUnsavedChangesAndSwitch(int newPosition) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
        if (currentFragment instanceof Page2) {
            Page2 page2Fragment = (Page2) currentFragment;
            if (page2Fragment.isEdited()) {
                showSaveAlertDialog(page2Fragment, newPosition);
            } else {
                viewPager.setCurrentItem(newPosition, true);
            }
        } else {
            viewPager.setCurrentItem(newPosition, true);
        }
    }

    private void showSaveAlertDialog(Page2 page2Fragment, int newPosition) {
        new AlertDialog.Builder(this)
                .setTitle("Save?")
                .setMessage("You have unsaved changes. Do you want to save before switching pages?")
                .setPositiveButton("save", (dialog, which) -> {
                    page2Fragment.saveChanges();
                    viewPager.setCurrentItem(newPosition, true);
                })
                .setNegativeButton("don't save", (dialog, which) -> {
                    viewPager.setCurrentItem(newPosition, true);
                })
                .setCancelable(false)
                .show();
    }
}
