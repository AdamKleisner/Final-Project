package com.example.ajp.s_cape_app.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ajp.s_cape_app.Adapters.CustomAdapter_Events;
import com.example.ajp.s_cape_app.GlobalUserName;
import com.example.ajp.s_cape_app.Objects.ObjectTrip;
import com.example.ajp.s_cape_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Photo extends AppCompatActivity implements View.OnClickListener {

    Button yes;
    Button no;
    ImageView picture;
    String tripName;
    ArrayList<String> pictures;
    HashMap<String,Object> update;
    int selectedEvent;
    String selectedEventString;
    Bitmap photo;
    ArrayList<String> oldPhotos;
    EditText photoName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__photo);

        Intent i = getIntent();
        tripName = i.getStringExtra("tripName");
        selectedEvent = i.getIntExtra("selectedEvent",0);
        oldPhotos = i.getStringArrayListExtra("pictureIds");
        selectedEventString = String.valueOf(selectedEvent);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        yes = (Button) findViewById(R.id.buttonYes);
        no = (Button) findViewById(R.id.buttonNo);
        picture = (ImageView) findViewById(R.id.imageUser);
        photoName = (EditText) findViewById(R.id.editTextPhotoName);

        pictures = new ArrayList<>();
        update = new HashMap<String,Object>();

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        yes.setEnabled(false);
        no.setEnabled(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);

        }else{
            Log.i("permission denied"," oh well");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {
            if(resultCode == RESULT_OK) {
                photo = (Bitmap) data.getExtras().get("data");
                picture.setImageBitmap(photo);

                yes.setEnabled(true);
                no.setEnabled(true);
            }
            if (resultCode == RESULT_CANCELED)
            {
                Log.i("reached","here");
                finish();
            }

        }
    }

    @Override
    public void onClick(View view) {

        if(!photoName.getText().toString().equals("")) {

            if(!oldPhotos.get(0).equals("test")){
                pictures = oldPhotos;
            }

            if (view.getId() == R.id.buttonYes) {
                Log.i("yes", "was clicked");

                pictures.add( tripName + "." + photoName.getText().toString());

                update.put("picturesIds", pictures);

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().
                        getRoot().child("users")
                        .child(GlobalUserName.userName.replace("@", "").replace(".", "")).child(tripName).child("tripEventList").child(selectedEventString).child("picturesIds");
                ref.setValue(pictures);

                //add to storage

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference ref2 = storage.getReference().child(tripName + "." + photoName.getText().toString());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = ref2.putBytes(data);

                finish();


            } else {
                Log.i("no", "was clicked");

                finish();
            }
        }else{
            Toast.makeText(this, "Field can't be empty.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
