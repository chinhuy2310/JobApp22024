package com.example.application22024.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.application22024.DatabaseHelper;
import com.example.application22024.R;
import com.example.application22024.adapter.JobAdapter;
import com.example.application22024.model.Job;

import java.util.List;

public class Page1 extends Fragment {

    private RecyclerView recyclerView,recyclerView2;
    private JobAdapter jobAdapter;
    private DatabaseHelper databaseHelper;
    private LinearLayout selectLocation;
    private TextView textViewLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1, container, false);

        selectLocation = view.findViewById(R.id.selectLocation);
        textViewLocation = view.findViewById(R.id.location); // TextView để hiển thị địa chỉ
        selectLocation.setOnClickListener(v -> openMap());

        recyclerView = view.findViewById(R.id.SuggestedJob);
        recyclerView2 = view.findViewById(R.id.recentJob);


        databaseHelper = new DatabaseHelper(getContext());
        List<Job> jobList = databaseHelper.getAllJobs();

        jobAdapter = new JobAdapter(getContext(), jobList);
        recyclerView.setAdapter(jobAdapter);

        ViewPager2 viewPager = getActivity().findViewById(R.id.viewPager);
        setupRecyclerView(recyclerView, viewPager);
        setupRecyclerView(recyclerView2, viewPager);
        return view;
    }


    private void openMap() {
        // nhập vị trí cụ thể hoặc chọn trên bản đồ
    }



    private void setupRecyclerView(RecyclerView recyclerView, ViewPager2 viewPager) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewPager.setUserInputEnabled(false); // Tắt vuốt ViewPager2 khi bắt đầu cuộn
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        viewPager.setUserInputEnabled(true);  // Bật lại vuốt ViewPager2 khi cuộn xong
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                // Không cần xử lý
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                // Không cần xử lý
            }
        });
    }

}
