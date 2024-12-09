package com.example.application22024.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.application22024.APIService;
import com.example.application22024.JobDetails;
import com.example.application22024.MyApplication;
import com.example.application22024.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.RetrofitClientInstance;
import com.example.application22024.SharedPrefManager;
import com.example.application22024.model.CompanyJobItem;
import com.example.application22024.model.DataViewModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Page1AndSearchAdapter extends RecyclerView.Adapter<Page1AndSearchAdapter.ViewHolder> {

    private List<CompanyJobItem> companyJobItems;
    private Context context;
    private DataViewModel viewModel;
    private APIService apiService;
    private int layoutType;

    // Map để ánh xạ salaryType với màu sắc
    private static final Map<String, Integer> SALARY_TYPE_COLORS = new HashMap<>();

    static {
        SALARY_TYPE_COLORS.put("시급", Color.parseColor("#f76211"));
        SALARY_TYPE_COLORS.put("일당", Color.parseColor("#11bef7"));
        SALARY_TYPE_COLORS.put("월급", Color.parseColor("#ef2cf2"));
        SALARY_TYPE_COLORS.put("연봉", Color.parseColor("#4a43fa"));
    }

    // Mặc định màu
    private static final int DEFAULT_COLOR = Color.parseColor("#000000");
    private static final int BACKGROUND_COLOR = Color.parseColor("#FFFFFF");

    public Page1AndSearchAdapter(Context context, List<CompanyJobItem> companyJobItems, int layoutType) {
        this.context = context;
        this.companyJobItems = companyJobItems;
        this.layoutType = layoutType;
        viewModel = ((MyApplication) context.getApplicationContext()).getDataViewModel();
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutRes = layoutType == 1 ? R.layout.item_on_search : R.layout.item_on_page1;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyJobItem item = companyJobItems.get(position);
        bindJobDetails(holder, item);
        bindSalaryType(holder, item);
        bindBookmark(holder, item, position);
    }

    private void bindJobDetails(ViewHolder holder, CompanyJobItem item) {
        // Gán các thông tin công ty và công việc vào View
        holder.companyNameTextView.setText(item.getCompany_name());
        holder.companyAddressTextView.setText(item.getAddress());
        holder.jobTitleTextView.setText(item.getTitle());
        holder.jobSalaryTextView.setText(String.format("%,d ₩", item.getSalary()));

        if (item.getCompany_image() != null) {
            String fullImageUrl = "http://192.168.0.3:3000" + item.getCompany_image();
            Picasso.get().load(fullImageUrl).into(holder.comapnyImage);
            holder.comapnyImage.setVisibility(View.VISIBLE);
        } else {
            holder.comapnyImage.setVisibility(View.GONE);
        }

        if (layoutType == 0) {
            holder.workField.setText(item.getWorkField());
            holder.workType.setText(item.getWorkType());
            holder.period.setText(item.getWorkPeriod());
        }
    }

    private void bindSalaryType(ViewHolder holder, CompanyJobItem item) {
        String salaryType = item.getSalaryType();
        int color = SALARY_TYPE_COLORS.getOrDefault(salaryType, DEFAULT_COLOR);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(16);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//        }
        if (layoutType == 1) {
            drawable.setStroke(1, color);
        }
        drawable.setColor(BACKGROUND_COLOR);

        holder.jobSalaryTypeTextView.setTextColor(color);
        holder.jobSalaryTypeTextView.setText(salaryType);
        holder.jobSalaryTypeTextView.setBackground(drawable);
    }

    private void bindBookmark(ViewHolder holder, CompanyJobItem item, int position) {
        int saved = item.getIs_saved();
        holder.bookmarkImageView.setImageResource(saved == 1 ? R.drawable.ic_bookmark2 : R.drawable.ic_bookmark);

        holder.bookmarkImageView.setOnClickListener(v -> {
            int newSavedStatus = saved == 1 ? 0 : 1;
            item.setIs_saved(newSavedStatus);
            holder.bookmarkImageView.setImageResource(newSavedStatus == 1 ? R.drawable.ic_bookmark2 : R.drawable.ic_bookmark);
            notifyItemChanged(position);
            updateBookmarkStatus(item);
        });

        holder.itemView.setOnClickListener(v -> {
            viewModel.setSelectedCompanyJobItem(item);
            Intent intent = new Intent(context, JobDetails.class);
            intent.putExtra("userType", "Employee");
            context.startActivity(intent);
            saveRecentlyViewed(item);
        });
    }

    private void updateBookmarkStatus(CompanyJobItem item) {
        int userId = SharedPrefManager.getInstance(context).getUserId();
        int jobId = item.getJob_id();
        apiService.updateBookmarkStatus(userId, jobId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("Bookmark", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Bookmark", "Network error: " + t.getMessage());
            }
        });
    }

    private void saveRecentlyViewed(CompanyJobItem item) {
        int userId = SharedPrefManager.getInstance(context).getUserId();
        int jobId = item.getJob_id();
        apiService.saveRecentlyViewed(userId, jobId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Xử lý thành công
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("RecentlyViewed", "Network error: " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyJobItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyNameTextView, companyAddressTextView, jobTitleTextView, jobSalaryTextView, jobSalaryTypeTextView, workField, workType, period;
        ImageView bookmarkImageView, comapnyImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.companyName);
            companyAddressTextView = itemView.findViewById(R.id.workLocation);
            jobTitleTextView = itemView.findViewById(R.id.jobTitle);
            jobSalaryTextView = itemView.findViewById(R.id.jobSalary);
            jobSalaryTypeTextView = itemView.findViewById(R.id.salaryType);
            bookmarkImageView = itemView.findViewById(R.id.bookmark);
            comapnyImage = itemView.findViewById(R.id.companyLogo);
            workField = itemView.findViewById(R.id.WorkField);
            workType = itemView.findViewById(R.id.workType);
            period = itemView.findViewById(R.id.period);
        }
    }
}
