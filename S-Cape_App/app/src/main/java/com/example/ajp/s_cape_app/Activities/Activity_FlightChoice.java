package com.example.ajp.s_cape_app.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.ajp.s_cape_app.R;
import com.github.clans.fab.FloatingActionButton;

public class Activity_FlightChoice extends AppCompatActivity{

    EditText departingDate;
    EditText returnDate;
    String city;
    String state;


    FloatingActionButton fabFlights;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__flightchoice);

        fabFlights = (FloatingActionButton)findViewById(R.id.material_design_floating_action_button_Flights);

        fabFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!departingDate.getText().toString().equals("") && !returnDate.getText().toString().equals("")) {
                    Intent goToHotels = new Intent(getApplicationContext(), Activity_Hotel.class);
                    goToHotels.putExtra("city",city);
                    goToHotels.putExtra("state",state);
                    goToHotels.putExtra("departingDate",departingDate.getText().toString());

                    //TODO: send price as well
                    goToHotels.putExtra("returnDate",returnDate.getText().toString());
                    startActivity(goToHotels);

                }else{
                    if(departingDate.getText().toString().equals("")) {
                        departingDate.setError("Field is empty");
                    }
                    if(returnDate.getText().toString().equals("")){
                        returnDate.setError("Field is empty");
                    }
                }
            }
        });

        departingDate = (EditText) findViewById(R.id.editTextDepartingDate);
        returnDate = (EditText) findViewById(R.id.editTextReturnDate);

        Intent i = getIntent();
        city = i.getStringExtra("city");
        state = i.getStringExtra("state");
    }

}
