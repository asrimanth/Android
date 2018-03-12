package com.example.srimanth.downloadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;

    public void downloadImage(View v)
    {
        Log.i("Action","The button is tapped");

        ImageDownloader task=new ImageDownloader();

        Bitmap myImage;
        try {
            myImage=task.execute("https://vignette.wikia.nocookie.net/deathbattle/images/2/2b/SSj_God_SSj_Goku.jpg/revision/latest/scale-to-width-down/313?cb=20150709050420").get();

            imageView.setImageBitmap(myImage);
        } catch (Exception e) {

            e.printStackTrace();

        }

        imageView=findViewById(R.id.imageView);

        //https://vignette.wikia.nocookie.net/deathbattle/images/2/2b/SSj_God_SSj_Goku.jpg/revision/latest/scale-to-width-down/313?cb=20150709050420
    }

    class ImageDownloader extends AsyncTask<String,Void,Bitmap>
        // Bitmap is used to return the image itself
    {

        @Override
        protected Bitmap doInBackground(String... urls) {


            try {

                URL url=new URL(urls[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                httpURLConnection.connect();

                InputStream inputStream=httpURLConnection.getInputStream();

                Bitmap myBitmap= BitmapFactory.decodeStream(inputStream);

                return myBitmap;


            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }


            return null;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
