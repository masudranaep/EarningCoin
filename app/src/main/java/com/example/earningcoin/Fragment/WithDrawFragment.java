package com.example.earningcoin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.earningcoin.R;
import com.example.earningcoin.Withdraw.FragmentReplaceActivity;
import com.example.earningcoin.Withdraw.RedeemActivity;


public class WithDrawFragment extends Fragment {

    private LinearLayout phonerecharge, bikash, nogod, rocket;


    public WithDrawFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_with_draw, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );



        Toolbar toolbar = (Toolbar)view.findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        clickListerner();



        phonerecharge = (LinearLayout)view.findViewById ( R.id.phoneRecharge );
        bikash = (LinearLayout)view.findViewById ( R.id.Bikash );
        nogod = (LinearLayout)view.findViewById ( R.id.NagadNumber );
        rocket = (LinearLayout)view.findViewById ( R.id.rocket );

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    private void init(){

//        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
//        setSupportActionBar ( toolbar );
//
////        amazonImage = (ImageView) findViewById ( R.id.amazonImage );
////        amazonCard = (CardView) findViewById ( R.id.amazonGiftCard );
////
////
////        phoneCard = (CardView) findViewById ( R.id.phoneCard );
////        mobilePay = (ImageView) findViewById ( R.id.phoneImage );
//
//
//        phonerecharge = (LinearLayout) findViewById ( R.id.phoneRecharge );
//        bikash = (LinearLayout) findViewById ( R.id.Bikash );
//        nogod = (LinearLayout) findViewById ( R.id.NagadNumber );
//        rocket = (LinearLayout) findViewById ( R.id.rocket );


    }

    private void clickListerner() {

        phonerecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getContext (), FragmentReplaceActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);

            }
        });

        bikash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext (), FragmentReplaceActivity.class);
                intent.putExtra("position", 4);
                startActivity(intent);

            }
        });

        nogod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext (), FragmentReplaceActivity.class);
                intent.putExtra("position", 5);
                startActivity(intent);

            }
        });


        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext (), FragmentReplaceActivity.class);
                intent.putExtra("position", 6);
                startActivity(intent);

            }
        });

    }


}