package com.example.ajp.s_cape_app.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajp.s_cape_app.Objects.ObjectBaseEvent;
import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;

public class CustomAdapter_Directions extends BaseAdapter {

    Context context;
    ArrayList<String> objects;

    public CustomAdapter_Directions(Context _context, ArrayList<String> _objects){
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
                    inflate(R.layout.directions_custom_adapter, viewGroup, false);
        }

        ImageView icon = (ImageView) view.findViewById(R.id.imageViewIcon);
        TextView name = (TextView) view.findViewById(R.id.textViewName);


        if(objects.get(i).contains("merge right")){

            icon.setImageResource(R.mipmap.ic_rightmerge);

        }else if(objects.get(i).contains("merge left")){

            icon.setImageResource(R.mipmap.ic_leftmerge);

        }else if(objects.get(i).contains("left")){

            icon.setImageResource(R.mipmap.ic_left);

        }else if(objects.get(i).contains("right")){

            icon.setImageResource(R.mipmap.ic_right);

        }else if(objects.get(i).contains("U-turn")){

            icon.setImageResource(R.mipmap.ic_uturn);

        }else{

            icon.setImageResource(R.mipmap.ic_boldarrow);

        }


        name.setText(objects.get(i));


        return view;
    }
}
