package com.example.srimanth.videodemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp=MediaPlayer.create(this, R.raw.laugh);
        SeekBar vControl=findViewById(R.id.volumeControl);
        final SeekBar pControl=findViewById(R.id.progressControl);

        int duration=mp.getDuration();
        pControl.setMax(duration);

        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume=audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        int currentVolume=audioManager.getStreamVolume(audioManager.STREAM_MUSIC);

        vControl.setMax(maxVolume);
        vControl.setProgress(currentVolume);


        new Timer().scheduleAtFixedRate(new TimerTask() {//scheduling a certain task at a fixed rate
            @Override
            public void run() {
                pControl.setProgress(mp.getCurrentPosition());

            }
        },0,500);//no delay, period is the time between no.of successive calls to the timer


        vControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(audioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mp.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.start();
            }
        });

        pControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Progress",Integer.toString(progress));
                mp.seekTo(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onPlay(View v)
    {
        mp.start();
    }
    public void onPause(View v)
    {
        mp.pause();
    }
}