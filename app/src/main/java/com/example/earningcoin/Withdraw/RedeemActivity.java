package com.example.earningcoin.Withdraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.earningcoin.R;

public class RedeemActivity extends AppCompatActivity {

    private ImageView amazonImage, mobilePay;
    private CardView amazonCard, phoneCard;

    private LinearLayout phonerecharge, bikash, nogod, rocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_redeem );

        init ();

        loadImages();
        clickListerner();


    }

    private void init(){

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

//        amazonImage = (ImageView) findViewById ( R.id.amazonImage );
//        amazonCard = (CardView) findViewById ( R.id.amazonGiftCard );
//
//
//        phoneCard = (CardView) findViewById ( R.id.phoneCard );
//        mobilePay = (ImageView) findViewById ( R.id.phoneImage );


        phonerecharge = (LinearLayout) findViewById ( R.id.phoneRecharge );
        bikash = (LinearLayout) findViewById ( R.id.Bikash );
        nogod = (LinearLayout) findViewById ( R.id.NagadNumber );
        rocket = (LinearLayout) findViewById ( R.id.rocket );


    }

    //loadImage google and gallery 1 set (15)
    private void loadImages() {
//        String amazonGiftImageURL = null;
//        String phoneImage = null;
//        try {
//            phoneImage = "https://www.evercarebd.com/wp-content/uploads/2020/04/BKASH-LOGO-Copy.jpeg";
//
//            amazonGiftImageURL = "https://thumbs.dreamstime.com/b/kiev-ukraine-november-gift-card-amazon-black-shadow-isolated-editorial-vector-202107582.jpg";
//        } catch (Exception e) {
//            e.printStackTrace ();
//        }
//
//
//        try {
//            Glide.with (   RedeemActivity.this )
//                    .load ( amazonGiftImageURL )
//                    .into ( amazonImage );
//
//            Glide.with ( RedeemActivity.this )
//                    .load ( phoneImage )
//                    .into ( mobilePay );
//        } catch (Exception e) {
//            e.printStackTrace ();
//        }

    }




    private void clickListerner() {

        phonerecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RedeemActivity.this, FragmentReplaceActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);

            }
        });

        bikash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RedeemActivity.this, FragmentReplaceActivity.class);
                intent.putExtra("position", 4);
                startActivity(intent);

            }
        });

        nogod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RedeemActivity.this, FragmentReplaceActivity.class);
                intent.putExtra("position", 5);
                startActivity(intent);

            }
        });


        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RedeemActivity.this, FragmentReplaceActivity.class);
                intent.putExtra("position", 6);
                startActivity(intent);

            }
        });

    }
//
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
//
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
//        //
//        finish();


    }
