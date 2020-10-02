package com.sprintweek.marvin.sugarassistant.mobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sprintweek.marvin.sugarassistant.mobile.Adapter.MessageListAdapter;
import com.sprintweek.marvin.sugarassistant.mobile.Model.BaseMessage;
import com.sprintweek.marvin.sugarassistant.mobile.Model.Bot;
import com.sprintweek.marvin.sugarassistant.mobile.Model.EntityMessage;
import com.sprintweek.marvin.sugarassistant.mobile.Model.User;
import com.sprintweek.marvin.sugarassistant.mobile.R;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.ApiClient;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotRequest;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotResponse;
import com.sprintweek.marvin.sugarassistant.mobile.botservice.BotService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private Bot bot;
    LinearLayoutManager layoutManager;
    EditText text;

    private BotService botService;
    private List<Call<List<BotResponse>>> calls = new CopyOnWriteArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        user = new User("John", "profileUrl");
        bot = new Bot();
        messageList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        text = (EditText) findViewById(R.id.edittext_chatbox);

        // set up recycler and adapter
        messageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        messageAdapter = new MessageListAdapter(this, messageList);
        //mLayoutManager.setStackFromEnd(true);
        messageRecycler.setLayoutManager(layoutManager);
        messageRecycler.setAdapter(messageAdapter);

        botService = ApiClient.getApiClient().create(BotService.class);

        sendGreeting();
    }

    public void sendUserMessage(View view) {
        String textString = text.getText().toString();
        if (textString.length() > 0) {
            EntityMessage message = new EntityMessage(textString, System.currentTimeMillis(), user);
            messageList.add(message);
            messageAdapter.notifyDataSetChanged();
            text.setText("");
            askBot(textString);
            messageRecycler.smoothScrollToPosition(messageRecycler.getAdapter().getItemCount());
        }
    }

    public void sendGreeting() {
        String greeting = "Welcome " + user.getName() + "." + " I can help you with a series of tasks, by" +
                " simply typing or saying some of these sample commands:\n\n" + "\u2022 \"What's on my agenda today?\"\n\u2022 \"Can you schedule" +
                " me a meeting?\"\n\u2022 \"What's my next task?\"\n\nSo " + user.getName() + "..." + "what can I help you with?";

        EntityMessage message = new EntityMessage(greeting, System.currentTimeMillis(), bot);
        messageList.add(message);
        messageAdapter.notifyDataSetChanged();
        messageRecycler.smoothScrollToPosition(messageRecycler.getAdapter().getItemCount());
    }

    private void askBot(String message) {
        BotRequest request = new BotRequest(user.getName(), message);

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
        EntityMessage message = new EntityMessage(answer, System.currentTimeMillis(), bot);
        messageList.add(message);
        messageAdapter.notifyDataSetChanged();
        messageRecycler.smoothScrollToPosition(messageRecycler.getAdapter().getItemCount());
    }

    private void addErrorMessage() {
        EntityMessage message = new EntityMessage("I think you ought to know I'm feeling very depressed.", System.currentTimeMillis(), bot);
        messageList.add(message);
        messageAdapter.notifyDataSetChanged();
        messageRecycler.smoothScrollToPosition(messageRecycler.getAdapter().getItemCount());
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (Call<List<BotResponse>> call : calls) {
            call.cancel();
        }
        calls.clear();
    }

    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your device does not support speech input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.setText(result.get(0));
                }
                break;
        }
    }

}
