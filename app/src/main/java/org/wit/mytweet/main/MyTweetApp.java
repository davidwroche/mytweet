package org.wit.mytweet.main;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.wit.mytweet.Database.MyTweetDB;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * Created by Tosh on 9/29/2016.
 */
public class MyTweetApp extends Application{

    SharedPreferences sp;
    public List<Tweet> mytweets;
    MyTweetDB db = new MyTweetDB(this);
    int ID;
    String FNAME;
    String LNAME;

    public void newUser(User user){
        db.addUser(user);
    }

    public void newTweet(Tweet tweet){
        db.addTweet(tweet);
    }


    public boolean validUser (String email, String password)
    {
        List<User> users = db.getAllUsers();
        for (User user : users)
        {

            if (user.email.equals(email) && user.password.equals(password))
            {
                Log.v("Login Details","hit the Login button" + "  " + email + "  " + password+ "  " );
                SharedPreferences.Editor e = sp.edit();
                e.putInt("ID", user.getId());
                e.putString("FNAME",user.getFirstName());
                e.putString("LNAME",user.getLastName());
                e.apply();
                return true;
            }
        }

        return false;
    }


    public ArrayList getTweets(){
        List<Tweet> allTweets = db.getAllTweets();
        ArrayList<Tweet> userTweets = new ArrayList<>();
        for (Tweet tweet : allTweets){
            if(tweet.getUid() == getCurrentUser()){
                userTweets.add(tweet);
            }
        }
        return userTweets;
    }

    public void deleteAllTweet(){
        List<Tweet> allTweets = db.getAllTweets();
        for(Tweet tweet: allTweets){
            if(tweet.getUid() == getCurrentUser()){
                db.deleteTweet(tweet);
            }
        }
    }

    public Tweet getSingleTweet(int id){
       return db.getTweet(id);

    }

    public int getCurrentUser(){
        ID = sp.getInt("ID", 0); // 0 is default value which means if couldn't find the value in the shared preferences file give it a value of 0.
        return ID;
    }

    public String getCurrentUserFirstName(){
        FNAME = sp.getString("FNAME","");
        return FNAME;
    }

    public String getCurrentUserLastName(){
        LNAME = sp.getString("LNAME", "");
        return LNAME;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("TweetActivity", "TweetActivity App Started");
        sp = getSharedPreferences("Session", 0); //"Session" is the file name it MUST be the same in all activities that require shared preferences.

    }
}
