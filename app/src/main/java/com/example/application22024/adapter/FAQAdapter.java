package com.example.application22024.adapter;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.application22024.R;
import com.example.application22024.model.FAQ;

import java.util.List;
public class FAQAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FAQ> messages;
    private OnButtonClickListener listener;

    // Interface để xử lý khi người dùng nhấn vào nút
    public interface OnButtonClickListener {
        void onButtonClick(String buttonText);
    }

    public FAQAdapter(List<FAQ> messages, OnButtonClickListener listener) {
        this.messages = messages;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bot_message, parent, false);
            return new BotMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new UserMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FAQ message = messages.get(position);

        if (holder instanceof BotMessageViewHolder) {
            BotMessageViewHolder botHolder = (BotMessageViewHolder) holder;
            botHolder.textView.setText(message.getText());
            botHolder.buttonsLayout.removeAllViews();

            // Kiểm tra xem tin nhắn này có phải là tin nhắn bot mới nhất không
            // Nếu là tin nhắn bot mới nhất thì hiển thị nút
            if (position == messages.size() - 1) {
                // Thêm các nút vào tin nhắn bot mới nhất
                for (String button : message.getButtons()) {
                    Button btn = new Button(botHolder.itemView.getContext());
                    btn.setText(button);
                    btn.setOnClickListener(v -> listener.onButtonClick(button));
                    botHolder.buttonsLayout.addView(btn);
                }
            }
        } else if (holder instanceof UserMessageViewHolder) {
            UserMessageViewHolder userHolder = (UserMessageViewHolder) holder;
            userHolder.textView.setText(message.getText());
            userHolder.textView.setBackgroundResource(R.drawable.sent_message_background);
        }
    }



    @Override
    public int getItemViewType(int position) {
        // Nếu không có nút, thì đó là tin nhắn người dùng (loại 1), ngược lại là tin nhắn bot (loại 0)
        return messages.get(position).getButtons() == null ? 1 : 0;
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    // ViewHolder cho tin nhắn bot
    class BotMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout buttonsLayout;

        BotMessageViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.botMessageText);
            buttonsLayout = itemView.findViewById(R.id.buttonsLayout);
        }
    }

    // ViewHolder cho tin nhắn người dùng
    class UserMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        UserMessageViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.messageContent);

            // Thiết lập LayoutParams để căn chỉnh tin nhắn người dùng sang bên phải
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END; // Căn chỉnh sang bên phải
            textView.setLayoutParams(params);
        }
    }
}
