package com.example.application22024;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application22024.adapter.FAQAdapter;
import com.example.application22024.model.FAQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FAQActivity extends AppCompatActivity implements FAQAdapter.OnButtonClickListener {

    private RecyclerView recyclerView;
    private FAQAdapter chatAdapter;
    private List<FAQ> messages;

    private Map<String, List<String>> questionsMap;
    private Map<String, List<String>> answersMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Cấu hình Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Gán Toolbar làm ActionBar
        getSupportActionBar().setTitle("FAQ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Hiển thị nút back

        recyclerView = findViewById(R.id.recyclerView);
        messages = new ArrayList<>();

        // Khởi tạo dữ liệu
        initializeData();

        chatAdapter = new FAQAdapter(messages, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // Gửi tin nhắn đầu tiên (Chọn chủ đề hỗ trợ)
        sendBotMessage("어떤 문제에 대해 지원이 필요하신가요?", Arrays.asList(
                "앱 사용 방법",
                "구직",
                "지원자 프로필",
                "채용 담당자",
                "지원서 제출 및 면접",
                "사용자 지원"
        ));
    }

    // Hàm gửi tin nhắn từ bot
    private void sendBotMessage(String text, List<String> buttons) {
        FAQ message = new FAQ(text, buttons);
        messages.add(message);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messages.size() - 1); // Cuộn xuống tin nhắn mới
    }

    // Xử lý khi người dùng nhấn vào nút
    @Override
    public void onButtonClick(String buttonText) {
        // Gửi tin nhắn của người dùng (thêm vào danh sách)
        sendUserMessage(buttonText);

        // Kiểm tra câu hỏi trong danh sách
        if (questionsMap.containsKey(buttonText)) {
            // Gửi câu hỏi cho bot
            sendBotMessage(buttonText + "에 대해 어떤 도움이 필요하신가요?", questionsMap.get(buttonText));
        } else if (answersMap.containsKey(buttonText)) {
            // Gửi câu trả lời từ bot
            sendBotMessage(answersMap.get(buttonText).get(0) + "\n" + "다른 문제에 대해 지원이 필요하신가요?",
                    Arrays.asList(
                            "앱 사용 방법",
                            "구직",
                            "지원자 프로필",
                            "채용 담당자",
                            "지원서 제출 및 면접",
                            "사용자 지원"
                    ));
        }
    }
    private void sendUserMessage(String text) {
        FAQ userMessage = new FAQ(text, null); // Không có nút cho tin nhắn người dùng
        messages.add(userMessage);  // Thêm tin nhắn vào danh sách
        chatAdapter.notifyDataSetChanged();  // Cập nhật RecyclerView
        recyclerView.scrollToPosition(messages.size() - 1); // Cuộn xuống tin nhắn mới
    }


    // Khởi tạo dữ liệu cho câu hỏi và câu trả lời
    private void initializeData() {
        questionsMap = new HashMap<>();
        answersMap = new HashMap<>();

        // Câu hỏi và trả lời cho mỗi chủ đề
        List<String> usageQuestions = Arrays.asList(
                "구직 프로필을 어떻게 만들 수 있나요?",
                "검색 결과를 어떻게 필터링하나요?",
                "좋아하는 직업을 어떻게 저장하나요?",
                "개인 정보를 수정할 수 있나요?"
        );
        questionsMap.put("앱 사용 방법", usageQuestions);

        List<String> usageAnswers = Arrays.asList(
                "구직 프로필을 만들려면 '프로필' 섹션으로 가서 필요한 정보를 입력하세요.",
                "위의 필터를 사용하여 다양한 기준에 따라 직업을 찾을 수 있습니다.",
                "좋아하는 직업을 저장하려면 각 직업의 하트 아이콘을 클릭하세요.",
                "개인 정보는 '설정' 섹션에서 수정할 수 있습니다."
        );
        // Mỗi câu hỏi cần có câu trả lời tương ứng
        answersMap.put ("구직 프로필을 어떻게 만들 수 있나요?", Arrays.asList(usageAnswers.get(0)));
        answersMap.put("검색 결과를 어떻게 필터링하나요?", Arrays.asList(usageAnswers.get(1)));
        answersMap.put("좋아하는 직업을 어떻게 저장하나요?", Arrays.asList(usageAnswers.get(2)));
        answersMap.put("개인 정보를 수정할 수 있나요?", Arrays.asList(usageAnswers.get(3)));

        // Thêm các chủ đề khác, câu hỏi và câu trả lời tương tự
        // Ví dụ với "Tìm kiếm việc làm"
        List<String> searchQuestions = Arrays.asList(
                "파트타임 직업을 어떻게 찾나요?",
                "내 근처에서 일자리를 찾으려면 어떻게 해야 하나요?",
                "새로운 일자리 알림을 받을 수 있나요?",
                "높은 급여의 직업을 찾으려면 어떻게 해야 하나요?"
        );
        questionsMap.put("구직", searchQuestions);

        List<String> searchAnswers = Arrays.asList(
                "파트타임 직업을 찾으려면 검색 시 '파트타임' 필터를 선택하세요.",
                "내 근처에서 일자리를 찾으려면 위치 서비스를 활성화하고 앱이 위치에 접근할 수 있도록 허용하세요.",
                "앱에서 알림을 활성화하면 새로운 일자리에 대한 알림을 받을 수 있습니다.",
                "높은 급여의 직업을 찾으려면 검색 결과를 급여가 높은 순으로 정렬하세요."
        );
        answersMap.put("파트타임 직업을 어떻게 찾나요?", Arrays.asList(searchAnswers.get(0)));
        answersMap.put("내 근처에서 일자리를 찾으려면 어떻게 해야 하나요?", Arrays.asList(searchAnswers.get(1)));
        answersMap.put("새로운 일자리 알림을 받을 수 있나요?", Arrays.asList(searchAnswers.get(2)));
        answersMap.put("높은 급여의 직업을 찾으려면 어떻게 해야 하나요?", Arrays.asList(searchAnswers.get(3)));

        // Các chủ đề khác sẽ làm tương tự


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


//    private void initData() {
//        // Tạo danh sách nhóm
//        groupList = new ArrayList<>();
//        groupList.add("Cách sử dụng ứng dụng");
//        groupList.add("Tìm kiếm việc làm");
//        groupList.add("Hồ sơ ứng viên");
//        groupList.add("Nhà tuyển dụng");
//        groupList.add("Nộp hồ sơ và phỏng vấn");
//        groupList.add("Hỗ trợ người dùng");
//
//        // Tạo danh sách câu hỏi con
//        childMap = new HashMap<>();
//        answerMap = new HashMap<>();
//
//        List<String> usageQuestions = new ArrayList<>();
//        usageQuestions.add("Làm thế nào để tạo hồ sơ tìm việc?");
//        usageQuestions.add("Làm sao để lọc kết quả tìm kiếm?");
//        usageQuestions.add("Làm thế nào để lưu công việc yêu thích?");
//        usageQuestions.add("Tôi có thể chỉnh sửa thông tin cá nhân không?");
//
//        List<String> usageAnswers = new ArrayList<>();
//        usageAnswers.add("Để tạo hồ sơ tìm việc, bạn vào phần 'Hồ sơ' và điền các thông tin cần thiết.");
//        usageAnswers.add("Bạn có thể sử dụng bộ lọc ở trên để tìm các công việc theo các tiêu chí khác nhau.");
//        usageAnswers.add("Để lưu công việc yêu thích, chỉ cần nhấn vào biểu tượng trái tim trên mỗi công việc.");
//        usageAnswers.add("Bạn có thể chỉnh sửa thông tin cá nhân trong phần 'Cài đặt'.");
//
//        List<String> searchQuestions = new ArrayList<>();
//        searchQuestions.add("Làm thế nào để tìm công việc bán thời gian?");
//        searchQuestions.add("Tôi muốn tìm việc ở gần tôi, phải làm sao?");
//        searchQuestions.add("Có cách nào nhận thông báo việc làm mới không?");
//        searchQuestions.add("Tôi muốn tìm công việc lương cao, làm thế nào?");
//
//        List<String> searchAnswers = new ArrayList<>();
//        searchAnswers.add("search answer1");
//        searchAnswers.add("search answer2");
//        searchAnswers.add("search answer3");
//        searchAnswers.add("search answer4");
//
//        List<String> profileQuestions = new ArrayList<>();
//        profileQuestions.add("Cần điền những thông tin gì vào hồ sơ?");
//        profileQuestions.add("Làm sao để thêm kinh nghiệm làm việc?");
//        profileQuestions.add("Có cần tải lên CV không?");
//        profileQuestions.add("Làm thế nào để xóa hồ sơ?");
//
//        List<String> profileAnswers = new ArrayList<>();
//        profileAnswers.add("profile answer1");
//        profileAnswers.add("profile answer2");
//        profileAnswers.add("profile answer3");
//        profileAnswers.add("profile answer4");
//
//        List<String> recruiterQuestions = new ArrayList<>();
//        recruiterQuestions.add("Làm thế nào để xem thông tin nhà tuyển dụng?");
//        recruiterQuestions.add("Tôi có thể liên hệ trực tiếp với nhà tuyển dụng không?");
//        recruiterQuestions.add("Ứng dụng có kiểm tra độ uy tín không?");
//
//        List<String> recruiterAnswers = new ArrayList<>();
//        recruiterAnswers.add("recruiter answer1");
//        recruiterAnswers.add("recruiter answer2");
//        recruiterAnswers.add("recruiter answer3");
//
//        List<String> applicationQuestions = new ArrayList<>();
//        applicationQuestions.add("Làm thế nào để nộp hồ sơ?");
//        applicationQuestions.add("Tôi đã nộp hồ sơ, làm sao để theo dõi?");
//        applicationQuestions.add("Có thể đặt lịch phỏng vấn không?");
//        applicationQuestions.add("Tôi cần chuẩn bị gì khi phỏng vấn?");
//
//        List<String> applicationAnswers = new ArrayList<>();
//        applicationAnswers.add("application answer1");
//        applicationAnswers.add("application answer2");
//        applicationAnswers.add("application answer3");
//        applicationAnswers.add("application answer4");
//
//        List<String> supportQuestions = new ArrayList<>();
//        supportQuestions.add("Làm thế nào để khôi phục mật khẩu?");
//        supportQuestions.add("Ứng dụng không tải được, tôi cần làm gì?");
//        supportQuestions.add("Làm sao để báo cáo tin tuyển dụng không hợp lệ?");
//        supportQuestions.add("Làm thế nào để liên hệ hỗ trợ?");
//
//        List<String> supportAnswers = new ArrayList<>();
//        supportAnswers.add("support answer1");
//        supportAnswers.add("support answer2");
//        supportAnswers.add("support answer3");
//        supportAnswers.add("support answer4");
//
//        // Gắn các câu hỏi vào nhóm
//        childMap.put(groupList.get(0), usageQuestions);
//        childMap.put(groupList.get(1), searchQuestions);
//        childMap.put(groupList.get(2), profileQuestions);
//        childMap.put(groupList.get(3), recruiterQuestions);
//        childMap.put(groupList.get(4), applicationQuestions);
//        childMap.put(groupList.get(5), supportQuestions);
//
//        answerMap.put(groupList.get(0), usageAnswers);
//        answerMap.put(groupList.get(1), searchAnswers);
//        answerMap.put(groupList.get(2), profileAnswers);
//        answerMap.put(groupList.get(3), recruiterAnswers);
//        answerMap.put(groupList.get(4), applicationAnswers);
//        answerMap.put(groupList.get(5), supportAnswers);
//    }