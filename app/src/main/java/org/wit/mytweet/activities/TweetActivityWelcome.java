package org.wit.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.wit.mytweet.R;

public class TweetActivityWelcome extends AppCompatActivity implements View.OnClickListener {

    public Button Signup;
    public Button Login;
    public TextView Header;
    public TextView subHeader;
    public TextView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_tweet);

        //Loads Animation for each button of TextView
        Login =  (Button)findViewById(R.id.welcomeLogin);
        Animation animlog = AnimationUtils.loadAnimation(this,R.anim.zoom_in);

        Signup =  (Button)findViewById(R.id.welcomeSignup);
        Animation animsign = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
        back = (TextView) findViewById(R.id.back);

        Header = (TextView) findViewById(R.id.header);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        subHeader = (TextView) findViewById(R.id.subheader);
        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.fade_ins);

        back.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bird,0,0,0);
        Animation anim2 = AnimationUtils.loadAnimation(this,R.anim.rotate);


        //Assigns specific animation to each Button or TextView
        Header.setAnimation(anim);
        subHeader.setAnimation(anim1);
        back.setAnimation(anim2);
        Login.setAnimation(animlog);
        Signup.setAnimation(animsign);
        Login.setOnClickListener(this);
        Signup.setOnClickListener(this);


    }

    //Redirects User toward either activity
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcomeLogin:
                Intent login = new Intent(this,TweetActivityLogin.class);
                startActivity(login);
                break;
            case R.id.welcomeSignup:
                Intent signup = new Intent(this,TweetActivitySignup.class);
                startActivity(signup);
                break;
        }
    }
}
