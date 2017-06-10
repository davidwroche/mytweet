package org.wit.mytweet.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.wit.mytweet.Database.MyTweetDB;
import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.User;

import java.util.List;

/**
 * Created by Tosh on 9/26/2016.
 */
public class TweetActivityLogin extends AppCompatActivity implements View.OnClickListener {

    public Button Login;
    public EditText LPass;
    public EditText LEmail;

   //MyTweetDB db = new MyTweetDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_tweet);

        Login = (Button) findViewById(R.id.logbut);
        LPass = (EditText) findViewById(R.id.logPass);
        LEmail = (EditText) findViewById(R.id.logEmail);

        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.fade_ins);
        Animation anim2 = AnimationUtils.loadAnimation(this,R.anim.zoom_inless);

        LPass.setAnimation(anim1);
        LEmail.setAnimation(anim1);
        Login.setAnimation(anim2);
        Login.setOnClickListener(this);
    }

    public void authenticateUser(View view) {
        Log.v("hit the login","hit the login button");
        Log.v("Login Details","hit the Login button" + "  " + LEmail.getText() + "  " + LPass.getText() + "  " );
        MyTweetApp app = (MyTweetApp) getApplication();

        if (app.validUser(LEmail.getText().toString(), LPass.getText().toString()))
        {
            startActivity (new Intent(this, TweetActivityList.class));
        }
        else
        {
            Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logbut:
                authenticateUser(v);
        }
    }
}