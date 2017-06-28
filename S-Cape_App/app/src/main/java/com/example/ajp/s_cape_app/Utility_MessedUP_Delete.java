package com.example.ajp.s_cape_app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ajp.s_cape_app.Objects.ObjectBaseEvent;

import java.util.ArrayList;

/**
 * Created by AJP on 4/28/17.
 */

public class Utility_MessedUP_Delete extends BaseAdapter {

    ArrayList<ObjectBaseEvent> listOfEvents;

    public Utility_MessedUP_Delete(ArrayList<ObjectBaseEvent> listOfEvents) {
        this.listOfEvents = listOfEvents;
    }

    @Override
    public int getCount() {
        return listOfEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return null;
    }
}
