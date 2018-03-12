package com.example.srimanth.newsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles=new ArrayList<>();
    ArrayList<String> content=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    SQLiteDatabase articleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleDB=this.openOrCreateDatabase("Articles",MODE_PRIVATE,null);

        articleDB.execSQL("CREATE TABLE IF NOT EXISTS articles(id INTEGER PRIMARY KEY,articleID INTEGER,title VARCHAR,content VARCHAR)");


        ListView listView=findViewById(R.id.listView);

        DownloadTask task=new DownloadTask();
        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ArticleActivity.class);
                intent.putExtra("content",content.get(position));

                startActivity(intent);
            }
        });

        updateListView();

    }
    public void updateListView()
    {
        Cursor c=articleDB.rawQuery("SELECT * FROM articles",null);

        int contentIndex=c.getColumnIndex("content");
        int titleIndex=c.getColumnIndex("title");

        if (c.moveToFirst())
        {
            titles.clear();
            content.clear();
            do {
                titles.add(c.getString(titleIndex));
                content.add(c.getString(contentIndex));
            }while (c.moveToNext());

            arrayAdapter.notifyDataSetChanged();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {

            String result="";
            URL url;
            HttpURLConnection httpURLConnection=null;
            try
            {
                url=new URL(urls[0]);

                httpURLConnection= (HttpURLConnection) url.openConnection();

                InputStream inputStream=httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();

                while(data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=inputStreamReader.read();
                }

                JSONArray jsonArray=new JSONArray(result);
                int numberOfItems=20;

                if (jsonArray.length()<20)
                {
                    numberOfItems=jsonArray.length();
                }

                articleDB.execSQL("DELETE FROM articles");
                for (int i=0;i<numberOfItems;i++)
                {
                    String articleID=jsonArray.getString(i);
                    url=new URL("https://hacker-news.firebaseio.com/v0/item/"+ articleID +".json?print=pretty");

                    httpURLConnection= (HttpURLConnection) url.openConnection();

                    inputStream=httpURLConnection.getInputStream();

                    inputStreamReader=new InputStreamReader(inputStream);

                    data=inputStreamReader.read();

                    String articleInfo="";

                    while(data!=-1)
                    {
                        char current=(char)data;
                        articleInfo+=current;
                        data=inputStreamReader.read();
                    }
                    JSONObject jsonObject=new JSONObject(articleInfo);
                    if (!jsonObject.isNull("title")&& !jsonObject.isNull("url"))
                    {
                        String articleTitle=jsonObject.getString("title");
                        String articleUrl=jsonObject.getString("url");

                        url=new URL(articleUrl);
                        httpURLConnection= (HttpURLConnection) url.openConnection();
                        inputStream=httpURLConnection.getInputStream();
                        inputStreamReader=new InputStreamReader(inputStream);
                        data=inputStreamReader.read();
                        String articleContent="";

                        while(data!=-1)
                        {
                            char current=(char)data;
                            articleContent+=current;
                            data=inputStreamReader.read();
                        }

                        Log.i("HTML",articleContent);

                        String sql="INSERT INTO articles(articleID,title,content) VALUES(?,?,?)";
                        SQLiteStatement sqLiteStatement=articleDB.compileStatement(sql);
                        sqLiteStatement.bindString(1,articleID);
                        sqLiteStatement.bindString(2,articleTitle);
                        sqLiteStatement.bindString(3,articleContent);

                        sqLiteStatement.execute();
                    }
                }

                Log.i("URL Content",result);

                return result;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateListView();
        }
    }
}