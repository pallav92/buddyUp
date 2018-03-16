package com.yatra.buddyup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yatra.buddyup.R;

public class ChatRoomActivity extends AppCompatActivity {

    RecyclerView chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chats = (RecyclerView) findViewById(R.id.chats);
        chats.setLayoutManager(new LinearLayoutManager(this));
    }
}