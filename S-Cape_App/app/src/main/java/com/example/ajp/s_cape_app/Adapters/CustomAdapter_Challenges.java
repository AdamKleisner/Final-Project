package com.example.ajp.s_cape_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;

/**
 * Created by AJP on 5/19/17.
 */

public class CustomAdapter_Challenges extends BaseAdapter{

    Context context;
    ArrayList<String> challengesList = new ArrayList<>();

    public CustomAdapter_Challenges(Context context, ArrayList<String> challengesList) {
        this.context = context;
        this.challengesList = challengesList;
    }

    @Override
    public int getCount() {
        return challengesList.size();
    }

    @Override
    public Object getItem(int i) {
        return challengesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_listview_challenges, parent, false);
        }

        TextView challengeTitle = (TextView)convertView.findViewById(R.id.textView_Challenge_Value);
        challengeTitle.setText(challengesList.get(position));

        return convertView;
    }
}
