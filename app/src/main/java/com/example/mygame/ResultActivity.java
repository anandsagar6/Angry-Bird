package com.example.mygame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

       private TextView textViewResultinfo,textViewhighscore,textViewmyscore;
       private Button buttonAgain;
       private int score;
       private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textViewhighscore = findViewById(R.id.textViewHighScore);
        textViewmyscore = findViewById(R.id.textViewMyScore);
        textViewResultinfo = findViewById(R.id.textViewResultinfo);
        buttonAgain = findViewById(R.id.buttonAgain);

        score = getIntent().getIntExtra("score",0);
        textViewmyscore.setText("Your score : "+ score);

        sharedPreferences = this.getSharedPreferences("Score", Context.MODE_PRIVATE);
       int highestScore =  sharedPreferences.getInt("highestScore",0);

        if(score >= 200)
        {
            textViewResultinfo.setText("You won the Game");
            textViewhighscore.setText("Highest Score : "+score);
            sharedPreferences.edit().putInt("highestScore",score).apply();
        }
        else if(score >= highestScore)
        {
            textViewResultinfo.setText("Sorry, you loss the Game");
            textViewhighscore.setText("Highest Score : "+score);
            sharedPreferences.edit().putInt("highestScore",score).apply();
        } else {
            textViewResultinfo.setText("Sorry you loss the Game");
            textViewhighscore.setText("Highest Score : "+highestScore);
        }

        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setTitle("Help The Innocent Bird");
        builder.setMessage("Are you sure you want to quit game");
        builder.setCancelable(false);
        builder.setNegativeButton("Quit game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
               android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
           builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
               }
           });
           builder.create().show();
    }
}