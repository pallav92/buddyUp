package com.yatra.buddyup.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wooplr.spotlight.SpotlightView;
import com.yatra.buddyup.R;

import java.util.ArrayList;

public class InterestListActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    ArrayList<String> interesList = new ArrayList<>();
    private View lastSelectedView;
    private LinearLayout llFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_list);
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("main/users");
        initViews();
        useSpotlight();
    }

    private void useSpotlight() {
        new SpotlightView.Builder(this)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(32)
                .headingTvText("Interest")
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText("Please choose this if you want to meet with similar interest people.")
                .maskColor(Color.parseColor("#dc000000"))
                .target(llFood)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .usageId("food") //UNIQUE ID
                .show();
    }

    private void initViews() {
        llFood = (LinearLayout) findViewById(R.id.food);
    }


    public void onClickOFInterest(View view) {
        if (lastSelectedView == null) {
            lastSelectedView = view;
            view.setBackground(ContextCompat.getDrawable(this, android.R.color.holo_blue_light));
            view.setElevation(300);
        } else {
            lastSelectedView.setBackground(ContextCompat.getDrawable(this, android.R.color.white));
            lastSelectedView.setElevation(0);
            view.setBackground(ContextCompat.getDrawable(this, android.R.color.holo_blue_light));
            view.setElevation(300);
            lastSelectedView = view;
        }
    }

    public void onClickOfSubmit(View view) {
        if (lastSelectedView == null) {
            Toast.makeText(this, "Please choose one interest", Toast.LENGTH_LONG).show();
            return;
        }

        LinearLayout selectedView = (LinearLayout) lastSelectedView;
        String selectedInterest = ((String) lastSelectedView.getTag());
        submitUserInterest(selectedInterest);

        finish();
    }

    private void submitUserInterest(String interest) {
        Intent chatIntent = new Intent(this, ChatActivity.class);
        chatIntent.putExtra("interest", interest);
        startActivity(chatIntent);
    }


}
