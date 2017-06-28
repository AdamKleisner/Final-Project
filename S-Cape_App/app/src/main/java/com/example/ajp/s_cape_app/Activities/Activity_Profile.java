package com.example.ajp.s_cape_app.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ajp.s_cape_app.Adapters.CustomAdapter_ProfileActivity;
import com.example.ajp.s_cape_app.GlobalUserName;
import com.example.ajp.s_cape_app.Objects.ObjectTrip;
import com.example.ajp.s_cape_app.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Activity_Profile extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ArrayList<ObjectTrip> trips;
    ListView tripsList;
    int selectedIndex;
    CustomAdapter_ProfileActivity adapter;
    TextView pastTrips;
    TextView currentTrips;

    FloatingActionButton fabProfileToSelection;

    @Override
    protected void onResume() {
        super.onResume();

        currentTrips.setTypeface(Typeface.DEFAULT_BOLD);
        pastTrips.setTypeface(Typeface.DEFAULT);
        trips.clear();
        tripsList.setAdapter(null);
        readingInTrips();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);

        //BEGIN PRETTIFY

        //Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // Setting up the background picture here
        setBackground(this, R.drawable.profile_drawable_fade);

        TextView banner = (TextView) findViewById(R.id.textView_profile_Title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);

        //END PRETTIFY

        fabProfileToSelection = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_ProfileToMap);

        fabProfileToSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });

        pastTrips = (TextView) findViewById(R.id.textView_PastTrips);
        currentTrips = (TextView) findViewById(R.id.textView_PlannedTrips);

        currentTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trips.clear();
                readingInTrips();
                currentTrips.setTypeface(Typeface.DEFAULT_BOLD);
                pastTrips.setTypeface(Typeface.DEFAULT);

            }
        });

        pastTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Past trips will be presented", Toast.LENGTH_LONG).show();
                trips.clear();
                readingInPastTrips();
                pastTrips.setTypeface(Typeface.DEFAULT_BOLD);
                currentTrips.setTypeface(Typeface.DEFAULT);
            }
        });

        trips = new ArrayList<>();

        tripsList = (ListView) findViewById(R.id.upcomingTrips);
        tripsList.setOnItemClickListener(this);
        tripsList.setOnItemLongClickListener(this);

    }

    public void updateList() {

        tripsList.setAdapter(null);
        adapter = new CustomAdapter_ProfileActivity(this, trips);
        tripsList.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

        selectedIndex = i;

        if (trips.get(0).getisTripCompleted()) {
            //functionality for past trips

            startIntentToPastEvents(selectedIndex);

        }else{

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("Do you want to start the trip?");
        build.setCancelable(true);

        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                startIntentToEvents(selectedIndex);


            }
        });

        build.setNeutralButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                updateAlert(i);

            }
        });

        build.setNegativeButton("Delete Trip", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                FirebaseDatabase.getInstance().getReference().
                        getRoot().child("users").child(GlobalUserName.userName.replace("@", "").replace(".", "")).
                        child(trips.get(selectedIndex).tripName).removeValue();

                trips.clear();

//                        recreate();
                adapter.notifyDataSetChanged();

                //updating trip code until we finalize how we want to do it


            }
        });

        AlertDialog deleteAlert = build.create();
        deleteAlert.show();
    }

}
    public void startIntentToPastEvents (int i){

        ObjectTrip selectedTrip = (ObjectTrip) tripsList.getItemAtPosition(selectedIndex);
        Intent iteneraryIntent = new Intent(Activity_Profile.this, Activity_PastTrips.class);
        iteneraryIntent.putExtra("trip",selectedTrip);
        startActivity(iteneraryIntent);
    }

    public void startIntentToEvents (int i){

        ObjectTrip selectedTrip = (ObjectTrip) tripsList.getItemAtPosition(selectedIndex);
        Intent iteneraryIntent = new Intent(Activity_Profile.this, Activity_Itenerary.class);
        iteneraryIntent.putExtra("trip",selectedTrip);
        startActivity(iteneraryIntent);
    }

    public void readingInTrips(){

        trips.clear();

        //reading goes here
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().
                getRoot().child("users")
                .child(GlobalUserName.userName.replace("@","").replace(".",""));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    Log.i("Trip value child", childSnapshot.getValue().toString());

                            ObjectTrip trip2 = childSnapshot.getValue(ObjectTrip.class);

                    if(!trip2.getisTripCompleted()) {


                        trips.add(trip2);
                    }

                }
                updateList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void readingInPastTrips(){

        //reading goes here
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().
                getRoot().child("users")
                .child(GlobalUserName.userName.replace("@","").replace(".",""));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    Log.i("Trip value child", childSnapshot.getValue().toString());

                    ObjectTrip trip2 = childSnapshot.getValue(ObjectTrip.class);

                    if(trip2.getisTripCompleted()) {


                        trips.add(trip2);
                    }

                }
                updateList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//        ObjectTrip selectedTrip = (ObjectTrip) tripsList.getItemAtPosition(i);
