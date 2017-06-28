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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ajp.s_cape_app.R;

public class Activity_Car_Planner extends AppCompatActivity implements View.OnClickListener {

    EditText cityField;
    EditText stateField;
    Button setDestination;
    String city;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__car__planner);

        //Begin Prettifying
        setBackground(this, R.drawable.car_planner_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_carTrip_banner);
        banner.setText("Hit the road");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);

        cityField = (EditText) findViewById(R.id.editText_State);
        stateField = (EditText) findViewById(R.id.editText_City);
        setDestination = (Button) findViewById(R.id.buttonCarTrip);

        setDestination.setOnClickListener(this);
        //End prettifying
    }








    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__car__planner);
        layout.setBackground(bitmapDrawable);
    }

    @Override
    public void onClick(View view) {


        if(!cityField.getText().toString().equals("") && !stateField.getText().toString().equals("")) {

            city = cityField.getText().toString();
            state = stateField.getText().toString();


            Intent hotelIntent = new Intent(this,Activity_Hotel.class);
            hotelIntent.putExtra("city",city);
            hotelIntent.putExtra("state",state);
            hotelIntent.putExtra("returnDate","Car Trip, Your choice");
            hotelIntent.putExtra("departingDate","Car Trip, Your choice");
            startActivity(hotelIntent);

        }else if(cityField.getText().toString().equals("")){

            cityField.setError("Can not be empty");

        }else if(stateField.getText().toString().equals("")){

            stateField.setError("Can not be empty");
        }

    }
    //endregion setting background method
}
