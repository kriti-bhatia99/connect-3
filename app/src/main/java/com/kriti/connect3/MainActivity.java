package com.kriti.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0: yellow  1: red  2: empty
    int activeplayer = 0;
    boolean gameActive = true;
    //Initial state of the board is empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //2-D array of winning rows columns and diagonals
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Method to make the coin drop in when the particular ImageView is clicked
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        //Log.i("Tag", counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activeplayer;

            //Initially putting the coins off the screen
            counter.setTranslationY(-1500);

            //Updating the image according to the active player
            if (activeplayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activeplayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activeplayer = 0;
            }

            //Making the coin drop into place in 300 milliseconds
            counter.animate().translationYBy(1500).setDuration(300);

            //Looping through all the imageViews to check for any wins
            for (int[] winningPosition : winningPositions)
            {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]]
                        == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2)
                {
                    //Someone has won
                    gameActive = false;

                    String winner = "";
                    if (activeplayer == 1) { winner = "Yellow"; }
                    else { winner = "Red"; }

                    //Asking the players if they want to play again
                    Button restartGame = findViewById(R.id.restartButton);
                    TextView winnerTextView = findViewById(R.id.winnerTextView);

                    winnerTextView.setText("Winner is: " +winner+ "!");
                    winnerTextView.setVisibility(View.VISIBLE);
                    restartGame.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void restartGame(View view)
    {
        Button restartGame = findViewById(R.id.restartButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);

        //Making the button and textview invisible again
        winnerTextView.setVisibility(View.INVISIBLE);
        restartGame.setVisibility(View.INVISIBLE);

        //Removing the images resources so they are empty again
        GridLayout myGridView = (GridLayout) findViewById(R.id.myGridView);
        for (int i=0; i<myGridView.getChildCount(); i++)
        {
            ImageView counter = (ImageView) myGridView.getChildAt(i);
            counter.setImageDrawable(null);
        }

        //Setting the game arrays back to their initial state
        activeplayer = 0;
        gameActive = true;

        //Looping through the gameState array to reset the values
        for (int i=0; i<gameState.length; i++)
        {
            gameState[i]=2;
        }
    }
}
