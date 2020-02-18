package com.example.rusheatery.Help;

public class restaurantList {

    private String name;
    private String lat;
    private String lon;
    private String rate;



    public restaurantList(String name, String lat, String lon, String rate){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.rate = rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getRate() {
        return rate;
    }

}
