package com.example.srimanth.timestables;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;


    public void generateTable(int timesTable)
    {
        ArrayList<String> arrayList=new ArrayList<String>();

        for (int i=1;i<=10;i++)
        {
            arrayList.add(Integer.toString(timesTable)+" * "+Integer.toString(i)+" = "+Integer.toString(timesTable*i));
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int max=20;

        SeekBar seekBar=findViewById(R.id.seekBar);
        seekBar.setMax(max);
        seekBar.setProgress(10);

        listView=findViewById(R.id.myListView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min=1,timesTable;
                if(progress<1)
                {
                    timesTable=min;
                    seekBar.setProgress(timesTable);
                }
                else
                {
                    timesTable=progress;
                }
                generateTable(timesTable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });







    }
}
