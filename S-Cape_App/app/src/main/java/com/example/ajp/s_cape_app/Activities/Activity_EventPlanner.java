package com.example.ajp.s_cape_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ajp.s_cape_app.GlobalUserName;
import com.example.ajp.s_cape_app.Objects.ObjectBaseEvent;
import com.example.ajp.s_cape_app.Objects.ObjectTrip;
import com.example.ajp.s_cape_app.Objects.Object_Api_Pull;
import com.example.ajp.s_cape_app.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_EventPlanner extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButtonFood, floatingActionButtonSights, floatingActionButtonTrending, floatingActionButtonCustom;

    Object_Api_Pull selectedHotel;
    String city;
    String state;
    String returnDate;
    String departingDate;
    FloatingActionMenu materialDesignFAMComplete;
    FloatingActionButton floatingActionButtonFinishTrip;
    ArrayList<String> picturesIds;
    String category;

    ArrayList<ObjectBaseEvent> currentEventList;
    GridView eventGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__event_planner);

        //Begin Prettifying
        setBackground(this, R.drawable.event_planner_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_EventPlanner_Banner);
        banner.setText("Plan it out");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        //End Prettifying

        Intent i = getIntent();
        city = i.getStringExtra("city");
        state = i.getStringExtra("state");
        returnDate = i.getStringExtra("returnDate");
        departingDate = i.getStringExtra("departingDate");
        selectedHotel = (Object_Api_Pull) i.getSerializableExtra("selectedHotel");

        final EditText tripName = (EditText)findViewById(R.id.editText_EventPlanner_TripName);

        picturesIds = new ArrayList<>();
        picturesIds.add("test");

        materialDesignFAMComplete = (FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu_COMPLETE);
        floatingActionButtonFinishTrip = (FloatingActionButton)findViewById(R.id.material_design_floating_action_menu_COMPLETE_Finish);

        // Make it unReachable until we have a list with values
        materialDesignFAMComplete.setVisibility(View.GONE);

        floatingActionButtonFinishTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // COMPLETING TRIP AND GOING TO THE NAMING AND COMPLETION STAGE
                String tripTitle;
                if (!tripName.getText().toString().equals("")) {
                    tripTitle = tripName.getText().toString();

                    //CREATE OUR TRIP AND POST TO FIREBASE
                    ObjectTrip trip = new ObjectTrip(tripTitle, departingDate, returnDate, city, city,
                            false, false, selectedHotel.getName(), selectedHotel.getAddress(), selectedHotel.getPrice(), currentEventList);


                    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
                    String user = GlobalUserName.userName.replace(".","").replace("@","").replace(".","");
                    data.child("users").child(user).child(tripTitle).setValue(trip);

                    Intent profileIntent = new Intent(getApplicationContext(), Activity_Profile.class);
                    startActivity(profileIntent);
                    finish();

                }
            }
        });

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButtonFood = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Food);
        floatingActionButtonSights = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Sights);
        floatingActionButtonTrending = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Trending);
        floatingActionButtonCustom = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Custom);

        currentEventList = new ArrayList<>();

        eventGridView = (GridView)findViewById(R.id.gridView_EventPlanner);

        //setting the adapter to the eventList
        if (!currentEventList.isEmpty()) {
            eventGridView.setAdapter(new Utility_gridView_EventAdapter(currentEventList));
        }


        // Click on Food
        floatingActionButtonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Food";
                Intent foodIntent = new Intent(getApplicationContext(), Activity_Food.class);
                foodIntent.putExtra("CATEGORY", category);
                foodIntent.putExtra("CITY",city);
                foodIntent.putExtra("STATE",state);
                startActivityForResult(foodIntent, 1);
            }
        });

        // Click on Sights
        floatingActionButtonSights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Sights";
                Intent sightsIntent = new Intent(getApplicationContext(), Activity_Sights.class);
                sightsIntent.putExtra("CATEGORY", category);
                sightsIntent.putExtra("CITY",city);
                sightsIntent.putExtra("STATE",state);
                startActivityForResult(sightsIntent, 2);
            }
        });

        // Click on Trending
        floatingActionButtonTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Trending";
                Intent trendingIntent = new Intent(getApplicationContext(), Activity_VistPlaces.class);
                trendingIntent.putExtra("CATEGORY", category);
                trendingIntent.putExtra("CITY",city);
                trendingIntent.putExtra("STATE",state);
                startActivityForResult(trendingIntent, 3);
            }
        });

        // Click on Custom
        floatingActionButtonCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Custom";
                Intent customIntent = new Intent(getApplicationContext(), Activity_CustomEvent.class);
                customIntent.putExtra("CATEGORY", category);
                startActivityForResult(customIntent, 4);
            }
        });

    }

    //region CustomAdapter for gridview

    // This will be our custom adapter for our gridview
    public class Utility_gridView_EventAdapter extends BaseAdapter {

        ArrayList<ObjectBaseEvent> listOfEvents;

        public Utility_gridView_EventAdapter(ArrayList<ObjectBaseEvent> listOfEvents) {
            this.listOfEvents = listOfEvents;
        }

        @Override
        public int getCount() {
            return listOfEvents.size();
        }

        @Override
        public Object getItem(int i) {
            return listOfEvents.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.layout_gridview_eventplanner, parent, false);

            TextView eventTypeText = (TextView)row.findViewById(R.id.textView_GridView_EventType);
            ImageView categoryEventIcon = (ImageView)row.findViewById(R.id.imageView_GridView_EventImage);

            switch (listOfEvents.get(i).getCategory()) {
                case "Sights":
                    categoryEventIcon.setImageDrawable(getResources().getDrawable(R.drawable.image_sight));
                    break;
                case "Food":
                    categoryEventIcon.setImageDrawable(getResources().getDrawable(R.drawable.image_food));
                    break;
                case "Trending":
                    categoryEventIcon.setImageDrawable(getResources().getDrawable(R.drawable.image_visitplace));
                    break;
                case "Custom":
                    categoryEventIcon.setImageDrawable(getResources().getDrawable(R.drawable.image_customevent));
                    break;
            }

            // setting our category here
            eventTypeText.setText(listOfEvents.get(i).getCategory());

            //Setting image view based off category here
            //Todo change from null to the icon based off what type of event it is!
            //categoryEventIcon.setImageBitmap(null);


            return row;
        }
    }
    //endregion


    //region GetResultFromActivity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 4:
                if (resultCode == RESULT_OK) {
                    Bundle customEventObject = data.getExtras();
                    String title = customEventObject.getString("TITLE");
                    String address = customEventObject.getString("ADDRESS");
                    String category = customEventObject.getString("CATEGORY");
                    String lat = customEventObject.getString("lat");
                    String lng = customEventObject.getString("lng");
                    String price = customEventObject.getString("price");

                    //ObjectEventCustom newCustomEvent = new ObjectEventCustom(category, title, address, lat, lng);
                    ObjectBaseEvent newEvent = new ObjectBaseEvent(category,title,address,"",lat,lng,price,picturesIds);
                    currentEventList.add(newEvent);

                    updateList();
                }
                break;

            case 1:
                if(resultCode == RESULT_OK){
                    Bundle customEventObject = data.getExtras();
                    String title = customEventObject.getString("TITLE");
                    String address = customEventObject.getString("ADDRESS");
                    String category = customEventObject.getString("CATEGORY");
                    String description = customEventObject.getString("DESCRIPTION");
                    String lat = customEventObject.getString("lat");
                    String lng = customEventObject.getString("lng");
                    String price = customEventObject.getString("price");

                    ObjectBaseEvent newEvent = new ObjectBaseEvent(category,title,address,description,lat,lng,price,picturesIds);
                    currentEventList.add(newEvent);

                    updateList();
                }
                break;

            case 2:
                if(resultCode == RESULT_OK){
                    Bundle customEventObject = data.getExtras();
                    String title = customEventObject.getString("TITLE");
                    String address = customEventObject.getString("ADDRESS");
                    String category = customEventObject.getString("CATEGORY");
                    String description = customEventObject.getString("DESCRIPTION");
                    String lat = customEventObject.getString("lat");
                    String lng = customEventObject.getString("lng");
                    String price = customEventObject.getString("price");

                    ObjectBaseEvent newEvent = new ObjectBaseEvent(category,title,address,description,lat,lng,price,picturesIds);
                    currentEventList.add(newEvent);

                    updateList();
                }
                break;

            case 3:
                if(resultCode == RESULT_OK){
                    Bundle customEventObject = data.getExtras();
                    String title = customEventObject.getString("TITLE");
                    String address = customEventObject.getString("ADDRESS");
                    String category = customEventObject.getString("CATEGORY");
                    String description = customEventObject.getString("DESCRIPTION");
                    String lat = customEventObject.getString("lat");
                    String lng = customEventObject.getString("lng");
                    String price = customEventObject.getString("price");

                    ObjectBaseEvent newEvent = new ObjectBaseEvent(category,title,address,description,lat,lng,price,picturesIds);
                    currentEventList.add(newEvent);

                    updateList();
                }
                break;
        }
    }

    //endregion


    public void updateList() {
        eventGridView.setAdapter(new Utility_gridView_EventAdapter(currentEventList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentEventList.size() > 0) {
            materialDesignFAMComplete.setVisibility(View.VISIBLE);
        }
    }


    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__event_planner);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}
