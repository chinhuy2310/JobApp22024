package com.example.application22024.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.R;
import com.example.application22024.employer.RegistrationActivity;
import com.example.application22024.model.Company;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private Context context;
    private List<Company> companyList;


    // Constructor
    public CompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("AdapterDebug", "onCreateViewHolder called");
        View view = LayoutInflater.from(context).inflate(R.layout.company, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Log.e("AdapterDebug", "onBindViewHolder for position: " + position);

        Company company = companyList.get(position);

        holder.companyNameTextView.setText(company.getCompanyName());
        holder.cityTextView.setText(company.getAddress());
        holder.jobPositionsTextView.setText(String.valueOf(company.getJobCount()));
        Log.e("RecruitmentData", "Jobs: " + company.getJobs());
        // Hiển thị hoặc ẩn danh sách bài đăng tuyển
        if (company.isExpanded()) {
            holder.jobListView.setVisibility(View.VISIBLE);
            holder.expandTextView.setText("≙");
            if (company.getJobs() == null || company.getJobs().isEmpty()) {
                holder.noJobPostsTextView.setVisibility(View.VISIBLE); // Hiển thị thông báo "Chưa có bài đăng tuyển dụng"
                holder.jobListView.setVisibility(View.GONE); // Ẩn RecyclerView
            } else {
                holder.noJobPostsTextView.setVisibility(View.GONE); // Ẩn thông báo
                holder.jobListView.setVisibility(View.VISIBLE); // Hiển thị RecyclerView
                RecruitmentAdapter recruitmentAdapter = new RecruitmentAdapter(context, company.getJobs());
                holder.jobListView.setLayoutManager(new LinearLayoutManager(context));
                holder.jobListView.setAdapter(recruitmentAdapter);
            }
        } else {
            holder.expandTextView.setText("≚");
            holder.jobListView.setVisibility(View.GONE);
            holder.noJobPostsTextView.setVisibility(View.GONE); // Ẩn thông báo nếu công ty không mở rộng
        }
        // Xử lý sự kiện click để mở rộng hoặc thu gọn
        holder.itemView.setOnClickListener(v -> {
            boolean newExpandedState = !company.isExpanded();
            company.setExpanded(newExpandedState);
            // Nếu công ty được mở rộng, thu gọn tất cả các công ty khác
            if (newExpandedState) {
                // Duyệt qua tất cả các công ty và thu gọn các công ty khác
                for (int i = 0; i < companyList.size(); i++) {
                    if (i != position) {
                        companyList.get(i).setExpanded(false); // Thu gọn các công ty khác
                        notifyItemChanged(i); // Chỉ cần thông báo thay đổi cho các công ty bị thu gọn
                    }
                }
            }

            notifyItemChanged(position);
        });

        // Set icon or click listeners (optional)
        holder.optionsImageView.setOnClickListener(v -> showPopupMenu(v, position));

    }

    private void showPopupMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.options_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.addNewRecuitment) {
                // Khi chọn "Thêm tuyển dụng", chuyển sang AddRecruitmentActivity
                Intent intent = new Intent(context, RegistrationActivity.class);
                // Truyền dữ liệu, ví dụ: truyền tên công ty
                intent.putExtra("companyName", companyList.get(position).getCompanyName());
                context.startActivity(intent);

//                // Nhận dữ liệu từ Intent trong activity:
//   //             Intent intent = getIntent();
////                String companyName = intent.getStringExtra("companyName");
////
////                // Hiển thị tên công ty trong TextView
////                if (companyName != null) {
////                    companyNameTextView.setText("Company: " + companyName);
//

                return true;
            } else if (item.getItemId() == R.id.edit_company) {
                // Handle edit company action
                Toast.makeText(context, "Edit: " + companyList.get(position).getCompanyName(), Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.delete_company) {
                // Handle delete company action
//                companyList.remove(position);
//                notifyItemRemoved(position);
                Toast.makeText(context, "Deleted: " + companyList.get(position).getCompanyName(), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public void updateData(List<Company> companyList) {
        this.companyList = companyList; // Cập nhật dữ liệu trong adapter
        notifyDataSetChanged(); // Thông báo dữ liệu đã thay đổi
    }


    // ViewHolder class
    public static class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView companyNameTextView;
        TextView cityTextView;
        TextView jobPositionsTextView;
        TextView noJobPostsTextView;
        TextView expandTextView;
        ImageView companyImageView;
        ImageView optionsImageView;
        RecyclerView jobListView;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);

            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            jobPositionsTextView = itemView.findViewById(R.id.jobPositionsTextView);
            companyImageView = itemView.findViewById(R.id.companyImageView);
            optionsImageView = itemView.findViewById(R.id.optionsImageView);
            jobListView = itemView.findViewById(R.id.jobListView);
            noJobPostsTextView = itemView.findViewById(R.id.noJobPostsTextView);
            expandTextView = itemView.findViewById(R.id.expandTextView);
        }
    }
}