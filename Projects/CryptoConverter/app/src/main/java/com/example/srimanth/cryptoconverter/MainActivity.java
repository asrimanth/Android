package com.example.srimanth.cryptoconverter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    String apiURL;
    JSONObject currencyInfo;
    ArrayList<Integer> imageList;
    JSONArray jsonArray;
    String[] countries = {"USD","AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD", "THB", "TRY", "TWD", "ZAR"};
    String item = "usd";
    Spinner spinner;
    List<DataClass> dataClassList;
    RecyclerView recyclerView;
    CustomRecyclerViewAdapter adapter;
    ArrayAdapter<String> arrayAdapter;

    @SuppressLint("StaticFieldLeak")
    class CurrencyGetTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection urlConnection;
            StringBuilder result = new StringBuilder();

            try
            {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                int data = isr.read();

                while(data != -1)
                {
                    char current = (char) data;
                    result.append(current);
                    data = isr.read();
                }
                //return result;
                jsonArray=new JSONArray(result.toString());

                dataClassList = new ArrayList<>();

                for(int i=0;i<jsonArray.length();i++)
                {
                    currencyInfo=jsonArray.getJSONObject(i);

                    /*
                    *Log.i("Rank",currencyInfo.getString("rank"));
                    *Log.i("Name",currencyInfo.getString("name"));
                    *Log.i("USD",currencyInfo.getString("price_"+item.toLowerCase()));
                    *Log.i("Variation",currencyInfo.getString("percent_change_24h"));
                    */

                    DataClass dataClass=new DataClass();

                    dataClass.setRank(currencyInfo.getString("rank"));
                    dataClass.setNames(currencyInfo.getString("name"));
                    dataClass.setCurrency(currencyInfo.getString("price_"+item.toLowerCase()));
                    dataClass.setVariation(currencyInfo.getString("percent_change_24h"));

                    dataClassList.add(dataClass);

                    //Log.i("the values are",dataClassList.get(i).getNames());

                }

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
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);

        progressBar = findViewById(R.id.myProgressBar);

        apiURL = "https://api.coinmarketcap.com/v1/ticker/?limit=10";

        CurrencyGetTask task = new CurrencyGetTask();
        try {
            task.execute(apiURL).get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        imageList=new ArrayList<>();

        imageList.add(R.drawable.bitcoin);
        imageList.add(R.drawable.ethereum);
        imageList.add(R.drawable.ripple);
        imageList.add(R.drawable.bitcoincash);
        imageList.add(R.drawable.cardano);
        imageList.add(R.drawable.litecoin);
        imageList.add(R.drawable.stellar);
        imageList.add(R.drawable.neo);
        imageList.add(R.drawable.eos);
        imageList.add(R.drawable.nem);

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,countries);
        spinner.setAdapter(arrayAdapter);

        recyclerView = findViewById(R.id.itemsRecyclerView);

        adapter = new CustomRecyclerViewAdapter(this,dataClassList,imageList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
