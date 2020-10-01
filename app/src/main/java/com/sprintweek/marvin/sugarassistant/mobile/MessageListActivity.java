package com.sprintweek.marvin.sugarassistant.mobile;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    List<BaseMessage> messageList;
    private User user;
    private Bot bot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        user = new User( "user", "profileUrl");
        bot = new Bot();
        messageList = new ArrayList();

        // set up recycler and adapter
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);


        sendGreeting();
    }

    public void sendUserMessage(View view){
        EditText text = (EditText)findViewById(R.id.edittext_chatbox);
        String textString = text.getText().toString();
        if (textString.length() > 0){
            EntityMessage message = new EntityMessage(textString, System.currentTimeMillis(), user);
            messageList.add(message);
            mMessageAdapter.notifyDataSetChanged();
            text.setText("");
        }
    }

    public void sendGreeting(){
        String greeting = "Welcome " + user.getName() + "." + " I can help you with a series of tasks, by" +
                " simply typing or saying some of these sample commands:\n\n\u2022 What's on my agenda today?\n\u2022 Can you schedule" +
                " me a meeting?\n\u2022 What's my next task?\n\nSo " + user.getName() + "..." + "what can I help you with?";

        EntityMessage message = new EntityMessage(greeting, System.currentTimeMillis(), bot);
        messageList.add(message);
        mMessageAdapter.notifyDataSetChanged();
    }
}
