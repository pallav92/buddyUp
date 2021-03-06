package com.yatra.buddyup.activity;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yatra.buddyup.R;
import com.yatra.buddyup.adapter.ChatUserAdapter;
import com.yatra.buddyup.adapter.ChatsAdapter;
import com.yatra.buddyup.model.ChatRoom;
import com.yatra.buddyup.model.Message;
import com.yatra.buddyup.model.User;
import com.yatra.buddyup.util.BuddyConstants;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private final String TAG = ChatActivity.class.getSimpleName();
    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    private List<Message> messages = new ArrayList<>();
    private List<User> usersList = new ArrayList<>();
    TextView tvChatTitle;
    RecyclerView chats, users;
    ChatsAdapter chatsAdapter;
    ChatUserAdapter chatUserAdapter;
    ChatRoom chatRoom;
    User user;
    private String interestChoosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        tvChatTitle = findViewById(R.id.tv_chat_title);

        interestChoosen = getIntent().getStringExtra("interest") != null ? getIntent().getStringExtra("interest") : "xyz";
        database = FirebaseDatabase.getInstance();

        mDatabaseRef = database.getReference("main");

        final ArrayList<String> interest = new ArrayList<>();
        interest.add(interestChoosen);
        addUserIdInChatRoom(interestChoosen);
        tvChatTitle.setText(interestChoosen + " lovers");
       /* interest.add("Books");
        interest.add("Cricket");*/

        writeNewUser(BuddyConstants.userName, BuddyConstants.userName, BuddyConstants.userEmail, interest);
        initializeUI();
        chatRoom = new ChatRoom(interestChoosen, null, null);

        usersList.add(user);
        chatUserAdapter.notifyDataSetChanged();
        writeNewUser(BuddyConstants.userEmail, BuddyConstants.userName, BuddyConstants.userEmail, interest);
        initializeUI();
        chatRoom = new ChatRoom(interestChoosen, null, null);

        mDatabaseRef.child("chatrooms").child(chatRoom.getChatRoomId()).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                chatUserAdapter.notifyDataSetChanged();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    if (value != null) {
                        Message message = value.getValue(Message.class);
                        /*if (message != null && !isUserPresent(message.getUser())) {
                            usersList.add(message.getUser());
                            chatUserAdapter.notifyDataSetChanged();
                        }*/
                        messages.add(message);
                    }
                }

                String msg = "";
                if (interestChoosen.toLowerCase().contains("food")) {
                    msg = BuddyConstants.foodMessage;
                } else if (interestChoosen.toLowerCase().contains("books")) {
                    msg = BuddyConstants.booksMessage;
                }

                if (!msg.isEmpty()) {
                    messages.add(0, new Message(msg, new User("", "YATRA", "", "", null), System.currentTimeMillis(), true));
                }
                chatsAdapter.notifyDataSetChanged();
                hideKeyboard();
                playNotificationSound();
                chats.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        mDatabaseRef.child("chatrooms").child(chatRoom.getChatRoomId()).child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    if (value != null) {
                        User user = value.getValue(User.class);
                        if (user != null) {
                            usersList.add(user);
                            chatUserAdapter.notifyDataSetChanged();
                        }
                    }
                }
                chatUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void addUserIdInChatRoom(String interest) {

        DatabaseReference mDatabase = database.getReference("main");
        String key = mDatabase.child("chatrooms").child(interest).child("users").push().getKey();
        ArrayList ar = new ArrayList<String>();
        ar.add(interest);
        mDatabase.child("/chatrooms").child(interest).child("users").child(BuddyConstants.userName).setValue(
                new User(BuddyConstants.userName, BuddyConstants.userName, BuddyConstants.userEmail, BuddyConstants.userImage, ar));
    }


    private boolean isUserPresent(User user) {
        boolean isPresent = false;
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUserId().equals(user.getUserId())) {
                isPresent = true;
                break;
            }
        }
        return isPresent;
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void playNotificationSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        chats = findViewById(R.id.chats);
        chats.setLayoutManager(new LinearLayoutManager(this));
        chatsAdapter = new ChatsAdapter(this, messages, user.getName());
        chats.setAdapter(chatsAdapter);

        users = findViewById(R.id.users);
        users.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        chatUserAdapter = new ChatUserAdapter(this, usersList);
        users.setAdapter(chatUserAdapter);

        final EditText editMsg = findViewById(R.id.et_msg);
        final ImageView sendMsg = findViewById(R.id.send);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editMsg.getText().toString().trim();
                if (!text.equals("")) {
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

    private void enterNewMessage(Message message, ChatRoom chatRoom) {
        String key = mDatabaseRef.child("chatrooms").child(chatRoom.getChatRoomId()).push().getKey();
        mDatabaseRef.child("/chatrooms").child(chatRoom.getChatRoomId()).child("messages/" + key).setValue(message);
    }


    private void writeNewUser(String userId, String name, String email, List<String> interests) {
        user = new User(userId, name, email, BuddyConstants.userImage, interests);
        /*String key = mDatabaseRef.child("users").push().getKey();
        user.setUserId(key);*/
        mDatabaseRef.child("/users").child(user.getName()).setValue(user);
    }

    private List<Message> getDummyMessages() {

        String[] imgs = {
                "https://vignette.wikia.nocookie.net/batman/images/8/8f/Christian_Bale_as_The_Dark_Knight.jpg/revision/latest?cb=20140208170841",
                "https://i.pinimg.com/originals/86/17/f3/8617f3d63e9c58807430ee02d7b095f6.jpg",
                "https://pre00.deviantart.net/6a46/th/pre/i/2016/249/9/8/justice_league__wonder_woman_by_goxiii-dac6ztv.jpg"
        };

        List<Message> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int rand = (int) (100 * Math.random());
            Message message = new Message();
            message.setMessageText("Hello guys, How are you all Hello guys, How are you all Hello guys, How are you all Hello guys, How are you all");
            message.setUser(new User("", "", "", imgs[rand % 3], null));
            if (rand % i == 0) {
                message.setSender(true);
            } else {
                message.setSender(false);
            }
            list.add(message);
        }
        return list;
    }
}
