package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.wit.mytweet.Database.MyTweetDB;
import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.User;

import java.util.List;

/**
 * Created by Tosh on 10/12/2016.
 */
public class TweetSettings extends AppCompatActivity implements View.OnClickListener {

    public Button Update;
    public TextView newUsername;
    public TextView newPassword;
    MyTweetDB db = new MyTweetDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_tweet);

        Update = (Button) findViewById(R.id.update) ;
        newUsername = (TextView) findViewById(R.id.newUsername);
        newPassword = (TextView) findViewById(R.id.newPassword);

        Update.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, TweetActivityList.class));
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //Displays the current information of the user
    public void updateSettings(View v){
        MyTweetApp app = (MyTweetApp) getApplication();
        app.getCurrentUser();
        List<User> users = db.getAllUsers();


        if(newUsername.getText().toString().length()>0){
            for (User user : users)
            {
                if(app.getCurrentUser() == user.id){
                    user.setEmail(newUsername.getText().toString());
                    db.updateEmail(user);
                    Toast toast = Toast.makeText(this, "Log back in to update your view" , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        }
        else{
            Toast toast = Toast.makeText(this, "Field cannot be blank" , Toast.LENGTH_SHORT);
            toast.show();
        }


        if(newPassword.getText().toString().length()>0) {
            for (User user : users)
            {
                if(app.getCurrentUser() == user.id){
                    user.setPassword(newPassword.getText().toString());
                    db.updatePassword(user);
                    Toast toast = Toast.makeText(this, "Log back in to update your view" , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        else{
            Toast toast = Toast.makeText(this, "Field cannot be blank" , Toast.LENGTH_SHORT);
            toast.show();
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                updateSettings(v);
                break;
        }
    }
}
