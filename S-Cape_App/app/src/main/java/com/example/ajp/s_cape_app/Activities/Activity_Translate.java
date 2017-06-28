package com.example.ajp.s_cape_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ajp.s_cape_app.API_PULLS.Api_Pull_DetectLanguage;
import com.example.ajp.s_cape_app.API_PULLS.Api_Pull_Translate;
import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;
import java.util.Locale;

public class Activity_Translate extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {

    static TextToSpeech tts;
    static Locale dstLanguage = Locale.ENGLISH;
    Button translateButton;
    static TextView translatedText;
    String detectLang;
    static String translate;
    static ArrayList<String> matches;
    private static Activity_Translate context;
    static String spacesRemoved;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__translate);

        context = this;

        // Begin prettifying
        setBackground(this, R.drawable.translation_bg);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        translatedText = (TextView)findViewById(R.id.translateText);
        translatedText.setText("Talk to me");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        translatedText.setTypeface(typeface);
        //end prettifying

        tts = new TextToSpeech(this,this);
        translateButton = (Button)findViewById(R.id.translateButton);


        translateButton.setOnClickListener(this);


    }

    @Override
    public void onInit(int i) {

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak!");

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {

            matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            spacesRemoved = matches.get(0).replace(" ","%20");

            detectLang = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20170503T140215Z.fcd2ebdeaf2d5cf7.b029a8ac81bc0d6f84def0329838f0f070ae1469&text=" + spacesRemoved;

            Api_Pull_DetectLanguage api = new Api_Pull_DetectLanguage(this);

            api.execute(detectLang);

        }
    }

    public static void updateDetectLang(ArrayList<String> recievedArray){

        translate = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170503T140215Z.fcd2ebdeaf2d5cf7.b029a8ac81bc0d6f84def0329838f0f070ae1469&text=" + spacesRemoved + "&lang=" + recievedArray.get(0) + "-en";

        Api_Pull_Translate api = new Api_Pull_Translate(context);

        api.execute(translate);
    }

    public static void updateTranslateLang(ArrayList<String> recievedArray){

        String translatedTextFixed = recievedArray.get(0).toString().replace("[","").replace("]","");

        translatedText.setText(translatedTextFixed);

        tts.setLanguage(dstLanguage);
        tts.speak(translatedTextFixed, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {

        if(tts != null) {

            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }

    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout) findViewById(R.id.translate_background);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}
