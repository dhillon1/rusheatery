package com.example.rusheatery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.rusheatery.InfoWindow;
import com.example.rusheatery.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MapFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private SupportMapFragment mapFragment;
    private EditText search;
    private Button searchButton;
    private TextView resetFilters;
    private RecyclerView recyclerViewList;
    private ProgressBar progressBarMain;
    private MapView map;
    public static ArrayList<restaurantList> list;
    private Marker m1,m2,m3,m4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        progressBarMain = view.findViewById(R.id.progressBarMap);
        search = view.findViewById(R.id.searchMap);
        searchButton = view.findViewById(R.id.searchButtonMap);
        resetFilters = view.findViewById(R.id.resetFiltersMap);

        list = new ArrayList<>();
        Menu menu = bottomNavigationView.getMenu();
        if (!menu.findItem(R.id.mapViewMain).isChecked()) {
            menu.findItem(R.id.mapViewMain).setChecked(true);
        }
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.828536,-79.242987),4));

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        progressBarMain.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()==0){
                                //TODO


                                progressBarMain.setVisibility(View.GONE);
                            }else{
                                mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(final GoogleMap googleMap) {
                                        googleMap.clear();

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            list.add(new restaurantList(String.valueOf(document.get("name")),String.valueOf(document.get("lat")),String.valueOf(document.get("lon")),String.valueOf(document.get("rate"))));
                                            progressBarMain.setVisibility(View.GONE);
                                            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(String.valueOf(document.get("lat"))),Double.valueOf(String.valueOf(document.get("lon")))))).setTag(String.valueOf(document.get("name")));

                                        }

                                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(Marker marker) {
                                                if(marker.getTag() == "Tim Hortons"){
                                                    InfoWindow infoWindow = new InfoWindow(getActivity(),"Tim Hortons");
                                                    googleMap.setInfoWindowAdapter(infoWindow);
                                                    marker.showInfoWindow();

                                                }
                                                else if(marker.getTag() == "McDonalds"){
                                                    InfoWindow infoWindow = new InfoWindow(getActivity(),"McDonalds");
                                                    googleMap.setInfoWindowAdapter(infoWindow);
                                                    marker.showInfoWindow();
                                                }
                                                else if(marker.getTag() == "Pizza Pizza"){
                                                    InfoWindow infoWindow = new InfoWindow(getActivity(),"Pizza Pizza");
                                                    googleMap.setInfoWindowAdapter(infoWindow);
                                                    marker.showInfoWindow();
                                                }
                                                else if(marker.getTag() == "Starbucks"){
                                                    InfoWindow infoWindow = new InfoWindow(getActivity(),"Starbucks");
                                                    googleMap.setInfoWindowAdapter(infoWindow);
                                                    marker.showInfoWindow();
                                                }
                                                return true;
                                            }
                                        });



                                    }
                                });





                            }
                        } else {
                            //   Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarMain.setVisibility(View.GONE);
                        }
                    }
                });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search1 = search.getText().toString().trim();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                progressBarMain.setVisibility(View.VISIBLE);

                db.collection("restaurant")
                        .whereEqualTo("name", search1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    list.clear();
                                    if(task.getResult().size()==0){
                                        //TODO
                                        list.clear();
                                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                            @Override
                                            public void onMapReady(GoogleMap googleMap) {
                                                googleMap.clear();
                                            }
                                        });
                                        Toast.makeText(getActivity(),"No record found",Toast.LENGTH_SHORT).show();

                                        progressBarMain.setVisibility(View.GONE);
                                    }else{
                                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                            @Override
                                            public void onMapReady(final GoogleMap googleMap) {
                                                googleMap.clear();

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    list.add(new restaurantList(String.valueOf(document.get("name")),String.valueOf(document.get("lat")),String.valueOf(document.get("lon")),String.valueOf(document.get("rate"))));
                                                    progressBarMain.setVisibility(View.GONE);
                                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(String.valueOf(document.get("lat"))),Double.valueOf(String.valueOf(document.get("lon")))))).setTag(String.valueOf(document.get("name")));

                                                }

                                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                    @Override
                                                    public boolean onMarkerClick(Marker marker) {
                                                        if(marker.getTag() == "Tim Hortons"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"Tim Hortons");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();

                                                        }
                                                        else if(marker.getTag() == "McDonalds"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"McDonalds");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();
                                                        }
                                                        else if(marker.getTag() == "Pizza Pizza"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"Pizza Pizza");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();
                                                        }
                                                        else if(marker.getTag() == "Starbucks"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"Starbucks");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();
                                                        }
                                                        return true;
                                                    }
                                                });



                                            }
                                        });
                                    }

                                } else {
                                    //   Log.d(TAG, "Error getting documents: ", task.getException());
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBarMain.setVisibility(View.GONE);
                                }

                            }
                        });
            }
        });

        resetFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarMain.setVisibility(View.VISIBLE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("restaurant")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    list.clear();
                                    if(task.getResult().size()==0){
                                        //TODO
                                        progressBarMain.setVisibility(View.GONE);
                                    }else{
                                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                            @Override
                                            public void onMapReady(final GoogleMap googleMap) {
                                                googleMap.clear();

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    list.add(new restaurantList(String.valueOf(document.get("name")),String.valueOf(document.get("lat")),String.valueOf(document.get("lon")),String.valueOf(document.get("rate"))));
                                                    progressBarMain.setVisibility(View.GONE);
                                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(String.valueOf(document.get("lat"))),Double.valueOf(String.valueOf(document.get("lon")))))).setTag(String.valueOf(document.get("name")));

                                                }

                                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                    @Override
                                                    public boolean onMarkerClick(Marker marker) {
                                                        if(marker.getTag() == "Tim Hortons"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"Tim Hortons");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();

                                                        }
                                                        else if(marker.getTag() == "McDonalds"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"McDonalds");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();
                                                        }
                                                        else if(marker.getTag() == "Pizza Pizza"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"Pizza Pizza");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();
                                                        }
                                                        else if(marker.getTag() == "Starbucks"){
                                                            InfoWindow infoWindow = new InfoWindow(getActivity(),"Starbucks");
                                                            googleMap.setInfoWindowAdapter(infoWindow);
                                                            marker.showInfoWindow();
                                                        }
                                                        return true;
                                                    }
                                                });



                                            }
                                        });
                                    }
                                } else {
                                    //   Log.d(TAG, "Error getting documents: ", task.getException());
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBarMain.setVisibility(View.GONE);
                                }

                            }
                        });
            }
        });


    }
}
