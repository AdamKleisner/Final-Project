package com.example.ajp.s_cape_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajp.s_cape_app.GlobalUserName;
import com.example.ajp.s_cape_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fireAuth;
    EditText usernameTextField;
    EditText passwordTextField;
    Button signUp;
    TextView backToSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register);

        //BEGIN PRETTIFY

        //Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // set background image
        setBackground(this, R.drawable.login_drawable_new);

        TextView banner = (TextView)findViewById(R.id.textView_register_welcome);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        banner.setTypeface(typeface);

        //END PRETTIFY

        fireAuth = FirebaseAuth.getInstance();

        usernameTextField = (EditText) findViewById(R.id.editTextUsernameSignUp);
        passwordTextField = (EditText) findViewById(R.id.editTextPasswordSignUp);
        signUp = (Button) findViewById(R.id.buttonSignUp);
        signUp.setOnClickListener(this);

        backToSignIn = (TextView) findViewById(R.id.textView_BackToLogin);

        backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {

        String username = usernameTextField.getText().toString().toLowerCase();
        String password = passwordTextField.getText().toString();

        if(!username.equals("") && !password.equals("")) {

            fireAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("authentication", "success");

                                GlobalUserName.userName = usernameTextField.getText().toString().toLowerCase();
                                transitionToProfile();
                                // to push again
                            } else {
                                Log.i("authentication", "failed");

                                Toast.makeText(Activity_Register.this, "Authentication failed." + " " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(Activity_Register.this, "Authentication failed." + " One or more fields is empty",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void transitionToProfile(){

        Intent profileIntent = new Intent(this,Activity_Profile.class);
        startActivity(profileIntent);
    }

    public void setBackground(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity__register);
        layout.setBackground(bitmapDrawable);
    }

}
