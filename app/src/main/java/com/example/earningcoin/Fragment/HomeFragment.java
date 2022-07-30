package com.example.earningcoin.Fragment;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.earningcoin.Invite.WatchVideo;
import com.example.earningcoin.MainActivity;
import com.example.earningcoin.Model.Internet;
import com.example.earningcoin.Model.ProfileModel;
import com.example.earningcoin.Profile.ProfileActivity;
import com.example.earningcoin.R;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollfish.classes.SurveyInfo;
import com.pollfish.constants.Position;

import com.pollfish.main.PollFish;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment{
//        implements
//        PollfishCompletedSurveyListener,
//        PollfishOpenedListener, PollfishReceivedSurveyListener, PollfishClosedListener

    private CardView dailyCheck, luckyCard, taskCard,redeemCard, referCard, watchCard, aboutCard;
    private CircleImageView profileImage;
    private TextView cointTv, nameTv, emailTv;
    Toolbar toolbar;

    private DatabaseReference reference;
    private FirebaseUser user;

    private Dialog dialog;

    Internet internet;


    AdView adView;

    InterstitialAd interstitialAd;



    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_home, container, false );
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );


        FirebaseAuth auth = FirebaseAuth.getInstance ();
        user = auth.getCurrentUser ();

        // taskSdkInit ();
//        setContentView( R.layout.activity_main );


//        setSupportActionBar ( toolbar );

        reference = FirebaseDatabase.getInstance ().getReference ().child ( "users" );


        sgetDataFromdatabse();

        clicklistener();






        dailyCheck = view.findViewById ( R.id.dailyCheckbox );
        watchCard = view.findViewById ( R.id.watchCard );


        profileImage = (CircleImageView) view.findViewById ( R.id.profileImage );
        nameTv = (TextView)view.findViewById ( R.id.name_Tv );
        emailTv = (TextView)view.findViewById ( R.id.email_Tv );
        cointTv = (TextView)view.findViewById ( R.id.coins_Tv );
        toolbar = view.findViewById ( R.id.toolbar );

        dialog = new Dialog ( getContext ());
        dialog.setContentView ( R.layout.loading_dialog );

        if (dialog.getWindow () != null)

            dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT ) );

        dialog.setCancelable ( false );

    }




//    private void init() {
//        dailyCheck = findViewById ( R.id.dailyCheckbox );
//        luckyCard = findViewById ( R.id.luckySpinCard );
//        taskCard = findViewById ( R.id.testCard );
//        redeemCard = findViewById ( R.id.redeemCard );
//        referCard = findViewById ( R.id.referCard );
//        watchCard = findViewById ( R.id.watchCard );
//        aboutCard = findViewById ( R.id.aboutCard );
//        profileImage = (CircleImageView) findViewById ( R.id.profileImage );
//        nameTv = (TextView)adView.findViewById ( R.id.name_Tv );
//        emailTv = (TextView) findViewById ( R.id.email_Tv );
//        cointTv = (TextView) findViewById ( R.id.coins_Tv );
//        toolbar = findViewById ( R.id.toolbar );
//
//
//        dialog = new Dialog ( this);
//        dialog.setContentView ( R.layout.loading_dialog );
//
//        if (dialog.getWindow () != null)
//
//            dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT ) );
//
//        dialog.setCancelable ( false );
//    }


    //Firebase get data 1 set (4)
    private void sgetDataFromdatabse() {

        dialog.show ();

        reference.child ( user.getUid () ).addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProfileModel model = snapshot.getValue (ProfileModel.class);

                try {
                    nameTv.setText ( model.getName () );
                    emailTv.setText ( model.getEmail () );
                    cointTv.setText ( String.valueOf ( model.getCoins () ) );
                } catch (Exception e) {
                    e.printStackTrace ();
                }


                //imgar upload firebase 1 set (5)

                try {
                    Glide.with ( getContext ())
                            .load ( model.getImage () )
                            .timeout ( 6000 )
                            .placeholder ( R.drawable.quizphoto )
                            .into ( profileImage );
                    // getApplicationContext

                } catch (Exception e) {
                    e.printStackTrace ();
                }
                dialog.dismiss ();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                dialog.dismiss ();

                Toast.makeText ( getContext (), "Error" + error.getMessage (), Toast.LENGTH_LONG ).show ();
                //finish ();

            }
        } );
    }



    //profile firebase 1 set (5)
    private void clicklistener() {
        profileImage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (getContext (), ProfileActivity.class ) );
            }
        } );
//        referCard.setOnClickListener ( new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                startActivity ( new Intent (MainActivity.this, InviteActivity.class ) );
//            }
//        } );
//
        dailyCheck.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dailyCheck();
            }
        } );
