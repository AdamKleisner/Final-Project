package com.example.ajp.s_cape_app.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.ajp.s_cape_app.Adapters.CustomAdapter_Events;
import com.example.ajp.s_cape_app.Objects.ObjectTrip;
import com.example.ajp.s_cape_app.R;


public class Activity_PastTrips extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView eventList;
    ObjectTrip trip;
    TextView flightInfo;
    TextView hotelInfo;
    ImageView flightPic;
    ImageView hotelPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__pasttrips);



        Intent startIntent = getIntent();
        Bundle extras = startIntent.getExtras();
        trip = (ObjectTrip) startIntent.getSerializableExtra("trip");

        eventList = (ListView) findViewById(R.id.listEvents);
        flightInfo = (TextView) findViewById(R.id.flightInfo) ;
        hotelInfo = (TextView) findViewById(R.id.HotelInfo);
        flightPic = (ImageView)findViewById(R.id.flightPic);
        hotelPic =(ImageView) findViewById(R.id.hotelPic);


        flightInfo.setText(trip.tripStartDate);
        hotelInfo.setText(trip.hotelName);
        flightPic.setImageResource(R.drawable.image_flight);
        hotelPic.setImageResource(R.drawable.image_hotel);
        eventList.setOnItemClickListener(this);

        //ArrayAdapter<ObjectBaseEvent> adapter = new ArrayAdapter<ObjectBaseEvent>(this,android.R.layout.simple_list_item_1,trip.tripEventList);
        CustomAdapter_Events adapter1 = new CustomAdapter_Events(this,trip.tripEventList,trip.getTripName(),trip.getisTripCompleted());

        eventList.setAdapter(adapter1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);

        }

        //begin prettifying
        setBackground(this, R.drawable.itinerary_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView itineraryTitle = (TextView)findViewById(R.id.textView_TitleIntenerary);
        itineraryTitle.setText(trip.getTripName());
        //end prettifying

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterSave = getMenuInflater();
        inflaterSave.inflate(R.menu.itenerary_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //go to translation activity

        if (item.getItemId() == R.id.menuTranslate){
            //go to translation activity
            Log.i("reached menu","translate");

            Intent translate = new Intent(this,Activity_Translate.class);
            startActivity(translate);

        }else{
            //go to challenges
            Log.i("reached menu","Challenge");
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //go to route
        Intent routeIntent = new Intent(this, Activity_Route.class);
        routeIntent.putExtra("destination",trip.getTripEventList().get(i));
        startActivity(routeIntent);
    }

    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout) findViewById(R.id.pastTrips_background);
        layout.setBackground(bitmapDrawable);
    }
}
