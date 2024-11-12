package com.example.application22024.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.DatabaseHelper;
import com.example.application22024.R;
import com.example.application22024.adapter.JobAdapter;
import com.example.application22024.model.Job;

import java.util.List;

public class Page1 extends Fragment {

    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.page1, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.SuggestedJob);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        // Get the job list from the database
        List<Job> jobList = databaseHelper.getAllJobs();

        // Initialize the adapter with the job list
        jobAdapter = new JobAdapter(getContext(), jobList);
        // Thiết lập LayoutManager cho RecyclerView theo chiều ngang
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(jobAdapter);

        return view;
    }
}
