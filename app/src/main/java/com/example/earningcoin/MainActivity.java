package com.example.earningcoin;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{


    private BottomNavigationView bottomNavigationView;
    private NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );


        bottomNavigationView = (BottomNavigationView) findViewById ( R.id.bottnavigationView );
        navController = Navigation.findNavController ( this, R.id.frame_layout );


        NavigationUI.setupWithNavController ( bottomNavigationView, navController );

    }
    }




