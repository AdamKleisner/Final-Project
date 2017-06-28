package com.example.ajp.s_cape_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajp.s_cape_app.Objects.Object_Api_Pull;
import com.example.ajp.s_cape_app.R;

import java.util.ArrayList;


public class CustomAdapter_ApiPull extends BaseAdapter {

    Context context;
    ArrayList<Object_Api_Pull> objects;
    LayoutInflater inflter;

    public CustomAdapter_ApiPull(Context _context, ArrayList<Object_Api_Pull> _objects){
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
                    inflate(R.layout.util__apipull__baseadapter, viewGroup, false);
        }

        ImageView icon = (ImageView) view.findViewById(R.id.image);
        TextView name = (TextView) view.findViewById(R.id.textViewName);
        TextView address = (TextView) view.findViewById(R.id.textViewAddress);
        TextView price = (TextView) view.findViewById(R.id.textViewPrice);
        TextView description = (TextView) view.findViewById(R.id.textViewDescription);

        String category = objects.get(i).getCategory();

        switch (category) {
            case "Food":
                icon.setImageResource(R.drawable.image_food);
                break;
            case "Sights":
                icon.setImageResource(R.drawable.image_sight);
                break;
            case "Trending":
                icon.setImageResource(R.drawable.image_visitplace);
                break;
            case "Hotel":
                icon.setImageResource(R.drawable.image_hotel);
                break;
            default:
                icon.setImageResource(R.mipmap.ic_launcher);
                break;
        }


        name.setText(objects.get(i).getName());
        address.setText(objects.get(i).getAddress());
        price.setText(objects.get(i).getPrice());
        description.setText(objects.get(i).getCategory());
        return view;
    }
}
