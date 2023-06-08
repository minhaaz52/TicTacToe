package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


//    0->0
//    1->X

    int currentUser=0, totalMoves=0;
    int playerZeroScore=0, playerOneScore=0, draw=0;
    Boolean finished=false;

    // 0->0, 1->X, -1->empty
    int []gameState={-1,-1,-1,-1,-1,-1,-1,-1,-1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void playerTap(View view){
        ImageView img=(ImageView)view;
        int currentCell=Integer.parseInt(img.getTag().toString())-1;

        if (gameState[currentCell]==-1 && !finished){
            gameState[currentCell]=currentUser;
            totalMoves++;
            if (currentUser==0){
                img.setImageResource(R.drawable.o);
            }
            else{
                img.setImageResource(R.drawable.x);
            }

            int row=currentCell/3;
            int col=currentCell%3;
            int cnt1=0, cnt2=0, cnt3=0, cnt4=0;
            TextView res=findViewById(R.id.tvResult);

            for (int i=0; i<3; i++){
                if (gameState[row*3+i]==currentUser)   // checking horizontal match
                    cnt1++;
                if (gameState[i*3+col]==currentUser)   // checking vertical match
                    cnt2++;
                if (gameState[i+3*i]==currentUser)     // checking primary diagonal match
                    cnt3++;
                if (gameState[3*i+3-i-1]==currentUser) // checking secondary diagonal match
                    cnt4++;
            }

            if (cnt1==3 || cnt2==3 || cnt3==3 || cnt4==3){
                String s="Player "+currentUser+" Won!!!";
                res.setText(s);
                finished=true;
                if (currentUser==0) {
                    playerZeroScore++;
                    ((TextView)findViewById(R.id.tvPlayerZero)).setText("Player 0: "+playerZeroScore);
                }
                else {
                    playerOneScore++;
                    ((TextView)findViewById(R.id.tvPlayerOne)).setText("Player 1: "+playerOneScore);
                }
                return;
            }

            if (totalMoves==9){
                String s="Draw";
                res.setText(s);
                finished=true;
                draw++;
                ((TextView)findViewById(R.id.tvDraw)).setText("Draw: "+draw);
                return;
            }
            currentUser=(currentUser+1)%2;
            res.setText("Player "+currentUser+" turn");
//            img.animate().translationYBy(1000f).setDuration(300);
        }
    }

    public void replayGame(View view){
        currentUser=0;
        totalMoves=0;
        finished=false;
        ((TextView)findViewById(R.id.tvResult)).setText("Player 0 turn");
        for (int i=0; i<9; i++){
            int resID = getResources().getIdentifier("img"+i,
                    "id", getPackageName());
            ImageView img=findViewById(resID);
            img.setImageResource(0);
            gameState[i]=-1;
        }
    }

    public void restartGame(View view){
        replayGame(null);
        playerOneScore=0;
        playerZeroScore=0;
        draw=0;
        ((TextView)findViewById(R.id.tvPlayerOne)).setText("Player 1: 0");
        ((TextView)findViewById(R.id.tvPlayerZero)).setText("Player 0: 0");
        ((TextView)findViewById(R.id.tvDraw)).setText("Draw: 0");
    }
}