//
//        redeemCard.setOnClickListener ( new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                startActivity ( new Intent (MainActivity.this, RedeemActivity.class ) );
//            }
//        } );
//
//        luckyCard.setOnClickListener ( new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (MainActivity.this, FragmentReplaceActivity.class );
//                intent.putExtra ( "position", 2 );
//                startActivity ( intent );
//            }
//        } );
//
//        aboutCard.setOnClickListener ( new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (MainActivity.this, FragmentReplaceActivity.class);
//                intent.putExtra ( "position", 3 );
//                startActivity ( intent );
//            }
//        } );
        watchCard.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext (), WatchVideo.class );
                intent.putExtra ( "position", 4 );
                startActivity ( intent );
            }
        } );


    }

    //dailycheck 1 set (14)
    private void dailyCheck() {

        if (internet.isConnected ()) {


            final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog ( getContext (), SweetAlertDialog.PROGRESS_TYPE );
            sweetAlertDialog.setTitleText ( "Please Wait..." );
            sweetAlertDialog.setCancelable ( false );
            sweetAlertDialog.show ();


            final Date currentDate = Calendar.getInstance ().getTime ();

            final SimpleDateFormat dateFormat = new SimpleDateFormat ( "dd/MM/yyyy", Locale.ENGLISH );

            final String date = dateFormat.format ( currentDate );

            DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ();

            reference.child ( "Daily Check" ).child ( user.getUid () )
                    .addListenerForSingleValueEvent ( new ValueEventListener () {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists ()) {

                                String dbDateString = snapshot.child ( "date" ).getValue ( String.class );

                                try {
                                    assert dbDateString != null;
                                    Date dbDate = dateFormat.parse ( dbDateString );

                                    String xDate = dateFormat.format ( currentDate );
                                    Date date = dateFormat.parse ( xDate );

                                    if (date.after ( dbDate ) && date.compareTo ( dbDate ) != 0) {

                                        //reward ads
                                        reference.child ( "users" ).child ( user.getUid () )

                                                .addListenerForSingleValueEvent ( new ValueEventListener () {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        ProfileModel model = snapshot.getValue ( ProfileModel.class );

                                                        int currentCoins = model.getCoins ();

                                                        int update = currentCoins + 50000;
                                                        int spinC = model.getSpins ();
                                                        int updatedSpins = spinC + 3;

                                                        HashMap<String, Object> map = new HashMap<> ();
                                                        map.put ( "coins", update );
                                                        map.put ( "spin", updatedSpins );

                                                        reference.child ( "users" ).child ( user.getUid () )
                                                                .updateChildren ( map );

                                                        //dailycheck firebase 2 set (14)

                                                        reference.child ( "users" ).child ( user.getUid () )
                                                                .updateChildren ( map );


                                                        Date newDate = Calendar.getInstance ().getTime ();
                                                        String newDateString = dateFormat.format ( newDate );

                                                        HashMap<String, String> dateMap = new HashMap<> ();
                                                        dateMap.put ( "date", newDateString );

                                                        reference.child ( "Daily Check" ).child ( user.getUid () )
                                                                .setValue ( dateMap )
                                                                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {


                                                                        sweetAlertDialog.changeAlertType ( SweetAlertDialog.SUCCESS_TYPE );
                                                                        sweetAlertDialog.setTitleText ( "Success" );
                                                                        sweetAlertDialog.setContentText ( "Coins added to your acceount successfully" );
                                                                        sweetAlertDialog.setConfirmButton ( "Dismiss", new SweetAlertDialog.OnSweetClickListener () {
                                                                            @Override
                                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                                                sweetAlertDialog.dismissWithAnimation ();

                                                                            }
                                                                        } ).show ();
                                                                    }
                                                                } );

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Toast.makeText ( getContext (), "Error" + error.getMessage (),
                                                                Toast.LENGTH_LONG ).show ();

                                                    }
                                                } );

                                    } else {
                                        sweetAlertDialog.changeAlertType ( SweetAlertDialog.ERROR_TYPE );
                                        sweetAlertDialog.setTitleText ( "Failed" );
                                        sweetAlertDialog.setContentText ( "You have already rewarded, come back tomorrow" );
                                        sweetAlertDialog.setConfirmButton ( "Dismiss", null );
                                        sweetAlertDialog.show ();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace ();
                                    sweetAlertDialog.dismissWithAnimation ();
                                }
                            } else {
                                sweetAlertDialog.changeAlertType ( SweetAlertDialog.WARNING_TYPE );
                                sweetAlertDialog.setTitleText ( "System Busy" );
                                sweetAlertDialog.setContentText ( "System is busy, please try again later" );
                                sweetAlertDialog.setConfirmButton ( "Dismiss", new SweetAlertDialog.OnSweetClickListener () {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.dismissWithAnimation ();
                                    }
                                } );
                                sweetAlertDialog.show ();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText ( getContext (), "Error" + error.getMessage (),
                                    Toast.LENGTH_LONG ).show ();
                            sweetAlertDialog.dismissWithAnimation ();

                        }
                    } );
        }else {
            Toast.makeText ( getContext (), "Please check your internet", Toast.LENGTH_LONG ).show ();
        }


    }




    // PollFish 1 set (1)







    private void updateData(int reawrd){


        int currentCoins = Integer.parseInt ( cointTv.getText ().toString () );
        int updateCoins = currentCoins + reawrd;

        HashMap <String, Object> map = new HashMap<> ();
        map.put ( "coins", updateCoins);


        FirebaseDatabase.getInstance ().getReference ().child ( "users" )
                .child ( user.getUid () )
                .updateChildren ( map )
                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful ()){
                            Toast.makeText ( getContext (),"Coins added", Toast.LENGTH_LONG ).show ();
                        }
                    }
                } );
    }






}