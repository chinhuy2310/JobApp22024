package com.example.application22024.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.JobDetails;
import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.model.Job;
import com.example.application22024.model.RegistrationViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecruitmentAdapter extends RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder> {

    private Context context;
    private List<Job> jobList;
    RegistrationViewModel viewModel;
    // Constructor
    public RecruitmentAdapter(Context context, List<Job> jobList) {
        this.context = context;
        this.jobList = jobList;
        viewModel =((MyApplication) context.getApplicationContext()).getRegistrationViewModel();
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
        Job recruitment = jobList.get(position);

        // Bind data to the views
        holder.jobTitleTextView.setText(recruitment.getTitle());
        holder.salaryTextView.setText(String.valueOf(recruitment.getSalary()));
        holder.salaryType.setText(recruitment.getSalaryType());
        holder.recruitmentDeadline.setText(recruitment.getRecruitmentEnd());
        holder.startTime.setText(formatTimeToHoursAndMinutes(recruitment.getWorkHoursStart()));
        holder.endTime.setText(formatTimeToHoursAndMinutes(recruitment.getWorkHoursEnd()));


        // Xử lý sự kiện click để chuyển sang trang chi tiết của công việc
        holder.itemView.setOnClickListener(v -> {

            // Lưu công việc vào ViewModel
            viewModel.setSelectedJob(recruitment);

            // Chuyển đến trang chi tiết công việc
            Intent intent = new Intent(context, JobDetails.class);

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class RecruitmentViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTextView;
        TextView salaryTextView;
        TextView recruitmentDeadline;
        TextView startTime;
        TextView endTime;
        TextView salaryType;

        public RecruitmentViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            salaryTextView = itemView.findViewById(R.id.salary);
            recruitmentDeadline = itemView.findViewById(R.id.recuirmentendtime);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            salaryType = itemView.findViewById(R.id.salaryType);
        }
    }
    private String formatTimeToHoursAndMinutes(String time) {
        try {
            // Định dạng chuỗi thời gian đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(time);

            // Định dạng lại chỉ hiển thị giờ và phút
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return time;  // Nếu có lỗi, trả về thời gian ban đầu
        }
    }
}
