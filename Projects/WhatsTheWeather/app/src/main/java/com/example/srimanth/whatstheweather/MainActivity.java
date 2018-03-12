package com.example.srimanth.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText cityName;

    public void findWeather(View v)
    {
        DownloadTask task=new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=Mumbai");

        //hide Keyboard as follows
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(cityName.getWindowToken(),0);

        Log.i("The Location",cityName.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName=findViewById(R.id.locationEditText);

    }

    class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {

            String result="";

            URL url;

            HttpURLConnection httpURLConnection=null;

            try
            {

                url=new URL(urls[0]);

                httpURLConnection=(HttpURLConnection) url.openConnection();

                InputStream inputStream=httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();

                while (data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=inputStreamReader.read();
                }

                return result;

            }
            catch (Exception e)
            {
                e.printStackTrace();

                return "failed";//null;
            }


        }

        @Override
        protected void onPostExecute(String result) {
            //Called when the doInBackground() method is completed and it will parse whatever we return i.e. result
            //doInBackground method does not update the UI ayt all but this method does

            super.onPostExecute(result);

            try {

                JSONObject jsonObject=new JSONObject(result);

                String weatherInfo=jsonObject.getString("weather");

                Log.i("Website content",weatherInfo);

                JSONArray array=new JSONArray(weatherInfo);

                for (int i=0;i<array.length();i++)
                {
                    JSONObject jsonPart=array.getJSONObject(i);

                    Log.i("main",jsonPart.getString("main"));

                    Log.i("Description",jsonPart.getString("description"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
