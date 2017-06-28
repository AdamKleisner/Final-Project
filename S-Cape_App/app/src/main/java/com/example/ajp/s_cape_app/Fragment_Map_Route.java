package com.example.ajp.s_cape_app;

import android.content.Context;
import android.os.Bundle;

import com.cs.googlemaproute.DrawRoute;
import com.example.ajp.s_cape_app.Objects.ObjectBaseEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Map_Route extends MapFragment implements OnMapReadyCallback, DrawRoute.onDrawRoute {
    public static final String tag = "MapFrag_TAG";

    static ObjectBaseEvent event;
    static Double lat;
    static Double lng;
    static Double lngStart;
    static Double latStart;
    static Context context;

    public static Fragment_Map_Route newInstance(ObjectBaseEvent _event, Context _context, Double _latStart, Double _lngStart) {
        event = _event;
        context = _context;
        lat = Double.parseDouble(_event.getLat());
        lng = Double.parseDouble(_event.getLng());
        lngStart = _lngStart;
        latStart = _latStart;
        return new Fragment_Map_Route();
    }

    private GoogleMap mMap;

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        getMapAsync(this);

    }

    private void mappMarker() {
        if (mMap == null) {
            return;
        }
        MarkerOptions options = new MarkerOptions();
        options.title("Starting Point");

        LatLng startLocation = new LatLng(latStart, lngStart);
        options.position(startLocation);
        mMap.addMarker(options);

        MarkerOptions options2 = new MarkerOptions();
        options2.title("Ending Point");

        LatLng endLocation = new LatLng(lat, lng);
        options2.position(endLocation);
        mMap.addMarker(options2);


        DrawRoute.getInstance(this, context).setFromLatLong(latStart, lngStart)
                .setToLatLong(lat, lng).setGmapAndKey("AIzaSyBmRMHwlIOsArJNwraLk-EHM4djI69_FFI", mMap)
                .setColorHash("#20bf55").setLoader(true)
                .setLoaderMsg("Calculating route").run();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mappMarker();
    }

    @Override
    public void afterDraw(String result) {

    }
}

