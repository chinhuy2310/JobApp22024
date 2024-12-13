package com.example.application22024.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.R;
import com.example.application22024.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.messageContent.setText(message.getContent());

        // Căn chỉnh tin nhắn tùy thuộc vào loại (Sent hoặc Received)
        if ("Sent".equals(message.getType())) {
            // Căn phải cho tin nhắn gửi đi
            holder.messageContainer.setGravity(Gravity.END);
            holder.messageContent.setBackgroundResource(R.drawable.sent_message_background);
        } else {
            // Căn trái cho tin nhắn nhận được
            holder.messageContainer.setGravity(Gravity.START);
            holder.messageContent.setBackgroundResource(R.drawable.received_message_background);
        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageContent;
        LinearLayout messageContainer;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.messageContent);
            messageContainer = itemView.findViewById(R.id.messageContainer);
        }
    }

}