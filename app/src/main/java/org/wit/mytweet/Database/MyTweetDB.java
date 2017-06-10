package org.wit.mytweet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tosh on 9/30/2016.
 */
public class MyTweetDB extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "MyTweet";
    //User table name
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TWEETS = "tweets";

    //User table Column names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    //Tweets table column names
    private static final String TWEET_ID = "id";
    private static final String TWEET_MESSAGE = "message";
    private static final String TWEET_DATE = "date";
    private static final String TWEET_UID = "Uid";


    public MyTweetDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        //Create User Table
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_FNAME + " TEXT, " + KEY_LNAME + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PASSWORD + " TEXT " + ")";

        //Create Tweets Table
        String CREATE_TWEETS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TWEETS + " (" + TWEET_ID + " INTEGER PRIMARY KEY, " +
                TWEET_MESSAGE + " TEXT, " + TWEET_DATE + " TEXT, " + TWEET_UID + " INTEGER, " + " FOREIGN KEY (" + TWEET_UID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "));";



        db.execSQL(CREATE_USERS_TABLE);

        db.execSQL(CREATE_TWEETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS);

        onCreate(db);
    }


    //Adding a new User
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, user.getFirstName()); //First Name
        values.put(KEY_LNAME, user.getLastName()); //Last Name
        values.put(KEY_EMAIL, user.getEmail()); //Email
        values.put(KEY_PASSWORD, user.getPassword()); //Password

        //Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); //Closing database connection

    }

    //Getting one User
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_FNAME, KEY_LNAME, KEY_EMAIL, KEY_PASSWORD}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        //Return User
        return user;
    }

    //Getting all users

    public List<User> getAllUsers(){
        List <User> userList = new ArrayList<User>();

        //Select All Query

        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setPassword(cursor.getString(4));


                //Adding user to list
                userList.add(user);
            }while (cursor.moveToNext());
        }

        //return user list
        return userList;

    }

    //Getting User Count
    public int getUserCount(){
        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();

        //return count
        return cursor.getCount();
    }

    //Updating a User
    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, user.getFirstName()); //First Name
        values.put(KEY_LNAME, user.getLastName()); //Last Name
        values.put(KEY_PASSWORD, user.getPassword()); //Password
        values.put(KEY_EMAIL, user.getEmail()); //Email

        // Updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    //Updating a Username
    public int updateEmail(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail()); //First Name

        // Updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    //Updating a Password
    public int updatePassword(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, user.getPassword()); //Password

        // Updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    //Deleting a User

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[] {String.valueOf(user.getId())});
        db.close();
    }

    //Tweets Section

    public void addTweet(Tweet tweet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TWEET_MESSAGE, tweet.getMessage()); //Tweet Message
        values.put(TWEET_DATE, tweet.getDate()); //Tweet Date
        values.put(TWEET_UID, tweet.getUid()); //Tweet Poster

        //Inserting Row
        db.insert(TABLE_TWEETS, null, values);
        db.close(); //Closing database connection

    }

    //Getting all tweets

    public List<Tweet> getAllTweets(){
        List <Tweet> tweetList = new ArrayList<Tweet>();

        //Select All Query

        String selectQuery = "SELECT * FROM " + TABLE_TWEETS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list

        if(cursor.moveToFirst()){
            do{
                Tweet tweet = new Tweet();
                tweet.setId(Integer.parseInt(cursor.getString(0)));
                tweet.setMessage(cursor.getString(1));
                tweet.setDate(cursor.getString(2));
                tweet.setUid(Integer.parseInt(cursor.getString(3)));

                //Adding tweet to list
                tweetList.add(tweet);
            }while (cursor.moveToNext());
        }

        //return tweet list
        return tweetList;

    }



    //Updating a tweet
    public int updateTweet(Tweet tweet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TWEET_MESSAGE, tweet.getMessage()); //Message

        return db.update(TABLE_TWEETS, values, TWEET_ID + " = ?",
                new String[]{String.valueOf(tweet.getId())});
    }

    //Getting one Tweet
    public Tweet getTweet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TWEETS, new String[]{TWEET_ID, TWEET_MESSAGE, TWEET_DATE, TWEET_UID }, TWEET_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Tweet tweet = new Tweet(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)));
        //Return User
        return tweet;
    }

    //Deleting a Tweet

    public void deleteTweet(Tweet tweet){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TWEETS, TWEET_ID + " = ?",
                new String[] {String.valueOf(tweet.getId())});
        db.close();
    }

}
