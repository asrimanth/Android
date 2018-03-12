package com.example.srimanth.animationpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView vegeta,goku;
    int v=R.id.vegeta, g=R.id.goku;


    public void fade1(View view)
    {
        if(view.getId()==v)
        {
            vegeta = (ImageView) findViewById(R.id.vegeta);
            goku = (ImageView) findViewById(R.id.goku);
            vegeta.animate().alpha(0.01f).setDuration(2000);
            goku.animate().alpha(1f).setDuration(2000);
            goku.bringToFront();
        }
        if (view.getId()==g)
        {
            vegeta = (ImageView) findViewById(R.id.vegeta);
            goku = (ImageView) findViewById(R.id.goku);
            vegeta.animate().alpha(1f).setDuration(2000);
            goku.animate().alpha(0.01f).setDuration(2000);
            vegeta.bringToFront();
        }

    }
    /*public void fade2(View view)
    {
        vegeta = (ImageView) findViewById(R.id.vegeta);
        goku = (ImageView) findViewById(R.id.goku);
        vegeta.animate().alpha(1f).setDuration(2000);
        goku.animate().alpha(0.01f).setDuration(2000);
        vegeta.bringToFront();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vegeta = (ImageView) findViewById(R.id.vegeta);
        /*
        vegeta.setScaleX(0.5f);
        vegeta.setScaleY(0.5f);
        vegeta.setAlpha(0f);
        */
    }
}
