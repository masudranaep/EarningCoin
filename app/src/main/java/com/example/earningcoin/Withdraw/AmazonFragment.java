package com.example.earningcoin.Withdraw;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.earningcoin.Model.ProfileModel;
import com.example.earningcoin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class AmazonFragment extends Fragment {


    private RadioGroup radioGroup;
    private Button withdrawBtn;
    private TextView coinsTv;

    DatabaseReference reference;
    FirebaseUser user;

    String name, email;

    private Dialog dialog;

    public AmazonFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate ( R.layout.fragment_amazon, container, false );

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        init(view);

        FirebaseAuth auth = FirebaseAuth.getInstance ();
        user = auth.getCurrentUser ();
        reference = FirebaseDatabase.getInstance ().getReference ().child ( "users" );

        loadData();

        clickListener();
    }
    private void init(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        withdrawBtn = (Button) view.findViewById(R.id.submiBtn);
        coinsTv = (TextView) view.findViewById(R.id.coins_Tv);

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loading_dialog);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

    }

    private void loadData() {

        reference.child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        try {
                            ProfileModel model = snapshot.getValue(ProfileModel.class);
                            coinsTv.setText(String.valueOf(model.getCoins()));

                            name = model.getName();
                            email = model.getEmail();
                        } catch (Exception e) {
                            e.printStackTrace ();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                });

    }

    private void clickListener() {

        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(getActivity())
                        .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {

                                if (report.areAllPermissionsGranted()) {


                                    try {
                                        String filePath = Environment.getExternalStorageDirectory()
                                                + "/Earning App/Amazon Gift Card";
                                        File file = new File(filePath);
                                        file.mkdirs();
                                    } catch (Exception e) {
                                        e.printStackTrace ();
                                    }

                                    int currentCoins = Integer.parseInt(coinsTv.getText().toString());
                                    int checkedId = radioGroup.getCheckedRadioButtonId();
                                    switch (checkedId) {

                                        case R.id.amazon25:
                                            AmazonCard(25, currentCoins);
                                            break;

                                        case R.id.amazon50:
                                            AmazonCard(50, currentCoins);
                                            break;

                                    }

                                } else {
                                    Toast.makeText(getContext(), "Please allow permissions", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }
                        }).check();

            }
        });

    }

    private void AmazonCard(int amazonCard, int currentCoins) {

        if (amazonCard == 25) {

            if (currentCoins >= 6000) { //minimum coins should be 6000
                sendGiftCard(1);
            } else {
                Toast.makeText(getContext(), "You do not have enough coins", Toast.LENGTH_SHORT).show();
            }

        } else if (amazonCard == 50) {

            if (currentCoins >= 12000) {
                sendGiftCard(2);
            } else {
                Toast.makeText(getContext(), "You do not have enough coins", Toast.LENGTH_SHORT).show();
            }
        }


    }

    DatabaseReference amazonRef;
    Query query;

    private void sendGiftCard(final int cardAmount) {

        dialog.show();

        amazonRef = FirebaseDatabase.getInstance().getReference().child("Gift Cards").child("Amazon");


        if (cardAmount == 1) {

            query = amazonRef.orderByChild("amazon").equalTo(25);

        } else if (cardAmount == 2) {
            query = amazonRef.orderByChild("amazon").equalTo(50);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Random random = new Random();

                int childCount = (int) snapshot.getChildrenCount();
                int rand = random.nextInt(childCount);

                Iterator iterator = snapshot.getChildren().iterator();

                for (int i = 0; i < rand; i++) {

                    iterator.next();

                }

                DataSnapshot childSnap = (DataSnapshot) iterator.next();

                AmazonModel model = childSnap.getValue(AmazonModel.class);

                String id = model.getId();
                String giftCode = model.getAmazonCode();

                printAmazonCode(id, giftCode, cardAmount);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage()
                        , Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printAmazonCode(String id, String amazonCode, int cardAmount) {

        updateDate(cardAmount, id, amazonCode);


        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.ENGLISH);
        String currentTime = dateFormat.format(date);


        String text = "Date: " + currentTime + "\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Redeem ID: " + id + "\n\n" +
                "Amazon Claim Code: " + amazonCode;



        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(800, 800, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Paint paint = new Paint();

        page.getCanvas().drawText(text, 10, 12, paint);
        pdfDocument.finishPage(page);



        String filePath = Environment.getExternalStorageDirectory()
                + "/Earning App/Amazon Gift Card/"
                + System.currentTimeMillis()
                + user.getUid()
                + "amazonCode.pdf";

        File file = new File(filePath);
        try {

            pdfDocument.writeTo(new FileOutputStream(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();

        //Open pdf document
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            startActivity(Intent.createChooser(intent, "Open with"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Please install pdf reader", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateDate(int cardAmount, String id, String amazonCode) {

        try {
            HashMap<String, Object> map = new HashMap<>();
            int currentCoins = Integer.parseInt(coinsTv.getText().toString());


            HashMap<String, Object> withdrawMap = new HashMap<>();


            if (cardAmount == 1) { //User select 25$ option
                int updatedCoins = currentCoins - 6000;
                map.put("coins", updatedCoins);

                withdrawMap.put("amount", 25);


            } else if (cardAmount == 2) {
                int updatedCoins = currentCoins - 12000;
                map.put("coins", updatedCoins);

                withdrawMap.put("amount", 50);
            }

            reference.child(user.getUid())
                    .updateChildren(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Congrats!", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();
                        }
                    });


            FirebaseDatabase.getInstance().getReference()
                    .child("Gift Cards").child("Amazon")
                    .child(id).removeValue();


            DatabaseReference withReference = FirebaseDatabase.getInstance().getReference()
                    .child("Withdraw").child(user.getUid());

            String pushID = withReference.push().getKey();
           String imageAmazon = "https://thumbs.dreamstime.com/b/kiev-ukraine-november-gift-card-amazon-black-shadow-isolated-editorial-vector-202107582.jpg";


            withdrawMap.put("type", "amazon");
            withdrawMap.put("phone", amazonCode);
            withdrawMap.put("name", " ");
            withdrawMap.put("id", pushID);
            withdrawMap.put("status", "Completed");
            withdrawMap.put("image", imageAmazon);
            withdrawMap.put("uid", user.getUid());

            withReference.child(pushID).setValue(withdrawMap);
        } catch (NumberFormatException e) {
            e.printStackTrace ();
        }

    }

}