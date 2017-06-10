package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tosh on 9/26/2016.
 */
public class TweetActivitySignup extends AppCompatActivity implements View.OnClickListener {

    public Button Signup;
    public EditText fName;
    public EditText lName;
    public EditText Email;
    public EditText Password;

   //We are going to use this database
    MyTweetDB db = new MyTweetDB(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_tweet);

        Signup =  (Button)findViewById(R.id.signbut);
        fName = (EditText)findViewById(R.id.signfn);
        lName = (EditText)findViewById(R.id.signln);
        Email = (EditText)findViewById(R.id.signem);
        Password = (EditText)findViewById(R.id.signpass);

        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.fade_ins);
        Signup.setAnimation(anim1);
        fName.setAnimation(anim1);
        lName.setAnimation(anim1);
        Email.setAnimation(anim1);
        Password.setAnimation(anim1);


        Signup.setOnClickListener(this);

    }

    //We check if all the fields have been filled on the register page
    public void registerUser(View view){
        List<User> users = db.getAllUsers();

            if (Email.getText().toString().length() > 1) {
                for (User user : users) {
                    if (user.email.equals(Email.getText().toString())) {
                        Toast toast2 = Toast.makeText(this, "Email already exits", Toast.LENGTH_LONG);
                        toast2.show();
                        Email.setText("");
                }
                }
            }

            if(fName.getText().toString().length() <= 0 || lName.getText().toString().length() <= 0 ||
                        Email.getText().toString().length() <= 0 || Password.getText().toString().length()
                        <= 0) {
                    Toast toast = Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG);
                    toast.show();
            } else {
                //If they have we add the user to the database and start the welcome activity
                db.addUser(new User(fName.getText().toString(), lName.getText().toString(),
                        Email.getText().toString(), Password.getText().toString()));
                Log.v("user", "Added a user");
                startActivity(new Intent(this, TweetActivityWelcome.class));
            }


    }

    //Calls method above and check whether all checks have been validated
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signbut:
                registerUser(v);
        }
    }
}
