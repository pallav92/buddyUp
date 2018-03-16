package com.yatra.buddyup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yatra.buddyup.R;
import com.yatra.buddyup.model.User;

import java.util.ArrayList;
import java.util.List;

public class InterestListActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    ArrayList<String>interesList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_list);
        setTitle("Choose Your Interest");
        setTitleColor(R.color.colorPrimary);
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("main/users");
       
        initViews();
    }

    private void initViews() {

    }



    public  void onClickOFInterest(View view){
        if(view.getTag() == null){
            view.setTag(true);
        }else {
            view.setTag(!((boolean) view.getTag()));
        }
        if(((boolean) view.getTag())){
            view.setBackground(ContextCompat.getDrawable(this,android.R.color.holo_blue_light));
            view.setElevation(300);

        }else{
            view.setBackground(ContextCompat.getDrawable(this,android.R.color.white));
            view.setElevation(0);


        }


    }

    public void onClickOfSubmit(View view) {
        submitUserInterest(new ArrayList<String>());
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    private void submitUserInterest( List<String> interests) {
        interests.add("Movies");
        interests.add("Pets");
        interests.add("Health");
        mDatabaseRef.child("Pallav").child("interests").setValue(interests);
    }
}
