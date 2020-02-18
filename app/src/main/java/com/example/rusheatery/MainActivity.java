package com.example.rusheatery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rusheatery.Fragment.HomeFragment;
import com.example.rusheatery.Fragment.ListFragmentMain;
import com.example.rusheatery.Fragment.MapFragment;
import com.example.rusheatery.Help.restaurantList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        fragmentManager = getSupportFragmentManager();
        addFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if(id == R.id.listViewMain){
                    fragment = new ListFragmentMain();
                }
                else if (id == R.id.mapViewMain){
                    fragment = new MapFragment();

                }else{
                    fragment = new HomeFragment();
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutCustomer,fragment,"back");
                fragmentTransaction.addToBackStack("back");
                fragmentTransaction.commit();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    //adding fragment on create
    private void addFragment() {
        Fragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutCustomer,fragment,null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()!= 0) {
            int i = getSupportFragmentManager().getBackStackEntryCount();
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }
}
