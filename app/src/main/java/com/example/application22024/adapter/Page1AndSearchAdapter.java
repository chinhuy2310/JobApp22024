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
import com.example.application22024.model.RegistrationViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Page1AndSearchAdapter extends RecyclerView.Adapter<Page1AndSearchAdapter.ViewHolder> {

    private List<CompanyJobItem> companyJobItems;
    private Context context; // Context để khởi chạy Activity
    RegistrationViewModel viewModel;
    APIService apiService;
    private int layoutType;
    public Page1AndSearchAdapter(Context context, List<CompanyJobItem> companyJobItems, int layoutType) {
        this.context = context;
        this.companyJobItems = companyJobItems;
        this.layoutType = layoutType;
        viewModel = ((MyApplication) context.getApplicationContext()).getRegistrationViewModel();
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (layoutType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_search, parent, false);}
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_page1, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyJobItem item = companyJobItems.get(position);

        if (item.getCompany_image() != null) {
            holder.comapnyImage.setVisibility(View.VISIBLE);
            String baseUrl = "http://10.0.2.2:3000"; // Địa chỉ gốc
            String relativePath = item.getCompany_image();
            String fullImageUrl = baseUrl + relativePath; // Ghép URL đầy đủ
            Picasso.get().load(fullImageUrl).into(holder.comapnyImage);
//            Log.e("imageUrl", fullImageUrl);
        } else {
            // Load a default image if the avatar URL is empty
//            Picasso.get().load(R.drawable.ic_launcher_background).into(holder.comapnyImage);
            holder.comapnyImage.setVisibility(View.GONE);
        }

        if (layoutType == 0) {
            holder.workField.setText(companyJobItems.get(position).getWorkField());
            holder.workType.setText(companyJobItems.get(position).getWorkType());
            holder.period.setText(companyJobItems.get(position).getWorkPeriod());
        } else {
            Log.e("layoutType", "layoutType is not 0");
        }


        // Gán dữ liệu vào các view trong item
        holder.companyNameTextView.setText(item.getCompany_name());
        holder.companyAddressTextView.setText(item.getAddress());
        holder.jobTitleTextView.setText(item.getTitle());
        int number = item.getSalary();
        String formattedNumber = String.format("%,d", number) + " ₩";
        holder.jobSalaryTextView.setText(formattedNumber);



        // Tạo một GradientDrawable để làm nền và stroke cho TextView
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // Chọn hình dạng chữ nhật
        drawable.setCornerRadius(16);  // Đặt bán kính góc, có thể bỏ qua nếu không cần bo góc

        // Giả sử bạn có một chuỗi để so sánh
        String value = item.getSalaryType();  // Bạn có thể thay đổi tùy theo dữ liệu bạn muốn so sánh

        // Kiểm tra giá trị chuỗi và thay đổi màu stroke và màu chữ
        if (value.equals("시급")) {
            // Nếu chuỗi là "a", màu stroke sẽ là xanh và màu nền sẽ là sáng
            drawable.setStroke(1, Color.parseColor("#f76211"));  // Màu stroke xanh
//            drawable.setColor(Color.parseColor("#E0E0FF"));  // Màu nền sáng xanh
            holder.jobSalaryTypeTextView.setTextColor(Color.parseColor("#f76211"));  // Màu chữ xanh
        } else if (value.equals("일급")) {
            // Nếu chuỗi là "b", màu stroke sẽ là vàng và màu nền sẽ là sáng
            drawable.setStroke(1, Color.parseColor("#11bef7"));  // Màu stroke vàng
//            drawable.setColor(Color.parseColor("#FFF9C4"));  // Màu nền sáng vàng
            holder.jobSalaryTypeTextView.setTextColor(Color.parseColor("#11bef7"));  // Màu chữ vàng
        } else if (value.equals("월급")) {
            // Nếu chuỗi là "c", màu stroke sẽ là đỏ và màu nền sẽ là sáng
            drawable.setStroke(1, Color.parseColor("#ef2cf2"));  // Màu stroke đỏ
//            drawable.setColor(Color.parseColor("#FFCDD2"));  // Màu nền sáng đỏ
            holder.jobSalaryTypeTextView.setTextColor(Color.parseColor("#ef2cf2"));  // Màu chữ đỏ
        } else if (value.equals("연봉")) {
            // Nếu chuỗi là "c", màu stroke sẽ là đỏ và màu nền sẽ là sáng
            drawable.setStroke(1, Color.parseColor("#4a43fa"));  // Màu stroke đỏ
//            drawable.setColor(Color.parseColor("#FFCDD2"));  // Màu nền sáng đỏ
            holder.jobSalaryTypeTextView.setTextColor(Color.parseColor("#4a43fa"));  // Màu chữ đỏ
        } else {
            // Nếu chuỗi không phải là "a", "b" hoặc "c", sử dụng màu mặc định
            drawable.setStroke(1, Color.parseColor("#000000"));  // Màu stroke đen
            drawable.setColor(Color.parseColor("#FFFFFF"));  // Màu nền trắng
            holder.jobSalaryTypeTextView.setTextColor(Color.parseColor("#000000"));  // Màu chữ đen
        }

        // Áp dụng GradientDrawable làm background cho TextView
        holder.jobSalaryTypeTextView.setText(item.getSalaryType());
        holder.jobSalaryTypeTextView.setBackground(drawable);


        int saved = item.getIs_saved();
        holder.bookmarkImageView.setImageResource(saved == 1 ? R.drawable.ic_bookmark2 : R.drawable.ic_bookmark);
        holder.bookmarkImageView.setOnClickListener(v -> {
            // Cập nhật trạng thái lưu và gửi yêu cầu API
            int newSavedStatus = saved == 1 ? 0 : 1; // Nếu đang lưu thì xóa, nếu chưa lưu thì lưu
            item.setIs_saved(newSavedStatus); // Cập nhật lại trạng thái trong item

            // Cập nhật hình ảnh bookmark
            holder.bookmarkImageView.setImageResource(newSavedStatus == 1 ? R.drawable.ic_bookmark2 : R.drawable.ic_bookmark);
            notifyItemChanged(position);  // Cập nhật lại item tại vị trí hiện tại

            // Gửi yêu cầu API để lưu hoặc xóa bài viết
            updateBookmarkStatus(item); // Cập nhật trạng thái lên server (API)
        });


        holder.itemView.setOnClickListener(v -> {
            viewModel.setSelectedCompanyJobItem(companyJobItems.get(position));
            Intent intent = new Intent(context, JobDetails.class);
            intent.putExtra("userType", "Employee");
            context.startActivity(intent);
        });


    }

    private void updateBookmarkStatus(CompanyJobItem item) {
        int userId = SharedPrefManager.getInstance(context).getUserId(); // Lấy userId từ SharedPreferences
//        Log.e("Bookmark", "User ID: " + userId);
        int jobId = item.getJob_id(); // ID của công việc
        apiService.updateBookmarkStatus(userId, jobId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Xử lý thành công (Thông báo cho người dùng, hoặc cập nhật giao diện)
//                            Log.d("Bookmark", "Bookmark updated successfully!");
                        } else {
                            // Xử lý lỗi phản hồi không thành công
                            Log.e("Bookmark", "Response code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Xử lý khi có lỗi kết nối (Ví dụ: mạng không ổn định)
                        Log.e("Bookmark", "Network error: " + t.getMessage());
                    }
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
        ImageView bookmarkImageView,comapnyImage;
        TextView workField, workType, period;

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
