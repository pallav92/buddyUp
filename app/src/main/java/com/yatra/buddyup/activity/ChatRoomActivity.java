package com.yatra.buddyup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yatra.buddyup.R;
import com.yatra.buddyup.Utils.ChatsAdapter;
import com.yatra.buddyup.model.Message;
import com.yatra.buddyup.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    RecyclerView chats;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chats = findViewById(R.id.chats);
        chats.setLayoutManager(new LinearLayoutManager(this));

        ChatsAdapter chatsAdapter = new ChatsAdapter(this, getDummyMessages());
        chats.setAdapter(chatsAdapter);

        final EditText editMsg = findViewById(R.id.et_msg);
        ImageView sendMsg = findViewById(R.id.send);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editMsg.getText().toString().trim();
                if(!text.equals("")) {
                    //Message message = new Message(text, );
                    //sendMsgToServer(text);
                }
            }
        });
    }

    private List<Message> getDummyMessages() {

        String[] imgs = {
                "https://vignette.wikia.nocookie.net/batman/images/8/8f/Christian_Bale_as_The_Dark_Knight.jpg/revision/latest?cb=20140208170841",
                "https://i.pinimg.com/originals/86/17/f3/8617f3d63e9c58807430ee02d7b095f6.jpg",
                "https://pre00.deviantart.net/6a46/th/pre/i/2016/249/9/8/justice_league__wonder_woman_by_goxiii-dac6ztv.jpg"
        };

        List<Message> list = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            int rand = (int)(100 * Math.random());
            Message message = new Message();
            message.setMessageText("Hello guys, How are you all Hello guys, How are you all Hello guys, How are you all Hello guys, How are you all");
            message.setUser(new User("", "", "", imgs[rand % 3], null));
            if(rand % i == 0) {
                message.setSender(true);
            }else {
                message.setSender(false);
            }
            list.add(message);
        }
        return list;
    }
}