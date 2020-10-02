package com.sprintweek.marvin.sugarassistant.mobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sprintweek.marvin.sugarassistant.mobile.Activity.MessageListActivity;
import com.sprintweek.marvin.sugarassistant.mobile.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void goToChatView(View view){
        switch (view.getId()) {
            case R.id.get_started_button:
                Intent intent = new Intent(this, MessageListActivity.class);
                startActivity(intent);
                //finish();
                break;
        }
    }
}