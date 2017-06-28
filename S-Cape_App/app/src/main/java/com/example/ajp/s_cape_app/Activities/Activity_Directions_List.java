package com.example.ajp.s_cape_app.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ajp.s_cape_app.API_PULLS.Api_Pull_Directions;
import com.example.ajp.s_cape_app.Adapters.CustomAdapter_Directions;
import com.example.ajp.s_cape_app.Objects.ObjectBaseEvent;
import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;

public class Activity_Directions_List extends AppCompatActivity {

    ListView directions;
    String address;
    ArrayList<String> recievedArray;
    ObjectBaseEvent event;
    TextView durationDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__directions);

        Intent intent = getIntent();
        address = intent.getStringExtra("fullAddress");
        event = (ObjectBaseEvent) intent.getSerializableExtra("endAddress");
        directions = (ListView) findViewById(R.id.directionsList);
        durationDistance = (TextView) findViewById(R.id.textDurationDistance);

        String urlStartingAddress = address.replace(" ","%20");
        String urlEndingAddress = event.getAddress().replace(" ", "%20");

        if(address != null || !address.equals("")) {
            String url = "https://maps.googleapis.com/maps/api/directions/json?&origin=" + urlStartingAddress + "&destination=" + urlEndingAddress + "&sensor=false&key=AIzaSyBmRMHwlIOsArJNwraLk-EHM4djI69_FFI";
            Api_Pull_Directions pulling = new Api_Pull_Directions(this);
            pulling.execute(url);
        }

    }

    public void updateDirections(ArrayList<String> directionsString, String _distance, String _duration){

        recievedArray = directionsString;
        durationDistance.setText("Total distance: " + _distance + " Total time: " + _duration);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recievedArray);

        CustomAdapter_Directions adapter = new CustomAdapter_Directions(this,recievedArray);

        directions.setAdapter(adapter);
    }
}
