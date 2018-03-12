package com.example.srimanth.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //0=x ; 1=o;
    int activePlayer=0;

    boolean gameIsActive=true;

    //2 means not played yet
    int[] gameState={2,2,2,2,2,2,2,2,2};

    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    public void dropIn(View v)
    {
        ImageView imageView= (ImageView) v;
        imageView.setScaleX(0f);
        imageView.setScaleY(0f);


        int tappedButton = Integer.parseInt(imageView.getTag().toString());

        if(gameState[tappedButton]==2 && gameIsActive) {

            gameState[tappedButton]=activePlayer;
            //imageView.setEnabled(false);

            if (activePlayer == 0) {
                imageView.setImageResource(R.drawable.x);

                activePlayer = 1;
            } else {
                imageView.setImageResource(R.drawable.o);

                activePlayer = 0;
            }

            imageView.animate().scaleX(1f).scaleY(1f).setDuration(400);

            for (int[] winningPosition : winningPositions)
            {
                if (gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]]==gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]]!=2)
                {
                    gameIsActive=false;

                    //imageView.setEnabled(true);

                    System.out.println(gameState[winningPosition[0]]);

                    String message="O";

                    TextView winnerMessage=(TextView)findViewById(R.id.winnerMessage);

                    if(gameState[winningPosition[0]]==0)
                    {
                        message="X";
                    }


                    winnerMessage.setText(message+" is the winner!");

                    LinearLayout layout=(LinearLayout) findViewById(R.id.askBox);
                    layout.setVisibility(View.VISIBLE);
                    layout.animate().alpha(1f).setDuration(400);

                }
                else
                {
                    boolean gameOver=true;
                    for (int counterState : gameState)
                    {
                        if(counterState==2)
                        {
                            gameOver=false;
                        }
                    }
                    if (gameOver)
                    {

                        TextView winnerMessage=(TextView)findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw!");

                        LinearLayout layout=(LinearLayout) findViewById(R.id.askBox);
                        layout.setVisibility(View.VISIBLE);
                        layout.animate().alpha(1f).setDuration(400);
                    }
                }
            }
        }
    }
    public void playAgain(View v)
    {
        LinearLayout layout=(LinearLayout) findViewById(R.id.askBox);
        layout.setVisibility(View.INVISIBLE);

        gameIsActive=true;

        activePlayer=0;
        for(int i=0;i<gameState.length;i++)
        {
            gameState[i]=2;
        }
        GridLayout gridLayout=(GridLayout)findViewById(R.id.gridLayout);
        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
