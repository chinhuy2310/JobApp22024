package com.example.application22024.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.R;
import com.example.application22024.model.CompanyJobItem;
import com.example.application22024.model.Job;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {
    private List<CompanyJobItem> appliedJobs;

    public ApplicationAdapter(List<CompanyJobItem> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applied_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompanyJobItem job = appliedJobs.get(position);

        // 직업 제목 설정
        holder.titleTextView.setText(job.getTitle());

        // 회사 이름 설정 (Company 객체에서 꺼냄)
        holder.companyTextView.setText(job.getCompany_name());

        // 게시 날짜 설정
        holder.dateTextView.setText(job.getPostDate());
    }

    @Override
    public int getItemCount() {
        return appliedJobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView companyTextView;
        public TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            companyTextView = itemView.findViewById(R.id.companyTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
