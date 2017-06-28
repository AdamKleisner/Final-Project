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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ajp.s_cape_app.API_PULLS.Api_Pull_Food;
import com.example.ajp.s_cape_app.Objects.Object_Api_Pull;
import com.example.ajp.s_cape_app.R;
import com.example.ajp.s_cape_app.Adapters.CustomAdapter_ApiPull;

import java.util.ArrayList;

public class Activity_Food extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    ListView foodList;
    ArrayList<Object_Api_Pull> arrayReturnedFromApiPull;
    String category;
    String city;
    String state;
    Spinner spinner;
    Api_Pull_Food pull;
    ArrayList<Object_Api_Pull> filteredArray;
    CustomAdapter_ApiPull adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__food);

        //Begin Prettifying
        setBackground(this, R.drawable.food_finder_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_API_PULL_TITLE);
        banner.setText("Grab a bite");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        //End Prettifying

        Intent startIntent = getIntent();
        Bundle extras = startIntent.getExtras();
        category = extras.getString("CATEGORY");
        city = extras.getString("CITY");
        state = extras.getString("STATE");


        spinner = (Spinner) findViewById(R.id.spinnerFoodType);
        spinner.setOnItemSelectedListener(this);

        arrayReturnedFromApiPull = new ArrayList<>();
        filteredArray = new ArrayList<>();

        foodList = (ListView) findViewById(R.id.foodList);

        foodList.setOnItemClickListener(this);


    }

    public void update(ArrayList<Object_Api_Pull> recievedArray){

        //ArrayAdapter<Object_Api_Pull> adapter = new ArrayAdapter<Object_Api_Pull>(Activity_Food.this,android.R.layout.simple_list_item_1, recievedArray);
       arrayReturnedFromApiPull = recievedArray;
        adapter = new CustomAdapter_ApiPull(this, recievedArray);

        foodList.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//        Intent sightsIntent = new Intent(this,Activity_Sights.class);
//        startActivity(sightsIntent);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            switch (spinner.getSelectedItem().toString()) {
                case "All":

                    Log.i("All downloaded"," first hit");

                    pull = new Api_Pull_Food(this);
                    String apiData = "https://api.foursquare.com/v2/venues/explore?near=" + city + "," + state + "&section=food&v=20150214&m=foursquare&client_secret=KV002PMEG5FYVXDTYMKY0RPF5RY5DJH4HYRIRTQK3DNODB3Y&client_id=UMU4IH4FH0422RRF5N2NY4N22II5DRVK23W0PN4LOJIPF0TQ";
                    pull.execute(apiData);

                    break;
                case "Italian":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("Italian")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    break;
                case "Pizza":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("Pizza")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    break;
                case "Mediterranean":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("Mediterranean")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    break;
                case "BBQ":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("BBQ")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    break;
                case "American":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("American")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    Log.i("American"," food");
                    break;
                case "Mexican":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("Mexican")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    break;
                case "Southern / Soul":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("Southern / Soul")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    break;

                case "Deli / Bodega":

                    filteredArray.clear();

                    for(int j = 0; j <arrayReturnedFromApiPull.size();j++){
                        if(arrayReturnedFromApiPull.get(j).getDescription().equals("Deli / Bodega")){
                            filteredArray.add(arrayReturnedFromApiPull.get(j));
                        };
                    }

                    adapter = new CustomAdapter_ApiPull(this, filteredArray);

                    foodList.setAdapter(adapter);

                    break;
                default:

                    Log.i("Not supposed to do that","nooooo");

                    update(arrayReturnedFromApiPull);

                    break;
            }
        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
