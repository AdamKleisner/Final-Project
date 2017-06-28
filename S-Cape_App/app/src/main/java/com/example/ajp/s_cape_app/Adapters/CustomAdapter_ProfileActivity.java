package com.example.ajp.s_cape_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajp.s_cape_app.Objects.ObjectTrip;
import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;


public class CustomAdapter_ProfileActivity extends BaseAdapter {

    Context context;
    ArrayList<ObjectTrip> objects;

    public CustomAdapter_ProfileActivity(Context _context, ArrayList<ObjectTrip> _objects){
        context = _context;
        objects = _objects;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.actiivty__profile__customadapter, viewGroup, false);
        }
        ImageView imageProfile = (ImageView)view.findViewById(R.id.image);
        imageProfile.setImageResource(R.drawable.image_travel);

        TextView name = (TextView) view.findViewById(R.id.textViewName);
        TextView start = (TextView) view.findViewById(R.id.textViewStart);
        TextView end = (TextView) view.findViewById(R.id.textViewEnd);

        String flightStart = "Trip start: " + objects.get(i).tripStartDate;
        String flightEnd = "Trip end: " + objects.get(i).tripEndDate;
        name.setText(objects.get(i).tripName);
        start.setText(flightStart);
        end.setText(flightEnd);

        return view;
    }
}
