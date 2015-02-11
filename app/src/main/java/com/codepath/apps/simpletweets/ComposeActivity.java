package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.simpletweets.R;

public class ComposeActivity extends ActionBarActivity {

    private EditText composeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Button cancelButton = (Button)findViewById(R.id.btCancel);
        Button composeButton = (Button)findViewById(R.id.btSend);
        composeField = (EditText)findViewById(R.id.etCompose);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        composeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweet();
            }
        });


    }

    void sendTweet(){
        String text = composeField.getText().toString();
        if(text == null || text.length() == 0){
            Toast.makeText(this,"Tweets cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle composeBundle = new Bundle();
        composeBundle.putString("body",text);
        Intent i = new Intent();
        i.putExtras(composeBundle);
        setResult(RESULT_OK,i);
        finish();
    }

    void cancel(){
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
