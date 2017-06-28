package com.example.ajp.s_cape_app.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.ajp.s_cape_app.Adapters.CustomAdapter_Events;
import com.example.ajp.s_cape_app.GlobalUserName;
import com.example.ajp.s_cape_app.Objects.ObjectTrip;
import com.example.ajp.s_cape_app.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;


public class Activity_Itenerary extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView eventList;
    ObjectTrip trip;
    TextView flightInfo;
    TextView hotelInfo;
    ImageView flightPic;
    ImageView hotelPic;
    LoginButton loginButton;
//    Button tripComplete;
    FloatingActionMenu iteneraryMenu;
    FloatingActionButton floatingActionButtonChallenges, floatingActionButtonFinishTrip, floatingActionButtonTranslate;

    CallbackManager callbackManager;
//    LoginManager loginManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity__itentitentary);

        //begin prettifying
        setBackground(this, R.drawable.itinerary_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView itineraryTitle = (TextView)findViewById(R.id.textView_TitleIntenerary);
        itineraryTitle.setText("Itinerary");
        //end prettifying


        Intent startIntent = getIntent();
        Bundle extras = startIntent.getExtras();
        trip = (ObjectTrip) startIntent.getSerializableExtra("trip");

        eventList = (ListView) findViewById(R.id.listEvents);
        flightInfo = (TextView) findViewById(R.id.flightInfo) ;
        hotelInfo = (TextView) findViewById(R.id.HotelInfo);
        flightPic = (ImageView)findViewById(R.id.flightPic);
        hotelPic =(ImageView) findViewById(R.id.hotelPic);
//        tripComplete = (Button) findViewById(R.id.buttonFinishTrip);

        loginButton = (LoginButton)findViewById(R.id.login_button);

        flightInfo.setText(trip.tripStartDate);
        hotelInfo.setText(trip.hotelName);

        if(!trip.getTripStartDate().equals("NA") && !trip.getTripStartDate().equals("Car")) {
            flightPic.setImageResource(R.drawable.plane_red);
        }else{
            flightInfo.setText("Road Trip!");
            flightPic.setImageResource(R.drawable.automobile);
        }

        hotelPic.setImageResource(R.drawable.image_hotel);
        eventList.setOnItemClickListener(this);
//        tripComplete.setOnClickListener(this);

        iteneraryMenu = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu_itenerary);
        floatingActionButtonChallenges = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Directions);
        floatingActionButtonFinishTrip = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_End_Trip);
        floatingActionButtonTranslate = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_Translate);


        eventList.setAdapter(null);

        //ArrayAdapter<ObjectBaseEvent> adapter = new ArrayAdapter<ObjectBaseEvent>(this,android.R.layout.simple_list_item_1,trip.tripEventList);
        CustomAdapter_Events adapter1 = new CustomAdapter_Events(this,trip.tripEventList,trip.getTripName(),trip.getisTripCompleted());

        eventList.setAdapter(adapter1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);

        }

        floatingActionButtonFinishTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //have finish trip code here

                Map<String,Object> taskMap = new HashMap<String,Object>();
                taskMap.put("tripCompleted", true);

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().
                        getRoot().child("users")
                        .child(GlobalUserName.userName.replace("@", "").replace(".", "")).child(trip.getTripName());
                ref.updateChildren(taskMap);

                finish();
            }
        });

        floatingActionButtonTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //have translate code here

                Intent translate = new Intent(getApplicationContext(),Activity_Translate.class);
                startActivity(translate);
            }
        });

        floatingActionButtonChallenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //have challenges code here

                Intent challengeIntent = new Intent(getApplicationContext(), Activity_Challenges.class);
                startActivity(challengeIntent);
            }
        });



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //go to route
        Intent routeIntent = new Intent(this, Activity_Route.class);
        routeIntent.putExtra("destination",trip.getTripEventList().get(i));
        startActivity(routeIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        eventList.setAdapter(null);

        CustomAdapter_Events adapter1 = new CustomAdapter_Events(this,trip.tripEventList,trip.getTripName(),trip.getisTripCompleted());

        eventList.setAdapter(adapter1);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }



//    @Override
//    public void onClick(View view) {
//
//        Map<String,Object> taskMap = new HashMap<String,Object>();
//        taskMap.put("tripCompleted", true);
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference().
//                getRoot().child("users")
//                .child(GlobalUserName.userName.replace("@", "").replace(".", "")).child(trip.getTripName());
//        ref.updateChildren(taskMap);
//
//        finish();
//    }

    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        ScrollView layout = (ScrollView) findViewById(R.id.scrollView_itinerary_bg);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}
