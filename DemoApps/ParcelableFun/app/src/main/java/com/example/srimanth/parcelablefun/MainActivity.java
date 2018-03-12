package com.example.srimanth.parcelablefun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToNext(View view)
    {
        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
        intent.putExtra("Student", new Student(1,"Srimanth","10"));

        startActivity(intent);
    }
}
