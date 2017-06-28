package com.example.ajp.s_cape_app.API_PULLS;


import android.os.AsyncTask;
import android.util.Log;

import com.example.ajp.s_cape_app.Activities.Activity_Translate;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Api_Pull_DetectLanguage extends AsyncTask<String, Void, ArrayList<String>> {

    String language;

    public ArrayList<String> apiArray;

    private Activity_Translate translateActivity;

    public Api_Pull_DetectLanguage(Activity_Translate originalSightsScreen) {
        this.translateActivity = originalSightsScreen;
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


                language = outerMostObject.getString("lang");

                apiArray.add(language);

            }catch (JSONException e) {
                e.printStackTrace();
            }
            is.close();
            connection.disconnect();

            return apiArray;

        }catch (IOException e){
            e.printStackTrace();
        }
        return apiArray;
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
        Activity_Translate.updateDetectLang(apiArray);
    }
}
