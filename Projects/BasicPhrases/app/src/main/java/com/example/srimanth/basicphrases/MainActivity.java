package com.example.srimanth.basicphrases;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    public void tappedButton(View v)
    {
        int id=v.getId();
        String ourId="";

        ourId=v.getResources().getResourceEntryName(id);

        int resourceId=getResources().getIdentifier(ourId,"raw",getPackageName());
        MediaPlayer mPlayer=MediaPlayer.create(this,resourceId);
        mPlayer.start();

        Log.i("Button tapped!",ourId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
