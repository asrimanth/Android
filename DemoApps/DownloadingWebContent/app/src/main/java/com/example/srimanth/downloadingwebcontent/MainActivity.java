package com.example.srimanth.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task=new DownloadTask();
        String result= null;

        try {
            result = task.execute("http://www.posh24.se/kandisar").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        Log.i("Contents of URL",result);
    }


    class DownloadTask extends AsyncTask<String,Void,String>
        //A way of running the program in a different thread
            /*First param in the generic type is String which instructs i.e."What to do"
            * 2nd attribute is the name of the method that is used to show the progress of the task
            * 3rd variable is the type of the variable returned by the task*/
    {
        @Override
        protected String doInBackground(String... urls)
            /*This is a background thread
            * String... is var args[] similar to arrays*/
        {

            String result="";

            URL url;

            HttpURLConnection urlConnection=null;
            // A bit like a browser to fetch the contents of the URL
            try
            {

                url=new URL(urls[0]);

                urlConnection=(HttpURLConnection)url.openConnection();
                //Connection is set up

                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();

                while (data!=-1)
                {
                    char current=(char) data;

                    result+=current;

                    data=inputStreamReader.read();
                }

                return result;

            }
            catch (Exception e)
            {
                e.printStackTrace();

                return "Failed";
            }

            //Log.i("The URL is",strings[0]);


        }
    }

}
