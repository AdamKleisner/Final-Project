package com.example.ajp.s_cape_app.Activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ajp.s_cape_app.Adapters.CustomAdapter_Challenges;
import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;

public class Activity_Challenges extends AppCompatActivity {

    ListView challengeList;
    ArrayList<String> challengeData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__challenges);

        //Begin Prettifying
        setBackground(this, R.drawable.challenges_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_Challenges_Banner);
        banner.setText("Challenges");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        //End Prettifying

        challengeList = (ListView)findViewById(R.id.listView_challenges);

        challengeData = new ArrayList<>();
        challengeData.add("Meet somebody new");
        challengeData.add("Go to 4 different states");
        challengeData.add("Try some weird food");

        CustomAdapter_Challenges challengeAdapter = new CustomAdapter_Challenges(this, challengeData);
        challengeList.setAdapter(challengeAdapter);

    }


    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__challenges);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}
