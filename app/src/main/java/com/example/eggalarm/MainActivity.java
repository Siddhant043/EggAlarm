package com.example.eggalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Boolean counterActive = false;
    CountDownTimer countDownTimer;

    Button button;

    public void resetTimer(){ // timer reset function, set everything to initial value
        textView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        button.setText("Go!");
        countDownTimer.cancel();
        counterActive = false;
    }

    public void buttonClicked(View view){ // here we create a countdowntimer and set it to the seekbar value
        if(counterActive){
            resetTimer(); 
        }else {
            counterActive = true;
            seekBar.setEnabled(false);
            button.setText("Stop!");
            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000 + 200, 1000) { // adding 200 to come over runtime lag
                @Override
                public void onTick(long millisUntilFinished) {
                    timerUpdate((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mp.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void timerUpdate(int secondsLeft){ // this function takes any number and converts it into minutes and seconds and show in the textview
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        String secondsString = Integer.toString(seconds);
        if(seconds <= 9){
            secondsString = "0" + secondsString;
        }
        textView.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        seekBar.setMax(600); //here we set seekbar max value to 10mins
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { // this function gives the progress seekbar value to the timerupdate function to print
                timerUpdate(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}