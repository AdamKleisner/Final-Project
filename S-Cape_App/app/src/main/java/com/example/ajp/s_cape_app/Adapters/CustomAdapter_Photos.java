package com.example.ajp.s_cape_app.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ajp.s_cape_app.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CustomAdapter_Photos extends BaseAdapter {

    Context context;
    ArrayList<Uri> photoUri;
    ArrayList<String>photoName;

    public CustomAdapter_Photos(Context _context, ArrayList<Uri> _photoNames, ArrayList<String> _photoName){
        context = _context;
        photoUri = _photoNames;
        photoName = _photoName;
    }

    @Override
    public int getCount() {
        return photoUri.size();
    }

    @Override
    public Object getItem(int i) {
        return photoUri.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.photo__custom_adapter, viewGroup, false);
        }

        ImageView icon = (ImageView) view.findViewById(R.id.imageViewPhoto);
        TextView name = (TextView) view.findViewById(R.id.textViewPhotoName);

        photoUri.get(i);

        Bitmap myImg = BitmapFactory.decodeFile(photoUri.get(i).getPath());
        icon.setImageBitmap(myImg);

        Picasso.with(context)
                .load(photoUri.get(i))
                .centerCrop()
                .resize(200,200)
                .into(icon);

        //icon.setImageURI(photoUri.get(i));
        name.setText(photoName.get(i));

        return view;
    }
}
