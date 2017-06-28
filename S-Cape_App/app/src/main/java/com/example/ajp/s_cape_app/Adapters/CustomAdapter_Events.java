package com.example.ajp.s_cape_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ajp.s_cape_app.Activities.Activity_Itenerary;
import com.example.ajp.s_cape_app.Activities.Activity_Photo;
import com.example.ajp.s_cape_app.Activities.Activity_Route;
import com.example.ajp.s_cape_app.Objects.ObjectBaseEvent;
import com.example.ajp.s_cape_app.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;


public class CustomAdapter_Events extends BaseAdapter implements View.OnClickListener, AdapterView.OnItemClickListener {

    Context context;
    ArrayList<ObjectBaseEvent> objects;
    int selectedIndex;
    String tripName;
    GridView pics;
    ArrayList<Uri> photoUris;
    ArrayList<String> photoNames;
    Boolean isTripCompleted;

    public CustomAdapter_Events(Context _context, ArrayList<ObjectBaseEvent> _objects, String _tripName, Boolean _isTripCompleted){
        context = _context;
        objects = _objects;
        tripName = _tripName;
        isTripCompleted = _isTripCompleted;

        photoUris = new ArrayList<>();
        photoNames = new ArrayList<>();

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
                    inflate(R.layout.event__custom_adapter, viewGroup, false);
        }

        selectedIndex = i;

        ImageView icon = (ImageView) view.findViewById(R.id.image);
        TextView name = (TextView) view.findViewById(R.id.textViewName);
        TextView address = (TextView) view.findViewById(R.id.textViewAddress);
        TextView category = (TextView) view.findViewById(R.id.textViewCategory);

        ImageView directions = (ImageView) view.findViewById(R.id.buttonDirections);
        ImageView photo = (ImageView) view.findViewById(R.id.buttonPhoto);
        pics = (GridView) view.findViewById(R.id.picturesGrid);

        if(!isTripCompleted) {
            pics.setOnItemClickListener(this);
        }

        photo.setTag(selectedIndex);
        directions.setTag(selectedIndex);

        if(!isTripCompleted) {
            directions.setOnClickListener(this);
            photo.setOnClickListener(this);
        }else{
            directions.setVisibility(View.GONE);
            photo.setVisibility(View.GONE);
        }

        final String eventType = objects.get(i).getCategory();


        switch (eventType) {
            case "Food":
                icon.setImageResource(R.drawable.image_food);
                break;
            case "Trending":
                icon.setImageResource(R.drawable.image_visitplace);
                break;
            case "Custom":
                icon.setImageResource(R.drawable.image_customevent);
                break;
            case "Sights":
                icon.setImageResource(R.drawable.image_sight);
                break;

        }

        name.setText(objects.get(i).getName());
        address.setText(objects.get(i).getAddress());
        category.setText(objects.get(i).getCategory());

        //grab pictures to put in grid view
        if(!objects.get(i).getPicturesIds().get(0).equals("test")){
            //aactually has photos

            for(int j = 0; j < objects.get(i).getPicturesIds().size(); j++) {
                //gets an instance of fire base storage
                FirebaseStorage storage = FirebaseStorage.getInstance();

                //grabs the correct file inside the fire base
                final StorageReference storeReference = storage.getReference(objects.get(i).getPicturesIds().get(j));

                storeReference.getName();

                Log.i("name is: ", storeReference.getName());

                storeReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        photoUris.add(uri);
                        photoNames.add(storeReference.getName());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.i("failure", exception.getMessage());
                        exception.printStackTrace();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        CustomAdapter_Photos adapter_photos = new CustomAdapter_Photos(context,photoUris,photoNames);
                        pics.setAdapter(adapter_photos);
                    }
                });
            }

            return view;


        }
        //call custom photo adapter

        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.buttonPhoto){
            Log.i("photo"," gonna take");


            Intent intent = new Intent(context, Activity_Photo.class);
            intent.putExtra("tripName",tripName);
            intent.putExtra("selectedEvent",(int)view.getTag());
            intent.putStringArrayListExtra("pictureIds",objects.get((int)view.getTag()).getPicturesIds());
            context.startActivity(intent);


        }else {
            //go to route
            Intent routeIntent = new Intent(context, Activity_Route.class);
            routeIntent.putExtra("destination", objects.get((int)view.getTag()));
            context.startActivity(routeIntent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("clicked on ", photoNames.get(i));

        ShareDialog shareDialog = new ShareDialog((Activity_Itenerary)context);

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setImageUrl(photoUris.get(i))
                    .build();
            shareDialog.show(linkContent);
        }
    }
}
