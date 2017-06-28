package com.example.ajp.s_cape_app.API_PULLS;


import android.os.AsyncTask;
import android.util.Log;

import com.example.ajp.s_cape_app.Activities.Activity_Directions_List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Api_Pull_Directions extends AsyncTask<String, Void, ArrayList<String>> {

    public ArrayList<String> apiArray;
    private Activity_Directions_List activity;
    String distance;
    String duration;
    String stepsDuration;
    String stepsDistance;
    String stepsInstruction;
    ArrayList<String> directions;

    public Api_Pull_Directions(Activity_Directions_List originalMainScreen) {
        this.activity = originalMainScreen;

        directions = new ArrayList<>();
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {

        apiArray = new ArrayList<>();
        Log.i("Here!", "1");
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();
            String data = IOUtils.toString(is);

            try {
                JSONObject outerMostObject = new JSONObject(data);
                JSONArray routesArray = outerMostObject.getJSONArray("routes");

                for(int i = 0; i < routesArray.length(); i++){

                    JSONObject arrayRoutesObject =  routesArray.getJSONObject(i);

                    JSONArray legsArray = arrayRoutesObject.getJSONArray("legs");

                    for(int j = 0; j < legsArray.length(); j++){
                        JSONObject legsArrayObject = legsArray.getJSONObject(j);

                        JSONObject distanceObject = legsArrayObject.getJSONObject("distance");
                        distance = distanceObject.getString("text");

                        JSONObject durationObject = legsArrayObject.getJSONObject("duration");
                        duration = durationObject.getString("text");

                        JSONArray stepsArray = legsArrayObject.getJSONArray("steps");
                        for(int k = 0; k < stepsArray.length(); k++){
                            JSONObject stepsArrayObject = stepsArray.getJSONObject(k);

                            JSONObject stepsDurationObject = stepsArrayObject.getJSONObject("duration");
                            stepsDuration = stepsDurationObject.getString("text");


                            JSONObject stepsDistanceObject = stepsArrayObject.getJSONObject("distance");
                            stepsDistance = stepsDistanceObject.getString("text");

                            stepsInstruction = stepsArrayObject.getString("html_instructions");
                            stepsInstruction = stepsInstruction.replace("<b>","");
                            stepsInstruction = stepsInstruction.replace("</b>","");
                            stepsInstruction = stepsInstruction.replace("</div>","");
                            stepsInstruction = stepsInstruction.replace("</div style=\"font-size:0.9em\">","");

                            directions.add(stepsDuration + " " + stepsDistance + " " + stepsInstruction);
                        }
                    }
                }

                apiArray = directions;


            } catch (JSONException e) {
                e.printStackTrace();
            }

            is.close();
            connection.disconnect();

            return apiArray;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();

        cancel(true);
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        activity.updateDirections(strings, distance, duration);
    }

}
