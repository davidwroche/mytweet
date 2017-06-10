package org.wit.mytweet.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.wit.mytweet.Database.MyTweetDB;
import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tosh on 10/3/2016.
 */
public class TweetActivityList extends AppCompatActivity implements AdapterView.OnItemClickListener,AbsListView.MultiChoiceModeListener {
    private ListView listView;
    private TweetAdapter adapter;
    private ArrayList<Tweet> tweets;
    MyTweetDB db = new MyTweetDB(this);
    private List<Tweet> holder = new ArrayList<Tweet>();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_tweet);

        listView = (ListView)findViewById(R.id.tweetList);
        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.fade_ins);
        listView.setAnimation(anim1);
        MyTweetApp app = (MyTweetApp)getApplication();
        tweets = (ArrayList<Tweet>) app.getTweets();

        adapter = new TweetAdapter(this, tweets);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Tweet tweet = adapter.getItem(position);
        Intent intent = new Intent(this, TweetActivity.class);
        intent.putExtra("TWEET_ID", tweet.getId());
        startActivity(intent);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tweet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MyTweetApp app = (MyTweetApp) getApplication();
        switch (item.getItemId()) {
            case R.id.action_clear:
                app.deleteAllTweet();
                startActivity(new Intent(this, TweetActivityList.class));
                break;

            case R.id.action_settings:
                startActivity(new Intent(this, TweetSettings.class));
                break;

            case R.id.plus_sign:
                Intent intent = new Intent(this, TweetActivity.class);
                this.startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void deleteSelectedTweets(){
        List<Tweet> allTweets = db.getAllTweets();
       for(Tweet tweet: allTweets){
            for(Tweet tweet1: holder){
                if(tweet.getId() == tweet1.getId()){
                    db.deleteTweet(tweet);
                }
            }

        }

    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        Tweet tweet = adapter.getItem(position);
        if(checked) {
            holder.add(tweet);
        }

        if(!checked) {
            for (int i = 0; i < holder.size(); i++) {
                if (holder.contains(tweet)) {
                    holder.remove(tweet);
                }
            }
        }

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.tweet_list_context,menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
        MyTweetApp app = (MyTweetApp) getApplication();
        switch (menuItem.getItemId()) {
            case R.id.menu_item_delete_tweets:
                deleteSelectedTweets();
                startActivity(new Intent(this, TweetActivityList.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

}




class TweetAdapter extends ArrayAdapter<Tweet>
{
    private Context context;

    public TweetAdapter(Context context, ArrayList<Tweet> tweets)
    {
        super(context, 0, tweets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_item_tweet, null);
        }

        Tweet tweets = getItem(position);

        TextView tweetmessage = (TextView)convertView.findViewById(R.id.tweet_message);
        tweetmessage.setText(tweets.getMessage());

        TextView tweetdate = (TextView) convertView.findViewById(R.id.tweet_date);
        tweetdate.setText(tweets.getDate());

        return convertView;


    }
}