package com.yatra.buddyup.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.yatra.buddyup.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends OnboarderActivity {

    List<OnboarderPage> onboarderPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboarderPages = new ArrayList<OnboarderPage>();

        // Create your first page
        OnboarderPage onboarderPage1 = new OnboarderPage("Do you usually spend layover time at airport like this?", "", R.drawable.getting_bored);
        OnboarderPage onboarderPage2 = new OnboarderPage("Meet New People And Share your stories", "", R.drawable.meet_new_people);
        OnboarderPage onboarderPage3 = new OnboarderPage("A simple Hello could lead to million things", "", R.drawable.ladies_shopping);
        OnboarderPage onboarderPage4 = new OnboarderPage("Make friends for life!!", "", R.drawable.happiness_is_when);

        onboarderPage1.setTitleColor(R.color.white);
        onboarderPage1.setDescriptionColor(R.color.white);
        onboarderPage1.setBackgroundColor(R.color.colorGreen);

        onboarderPage2.setTitleColor(R.color.white);
        onboarderPage2.setDescriptionColor(R.color.white);
        onboarderPage2.setBackgroundColor(R.color.colorBlue);

        onboarderPage3.setTitleColor(R.color.white);
        onboarderPage3.setDescriptionColor(R.color.white);
        onboarderPage3.setBackgroundColor(R.color.colorPink);

        onboarderPage4.setTitleColor(R.color.white);
        onboarderPage4.setDescriptionColor(R.color.white);
        onboarderPage4.setBackgroundColor(R.color.colorPurple);

        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);
        onboarderPages.add(onboarderPage4);

        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);

        setActiveIndicatorColor(android.R.color.white);
        setInactiveIndicatorColor(android.R.color.darker_gray);
        shouldDarkenButtonsLayout(true);
        setDividerColor(Color.WHITE);
        setDividerHeight(2);
        setDividerVisibility(View.GONE);
        shouldUseFloatingActionButton(true);
        setSkipButtonTitle("Skip");
    }

    @Override
    public void onFinishButtonPressed() {
        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
    }
}
