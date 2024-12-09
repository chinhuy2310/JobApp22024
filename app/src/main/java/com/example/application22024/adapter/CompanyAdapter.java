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

import com.example.application22024.APIService;
import com.example.application22024.MyApplication;
import com.example.application22024.R;
import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.employer.RegistrationActivity;
import com.example.application22024.model.Company;
import com.example.application22024.model.Job;
import com.example.application22024.model.DataViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private Context context;
    private List<Company> companyList;
    ImageView companyImageView;
    DataViewModel viewModel;
    APIService apiService;

    // Constructor
    public CompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList != null ? companyList : new ArrayList<>();
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        viewModel = ((MyApplication) context.getApplicationContext()).getDataViewModel();

    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.e("AdapterDebug", "onCreateViewHolder called");
        View view = LayoutInflater.from(context).inflate(R.layout.company, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
//        Log.e("AdapterDebug", "onBindViewHolder for position: " + position);
        Company company = companyList.get(position);

        holder.companyNameTextView.setText(company.getCompanyName());
        holder.cityTextView.setText(company.getAddress());
        holder.jobPositionsTextView.setText(String.valueOf(company.getJobCount()));
        holder.numberOfApplicants.setText("모든 지원자 수: "+String.valueOf(company.getTotal_applicants()));
        // Load the image using Picasso6
        if (company.getCompanyIamge() != null) {
            String baseUrl = "http://192.168.0.3:3000"; // Địa chỉ gốc
            String relativePath = company.getCompanyIamge();
            String fullImageUrl = baseUrl + relativePath; // Ghép URL đầy đủ
            Picasso.get().load(fullImageUrl).into(holder.companyImageView);
        } else {
            Picasso.get().load(R.drawable.ic_launcher_background).into(holder.companyImageView);
        }
        // Hiển thị hoặc ẩn danh sách bài đăng tuyển
        if (company.isExpanded()) {
            holder.jobListView.setVisibility(View.VISIBLE);
            holder.expandTextView.setText(R.string.expand_text_open);
            if (company.getJobs() == null) { // Chỉ gọi API nếu jobs chưa được tải
                // Gọi API để lấy danh sách job từ server
                fetchJobsForCompany(company, holder);
            } else {
                // Hiển thị danh sách job đã có
                displayJobsInRecyclerView(holder, company);
            }
        } else {
            holder.expandTextView.setText(R.string.expand_text_close);
            holder.jobListView.setVisibility(View.GONE);
            holder.noJobPostsTextView.setVisibility(View.GONE); // Ẩn thông báo nếu công ty không mở rộng
        }
        // Xử lý sự kiện click để mở rộng hoặc thu gọn
        holder.itemView.setOnClickListener(v -> {
            viewModel.setSelectedCompany(company);
//            Log.e("selectedCompany", company.getCompanyName());
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
            if (newExpandedState) {
                RecyclerView recyclerView = ((RecyclerView) holder.itemView.getParent());
                recyclerView.scrollToPosition(position);
            }
        });

        // Set icon or click listeners (optional)
        holder.optionsImageView.setOnClickListener(v -> showPopupMenu(v, position));

    }

    private void fetchJobsForCompany(Company company, CompanyViewHolder holder) {
        apiService.getJobsForCompany(company.getCompanyId()).enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Job> jobs = response.body();
                    company.setJobs(jobs); // Cập nhật danh sách jobs vào công ty
                    displayJobsInRecyclerView(holder, company);
                } else {
                    holder.noJobPostsTextView.setVisibility(View.VISIBLE); // Hiển thị thông báo "Chưa có bài đăng tuyển dụng"
                    holder.jobListView.setVisibility(View.GONE); // Ẩn RecyclerView
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Toast.makeText(context, "Failed to load jobs: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                holder.noJobPostsTextView.setVisibility(View.VISIBLE);
                holder.jobListView.setVisibility(View.GONE); // Ẩn RecyclerView
            }
        });
    }

    private void displayJobsInRecyclerView(CompanyViewHolder holder, Company company) {
        holder.noJobPostsTextView.setVisibility(View.GONE); // Ẩn thông báo
        holder.jobListView.setVisibility(View.VISIBLE); // Hiển thị RecyclerView

        holder.jobListView.setLayoutManager(new LinearLayoutManager(context));
        RecruitmentAdapter recruitmentAdapter = new RecruitmentAdapter(context, company.getJobs());

        holder.jobListView.setAdapter(recruitmentAdapter);


    }

    private void showPopupMenu(View v, int position) {
        // Kiểm tra xem position có hợp lệ không
        if (position < 0 || position >= companyList.size()) {
//            Log.e("CompanyAdapter", "Invalid position: " + position);
            return;  // Không thực hiện hành động nào nếu position không hợp lệ
        }
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.options_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.addNewRecuitment) {
                // Khi chọn "Thêm tuyển dụng", chuyển sang AddRecruitmentActivity
                Intent intent = new Intent(context, RegistrationActivity.class);
                viewModel.setSelectedCompany(companyList.get(position));
//                Log.e("selectedCompany", viewModel.getSelectedCompany().getCompanyName());
                context.startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.edit_company) {
                // Handle edit company action
                Toast.makeText(context, "Edit: " + companyList.get(position).getCompanyName(), Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.delete_company) {
//                Company companyToDelete = companyList.get(position);
//                Log.e("", String.valueOf(companyToDelete.getCompanyId()));
//                deleteCompany(companyToDelete, position);
                showDeleteConfirmationDialog(companyList.get(position), position);
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
    private void showDeleteConfirmationDialog(Company company, int position) {
        // Tạo một AlertDialog để xác nhận xóa công ty
        new android.app.AlertDialog.Builder(context)
                .setMessage("Are you sure you want to delete the company " + company.getCompanyName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Nếu người dùng chọn "Xóa", gọi hàm deleteCompany()
                    deleteCompany(company, position);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Nếu người dùng chọn "Hủy", không làm gì cả
                    dialog.dismiss();
                })
                .setCancelable(false)  // Không cho phép người dùng đóng dialog khi nhấn ra ngoài
                .show();
    }

    private void deleteCompany(Company company, int position) {
        // Gọi API để xóa công ty
        apiService.deleteCompany(company.getCompanyId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Nếu xóa thành công, thông báo cho người dùng
                    Toast.makeText(context, "Deleted: " + company.getCompanyName(), Toast.LENGTH_SHORT).show();

                    // Xóa công ty khỏi danh sách
                    companyList.remove(position);
                    notifyItemRemoved(position); // Thông báo cho adapter biết đã xóa item
                    notifyItemRangeChanged(position, companyList.size());
                    viewModel.reset();
                    // Cập nhật giao diện nếu không còn công ty nào
                    if (companyList.isEmpty()) {
                        Toast.makeText(context, "No companies left.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu có lỗi khi xóa
                    Toast.makeText(context, "Failed to delete company. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Nếu có lỗi kết nối với API
                Toast.makeText(context, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
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
        TextView numberOfApplicants;
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
            numberOfApplicants =itemView.findViewById(R.id.numberOfApplicants);
        }
    }
}
