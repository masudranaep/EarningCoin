package com.example.earningcoin.LoginAndReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.earningcoin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {


    private Button registerBtn;
    private EditText nameEdit, emailEdit, passwordEdit, confirmpasswordEdit;
    private ProgressBar progressBar;

    private TextView loginTv;

    private FirebaseAuth auth;

    private String deviceID;


    @SuppressLint("HardwareIds")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );

        auth = FirebaseAuth.getInstance ();

        deviceID = Settings.Secure.getString (getContentResolver (), Settings.Secure.ANDROID_ID );


        init();

       clickListener();



    }
    //register 1 set (1)

    private  void init(){
        registerBtn = findViewById ( R.id.registerBtn );
        nameEdit = (EditText) findViewById ( R.id.nameET );
        emailEdit = (EditText) findViewById ( R.id.emailET );
        passwordEdit = findViewById ( R.id.passwordET );
        confirmpasswordEdit = findViewById ( R.id.confimPassET );
        progressBar = findViewById ( R.id.progressbar );
        loginTv = findViewById ( R.id.login_tv );

    }



    //registerBtn 2 set (1)

    private void clickListener() {

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String pass = passwordEdit.getText().toString();
                String confirmPass = confirmpasswordEdit.getText().toString();

                if (name.isEmpty()) {
                    nameEdit.setError("Required");
                    return;
                }

                if (email.isEmpty()) {
                    emailEdit.setError("Required");
                    return;
                }

                if (pass.isEmpty()) {
                    passwordEdit.setError("Required");
                    return;
                }

                if (confirmPass.isEmpty() || !pass.equals(confirmPass)) {
                    confirmpasswordEdit.setError("Invalid Password");
                    return;
                }



                queryAccountExistence(email, pass);

            }
        });

    }

    private void createAccount(final String email, String password) {

        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            //Registration success:
                            final FirebaseUser user = auth.getCurrentUser();
                            assert user != null;


                            //send email verification link
                            auth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                updateUi(user, email);

                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Error: "
                                                                + task.getException().getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        } else {

                            progressBar.setVisibility(View.GONE);
                            //Registration failed:
                            Toast.makeText(RegisterActivity.this, "Error: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    private void queryAccountExistence(final String email,final String pass) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        Query query = ref.orderByChild("deviceID").equalTo(deviceID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    //device already registered
                    Toast.makeText(RegisterActivity.this,
                            "This device is already registered on another email, please login",
                            Toast.LENGTH_SHORT).show();

                } else {
                    //device id not found
                    createAccount(email, pass);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateUi(FirebaseUser user, String email) {

        String refer = email.substring(0, email.lastIndexOf("@"));
        String referCode = refer.replace(".", "");

        //identify that this user already sign up

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", nameEdit.getText().toString());
        map.put("email", email);
        map.put("uid", user.getUid());
        map.put("image", " ");
        map.put("coins", 0);
        map.put("referCode", referCode);
        map.put("spins", 2);
        map.put("deviceID", deviceID);
        map.put("redeemed", false);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // to get yesterday date
        Date previousDate = calendar.getTime();

        String dateString = dateFormat.format(previousDate);

        FirebaseDatabase.getInstance().getReference().child("Daily Check")
                .child(user.getUid())
                .child("date")
                .setValue(dateString);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        reference.child(user.getUid())
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Registered, Please verify email",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            finish();

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " +
                                            task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);

                    }
                });


    }

}