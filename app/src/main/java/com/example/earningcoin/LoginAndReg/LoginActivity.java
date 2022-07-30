package com.example.earningcoin.LoginAndReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.earningcoin.MainActivity;
import com.example.earningcoin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText  emailEdit, passwordEdit;
    private ProgressBar progressBar;

    private TextView signupTv;

    private FirebaseAuth auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );


        init ();

        auth = FirebaseAuth.getInstance ();



        clickListener();

    }

    private  void init(){

        loginBtn = findViewById ( R.id.loginBtn );
        emailEdit = findViewById ( R.id.emailET );
        passwordEdit = findViewById ( R.id.passwordET );
        signupTv = findViewById ( R.id.signup_tv );
        progressBar = findViewById ( R.id.progressbar );

    }

//login fasebase 1 set (3)
    private void clickListener() {
        signupTv.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (LoginActivity.this, RegisterActivity.class) );
                finish ();
            }
        } );

      //login fasebase 2 set (3)

        loginBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                String email = emailEdit.getText ().toString ();
                String password = passwordEdit.getText ().toString ();


                if (TextUtils.isEmpty ( email ) ){
                    emailEdit.setError ( "Input valid email" );
                    return;
                }
                if (TextUtils.isEmpty ( password )){
                    passwordEdit.setError ( "Required" );
                    return;
                }

                signIn(email, password);
            }
        } );

    }

    //login fasebase 2 set (3)
    private void signIn(String email, String password) {
        progressBar.setVisibility ( View.VISIBLE );
        auth.signInWithEmailAndPassword (email, password)
                .addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful ()){




                            //send email verfication link 2 set (22) <- regiosterActivity

                            FirebaseUser user = auth.getCurrentUser ();

                            //Check if user is verified
                            assert user != null;
                            if (user.isEmailVerified ()){


                            progressBar.setVisibility ( View.GONE );
                            startActivity ( new Intent (LoginActivity.this, MainActivity.class ) );
                            finish ();

                            }else {
                                progressBar.setVisibility ( View.GONE );
                                Toast.makeText ( LoginActivity.this, "Please verify your email", Toast.LENGTH_LONG ).show ();
                            }

                        }else {
                            progressBar.setVisibility ( View.GONE );
                            Toast.makeText ( LoginActivity.this, "Error" + task.getException ().getMessage (), Toast.LENGTH_LONG ).show ();
                        }
                    }
                } );

    }


    //send email verfication link 2 set (22) <- regiosterActivity

}