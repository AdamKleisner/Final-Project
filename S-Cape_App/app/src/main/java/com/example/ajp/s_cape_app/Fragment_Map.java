package com.example.ajp.s_cape_app;


import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class Fragment_Map extends MapFragment implements OnMapReadyCallback {
    public static final String tag = "MapFrag_TAG";

    public static Fragment_Map newInstance(){
        return new Fragment_Map();
    }

    private GoogleMap mMap;

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
