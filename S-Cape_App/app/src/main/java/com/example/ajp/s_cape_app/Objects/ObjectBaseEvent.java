package com.example.ajp.s_cape_app.Objects;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by AJP on 4/27/17.
 */

public class ObjectBaseEvent implements Serializable {

    public String category;
    public String name;
    public String address;
    public String desciption;
    public String lat;
    public String lng;
    public String price;
    public ArrayList<String> picturesIds;

    public ObjectBaseEvent(String category, String name, String address, String description, String lat, String lng, String price, ArrayList<String> picturesIds) {
        this.category = category;
        this.name = name;
        this.address = address;
        this.desciption = description;
        this.lat = lat;
        this.lng = lng;
        this.price = price;
        this.picturesIds = picturesIds;
    }

    public ObjectBaseEvent(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getDesciption() {
        return desciption;
    }

    public ArrayList<String> getPicturesIds() {
        return picturesIds;
    }

    public String getPrice() {
        return price;
    }
}
