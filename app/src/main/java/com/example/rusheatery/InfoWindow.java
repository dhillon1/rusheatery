package com.example.rusheatery;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//class for showing info window on click of marker
public class InfoWindow implements GoogleMap.InfoWindowAdapter {

    //variable
    private Context c;

    String name;

    //constructor
    public InfoWindow(Context c, String name) {
        this.name = name;
        this.c = c;

    }

    //override function of InfoWindowAdapter
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    //override function of InfoWindowAdapter
    @Override
    public View getInfoContents(Marker marker) {

        //view for showing  infoWindow
        View view = ((Activity) c).getLayoutInflater().inflate(R.layout.info_window, null);

        //adding id to the  variable
        TextView textView1 = view.findViewById(R.id.textVIewInfoName);
        textView1.setText(name);

        return view;


    }
}
