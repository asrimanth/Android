package com.example.srimanth.parcelablefun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Student student = new Student(-1, "Error", "Error");
        TextView textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        Bundle data = getIntent().getExtras();
        if (data != null) {
             student= data.getParcelable("Student");
        }
        if (student != null) {
            textView.setText(student.toString());
            Log.i("Data is",student.toString());
        }
    }
}
