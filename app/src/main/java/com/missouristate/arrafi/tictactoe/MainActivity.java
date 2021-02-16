package com.missouristate.arrafi.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];//3 row and 3 column 2D array
    private boolean player1Turn = true;//As soon we start the game player one will start
    private int player1Points;//stores total points on p1 and p2
    private int player2Points;
    private int roundCount;//this will count for the fields, a game will have 9 rounds
    private TextView textViewP1;//Display player one
    private TextView textViewP2;//Display player two


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assinging varriable for text view
        textViewP1 = findViewById(R.id.text_view_p1);
        textViewP2 = findViewById(R.id.text_view_p2);
        //Rather then assinging one by one, using for loop to assing all buttons
        for (int i = 0; i < 3; i++)//We want to go 3 rounds, we increment i with 3 rounds
            {
            for (int j = 0; j < 3; j++)
            {
                //with this nested loop, we will go through all cols and rows
                String buttonID = "button_" + i + j;//getting ID dynamically so it will loop through all buttonIds,this will
                // start at button_00 then 01,02 and so on which is set in the xml layout
                //int resID is to find.id. is created dynamically
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                //now [i][j] will go through button IDs on [0][0][0][1] and so on
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);// Also every time a player selects we setOnClickListener and its implimented to main Activity
            }
        }
        //Button reset and button.setOnclickListener(new Viwe.onClick)
        Button buttonReset = findViewById(R.id.button_reset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }


        });
    }

    //Is called when ever a button is clicked
    @Override
    public void onClick(View v) {
        //Checks for if the button contains an empty sting, to see if its already used or not

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        //if it containts an empty string then its player one turns
        // the string will be X or else it will be 0
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;//so we know one more round is over!!
        if (checkForWin())//This method will check if someone has won or not!!
        {
            if (player1Turn) {
                player1Wins();//this method checks if player one won the game on the current turn
            } else {
                player2Wins();
            }
        } else if (roundCount == 9)//one game has 9 rounds so at 9th round we can
            //declare the game as draw.
        {
            draw();
        } else {
            player1Turn = !player1Turn;
            //this activates the turn for player2
        }
    }
//Below is the method checkForWin() I wrote which checks if the
// game finished in a win or not.
    private boolean checkForWin() {
        //we will go through all my rows column and diagonals,
        String[][] field = new String[3][3];// same as button array so we can get the button text to string
        for (int i = 0; i < 3; i++)
        //this is my i loop
        {
            for (int j = 0; j < 3; j++)
            //this is my j loop
            {
                //field[i][j] so the buttons are in the same position and
                //then we put them into an string array
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        //outside the nested loop String array to go trough all rows and column
        for (int i = 0; i < 3; i++) {
            //This loop goes through the rows and column
            /* Below is the representation of my 2D array
            Col_1   Col_2   Col_3
            [0][0]  [0][1]  [0][2]row 1
            [1][0]  [1][1]  [1][2] row 2
            [2][0]  [2][1]  [2][2] row 3
             */
            //Checks if the ROW 1 or 2 or 3 Strings are equal or not
            if (field[i][0].equals(field[i][1])
                    //this is responsible to see if the 3 string side by side is the same or not
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals(""))//this part check if its just 3 empty string or not
            {
                return true;

            }
        }
        //
        for (int i = 0; i < 3; i++)
        {
            //this loop check on Col_1,2&3 are same or not
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;

            }
        }
        //Below to if statement checks on the diagonal buttons for winner
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        //dosnt return a winner if all check list fails
        return false;
    }

    private void player1Wins() {
        //adds up the player one total points and displays it with a toast msg
        //then it calls for updatePointsText()method and resetBoard()method
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        //dos'nt add any points to either player one or two simply
        // shows Toast msg as Draw and uses resetBoard()method to reset
        // the board for new game
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }


    private void updatePointsText() {
        //text view with updated points
        textViewP1.setText("Player 1: " + player1Points);
        textViewP2.setText("Player 2: " + player2Points);
    }

    private void player2Wins() {
        //adds up the player two total points and displays it with a toast msg
        //then it calls for updatePointsText()method and resetBoard()method
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void resetBoard() {
        //this method sets all button string to empty string. and sets rounds to 0 and makes the turn = p1
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        //This will reset the whole game with each player points stats with 0
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}








