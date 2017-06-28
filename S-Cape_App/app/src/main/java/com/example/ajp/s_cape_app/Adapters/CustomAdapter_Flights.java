package com.example.ajp.s_cape_app.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ajp.s_cape_app.Objects.Object_FlightFare;
import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;

/**
 * Created by AJP on 5/8/17.
 */

public class CustomAdapter_Flights extends BaseAdapter {

    private Context context;
    private ArrayList<Object_FlightFare> flightListData = new ArrayList<>();
    private int price;

    public CustomAdapter_Flights(Context context, ArrayList<Object_FlightFare> flightListData, int price) {
        this.context = context;
        this.flightListData = flightListData;
        this.price = price;
    }

    @Override
    public int getCount() {
        return flightListData.size();
    }

    @Override
    public Object getItem(int i) {
        return flightListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_listview_flights, parent, false);
        }

        Object_FlightFare currentFlight = (Object_FlightFare) getItem(position);

        TextView textViewFlightPrice = (TextView)
                convertView.findViewById(R.id.textView_FlightPricing);
        TextView textViewFlightAirline = (TextView)
                convertView.findViewById(R.id.textView_FlightAirline);
        TextView textViewFlightDestination = (TextView)
                convertView.findViewById(R.id.textView_FlightDestinationList);

        textViewFlightPrice.setText(currentFlight.getFare());
        if (Double.parseDouble(currentFlight.getFare()) < price) {
            textViewFlightPrice.setTextColor(Color.BLUE);
        } else if (Double.parseDouble(currentFlight.getFare()) > price) {
            textViewFlightPrice.setTextColor(Color.RED);
        }
        textViewFlightAirline.setText(currentFlight.getAirlines());
        textViewFlightDestination.setText(currentFlight.getDestination());

        return convertView;
    }
}
