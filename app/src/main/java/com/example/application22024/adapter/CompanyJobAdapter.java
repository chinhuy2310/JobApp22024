package com.example.application22024.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.application22024.JobDetails;
import com.example.application22024.MyApplication;
import com.example.application22024.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.model.Company;
import com.example.application22024.model.CompanyJobItem;
import com.example.application22024.model.Job;
import com.example.application22024.model.RegistrationViewModel;

import java.util.List;
public class CompanyJobAdapter extends RecyclerView.Adapter<CompanyJobAdapter.ViewHolder> {

    private List<CompanyJobItem> companyJobItems;
    private Context context; // Context để khởi chạy Activity
    RegistrationViewModel viewModel;


    public CompanyJobAdapter(Context context, List<CompanyJobItem> companyJobItems) {
        this.context = context;
        this.companyJobItems = companyJobItems;
        viewModel =((MyApplication) context.getApplicationContext()).getRegistrationViewModel();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_in_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyJobItem item = companyJobItems.get(position);

        // Gán dữ liệu vào các view trong item
        holder.companyNameTextView.setText(item.getCompany_name());
        holder.companyAddressTextView.setText(item.getAddress());
        holder.jobTitleTextView.setText(item.getTitle());
        holder.jobSalaryTextView.setText(String.valueOf(item.getSalary()) + " ₩");
        holder.jobSalaryTypeTextView.setText(item.getSalaryType());

        holder.itemView.setOnClickListener(v -> {
            viewModel.setSelectedCompanyJobItem(companyJobItems.get(position));
            Intent intent = new Intent(context, JobDetails.class);
            intent.putExtra("userType", "Employee");
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return companyJobItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyNameTextView;
        TextView companyAddressTextView;
        TextView jobTitleTextView;
        TextView jobSalaryTextView;
        TextView jobSalaryTypeTextView;
        ImageView bookmarkImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.companyName);
            companyAddressTextView = itemView.findViewById(R.id.workLocation);
            jobTitleTextView = itemView.findViewById(R.id.jobTitle);
            jobSalaryTextView = itemView.findViewById(R.id.jobSalary);
            jobSalaryTypeTextView = itemView.findViewById(R.id.salaryType);
            bookmarkImageView = itemView.findViewById(R.id.bookmark);
        }
    }
}
