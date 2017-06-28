package com.example.ajp.s_cape_app.API_PULLS;


import android.os.AsyncTask;
import android.util.Log;

import com.example.ajp.s_cape_app.Activities.Activity_VistPlaces;
import com.example.ajp.s_cape_app.Objects.Object_Api_Pull;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Api_Pull_VistPlaces extends AsyncTask<String, Void, ArrayList<Object_Api_Pull>> {

    String name;
    String shortName;
    String address;
    String message;
    String lat;
    String lon;

    public ArrayList<Object_Api_Pull> apiArray;

    private Activity_VistPlaces vistPlacesActivity;

    public Api_Pull_VistPlaces(Activity_VistPlaces originalVistPlacesScreen) {
        this.vistPlacesActivity = originalVistPlacesScreen;
    }

    @Override
    protected ArrayList<Object_Api_Pull> doInBackground(String... params) {

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
                JSONObject dataObject = outerMostObject.getJSONObject("response");

                JSONArray childrenData = dataObject.getJSONArray("groups");

                for (int i = 0; i < childrenData.length(); i++) {
                    JSONObject childrenObject = childrenData.getJSONObject(i);

                    JSONArray items = childrenObject.getJSONArray("items");

                    for (int j = 0; j < items.length(); j++) {
                        JSONObject childrenObject2 = items.getJSONObject(j);

                        JSONObject childrenDataObject = childrenObject2.getJSONObject("venue");


                        JSONArray categoriesArray = childrenDataObject.getJSONArray("categories");

                        for(int k = 0; k < categoriesArray.length(); k++){
                            JSONObject childrenObject3 = categoriesArray.getJSONObject(k);

                            shortName = childrenObject3.getString("shortName");
                        }

                        name = childrenDataObject.getString("name");

                        JSONObject childrendDataObjectAddress = childrenDataObject.getJSONObject("location");

                        address = childrendDataObjectAddress.getString("address");
                        lat = childrendDataObjectAddress.getString("lat");
                        lon = childrendDataObjectAddress.getString("lng");

                        Object_Api_Pull object = new Object_Api_Pull(name,address,message,"Trending",lat,lon,shortName);

                        apiArray.add(object);
                    }
                }

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
    protected void onPostExecute(ArrayList<Object_Api_Pull> strings) {
        super.onPostExecute(strings);
        vistPlacesActivity.update(apiArray);
    }
}
