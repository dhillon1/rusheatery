package com.example.rusheatery.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rusheatery.R;
import com.example.rusheatery.SignUpActivity;
import com.example.rusheatery.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class ProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private TextView name,email,password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        name = view.findViewById(R.id.profileNameData);
        email = view.findViewById(R.id.profileEmailData);
        password = view.findViewById(R.id.profilePasswordData);
        Menu menu = bottomNavigationView.getMenu();
        if (!menu.findItem(R.id.profileMain).isChecked()) {
            menu.findItem(R.id.profileMain).setChecked(true);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        String i = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("profile")
                .whereEqualTo("userUid", i)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        name.setText(String.valueOf(document.get("name")));
                        email.setText(String.valueOf(document.get("email")));
                        password.setText(String.valueOf(document.get("password")));
                    }
                }
            }
        });

    }
}
