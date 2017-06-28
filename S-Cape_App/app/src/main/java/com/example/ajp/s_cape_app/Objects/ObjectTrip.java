package com.example.ajp.s_cape_app.Objects;

import java.io.Serializable;
import java.util.ArrayList;


public class ObjectTrip implements Serializable {

    //Variables for our trip information

    public String tripName;
    public String tripStartDate;
    public String tripEndDate;
    public String tripOriginCity;
    public String tripDestinationCity;

    //Variables for our trip progress & completion
    public boolean tripInProgress;
    public boolean tripCompleted;

    //Variables for the hotel info
    public String hotelName;
    public String hotelAddress;
    public String hotelPrice;

    //Variable that holds all of the events
    public ArrayList<ObjectBaseEvent> tripEventList;

    public ObjectTrip(String tripName, String tripStartDate, String tripEndDate,
                      String tripOriginCity, String tripDestinationCity, boolean tripInProgress,
                      boolean tripCompleted, String hotelName, String hotelAddress, String hotelPrice,
                      ArrayList<ObjectBaseEvent> tripEventList) {

        this.tripName = tripName;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripOriginCity = tripOriginCity;
        this.tripDestinationCity = tripDestinationCity;
        this.tripInProgress = tripInProgress;
        this.tripCompleted = tripCompleted;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelPrice = hotelPrice;
        this.tripEventList = tripEventList;
    }

    public ObjectTrip(){

    }

    @Override
    public String toString() {
        return tripName;
    }

    //getters used for testing right now, can remove later if not used
    public String getTripName() {
        return tripName;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public String getTripOriginCity() {
        return tripOriginCity;
    }

    public String getTripDestinationCity() {
        return tripDestinationCity;
    }

    public boolean getisTripInProgress() {
        return tripInProgress;
    }

    public boolean getisTripCompleted() {
        return tripCompleted;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public ArrayList<ObjectBaseEvent> getTripEventList() {
        return tripEventList;
    }
}
