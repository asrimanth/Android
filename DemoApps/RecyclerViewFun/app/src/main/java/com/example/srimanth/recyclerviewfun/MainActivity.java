package com.example.srimanth.recyclerviewfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[],s2[];
    int imageResource[]={R.drawable.cat,R.drawable.dog,R.drawable.horse,R.drawable.rabbit,R.drawable.pigeon,R.drawable.eagle,R.drawable.peacock};
    CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.myRecyclerView);

        s1 = getResources().getStringArray(R.array.pet_name);
        s2 = getResources().getStringArray(R.array.description);

        adapter=new CustomRecyclerViewAdapter(this,s1,s2,imageResource);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
