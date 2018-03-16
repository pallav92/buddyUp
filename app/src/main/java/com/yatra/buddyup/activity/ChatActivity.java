package com.yatra.buddyup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yatra.buddyup.R;
import com.yatra.buddyup.Utils.ChatsAdapter;
import com.yatra.buddyup.model.ChatRoom;
import com.yatra.buddyup.model.Message;
import com.yatra.buddyup.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private final String TAG = ChatActivity.class.getSimpleName();
    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    private List<Message> messages = new ArrayList<>();

    RecyclerView chats;
    ChatsAdapter chatsAdapter;
    ChatRoom chatRoom;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        initializeUI();

        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("message");

        mDatabaseRef.setValue("Hello, World!");

        ArrayList<String> interest = new ArrayList<>();
        interest.add("Travel");
        interest.add("Books");
        interest.add("Cricket");

        writeNewUser("pallav619@gmail.com","Pallav","pallav619@gmail.com",interest);

        chatRoom = new ChatRoom("xyz",null,null);

        enterNewMessage(new Message("Hi", new User("pallav619@gmail.com","Pallav","pallav619@gmail.com", "https://vignette.wikia.nocookie.net/batman/images/8/8f/Christian_Bale_as_The_Dark_Knight.jpg/revision/latest?cb=20140208170841", interest),System.currentTimeMillis(), true),chatRoom);

        mDatabaseRef.child("chatrooms").child(chatRoom.getChatRoomId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatRoom value = dataSnapshot.getValue(ChatRoom.class);
                if(value != null) {
                    messages = value.getMessageList();
                    chatsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void initializeUI() {
        chats = findViewById(R.id.chats);
        chats.setLayoutManager(new LinearLayoutManager(this));

        chatsAdapter = new ChatsAdapter(this, messages);
        chats.setAdapter(chatsAdapter);

        final EditText editMsg = findViewById(R.id.et_msg);
        final ImageView sendMsg = findViewById(R.id.send);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editMsg.getText().toString().trim();
                if(!text.equals("")) {
                    Message message = new Message(text, user, System.currentTimeMillis(), true);
                    sendMsgToServer(message);
                    editMsg.setText("");
                }
            }
        });
    }

    private void sendMsgToServer(Message message) {
        enterNewMessage(message, chatRoom);
    }

    private void enterNewMessage(Message message, ChatRoom chatRoom){
        String key = mDatabaseRef.child("chatrooms").child(chatRoom.getChatRoomId()).push().getKey();
        mDatabaseRef.child("/chatrooms").child(chatRoom.getChatRoomId()).child("messages/"+key).setValue(message);
    }

    private void writeNewUser( String userId,String name, String email, List<String> interests) {
        user = new User(userId,name,email, "", interests);
        String key = mDatabaseRef.child("users").push().getKey();
        mDatabaseRef.child("/users").child(key).setValue(user);
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
