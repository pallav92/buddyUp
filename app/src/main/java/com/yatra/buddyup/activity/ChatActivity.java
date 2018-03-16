package com.yatra.buddyup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yatra.buddyup.R;
import com.yatra.buddyup.model.ChatRoom;
import com.yatra.buddyup.model.Message;
import com.yatra.buddyup.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private final String TAG = ChatActivity.class.getSimpleName();
    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("message");

        mDatabaseRef.setValue("Hello, World!");

        ArrayList<String> interest = new ArrayList<>();
        interest.add("Travel");
        interest.add("Books");
        interest.add("Cricket");


        writeNewUser("pallav619@gmail.com","Pallav","pallav619@gmail.com",interest);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void enterNewMessage(Message message, ChatRoom chatRoom){

    }


    private void writeNewUser( String userId,String name, String email, List<String> interests) {
        User user = new User(userId,name,email,interests);

        mDatabaseRef.child("users").child(userId).setValue(user);
    }
}
