package com.example.srimanth.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myFiendsView=findViewById(R.id.myFriendsView);

        final ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("Shanmukha");
        arrayList.add("Prathik");
        arrayList.add("Akshay");
        arrayList.add("Bharath");

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrayList);

        myFiendsView.setAdapter(myAdapter);

        myFiendsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message=arrayList.get(position);
                toastMsg(message);

            }
        });



    }
    public void toastMsg(String msg)
    {
        Toast.makeText(this, "The clicked name is : "+msg, Toast.LENGTH_SHORT).show();
    }
}
