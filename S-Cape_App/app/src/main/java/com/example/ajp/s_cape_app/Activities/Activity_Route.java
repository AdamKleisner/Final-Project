package com.example.ajp.s_cape_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ajp.s_cape_app.Fragment_Map_Route;
import com.example.ajp.s_cape_app.Objects.ObjectBaseEvent;
import com.example.ajp.s_cape_app.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Activity_Route extends AppCompatActivity {

    ObjectBaseEvent event;
    Geocoder geo;
    List<Address> userAddress;
    Double lngStart;
    Double latStart;
    String fullAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__route);

        Intent startIntent = getIntent();
        event = (ObjectBaseEvent) startIntent.getSerializableExtra("destination");


        gettingCurrentPosition();
        Fragment_Map_Route frag = Fragment_Map_Route.newInstance(event,this,latStart,lngStart);
        getFragmentManager().beginTransaction().replace(R.id.fragment_map_container,frag, Fragment_Map_Route.tag).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterSave = getMenuInflater();
        inflaterSave.inflate(R.menu.route_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent directions = new Intent(this, Activity_Directions_List.class);
        directions.putExtra("fullAddress",fullAddress);
        directions.putExtra("endAddress",event);
        startActivity(directions);
        return true;
    }

    public void gettingCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if(lm != null) {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null){
                    lngStart = location.getLongitude();
                    latStart = location.getLatitude();
                    geo = new Geocoder(this, Locale.getDefault());

                try {
                    userAddress = geo.getFromLocation(latStart, lngStart, 1);

                    String address = userAddress.get(0).getAddressLine(0);

                    String area = userAddress.get(0).getLocality();

                    String city = userAddress.get(0).getAdminArea();


                    fullAddress = address + ", " + area + ", " + city;


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }
        }
    }
}
