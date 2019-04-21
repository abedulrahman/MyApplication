package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.TextView;


import java.io.IOException;


public class Main4Activity extends AppCompatActivity implements SensorEventListener {
private SensorManager mSensorManager;
private Sensor msensor;
    TextView textView;
    float last_x ,last_y,last_z;
    long lastUpdate;
    int Shake_Threshold=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        AcceptThread t=new AcceptThread(mHandler, mHandler);
        t.start();

        textView=findViewById(R.id.sensor);
        mSensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        msensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private final Handler mHandler =new Handler(){

        public void handMessage(Message msg){
            switch (msg.what){
                case Constants.MESSAGE_READ:
                    String x =msg.getData().getString("data");

                    textView.setText(x);
                    break;
            }
        }

    };
boolean run =false;
    @Override
    public void onSensorChanged(SensorEvent event) {

        float x= event.values[0];
        float y=event.values[1];
        float z =event.values[2];



        long curTime=System.currentTimeMillis();


        if ((curTime-lastUpdate)>100){
            long diffTime =(curTime-lastUpdate);
            lastUpdate=curTime;

            float speed=Math.abs(x+y+z-last_x-last_y-last_z)/diffTime*10000;

            if (speed>Shake_Threshold){
                Vibrator v=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                textView.setText(textView.getText()+"News "+speed);

                Uri myuri=Uri.parse("https://a.tumblr.com/tumblr_mo8dtw8Usx1qzyk5ro1.mp3");

                try{
                    MediaPlayer mediaPlayer=new MediaPlayer();
                    mediaPlayer.setDataSource(this,myuri);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (IOException ex){
                    ex.printStackTrace();}

            }

            last_x=x;
            last_y=y;
            last_z=z;



        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,msensor,SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void sends(View view) {
        Intent intent=new Intent(this,ScrollingActivity.class);
        startActivity(intent);
    }
}
