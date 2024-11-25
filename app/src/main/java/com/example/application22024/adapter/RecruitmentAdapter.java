package com.example.application22024.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.JobDetails;
import com.example.application22024.R;
import com.example.application22024.model.Job;

import java.util.List;

public class RecruitmentAdapter extends RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder> {

    private Context context;
    private List<Job> recruitmentList;

    // Constructor
    public RecruitmentAdapter(Context context, List<Job> recruitmentList) {
        this.context = context;
        this.recruitmentList = recruitmentList;
    }

    @NonNull
    @Override
    public RecruitmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.e("AdapterDebug", "onCreateViewHolder called");
        View view = LayoutInflater.from(context).inflate(R.layout.job_post_in_imployer, parent, false);
        return new RecruitmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitmentViewHolder holder, int position) {
        Job recruitment = recruitmentList.get(position);

        // Bind data to the views
        holder.jobTitleTextView.setText(recruitment.getJobName());
        holder.salaryTextView.setText(recruitment.getSalary());
        holder.locationTextView.setText(recruitment.getLocation());
//        Log.e("RecruitmentData", "Job: " + recruitment.getJobName());

        // Xử lý sự kiện click để chuyển sang trang chi tiết của công việc
        holder.itemView.setOnClickListener(v -> {
            // Chuyển đến trang chi tiết công việc
            Intent intent = new Intent(context, JobDetails.class);
            // Truyền thông tin chi tiết về công việc (ví dụ: tên công việc, lương, địa điểm)
//            intent.putExtra("jobTitle", recruitment.getJobName());
//            intent.putExtra("salary", recruitment.getSalary());
//            intent.putExtra("location", recruitment.getLocation());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return recruitmentList.size();
    }

    public static class RecruitmentViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTextView;
        TextView salaryTextView;
        TextView locationTextView;

        public RecruitmentViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}
