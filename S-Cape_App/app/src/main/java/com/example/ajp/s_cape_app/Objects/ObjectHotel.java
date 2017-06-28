package com.example.ajp.s_cape_app.Objects;


public class ObjectHotel {

    String hotelName;
    String hotelPrice;
    String hotelAddress;

    public ObjectHotel(String _hotelName, String _hotelPrice, String _hotelAddress){
        hotelName = _hotelName;
        hotelPrice = _hotelPrice;
        hotelAddress = _hotelAddress;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

}
