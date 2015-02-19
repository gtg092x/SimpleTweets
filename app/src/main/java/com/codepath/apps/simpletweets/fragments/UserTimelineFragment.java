package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.TwitterClient;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mdrake on 2/17/15.
 */
public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    // send api request to get timeline json
    // fill list view
    protected void populateTimeline(int page) {
        Bundle args = getArguments();
        String screenName = args.getString("screen_name");
        client.getUserTimeline(screenName, page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TIMELINE", errorResponse.toString());

            }
        });
    }

    public static UserTimelineFragment create(String screenName){
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name",screenName);
        userFragment.setArguments(args);
        return userFragment;
    }

    @Override
    public void onLoadMoreRequest(int page, int totalItemsCount) {
        populateTimeline(page);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        client = TwitterApplication.getRestClient();

        populateTimeline();
        super.onCreate(savedInstanceState);
    }
}
