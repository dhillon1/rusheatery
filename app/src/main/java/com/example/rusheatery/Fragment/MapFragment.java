package com.example.rusheatery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.rusheatery.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MapFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

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
        Menu menu = bottomNavigationView.getMenu();
        if (!menu.findItem(R.id.mapViewMain).isChecked()) {
            menu.findItem(R.id.mapViewMain).setChecked(true);
        }
    }
}
