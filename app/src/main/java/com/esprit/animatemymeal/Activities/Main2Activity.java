package com.esprit.animatemymeal.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.esprit.animatemymeal.Fragments.RestaurantFragment;
import com.esprit.animatemymeal.R;

public class Main2Activity extends AppCompatActivity  {

    FrameLayout fragmentContainer ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home :

                RestaurantFragment restaurantsFragment =  RestaurantFragment.newInstance();
                if (getSupportFragmentManager().beginTransaction().isEmpty()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, restaurantsFragment).addToBackStack("a").commit();
                }
                else
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, restaurantsFragment).addToBackStack("a").commit();

                }
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //fragmentContainer = (FrameLayout) findViewById(R.id.frameLayout) ;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        RestaurantFragment restaurantsFragment =  RestaurantFragment.newInstance();
        if (getSupportFragmentManager().beginTransaction().isEmpty()) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, restaurantsFragment).commit();
        }
    }



}
