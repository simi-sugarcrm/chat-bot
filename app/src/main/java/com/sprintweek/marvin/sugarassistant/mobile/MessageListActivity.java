package com.sprintweek.marvin.sugarassistant.mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sprintweek.marvin.sugarassistant.mobile.botservice.ApiClient;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotRequest;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotResponse;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageListActivity extends AppCompatActivity {
    private static final String TAG = MessageListActivity.class.getSimpleName();
    private RecyclerView messageRecycler;
    private MessageListAdapter messageAdapter;
    private List<BaseMessage> messageList;
    private User user;
    private User bot;

    private BotService botService;
    private List<Call<List<BotResponse>>> calls = new CopyOnWriteArrayList<>();

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
            askBot(textString);
        }
    }

    private void askBot(String message) {
        BotRequest request = new BotRequest(user.nickname, message);

        Call<List<BotResponse>> call = botService.askQuestion(request);
        call.enqueue(new Callback<List<BotResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<BotResponse>> call, @NotNull Response<List<BotResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (BotResponse botResponse : response.body()) {
                        addAnswer(botResponse.getText());
                    }
                } else {
                    addErrorMessage();
                }
                calls.remove(call);
            }

            @Override
            public void onFailure(@NotNull Call<List<BotResponse>> call, @NotNull Throwable t) {
                Log.e(TAG, "call failure: " + t.getMessage(), t);
                addErrorMessage();
                calls.remove(call);
            }
        });
        calls.add(call);
    }

    private void addAnswer(String answer) {
        UserMessage message = new UserMessage(answer, System.currentTimeMillis(), bot);
        messageList.add(message);
        messageAdapter.notifyDataSetChanged();
    }

    private void addErrorMessage() {
        UserMessage message = new UserMessage("I think you ought to know I'm feeling very depressed.", System.currentTimeMillis(), bot);
        messageList.add(message);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (Call<List<BotResponse>> call : calls) {
            call.cancel();
        }
        calls.clear();
    }
}
