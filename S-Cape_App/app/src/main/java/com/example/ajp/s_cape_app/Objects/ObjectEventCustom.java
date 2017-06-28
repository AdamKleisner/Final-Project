package com.example.ajp.s_cape_app.Objects;

import java.util.ArrayList;

/**
 * Created by AJP on 4/27/17.
 */

public class ObjectEventCustom extends ObjectBaseEvent {

    public String eventName;
    public String address;
    public String category;
    public ArrayList<String> picturesIds;

    public ObjectEventCustom(String _category, String eventName, String address, ArrayList<String> picturesIds) {
        category = _category;
        this.eventName = eventName;
        this.address = address;
        this.picturesIds = picturesIds;
    }

    public ObjectEventCustom(){

    }

    public String getEventName() {
        return eventName;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getPicturesIds () {return picturesIds;}
}
