package com.example.ajp.s_cape_app.Objects;

/**
 * Created by AJP on 5/8/17.
 */

public class Object_FlightFare {

    String destination;
    String fare;
    String airlines;

    public Object_FlightFare(String destination, String fare, String airlines) {
        this.destination = destination;
        this.fare = fare;
        this.airlines = airlines;
    }

    public String getDestination() {
        return destination;
    }

    public String getFare() {
        return fare;
    }

    public String getAirlines() {
        return airlines;
    }
}
