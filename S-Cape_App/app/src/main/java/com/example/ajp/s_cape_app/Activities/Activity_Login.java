package com.example.ajp.s_cape_app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajp.s_cape_app.*;
import com.example.ajp.s_cape_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fireAuth;
    EditText usernameTextField;
    EditText passwordTextField;
    Button signIn;
    TextView signUp;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ajp.s_cape_app.R.layout.activity__login);

        //BEGIN PRETTIFY

        //Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //set background image here
        setBackground(this, R.drawable.register_drawable);

        // END PRETTIFY
        fireAuth = FirebaseAuth.getInstance();

        String[] requst = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, requst, 1);
        }

        usernameTextField = (EditText) findViewById(R.id.editTextUsernameSignIn);
        passwordTextField = (EditText) findViewById(R.id.editTextPasswordSignIn);
        forgotPassword = (TextView) findViewById(R.id.textView_ForgotPassword);
        signIn = (Button) findViewById(R.id.buttonSignIn);
        signUp = (TextView) findViewById(R.id.buttonCreateAccount);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //forgot password stuff
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String email = usernameTextField.getText().toString();

                if(!email.equals("")) {

                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("User reset", "Email sent.");

                                        alert();

                                    }
                                }
                            });
                }else{
                    usernameTextField.setError("Need an email to send you password reset");
                }
            }
        });


//        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
//            @Override
//            public void doOnBackground() {
//
//                try {
//                    //Flights.pullFlightInfo();
//                    DummyFlightPull.main();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e("Flights", "onCreate: Called flights: ");
//                }
//                // Pretend it's doing some background processing
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // Create a fake result (MUST be final)
//                final boolean result = true;
//
//                // Send the result to the UI thread and show it on a Toast
//                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
//                    @Override
//                    public void doInUIThread() {
//                        Toast.makeText(getApplicationContext(), "Result was: "+ result, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }

    public void alert(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("Email sent");
        build.setCancelable(true);
        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        build.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        final String username = usernameTextField.getText().toString().toLowerCase();
        String password = passwordTextField.getText().toString();

        if(view.getId() == signIn.getId()) {
            if(!username.equals("") && !password.equals("")) {
                fireAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.i("sign-in", "success");

                                    GlobalUserName.userName = username;
                                    transitionToProfile();

                                } else {
                                    Log.i("sign-in", "failed");

                                    Toast.makeText(Activity_Login.this, "Authentication failed." + " " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }else{
            Log.i("button","sign-up button clicked");

            Intent signUpIntent = new Intent(this, Activity_Register.class);
            startActivity(signUpIntent);
        }
    }

    public void transitionToProfile(){

        Intent profileIntent = new Intent(this,Activity_Profile.class);
        startActivity(profileIntent);
        finish();
    }

    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__login);
        layout.setBackground(bitmapDrawable);
    }
}
