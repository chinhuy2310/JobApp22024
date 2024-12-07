package com.example.application22024.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.application22024.MyApplication;
import com.example.application22024.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.application22024.employee.Profile;
import com.example.application22024.model.Applicant;
import com.example.application22024.model.CircleTransform;
import com.example.application22024.model.DataViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantViewHolder> {

    private List<Applicant> applicants;
    private DataViewModel viewModel;
    private Context context;

    // Thêm Context vào constructor
    public ApplicantAdapter(Context context, List<Applicant> applicants) {
        this.context = context;
        this.applicants = applicants;
        viewModel = ((MyApplication) context.getApplicationContext()).getDataViewModel(); // Truyền context
    }

    @NonNull
    @Override
    public ApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicants, parent, false);
        return new ApplicantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantViewHolder holder, int position) {
        Applicant applicant = applicants.get(position);
        holder.textViewName.setText(applicant.getFull_name());
        holder.textViewGender.setText(applicant.getGender());
        holder.textViewAge.setText(String.valueOf(applicant.getAge()) + "세");
        holder.textViewContact.setText(applicant.getPhone_number());
        String baseUrl = "http://10.0.2.2:3000"; // Địa chỉ gốc
        String relativePath = applicant.getAvatar_url();
        String fullImageUrl = baseUrl + relativePath; // Ghép URL đầy đủ
        if (!applicant.getAvatar_url().isEmpty()) {
            Picasso.get().load(fullImageUrl)
                    .transform(new CircleTransform())
                    .into(holder.employeeAvatar);
        } else {
            // Load a default image if the avatar URL is empty
            Picasso.get().load(R.drawable.ic_launcher_background).into(holder.employeeAvatar);
        }
        holder.itemView.setOnClickListener(v -> {
            viewModel.setSelectedApplicant(applicant);
            Intent intent = new Intent(context, Profile.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return applicants.size();
    }

    public static class ApplicantViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewGender, textViewAge, textViewContact;
        ImageView employeeAvatar;

        public ApplicantViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.employeeName);
            textViewGender = itemView.findViewById(R.id.employeeGender);
            textViewAge = itemView.findViewById(R.id.employeeAge);
            textViewContact = itemView.findViewById(R.id.employeeContact);
            employeeAvatar = itemView.findViewById(R.id.employeeAvatar);
        }
    }
}