//        Intent iteneraryIntent = new Intent(this, Activity_Itenerary.class);
//        iteneraryIntent.putExtra("trip",selectedTrip);
//        startActivity(iteneraryIntent);
//    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    public void updateAlert(final int selectedIndex){

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("Do you want to change the trip?");
        build.setCancelable(true);

        final EditText input = new EditText(Activity_Profile.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        build.setView(input);

        build.setPositiveButton("Set changes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                ObjectTrip updatedTrip = new ObjectTrip(input.getText().toString(),
                        trips.get(selectedIndex).getTripStartDate(),trips.get(selectedIndex).getTripStartDate(),
                        trips.get(selectedIndex).getTripOriginCity(),trips.get(selectedIndex).getTripDestinationCity(),
                        trips.get(selectedIndex).getisTripInProgress(),trips.get(selectedIndex).getisTripCompleted(),
                        trips.get(selectedIndex).getHotelName(),trips.get(selectedIndex).getHotelAddress(),
                        trips.get(selectedIndex).getHotelPrice(),trips.get(selectedIndex).getTripEventList());

                FirebaseDatabase.getInstance().getReference().
                        getRoot().child("users").child(GlobalUserName.userName.replace("@","").replace(".","")).
                        child(trips.get(selectedIndex).tripName).removeValue();

                FirebaseDatabase.getInstance().getReference().
                        getRoot().child("users").child(GlobalUserName.userName.replace("@","").replace(".","")).
                        child(input.getText().toString()).setValue(updatedTrip);

                trips.clear();
                adapter.notifyDataSetChanged();
            }
        });

        build.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog updateAlert = build.create();
        updateAlert.show();

    }

    //SETTING UP BACKGROUND IMAGE HERE
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__profile);
        layout.setBackground(bitmapDrawable);
    }

    public void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_car_planner, null);

        TextView banner = (TextView)view.findViewById(R.id.textView_Dialog_Profile_Title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        banner.setText("How are we getting there?");

        TextView plane = (TextView)view.findViewById(R.id.textView_Dialog_flight);
        plane.setTypeface(typeface);
        plane.setText("Flying");
        TextView car = (TextView)view.findViewById(R.id.textView_Dialog_car);
        car.setTypeface(typeface);
        car.setText("Road Trip");

        ImageView background = (ImageView)view.findViewById(R.id.dialog_profile_choice_bg);
        background.setImageResource(R.drawable.dialog_bg);

        ImageView planeImage = (ImageView)view.findViewById(R.id.imageView_dialog_profile_flight);
        ImageView carImage = (ImageView)view.findViewById(R.id.imageView_dialog_profile_car);

        planeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent flightIntent = new Intent(getApplicationContext(), Activity_FlightWorking.class);
                flightIntent.putExtra("city","Orlando");
                flightIntent.putExtra("state","FL");
                startActivity(flightIntent);
            }
        });

        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carIntent = new Intent(getApplicationContext(), Activity_Car_Planner.class);
                startActivity(carIntent);
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

}
