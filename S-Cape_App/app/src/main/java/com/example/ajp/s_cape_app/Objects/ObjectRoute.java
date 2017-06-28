package com.example.ajp.s_cape_app.Objects;


import java.util.ArrayList;

public class ObjectRoute {

    String distance;
    String duration;
    ArrayList<String> arraySteps;

    public ObjectRoute(String _distance, String _duration, ArrayList<String> _arraySteps){
        distance = _distance;
        duration = _duration;
        arraySteps = _arraySteps;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public ArrayList<String> getArraySteps() {
        return arraySteps;
    }
}
