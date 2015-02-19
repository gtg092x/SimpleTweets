package com.codepath.apps.simpletweets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;


import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL,
                context.getString(R.string.twitter_key), context.getString(R.string.twitter_secret),
                REST_CALLBACK_URL);

	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    // METHOD == endpoint

    // GET statuses/home_timeline.json
    //  count=25
    //  since_id=1
    public void getHomeTimeline(int page, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("page", page);
        params.put("since_id", 1);
        getClient().get(apiUrl, params, handler);
    }

    public void getMentionsTimeline(int page, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("page", page);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(AsyncHttpResponseHandler handler){
        getUserTimeline(null, 0 , handler);
    }

    public void getUserTimeline(String screenName, int page, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("page", page);
        if(screenName != null){
            params.put("screen_name", screenName);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler){
        String apiUrl;
        RequestParams params = new RequestParams();
        if(screenName != null){
            params.put("screen_name", screenName);
            apiUrl = getApiUrl("users/show.json");
        }else{
            apiUrl = getApiUrl("account/verify_credentials.json");
        }
        getClient().get(apiUrl, params, handler);
    }

    // COMPOSE TWEET
    public void postTweetHandler(String toCompose, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("status", toCompose);
        getClient().post(apiUrl, params, handler);
    }
}