package com.example.application22024;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.adapter.MessageAdapter;
import com.example.application22024.model.DataViewModel;
import com.example.application22024.model.Message;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private EditText messageInput;
    private Button sendButton;
    private Socket mSocket;
    private int senederId, recevierId;
    DataViewModel viewModel;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        viewModel = ((MyApplication) getApplication()).getDataViewModel();

        // Cấu hình Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Gán Toolbar làm ActionBar
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Hiển thị nút back

        recyclerView = findViewById(R.id.recyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        senederId = SharedPrefManager.getInstance(this).getUserId();
        if (viewModel.getSelectedApplicant() != null && viewModel.getSelectedApplicant().getValue() != null) {
            recevierId = viewModel.getSelectedApplicant().getValue().getEmployee_id();
        } else if (viewModel.getSelectedCompanyJobItem() != null) {
            recevierId = viewModel.getSelectedCompanyJobItem().getEmployer_id();
        } else {
            Toast.makeText(this, "No recipient selected", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc Activity nếu không có recipient
        }

        try {
            // Kết nối với server Socket.io
            mSocket = IO.socket("http://10.0.2.2:3000");
            mSocket.connect();

            // Lắng nghe sự kiện "receiveMessage"
            mSocket.on("receiveMessage", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(() -> {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            int senderId = data.getInt("sender_id");
                            String messageContent = data.getString("content");

                            // Kiểm tra nếu tin nhắn đến từ người khác
                            if (senderId != senederId) {
                                messageList.add(new Message("Received", messageContent,senderId));
                                messageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(messageList.size() - 1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

            // Gửi tin nhắn khi nhấn nút
            sendButton.setOnClickListener(v -> {
                String messageText = messageInput.getText().toString();
                if (!messageText.isEmpty()) {
                    try {
                        JSONObject messageData = new JSONObject();
                        messageData.put("sender_id", senederId);
                        messageData.put("receiver_id", recevierId);
                        messageData.put("content", messageText);
                        mSocket.emit("sendMessage", messageData); // Gửi tin nhắn qua Socket.IO

                        // Thêm tin nhắn vào danh sách và cập nhật giao diện
                        messageList.add(new Message("Sent", messageText,senederId));
                        messageAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(messageList.size() - 1);
                        messageInput.setText("");  // Xóa nội dung đã nhập
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            });
            // Lấy tin nhắn cũ khi ứng dụng bắt đầu
            loadOldMessages(senederId, recevierId);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private void loadOldMessages(int senderId, int receiverId) {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

        Call<MessageResponse> call = apiService.getMessages(senderId, receiverId);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy danh sách tin nhắn
                    List<Message> messages = response.body().getMessages();
                    messageList.clear();  // Xóa danh sách tin nhắn hiện tại
                    messageList.addAll(messages);  // Thêm tin nhắn cũ vào danh sách
                    messageAdapter.notifyDataSetChanged();  // Cập nhật giao diện RecyclerView
                    messages.toString();
                    recyclerView.scrollToPosition(messageList.size() - 1);  // Cuộn xuống cuối danh sách tin nhắn
                } else {
                    Toast.makeText(ChatActivity.this, "No messages found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Xử lý khi người dùng nhấn nút quay lại
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
