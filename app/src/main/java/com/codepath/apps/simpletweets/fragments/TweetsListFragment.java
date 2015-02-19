package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.simpletweets.EndlessScrollListener;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TweetsArrayAdapter;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdrake on 2/17/15.
 */
public class TweetsListFragment extends Fragment{


    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    // requires inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        /*lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("MORE", getSince() + "");
                populateTimeline(getSince());
            }
        });*/




        return v;
    }

    public void addAll(List<Tweet>tweets){
        aTweets.addAll(tweets);
    }

    public void insert(Tweet tweet, int index){
        aTweets.insert(tweet,index);
    }


    // creation lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create the arraylist
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        // connect adapter
    }

}
