package com.example.rusheatery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rusheatery.Adapter.RestaurantListMain;
import com.example.rusheatery.Help.restaurantList;
import com.example.rusheatery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ListFragmentMain extends Fragment {

    private BottomNavigationView bottomNavigationView;
    public static ArrayList<restaurantList> list;
    private EditText search;
    private Button searchButton;
    private TextView resetFilters;
    private RecyclerView recyclerViewList;
    private ProgressBar progressBarList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        search = view.findViewById(R.id.searchList);
        searchButton = view.findViewById(R.id.searchButtonList);
        resetFilters = view.findViewById(R.id.resetFiltersList);
        recyclerViewList = view.findViewById(R.id.recyclerViewList);
        progressBarList = view.findViewById(R.id.progressBarList);
        list = new ArrayList<>();
        Menu menu = bottomNavigationView.getMenu();
        if (!menu.findItem(R.id.listViewMain).isChecked()) {
            menu.findItem(R.id.listViewMain).setChecked(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        progressBarList.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()==0){
                                //TODO
                                progressBarList.setVisibility(View.GONE);
                            }else{
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    list.add(new restaurantList(String.valueOf(document.get("name")),String.valueOf(document.get("lat")),String.valueOf(document.get("lon")),String.valueOf(document.get("rate"))));

                                }
                                RestaurantListMain adapter = new RestaurantListMain(list);
                                recyclerViewList.setAdapter(adapter);
                                recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                progressBarList.setVisibility(View.GONE);


                            }
                        } else {
                            //   Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarList.setVisibility(View.GONE);
                        }
                    }
                });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search1 = search.getText().toString().trim();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                progressBarList.setVisibility(View.VISIBLE);

                db.collection("restaurant")
                        .whereEqualTo("name", search1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    list.clear();
                                    if(task.getResult().size()==0){
                                        //TODO
                                        list.clear();
                                        RestaurantListMain adapter = new RestaurantListMain(list);
                                        recyclerViewList.setAdapter(adapter);
                                        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        progressBarList.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(),"No record found",Toast.LENGTH_SHORT).show();
                                    }else{
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            list.add(new restaurantList(String.valueOf(document.get("name")),String.valueOf(document.get("lat")),String.valueOf(document.get("lon")),String.valueOf(document.get("rate"))));
                                        }
                                        RestaurantListMain adapter = new RestaurantListMain(list);
                                        recyclerViewList.setAdapter(adapter);
                                        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        progressBarList.setVisibility(View.GONE);
                                    }

                                } else {
                                    //   Log.d(TAG, "Error getting documents: ", task.getException());
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBarList.setVisibility(View.GONE);
                                }

                            }
                        });
            }
        });

        resetFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarList.setVisibility(View.VISIBLE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("restaurant")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    list.clear();
                                    if(task.getResult().size()==0){
                                        //TODO
                                        progressBarList.setVisibility(View.GONE);
                                    }else{
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            list.add(new restaurantList(String.valueOf(document.get("name")),String.valueOf(document.get("lat")),String.valueOf(document.get("lon")),String.valueOf(document.get("rate"))));
                                        }
                                        RestaurantListMain adapter = new RestaurantListMain(list);
                                        recyclerViewList.setAdapter(adapter);
                                        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        progressBarList.setVisibility(View.GONE);
                                    }
                                } else {
                                    //   Log.d(TAG, "Error getting documents: ", task.getException());
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBarList.setVisibility(View.GONE);
                                }

                            }
                        });
            }
        });


    }
}
