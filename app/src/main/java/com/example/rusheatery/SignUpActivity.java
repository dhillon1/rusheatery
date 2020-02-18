package com.example.rusheatery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText email,name,password;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Get views
        email = findViewById(R.id.signUpEmail);
        name = findViewById(R.id.signUpName);
        password = findViewById(R.id.signUpPassword);
        signUp = findViewById(R.id.signUpButton);


        // On click sign up button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String e = email.getText().toString().trim();
                final String n = name.getText().toString().trim();
                final String p = password.getText().toString().trim();

                // TODO : make it function or class
                // Validate email
                if (e.isEmpty()) {
                    email.setError("Enter Email Id");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                    email.setError("Email Id Incorrect");
                    email.requestFocus();
                    return;
                }

                if (n.isEmpty()) {
                    email.setError("Enter Name");
                    email.requestFocus();
                    return;
                }

                // Validate password
                if (p.length() < 6){
                    password.setError("Password should be at least 6 characters long");
                    password.requestFocus();
                    return;
                }

                // Request sign up (create user in Auth)
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Shared preference for user information
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("UserInfo", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();

                            // Get and set userUid to shared preference
                            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            editor.putString("userUid", userId);
                            editor.commit();

                            Map<String, Object> appUser = new HashMap<>();
                            appUser.put("userUid", userId);
                            appUser.put("name", n);
                            appUser.put("email", e);
                            appUser.put("password", p);
                            appUser.put("time",String.valueOf(System.currentTimeMillis()));

                            FirebaseFirestore.getInstance().collection("profile").document(userId).set(appUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignUpActivity. this, SplashActivity.class));
                                    finish();
                                }
                            });

                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            String a = "";
                        }
                    }
                });

            }
        });

    }
}
