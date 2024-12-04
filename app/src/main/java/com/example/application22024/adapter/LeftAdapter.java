package com.example.application22024.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {

    private final List<String> provinces;
    private final OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(String province);
    }

    public LeftAdapter(List<String> provinces, OnItemClickListener listener) {
        this.provinces = provinces;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String province = provinces.get(position);
        holder.textView.setText(province);

        // Lấy context và resources để tái sử dụng
        View itemView = holder.itemView;

        int selectedTextColor = 0xFFC8A8E9; // Màu chữ cho mục được chọn (#C8A8E9)
        int defaultTextColor = holder.itemView.getContext().getResources().getColor(android.R.color.black); // Màu chữ mặc định

        // Kiểm tra nếu mục hiện tại là mục được chọn
        if (position == selectedPosition) {
            holder.textView.setTextColor(selectedTextColor); // Màu chữ cho mục được chọn
        } else {
            holder.textView.setTextColor(defaultTextColor); // Màu chữ mặc định
        }

        // Xử lý sự kiện click để thay đổi lựa chọn
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition(); // Cập nhật vị trí được chọn

            // Chỉ thông báo thay đổi nếu có sự thay đổi về vị trí
            if (previousPosition != selectedPosition) {
                notifyItemChanged(previousPosition); // Làm mới mục cũ
                notifyItemChanged(selectedPosition); // Làm mới mục mới
            }

            listener.onItemClick(province); // Gọi listener
        });
    }
    public void setSelectedPosition(int position) {
        int previousSelectedPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousSelectedPosition); // Làm mới mục cũ
        notifyItemChanged(selectedPosition); // Làm mới mục mới
    }
    @Override
    public int getItemCount() {
        return provinces.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
