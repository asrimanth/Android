package com.example.srimanth.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    boolean counterIsActive=false;
    TextView textView;
    Button button;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft)
    {
        double min=(secondsLeft/60);
        int minutes=(int)Math.floor(min);
        int seconds=secondsLeft-minutes*60;


        String timeString = String.format("%02d:%02d", minutes, seconds);
        textView.setText(timeString);

        /* Second Method:*/
        /*String firstString=Integer.toString(minutes);
        String secondString = Integer.toString(seconds);
        if(seconds<=9)
        {
              secondString="0"+secondString;
        }
        if (minutes<=9)
        {
            firstString="00";
        }
        textView.setText(firstString+":"+secondString);*/


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int maxTimeSeconds=900;


        textView=findViewById(R.id.textView);

        seekBar=findViewById(R.id.seekBar);

        button=findViewById(R.id.button);

        seekBar.setMax(maxTimeSeconds);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }

    public void toggleCountdown(View v) {

        if (!counterIsActive) {
            counterIsActive = true;
            seekBar.setEnabled(!counterIsActive);


            button.setText("Stop");

            countDownTimer=new CountDownTimer((seekBar.getProgress() * 1000) + 150, 1000)
                    //A time of 100ms is added to allow for the delay in the system
            {

                @Override
                public void onTick(long millisUntilFinished) {


                    long l = (long) millisUntilFinished / 1000;
                    updateTimer((int) l);
                }

                @Override
                public void onFinish() {
                    
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.laugh);
                    mediaPlayer.start();
                    //The context inside a class is not equal to app context
                    Log.i("Finished", "Timer is done!");
                    restartTimer();

                }
            }.start();
        }
        else
        {
            restartTimer();
        }
    }
    public void restartTimer()
    {
        textView.setText("00:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Start");
        seekBar.setEnabled(true);
        counterIsActive=false;
    }
}
