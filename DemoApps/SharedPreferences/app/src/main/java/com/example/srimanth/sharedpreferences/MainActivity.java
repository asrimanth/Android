package com.example.srimanth.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.srimanth.sharedpreferences", Context.MODE_PRIVATE);

        ArrayList<String> friends=new ArrayList<>();

        friends.add("Shanmukha");
        friends.add("Prathik");
        friends.add("Bharath");
        friends.add("Akshay");

        try {

            sharedPreferences.edit().putString("Friends",ObjectSerializer.serialize(friends)).apply();

            Log.i("Friends",ObjectSerializer.serialize(friends));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> newFriends=new ArrayList<>();

        try {
            newFriends=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Friends",ObjectSerializer.serialize(new ArrayList<String>())));


            Log.i("newFriends",newFriends.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //sharedPreferences.edit().putString("username","srimanth").apply();

        //String username=sharedPreferences.getString("username","Not found");

        //Log.i("Name is",username);

    }
}
