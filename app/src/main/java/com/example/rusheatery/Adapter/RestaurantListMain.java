package com.example.rusheatery.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rusheatery.Help.restaurantList;
import com.example.rusheatery.R;

import java.util.ArrayList;

public class RestaurantListMain extends RecyclerView.Adapter<RestaurantListMain.ViewHolder> {

    public ArrayList<restaurantList> list;

    public RestaurantListMain(ArrayList<restaurantList> list){
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        restaurantList a = list.get(position);
        holder.restaurantName.setText(a.getName());
        holder.distance.setText("3 km");
        holder.ratingBar.setRating(Integer.valueOf(a.getRate()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView restaurantName, distance;
        public RatingBar ratingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            distance = itemView.findViewById(R.id.distance);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
