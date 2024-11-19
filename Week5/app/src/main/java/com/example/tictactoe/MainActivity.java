package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";
    boolean player1_Turn = true;
    byte[][] board = new byte[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = findViewById(R.id.board);
        for (int i=0;i<3;i++){
            TableRow row = (TableRow)table.getChildAt(i);
            for (int j=0;j<3;j++){
                Button btn =(Button)row.getChildAt(j);
                btn.setOnClickListener(new CellListener(i , j));
            }
        }
    }

    class CellListener implements View.OnClickListener{
        int row,col;

        public CellListener(int row,int col){
            this.row=row;
            this.col=col;
        }


        @Override
        public void onClick(View v) {
            if(!isValidMove(row,col)){
                Toast.makeText(MainActivity.this,
                        "Cell is already occupied.",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if(player1_Turn){
                ((Button)v).setText(PLAYER_1);
                board[row][col]=1;
            }
            else{
                ((Button)v).setText(PLAYER_2);
                board[row][col]=2;
            }
            if (gameEnded(row,col) == -1) {

                player1_Turn =! player1_Turn;

            }else if (gameEnded(row ,col) == 0){
                Toast.makeText(MainActivity.this,
                            "It is a Draw.",
                            Toast.LENGTH_LONG).show();
            }
            else if (gameEnded(row,col) == 1){
                    Toast.makeText(MainActivity.this,
                            "Player1 Wins.",
                            Toast.LENGTH_LONG).show();
            }
            else{
                    Toast.makeText(MainActivity.this,
                            "Player2 Wins.",
                            Toast.LENGTH_LONG).show();
            }


        }

    }

    public boolean isValidMove(int row ,int col){
        return board[row][col] == 0;
    }

    public int gameEnded(int row , int col){
        int symbol = board[row][col];
        boolean win = true;
        for(int i=0;i<3;i++){
            if(board[i][col] != symbol){
               win= false;
               break;
            }
        }
        if(win){
            return symbol;
        }
        

        return -1;
    }
}