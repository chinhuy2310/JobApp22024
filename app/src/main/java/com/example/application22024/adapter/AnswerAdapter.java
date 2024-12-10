package com.example.application22024.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private List<String> answerList;

    public AnswerAdapter(List<String> answerList) {
        this.answerList = answerList;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        holder.answerText.setText(answerList.get(position));
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView answerText;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            answerText = itemView.findViewById(android.R.id.text1);
        }
    }
}
