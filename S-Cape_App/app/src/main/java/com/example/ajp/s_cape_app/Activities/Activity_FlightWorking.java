package com.example.ajp.s_cape_app.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.ajp.s_cape_app.API_PULLS.Api_Pull_Flights;
import com.example.ajp.s_cape_app.Adapters.CustomAdapter_Flights;
import com.example.ajp.s_cape_app.Objects.Object_FlightFare;
import com.example.ajp.s_cape_app.R;
import com.example.ajp.s_cape_app.Utility.Utility_KeyboardDismiss;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import eu.amirs.JSON;

public class Activity_FlightWorking extends AppCompatActivity {

    JSON jsonFlights;
    String longValues;
    String city;
    String state;
    EditText departureDate;
    EditText returnDate;

    // String values for the api pull
    String returnTime;
    String departureTime;

    // Crystal seekbar for our prices
    CrystalSeekbar rangeSeekbar;
    int price;

    // Edittexts values
    AutoCompleteTextView origins;

    // List of flights that will be in the list after pull
    ArrayList<Object_FlightFare> flightList = new ArrayList<>();

    // Auto complete textview here!
    private AutoCompleteTextView.Validator iataCodeValidator = new AutoCompleteTextView.Validator() {

        @Override
        public boolean isValid(CharSequence text) {
            return (text.length() == 3);
        }

        @Override
        public CharSequence fixText(CharSequence invalidText) {
            return null;
        }
    };
    // end validator


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__flight_working);

        // Begin PRETTIFYING
        setBackground(this, R.drawable.flight_finder_bg);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_FlightPageTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        // END PRETTIFYING

        Intent startIntent = getIntent();
        Bundle extras = startIntent.getExtras();
        city = extras.getString("city");
        state = extras.getString("state");

        //Hiding the keyboard
        setupUI(findViewById(R.id.activity__flight_working));


        final TextView priceView = (TextView)findViewById(R.id.textView_Flights_Price);
        departureDate = (EditText)findViewById(R.id.editText_Flight_DeptDate);
        returnDate = (EditText)findViewById(R.id.editText_RtnDate);

        //region Seekbar

        // get seekbar from view
        rangeSeekbar = (CrystalSeekbar)findViewById(R.id.rangeSeekbar_FlightPrice);

        // setting initial value
        rangeSeekbar.setMinStartValue(100).apply();
        rangeSeekbar.setMaxValue(500).apply();
        // change position left to right
        rangeSeekbar.setPosition(CrystalSeekbar.Position.LEFT).apply();
        rangeSeekbar.setLeftThumbDrawable(R.mipmap.ic_plane128);
        rangeSeekbar.setPosition(0).apply();

        rangeSeekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                if (value.intValue() == 0) {
                    priceView.setText("$ " + value.toString());
                    priceView.setTextSize(32);
                    rangeSeekbar.setLeftThumbDrawable(R.mipmap.ic_plane128);
                    price = value.intValue();
                }
                else if (value.intValue() > 1 && value.intValue() <= 150) {
                    priceView.setText("$ " + value.toString());
                    priceView.setTextSize(32);
                    priceView.setTextColor(Color.BLUE);
                    rangeSeekbar.setLeftThumbDrawable(R.mipmap.ic_coins);
                    rangeSeekbar.setLeftThumbHighlightDrawable(R.mipmap.ic_coins);
                    price = value.intValue();
                } else if (value.intValue() <= 300) {
                    priceView.setText("$ " + value.toString());
                    priceView.setTextSize(42);
                    priceView.setTextColor(Color.GREEN);
                    rangeSeekbar.setLeftThumbDrawable(R.mipmap.ic_cash);
                    rangeSeekbar.setLeftThumbHighlightDrawable(R.mipmap.ic_cash);
                    price = value.intValue();
                } else if (value.intValue() <= 500) {
                    priceView.setText("$ " + value.toString());
                    priceView.setTextSize(52);
                    priceView.setTextColor(Color.RED);
                    rangeSeekbar.setLeftThumbDrawable(R.mipmap.ic_money_bag);
                    rangeSeekbar.setLeftThumbHighlightDrawable(R.mipmap.ic_money_bag);
                    price = value.intValue();
                }

            }
        });
        //endregion seekbar

        String[] airports = getResources().getStringArray(R.array.airports);
        ArrayAdapter<String> originAirportsAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, airports);
        origins = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_airport);
        origins.setAdapter(originAirportsAdapter);
        origins.setValidator(iataCodeValidator);

        ImageView calenderDepart = (ImageView)findViewById(R.id.imageView_Flight_CalendarDept);
        calenderDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(findViewById(R.id.activity__flight_working), 1);
            }
        });

        ImageView calenderReturn = (ImageView)findViewById(R.id.imageView_Flight_CalendarRtn);
        calenderReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(findViewById(R.id.activity__flight_working), 2);
            }
        });

        //Button to pull flight data:
        Button pullFlightData = (Button)findViewById(R.id.button_PullFlightsData);
        pullFlightData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Field checking
                if (departureDate.getText() == null || returnDate.getText() == null || origins.getText().toString().equals("")) {
                    Toast.makeText(Activity_FlightWorking.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                    return;
                } else if (price == 0) {
                    Toast.makeText(Activity_FlightWorking.this, "Price zero", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Handle the date tranformation here!
                departureTime = departureDate.getText().toString();
                returnTime = returnDate.getText().toString();


                //transform to format needed for the api pull
                DateFormat sdf = SimpleDateFormat.getDateInstance();

                //TODO get rid of log
                Log.e("PRICE VAL:", "===" + price);

                try {
                    Date depDate = sdf.parse(departureTime);
                    Date rtnDate = sdf.parse(returnTime);
                    returnTime = new SimpleDateFormat("yyyy-MM-dd").format(rtnDate);
                    departureTime = new SimpleDateFormat("yyyy-MM-dd").format(depDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                //region ASYNC TASK
                AsyncTask asyncTask = new AsyncTask() {

                    String airportCode;

                    @Override
                    protected void onPreExecute() {
                        // get airport code for the pull
                        airportCode = origins.getText().toString();
                        super.onPreExecute();
                    }

                    @Override
                    protected HttpResponse doInBackground(Object[] objects) {


                        try {
                            longValues = convertStreamToString(Api_Pull_Flights.callGetMethod(airportCode, departureTime, returnTime, price).getEntity().getContent());
                            jsonFlights = new JSON(longValues);
                            //Log.e("VALUE",convertStreamToString(callGetMethod().getEntity().getContent()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        return Api_Pull_Flights.callGetMethod(airportCode, departureTime, returnTime, price);
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        for (int i = 0; i < jsonFlights.key("FareInfo").count(); i ++) {
//                Log.e("JSON DEST LOCATION",jsonFlights.key("FareInfo").index(i).key("DestinationLocation").stringValue());
//                Log.e("JSON PRICE",jsonFlights.key("FareInfo").index(i).key("LowestNonStopFare").key("Fare").stringValue());
//                Log.e("JSON Airline",jsonFlights.key("FareInfo").index(i).key("LowestNonStopFare").key("AirlineCodes").index(0).stringValue());
                            Object_FlightFare flightFare = new Object_FlightFare(jsonFlights.key("FareInfo").index(i).key("DestinationLocation").stringValue(),
                                    jsonFlights.key("FareInfo").index(i).key("LowestFare").key("Fare").stringValue(),
                                    jsonFlights.key("FareInfo").index(i).key("LowestFare").key("AirlineCodes").index(0).stringValue());

                            flightList.add(flightFare);
                        }


                        super.onPostExecute(o);
                    }
                };
                //endregion

                // NOW EXECUTE
                asyncTask.execute();

                showDialog();
            }
        });
    }

    //region dismiss keyboard
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Utility_KeyboardDismiss.hideSoftKeyboard(Activity_FlightWorking.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    //endregion dismiss keyboard

    //Open up calendar for dates
    public void showDatePickerDialog(View v, final int i) {
        final Calendar cal = Calendar.getInstance();
        final AlertDialog newFragment = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        DateFormat sdf = SimpleDateFormat.getDateInstance();
                        if (i == 1) {
                            departureDate = (EditText) findViewById(R.id.editText_Flight_DeptDate);
                            departureDate.setText(sdf.format(cal.getTime()));
                        } else if (i == 2) {
                            returnDate = (EditText) findViewById(R.id.editText_RtnDate);
                            returnDate.setText(sdf.format(cal.getTime()));
                        }
                    }
                }, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        newFragment.show();

    }

    // Fligth data handling

    //Turn httpresponse to string
    public static String convertStreamToString(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),1024);
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }



    //region DialogBuilding
    private void showDialog(){

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);

        final AVLoadingIndicatorView avi = (AVLoadingIndicatorView)view.findViewById(R.id.avi);

        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.Dialog_linearLayout);
        linearLayout.setVisibility(View.GONE);

        final ListView dialogList = (ListView)view.findViewById(R.id.dialog_listView);
        dialogList.setVisibility(View.GONE);


        final CustomAdapter_Flights adapter = new CustomAdapter_Flights(getApplicationContext(), flightList, price);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 10000ms
                avi.hide();

                //Set visibility
                linearLayout.setVisibility(View.VISIBLE);

                //set adapter
                dialogList.setAdapter(adapter);
                dialogList.setVisibility(View.VISIBLE);


                //set click listener
                dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //TODO pass data to the next screen adam ;)

                        Intent hotelIntent = new Intent(getApplicationContext(),Activity_Hotel.class);
                        hotelIntent.putExtra("city",city);
                        hotelIntent.putExtra("state",state);
                        hotelIntent.putExtra("returnDate",returnDate.getText().toString());
                        hotelIntent.putExtra("departingDate",departureDate.getText().toString());
                        startActivity(hotelIntent);
                        finish();
                    }
                });
            }
        }, 7000);


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                flightList.clear();
            }
        });

        dialog.setContentView(view);

        dialog.show();

    }
    //endregion

    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__flight_working);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}


