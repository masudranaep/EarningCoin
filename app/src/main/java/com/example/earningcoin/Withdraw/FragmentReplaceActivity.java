package com.example.earningcoin.Withdraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.earningcoin.About.AboutFragment;
import com.example.earningcoin.Fragment.LuckySpingFragment;
import com.example.earningcoin.R;

public class FragmentReplaceActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_fragment_replace );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = findViewById(R.id.framelayout);

        int position = getIntent().getIntExtra("position", 0);

        if (position == 1) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Phone Recharge");

            fragmentReplacer(new RechargeFragment ());
        }

        if (position == 2) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Lucky Spin");

            fragmentReplacer(new LuckySpingFragment ());
        }

        if (position == 3) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("About");

            fragmentReplacer(new AboutFragment ());
        }

        if (position == 4) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Withdraw the Bikash");

            fragmentReplacer(new MobileFragment());
        }

        if (position == 5) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Withdraw the Nogad");

            fragmentReplacer(new NogadFragment ());
        }

        if (position == 6) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Phone");

            fragmentReplacer(new RocketFragment ());
        }

    }

    public void fragmentReplacer(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        fragmentTransaction.replace(frameLayout.getId(), fragment);

        fragmentTransaction.commit();


    }

}
