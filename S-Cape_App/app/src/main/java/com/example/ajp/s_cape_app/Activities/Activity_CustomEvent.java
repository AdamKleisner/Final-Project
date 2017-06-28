package com.example.ajp.s_cape_app.Activities;

import android.app.Activity;
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
import com.example.ajp.s_cape_app.Objects.ObjectEventCustom;
import com.example.ajp.s_cape_app.R;
import java.util.ArrayList;

public class Activity_CustomEvent extends AppCompatActivity {

    String title;
    String address;
    String category;
    ArrayList<String> picturesIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__custom_event);

        //Begin Prettifying
        setBackground(this, R.drawable.custom_finder_bg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView banner = (TextView)findViewById(R.id.textView_CustomEvent_Banner);
        banner.setText("Off the books");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);
        //End Prettifying

        Intent startIntent = getIntent();
        Bundle extras = startIntent.getExtras();
        category = extras.getString("CATEGORY");

        picturesIds = new ArrayList<>();

        final EditText titleEntry = (EditText) findViewById(R.id.editText_CustomEvent_Title);
        final EditText addressEntry = (EditText) findViewById(R.id.editText_CustomEvent_Address);



        Button finishEvent = (Button) findViewById(R.id.button_CustomEvent_Finish);

        finishEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (titleEntry.getText().toString().equals("")) {
                    titleEntry.setError("Please give this event a title");
                } else {
                    title = titleEntry.getText().toString();
                    address = addressEntry.getText().toString();

                    ObjectEventCustom customEvent = new ObjectEventCustom(category, title, address,picturesIds);

                    // send back result
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("CATEGORY", customEvent.getCategory());
                    resultIntent.putExtra("TITLE", customEvent.getEventName());
                    resultIntent.putExtra("ADDRESS", customEvent.getAddress());
                    setResult(Activity.RESULT_OK, resultIntent);
                    //finish
                    finish();
                }
            }
        });
    }

    //region setting background method
    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__custom_event);
        layout.setBackground(bitmapDrawable);
    }
    //endregion setting background method
}
