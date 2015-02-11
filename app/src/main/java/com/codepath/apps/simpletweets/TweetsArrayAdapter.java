package com.codepath.apps.simpletweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mdrake on 2/10/15.
 */

// Taking the Tweet objects and turning them into Views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    // View holder pattern

    // Override and setup custom template
    @Override
    public View getView(int pos, View conevertView, ViewGroup parent){
        Tweet tweet = getItem(pos);

        if(conevertView == null){
            conevertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) conevertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) conevertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) conevertView.findViewById(R.id.tvBody);
        TextView tvHandle = (TextView) conevertView.findViewById(R.id.tvHandle);
        TextView tvTime = (TextView) conevertView.findViewById(R.id.tvTmeAgo);

        tvUserName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvHandle.setText(" @"+tweet.getUser().getScreenName());
        tvTime.setText(" "+tweet.getTimeAgo());

        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return conevertView;
    }

}
