package com.example.ajp.s_cape_app.Activities;


import android.app.Activity;
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
import android.widget.TextView;

import com.example.ajp.s_cape_app.API_PULLS.Api_Pull_VistPlaces;
import com.example.ajp.s_cape_app.Objects.Object_Api_Pull;
import com.example.ajp.s_cape_app.R;
import com.example.ajp.s_cape_app.Adapters.CustomAdapter_ApiPull;

import java.util.ArrayList;

public class Activity_VistPlaces extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView vistPlaces;
    String category;
    String city;
    String state;
    ArrayList<Object_Api_Pull> arrayReturnedFromApiPull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__placesvists);

        //Begin Prettifying
        setBackground(this, R.drawable.trending_finder_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_Trending_Title);
        banner.setText("With the crowd");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        //End Prettifying

        Intent startIntent = getIntent();
        Bundle extras = startIntent.getExtras();
        category = extras.getString("CATEGORY");
        state = extras.getString("STATE");
        city = extras.getString("CITY");

        arrayReturnedFromApiPull = new ArrayList<>();

        Api_Pull_VistPlaces pull = new Api_Pull_VistPlaces(this);

        vistPlaces = (ListView) findViewById(R.id.vistList);
        vistPlaces.setOnItemClickListener(this);

        String apiData = "https://api.foursquare.com/v2/venues/explore?near="+ city + ","+ state + "&section=trending&v=20150214&m=foursquare&client_secret=KV002PMEG5FYVXDTYMKY0RPF5RY5DJH4HYRIRTQK3DNODB3Y&client_id=UMU4IH4FH0422RRF5N2NY4N22II5DRVK23W0PN4LOJIPF0TQ";

        pull.execute(apiData);

    }

    public void update(ArrayList<Object_Api_Pull> recievedArray){

        arrayReturnedFromApiPull = recievedArray;

        //ArrayAdapter<Object_Api_Pull> adapter = new ArrayAdapter<Object_Api_Pull>(Activity_VistPlaces.this,android.R.layout.simple_list_item_1, recievedArray);
        CustomAdapter_ApiPull adapter = new CustomAdapter_ApiPull(this, recievedArray);

        vistPlaces.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        // send back result
        Intent resultIntent = new Intent();
        resultIntent.putExtra("CATEGORY", category);
        resultIntent.putExtra("TITLE",arrayReturnedFromApiPull.get(i).getName());
        resultIntent.putExtra("ADDRESS",arrayReturnedFromApiPull.get(i).getAddress());
        resultIntent.putExtra("DESCRIPTION",arrayReturnedFromApiPull.get(i).getCategory());
        resultIntent.putExtra("lat",arrayReturnedFromApiPull.get(i).getLat());
        resultIntent.putExtra("lng",arrayReturnedFromApiPull.get(i).getLng());
        resultIntent.putExtra("price",arrayReturnedFromApiPull.get(i).getPrice());
        setResult(Activity.RESULT_OK, resultIntent);
        //finish
        finish();
    }

    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_TrendingAPI);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}
