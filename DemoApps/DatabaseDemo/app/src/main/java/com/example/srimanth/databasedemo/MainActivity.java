package com.example.srimanth.databasedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("users", Context.MODE_PRIVATE, null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS theNewUsers(name VARCHAR,age INT(3),id INTEGER PRIMARY KEY)");

            myDatabase.execSQL("INSERT INTO theNewUsers VALUES('Srimanth',25)");
            //myDatabase.execSQL("INSERT INTO newUsers VALUES('Srimanth',35,)");
            myDatabase.execSQL("INSERT INTO theNewUsers VALUES('Shanmukha',22)");
            //myDatabase.execSQL("INSERT INTO event VALUES('',2017)");

            Cursor cursor = myDatabase.rawQuery("SELECT * FROM theNewUsers", null);
            //myDatabase.execSQL("DELETE FROM newUsers WHERE name='Srimanth' AND age=35");

            int eventName = cursor.getColumnIndex("name");
            int ageValue = cursor.getColumnIndex("age");
            int idIndex=cursor.getColumnIndex("id");

            cursor.moveToFirst();

            while (cursor != null) {
                Log.i("Name", cursor.getString(eventName));
                Log.i("Age", cursor.getString(ageValue));
                Log.i("IDnumber",cursor.getString(idIndex));

                cursor.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
