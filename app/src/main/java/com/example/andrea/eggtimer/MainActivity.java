package com.example.andrea.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

   SeekBar timerSeekBar;
   TextView timerTextView;
   Button controllerButton;
   Boolean counterIsActive = false;
   CountDownTimer countDownTimer;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      controllerButton = (Button) findViewById(R.id.controllerButton);
      timerSeekBar = (SeekBar) findViewById(R.id.TimerSeekBar);
      timerSeekBar.setMax(300);
      timerSeekBar.setProgress(30);
      timerTextView = (TextView)findViewById(R.id.timerTextView);

      timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateTimer(progress);
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {}

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {}
      });

   }

   private void updateTimer(int secondsLeft)
   {
      int minutes = secondsLeft / 60;
      int seconds = secondsLeft - minutes * 60;
      String secondString = Integer.toString(seconds);
      if (seconds < 10){
         secondString = "0" + secondString ;
      }
      String time = Integer.toString(minutes) + ":" + secondString;
      timerTextView.setText(time);
   }

   public void controlTimer(View view)
   {
      if (!counterIsActive)
      {
         counterIsActive = true;
         timerSeekBar.setEnabled(false);
         controllerButton.setText(R.string.stop);
         countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
               updateTimer((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
               timerTextView.setText(R.string.timerDone);
               MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.foghorn);
               mediaPlayer.start();
               resetTimer();
            }
         };
         countDownTimer.start();
      }
      else
      {
         timerTextView.setText("0:30");
         timerSeekBar.setProgress(30);
         resetTimer();
      }
   }

   private void resetTimer()
   {
      countDownTimer.cancel();
      controllerButton.setText("Go");
      timerSeekBar.setEnabled(true);
      counterIsActive = false;
   }

}
