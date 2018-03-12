package com.example.srimanth.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button button0,button1,button2,button3;

    Bitmap celebImage;

    ArrayList<String>celebURLs=new ArrayList<String>();
    ArrayList<String>celebNames=new ArrayList<String>();
    int chosenCeleb=0;
    ImageView imageView;

    int locationOfCorrectAnswer;
    String[] answers=new String[4];

    ImageDownloader imageTask;

    class ImageDownloader extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... urls) {

            Bitmap picture;
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url=new URL(urls[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                picture= BitmapFactory.decodeStream(inputStream);
                return picture;


            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }

    class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {

            String result="";

            URL url;

            HttpURLConnection httpURLConnection=null;

            try {

                url=new URL(urls[0]);

                httpURLConnection=(HttpURLConnection)url.openConnection();

                httpURLConnection.connect();

                InputStream inputStream=httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();

                while(data!=-1)
                {
                    char current=(char) data;
                    result += current;
                    data=inputStreamReader.read();
                }

                return  result;

            }
            catch (Exception e)
            {
                e.printStackTrace();

                return "failed";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0=findViewById(R.id.button0);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);


        DownloadTask task=new DownloadTask();


        celebImage=null;

        imageView=findViewById(R.id.celebrityImageView);

        String result=null;
        try
        {
            result=task.execute("http://www.posh24.se/kandisar").get();

            String[] splitResult=result.split("<div class=\"sidebarContainer\">");

            Pattern p=Pattern.compile("<img src=\"(.*?)\"");
            Matcher m=p.matcher(splitResult[0]);

            while(m.find())
            {
                celebURLs.add(m.group(1));
            }
            p=Pattern.compile("alt=\"(.*?)\"");
            m=p.matcher(splitResult[0]);

            while(m.find())
            {
                celebNames.add(m.group(1));
            }


            /*Random random=new Random();

            chosenCeleb=random.nextInt(celebURLs.size());

            celebImage=imageTask.execute(celebURLs.get(chosenCeleb)).get();

            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer=random.nextInt(4);

            int incorrectAnswerLocation;

            for(int i=0;i<4;i++)
            {

                if(i==locationOfCorrectAnswer)
                {
                    answers[i]=celebNames.get(chosenCeleb);
                }
                else
                {
                    incorrectAnswerLocation=random.nextInt(celebURLs.size());

                    while (incorrectAnswerLocation==chosenCeleb)
                    {
                        incorrectAnswerLocation=random.nextInt(celebURLs.size());
                    }

                    answers[i]=celebNames.get(incorrectAnswerLocation);
                }

            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);*/

            generateQuestion();


            //Log.i("The Content is : ",result);

        }
        catch (InterruptedException e) {

            e.printStackTrace();

        }
        catch (ExecutionException e) {

            e.printStackTrace();

        }
    }

    public void celebChosen(View v)
    {
        if(v.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer)))
        {
            Toast.makeText(this, "The answer is correct!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Wrong! The answer was "+celebNames.get(chosenCeleb), Toast.LENGTH_SHORT).show();
        }
        generateQuestion();
    }

    public void generateQuestion()
    {

        Random random=new Random();

        chosenCeleb=random.nextInt(celebURLs.size());

        try {

            imageTask=new ImageDownloader();
            
            celebImage=imageTask.execute(celebURLs.get(chosenCeleb)).get();

            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer=random.nextInt(4);

            int incorrectAnswerLocation;

            for(int i=0;i<4;i++)
            {

                if(i==locationOfCorrectAnswer)
                {
                    answers[i]=celebNames.get(chosenCeleb);
                }
                else
                {
                    incorrectAnswerLocation=random.nextInt(celebURLs.size());

                    while (incorrectAnswerLocation==chosenCeleb)
                    {
                        incorrectAnswerLocation=random.nextInt(celebURLs.size());
                    }

                    answers[i]=celebNames.get(incorrectAnswerLocation);
                }

            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


}
