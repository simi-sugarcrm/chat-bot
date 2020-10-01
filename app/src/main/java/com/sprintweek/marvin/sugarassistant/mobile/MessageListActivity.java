package com.sprintweek.marvin.sugarassistant.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sprintweek.marvin.sugarassistant.mobile.botservice.ApiClient;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotRequest;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotService;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView messageRecycler;
    private MessageListAdapter messageAdapter;
    List<BaseMessage> messageList;
    private User user;
    private User bot;

    private BotService botService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        user = new User(true, "me");
        bot = new User(false, "marvin");
        messageList = new ArrayList<>();
        messageRecycler = findViewById(R.id.reyclerview_message_list);
        messageAdapter = new MessageListAdapter(this, messageList);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageRecycler.setAdapter(messageAdapter);

        botService = ApiClient.getApiClient().create(BotService.class);
    }

    public void sendMessage(View view) {
        EditText text = findViewById(R.id.edittext_chatbox);
        String textString = text.getText().toString();
        if (textString.length() > 0) {
            UserMessage message = new UserMessage(textString, System.currentTimeMillis(), user);
            messageList.add(message);
            messageAdapter.notifyDataSetChanged();
            text.setText("");
        }
    }

    private void askBot(String message) {
        BotRequest request = new BotRequest(user.nickname, message);
    }
}
