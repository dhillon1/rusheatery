package com.example.rusheatery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    public static List<String> profile;
    public static Map<String, Boolean> interests;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient f;
    private LocationCallback l;
    private String lat,lon,time;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        profile = new ArrayList<>();
        interests =new HashMap<>();

//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
//            return;
//        }
//
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(100000);
//
//        l = new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                for (Location location : locationResult.getLocations()) {
//                    lat = String.valueOf(location.getLatitude());
//                    lon = String.valueOf(location.getLongitude());
//                    time = String.valueOf(location.getTime());
//                    if(f!=null){
//                        f.removeLocationUpdates(l);
//                    }
//
//                    // Get User from Firebase
//                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                    if (firebaseUser!=null){
//                        // Get user id
//                        String userId = firebaseUser.getUid();
//                        // Shared preference for user information
//                        SharedPreferences pref = getSharedPreferences("UserInfo", 0);
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putString("userUid", userId);
//                        editor.commit();
//
//
//                        // Get the profile document by userId
//                        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                        DocumentReference docRef = db.collection("profile").document(userId);
//                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//
//                                    DocumentSnapshot document = task.getResult();
//
//                                    if (document.exists()) {
//                                        FirebaseFirestore.getInstance();
//                                        DocumentReference docRef = db.collection("profile").document(userId);
//                                        docRef.update("lat",lat,"lon",lon,"time",time).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//
//                                            }
//                                        });
//
//
//
//                                        // If user exists, go to MainActivity
//                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                                        finish();
//                                    } else {
//                                        // If user doesn't exist(New user) go to ProfileActivity
//                                        Toast.makeText(SplashActivity.this, "Enter details to move to the app", Toast.LENGTH_SHORT).show();
//                                        Intent o = new Intent(SplashActivity.this, ProfileActivity.class);
//                                        o.putExtra("name","create");
//                                        startActivity(o);
//
//                                        finish();
//                                    }
//                                }
//                                else {
//                                    // Error message
//                                    Toast.makeText(SplashActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                    else {
//                        // Go to LoginActivity
//                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                        finish();
//
//                    }
//
//                }
//            }
//        };
//
//
//
//        f = LocationServices.getFusedLocationProviderClient(this);
//        f.requestLocationUpdates(locationRequest,l,null);
//

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
        else{
            startActivity(new Intent(SplashActivity. this, LoginActivity.class));
            finish();
        }





    }
}
