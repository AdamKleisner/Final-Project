package com.example.ajp.s_cape_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ajp.s_cape_app.API_PULLS.Api_Pull_Hotel;
import com.example.ajp.s_cape_app.Objects.Object_Api_Pull;
import com.example.ajp.s_cape_app.R;
import com.example.ajp.s_cape_app.Adapters.CustomAdapter_ApiPull;

import java.util.ArrayList;


public class Activity_Hotel extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView foodList;
    String city;
    String state;
    String returnDate;
    String departingDate;
    String lat;
    String lng;
    ArrayList<Object_Api_Pull> apiArrayThatWasReturned;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__food);

        // BEGIN PRETTIFYING
        setBackground(this, R.drawable.hotel_finder_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_API_PULL_TITLE);
        banner.setText("Rest your head");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        // END PRETTIFYING

        Intent i = getIntent();
        city = i.getStringExtra("city");
        state = i.getStringExtra("state");
        returnDate = i.getStringExtra("returnDate");
        departingDate = i.getStringExtra("departingDate");
        Spinner spinner = (Spinner)  findViewById(R.id.spinnerFoodType);

        spinner.setVisibility(View.GONE);

        Api_Pull_Hotel pull = new Api_Pull_Hotel(this);

        foodList = (ListView) findViewById(R.id.foodList);

        foodList.setOnItemClickListener(this);

        String apiData = "https://api.foursquare.com/v2/venues/explore?near=" + city +","+ state +"&query=hotel&v=20150214&m=foursquare&client_secret=KV002PMEG5FYVXDTYMKY0RPF5RY5DJH4HYRIRTQK3DNODB3Y&client_id=UMU4IH4FH0422RRF5N2NY4N22II5DRVK23W0PN4LOJIPF0TQ&price=1";

        pull.execute(apiData);
    }

    public void update(ArrayList<Object_Api_Pull> recievedArray){
        apiArrayThatWasReturned = new ArrayList<>();
        apiArrayThatWasReturned = recievedArray;

        //ArrayAdapter<Object_Api_Pull> adapter = new ArrayAdapter<Object_Api_Pull>(Activity_Hotel.this,android.R.layout.simple_list_item_1, recievedArray);
        CustomAdapter_ApiPull adapter = new CustomAdapter_ApiPull(this, recievedArray);
        foodList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent eventPlanner = new Intent(this,Activity_EventPlanner.class);
        eventPlanner.putExtra("selectedHotel",apiArrayThatWasReturned.get(i));
        eventPlanner.putExtra("city",city);
        eventPlanner.putExtra("state",state);
        eventPlanner.putExtra("returnDate",returnDate);
        eventPlanner.putExtra("departingDate",departingDate);
        eventPlanner.putExtra("lat",apiArrayThatWasReturned.get(i).getLat());
        eventPlanner.putExtra("lng",apiArrayThatWasReturned.get(i).getLng());
        startActivity(eventPlanner);
        finish();
    }

    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__api_layouts);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}
