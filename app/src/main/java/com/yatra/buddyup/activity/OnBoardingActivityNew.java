package com.yatra.buddyup.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.yatra.buddyup.R;

/**
 * Created by kumar on 16/3/18.
 */

public class OnBoardingActivityNew extends AppCompatActivity {

    private FrameLayout flFragmentContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_onboarding_new);

        /*flFragmentContainer = (FrameLayout) findViewById(R.id.fl_fragment_container);

        PaperOnboardingPage scr1 = new PaperOnboardingPage("Make Friends While You Travel",
                "All hotels and hostels are sorted by hospitality rating",
                Color.parseColor("#678FB4"), R.drawable.getting_bored, R.drawable.getting_bored);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Make Friends While You Travel",
                "We carefully verify all banks before add them into the app",
                Color.parseColor("#65B0B4"), R.drawable.getting_bored, R.drawable.getting_bored);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Stores",
                "All local stores are categorized for your convenience",
                Color.parseColor("#9B90BC"), R.drawable.getting_bored, R.drawable.getting_bored);
        PaperOnboardingPage scr4 = new PaperOnboardingPage("Stores",
                "All local stores are categorized for your convenience",
                Color.parseColor("#9B90BC"), R.drawable.getting_bored, R.drawable.getting_bored);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);

        PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment_container, onBoardingFragment);
        fragmentTransaction.commit();*/

        /*onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment bf = new BlankFragment();
                fragmentTransaction.replace(R.id.fragment_container, bf);
                fragmentTransaction.commit();
            }
        });*/
    }


}
