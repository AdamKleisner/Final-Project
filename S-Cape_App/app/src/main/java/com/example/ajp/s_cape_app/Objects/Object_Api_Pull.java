package com.example.ajp.s_cape_app.Objects;

import java.io.Serializable;

public class Object_Api_Pull implements Serializable {

    public String name;
    public String address;
    public String price;
    public String category;
    public String lat;
    public String lng;
    public String description;


    public Object_Api_Pull(String _name, String _address, String _price, String _category, String _lat, String _lng, String _description){
        name = _name;
        address = _address;
        price = _price;
        category = _category;
        lat = _lat;
        lng = _lng;
        description = _description;
    }

    public Object_Api_Pull(){

    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getDescription() {
        return description;
    }
}
