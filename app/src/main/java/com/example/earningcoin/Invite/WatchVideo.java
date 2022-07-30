package com.example.earningcoin.Invite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.earningcoin.Model.ProfileModel;
import com.example.earningcoin.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class WatchVideo extends AppCompatActivity {


    private InterstitialAd interstitialAd;
    private com.facebook.ads.InterstitialAd mInterstitial;

    private com.facebook.ads.RewardedVideoAd rewardedVideoAd, rewardedVideoAd2;

    private Button watchBtn1, watchBtn2;
    private TextView coinsTv;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_watch_video );


        init();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        loadData();

   //     loadInterstitialAd();
        loadRewardedAds();

        clickListener();

    }

    private void clickListener() {

        try {
            watchBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRewardVideo(1);
                }
            });

            watchBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRewardVideo(2);
                }
            });
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    private void showRewardVideo(final int i) {

        try {
            if (rewardedVideoAd.isAdLoaded()) {
                rewardedVideoAd.show();

                rewardedVideoAd.setAdListener(new RewardedVideoAdListener () {
                    @Override
                    public void onRewardedVideoCompleted() {

                        if (i == 1) {
                            watchBtn1.setVisibility(View.GONE);
                            watchBtn2.setVisibility(View.VISIBLE);
                        }

                        if (i == 2) {
                            onBackPressed();
                        }

                        updateDataFirebase();

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }

                    @Override
                    public void onRewardedVideoClosed() {

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {

                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }
                });

                rewardedVideoAd.loadAd();

                return;
            }

            if (rewardedVideoAd2.isAdLoaded()) {
                rewardedVideoAd2.show();

                rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoCompleted() {

                        if (i == 1) {
                            watchBtn1.setVisibility(View.GONE);
                            watchBtn2.setVisibility(View.VISIBLE);
                        }

                        if (i == 2) {
                            onBackPressed();
                        }

                        updateDataFirebase();

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }

                    @Override
                    public void onRewardedVideoClosed() {

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {

                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }
                });

                rewardedVideoAd2.loadAd();
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Watch and Earn");

        watchBtn1 = (Button) findViewById(R.id.watchBtn1);
        watchBtn2 = (Button) findViewById(R.id.watchBtn2);
        coinsTv = (TextView) findViewById(R.id.coins_Tv);

    }

    private void loadRewardedAds() {

        rewardedVideoAd = new RewardedVideoAd (this, getString(R.string.fb_rewarded_id));
        rewardedVideoAd.loadAd();

//        rewardedVideoAd2 = new RewardedVideoAd(this, getString(R.string.fb_rewarded_id_2));
//        rewardedVideoAd2.loadAd();

    }

//    private void loadInterstitialAd() {
//
//        //admob init
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));
//        interstitialAd.loadAd(new AdRequest.Builder().build());
//
//        //fb init
//        mInterstitial = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitial_id));
//        mInterstitial.loadAd();
//
//    }

//    @Override
//    public void onBackPressed() {
//
//        //fb
//        if (mInterstitial.isAdLoaded()) {
//            mInterstitial.show();
//
//            mInterstitial.setAdListener(new InterstitialAdListener() {
//                @Override
//                public void onInterstitialDisplayed(Ad ad) {
//
//                }
//
//                @Override
//                public void onInterstitialDismissed(Ad ad) {
//                    finish();
//                }
//
//                @Override
//                public void onError(Ad ad, AdError adError) {
//
//                }
//
//                @Override
//                public void onAdLoaded(Ad ad) {
//
//                }
//
//                @Override
//                public void onAdClicked(Ad ad) {
//
//                }
//
//                @Override
//                public void onLoggingImpression(Ad ad) {
//
//                }
//            });
//
//            return;
//
//        }
//
//        //admob
//        if (interstitialAd.isLoaded()) {
//            interstitialAd.show();
//
//            interstitialAd.setAdListener(new AdListener() {
//
//                @Override
//                public void onAdClosed() {
//                    super.onAdClosed();
//                    finish();
//                }
//            });
//
//            return;
//        }
//
//        finish();
//
//    }

    private void loadData() {

        reference.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProfileModel model = snapshot.getValue( ProfileModel.class);
                coinsTv.setText(String.valueOf(model.getCoins()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WatchVideo.this, "Error: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void updateDataFirebase() {

        try {
            int currentCoins = Integer.parseInt(coinsTv.getText().toString());
            int updatedCoin = currentCoins + 5;

            HashMap<String, Object> map = new HashMap<>();
            map.put("coins", updatedCoin);

            reference.updateChildren(map)
                    .addOnCompleteListener(new OnCompleteListener<Void> () {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(WatchVideo.this, "Coins added successfully", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } catch (NumberFormatException e) {
            e.printStackTrace ();
        }

    }

    }
