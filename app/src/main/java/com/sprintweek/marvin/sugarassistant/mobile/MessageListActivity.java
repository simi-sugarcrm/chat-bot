package com.sprintweek.marvin.sugarassistant.mobile;

import android.os.Bundle;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        user = new User(true, "user", "profileUrl");
        messageList = new ArrayList();
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
    }

    public void sendMessage(View view){
        EditText text = (EditText)findViewById(R.id.edittext_chatbox);
        String textString = text.getText().toString();
        if (textString.length() > 0){
            UserMessage message = new UserMessage(textString, System.currentTimeMillis(), user);
            messageList.add(message);
            mMessageAdapter.notifyDataSetChanged();
            text.setText("");
        }
    }
}
