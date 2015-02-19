package com.codepath.apps.simpletweets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.simpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.simpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class TimelineActivity extends ActionBarActivity {

    private static final int COMPOSE_CODE = 20;
    public static final int RESULT_CLOSE_ALL = -1;
    private HomeTimelineFragment homeTimeline;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        // get view pager
        ViewPager vp = (ViewPager)findViewById(R.id.viewpager);
        homeTimeline = new HomeTimelineFragment();
        // set view pager adapter
        vp.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // find the pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // attach pager tabs to the viewpager
        tabStrip.setViewPager(vp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    public void onProfileView(MenuItem mi){
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compose) {

            Intent settingsIntent = new Intent(TimelineActivity.this, ComposeActivity.class);
            startActivityForResult(settingsIntent, COMPOSE_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 2.0 and above
    @Override
    public void onBackPressed() {
        finishWithExit();
    }

    // Before 2.0
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishWithExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    void finishWithExit(){
        setResult(RESULT_CLOSE_ALL);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == COMPOSE_CODE && resultCode == RESULT_OK) {

            String toCompose = data == null ? null : data.getStringExtra("body");

            if(toCompose != null)
            client.postTweetHandler(toCompose, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    Toast.makeText(getBaseContext(), "Tweeted!", Toast.LENGTH_SHORT).show();
                    Tweet tweet = Tweet.fromJSON(json);
                    homeTimeline.insert(tweet, 0);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(getBaseContext(),"Failed!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // return the order of fragments in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter{

        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager mgr){
            super(mgr);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return homeTimeline;
                case 1:
                    return new MentionsTimelineFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
