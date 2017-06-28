package com.example.ajp.s_cape_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ajp.s_cape_app.Activities.Activity_FlightChoice;
import com.example.ajp.s_cape_app.Fragment_Map;
import com.example.ajp.s_cape_app.R;
import com.github.clans.fab.FloatingActionButton;

public class Activity_Map extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] cities;
    Spinner state;
    Spinner city;
    String selectedCity = "Atlanta";
    String selectedState = "GA";

    FloatingActionButton fabMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__map);

        fabMap = (FloatingActionButton)findViewById(R.id.material_design_floating_action_button_Map);

        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change this to our new flightactivity and get rid of city/state
                //Intent toFlightPage = new Intent(getApplicationContext(), Activity_FlightChoice.class);
                Intent toFlightPage = new Intent(getApplicationContext(), Activity_FlightWorking.class);
                toFlightPage.putExtra("city", selectedCity.toLowerCase());
                toFlightPage.putExtra("state", selectedState.toLowerCase());
                startActivity(toFlightPage);
            }
        });

        state = (Spinner) findViewById(R.id.spinnerState);
        city = (Spinner) findViewById(R.id.spinnerCity);


        state.setOnItemSelectedListener(this);
        city.setOnItemSelectedListener(this);

        Fragment_Map frag = Fragment_Map.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,frag, Fragment_Map.tag).commit();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("int = "," " + i);
        if(adapterView.getId() == state.getId()) {
            switch (i) {
                case 0:
                    cities = getResources().getStringArray(R.array.Georgia);
                    ArrayAdapter<String> spinnerArrayAdapterGeorgia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities);
                    city.setAdapter(spinnerArrayAdapterGeorgia);
                    break;
                case 1:
                    cities = getResources().getStringArray(R.array.Washington);
                    ArrayAdapter<String> spinnerArrayAdapterWashington = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities);
                    city.setAdapter(spinnerArrayAdapterWashington);
                    break;
                default:
                    break;
            }
            selectedState = adapterView.getItemAtPosition(i).toString();
        }else {

            selectedCity = adapterView.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

}
